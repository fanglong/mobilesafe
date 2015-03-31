package com.fanglong.mobilesafe.common.notification;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.fanglong.mobilesafe.common.log.Logger;
import com.fanglong.mobilesafe.common.task.Task;
import com.fanglong.mobilesafe.common.task.TaskUtils;

import android.content.Intent;
/**
 * 通知中心
 * @author apple
 *
 */
public class NotificationCenter {
	
	public static final NotificationCenter gCneter = new NotificationCenter();
	
	 private ConcurrentHashMap<String, NotificationHandlers> notificationHandlersMap =
	            new ConcurrentHashMap<String, NotificationHandlers>();
	
	public static NotificationCenter getInstance() {
		return gCneter;
	}
	
	public static void scanHandlers (Object handler) {
		getInstance().addObserver(handler);
	}
	
	public static void clearHandlers (Object handler){
		getInstance().removeObserver(handler);
	}
	
	public static void post(final String notificationName , final Intent intent) {
		getInstance().postNotification(notificationName,intent);
	}
	

	/**
	 * 添加监听者
	 * @param handler
	 */
	public void addObserver(Object object) {
		// 处理注解
		for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(NotificationHandler.class)) {
                NotificationHandler handler = method.getAnnotation(NotificationHandler.class);
                if (handler != null && handler.name().length() > 0) {
                    if (notificationHandlersMap.contains(handler.name())) {
                        notificationHandlersMap.get(handler.name()).addHandler(object, method);
                    } else {
                        NotificationHandlers handlers = new NotificationHandlers();
                        NotificationHandlers oldHandlers = notificationHandlersMap.putIfAbsent(handler.name(), handlers);
                        if (oldHandlers != null) {
                            oldHandlers.addHandler(object, method);
                        } else {
                            handlers.addHandler(object,  method);
                        }
                    }
                }
            }
        }
	}
	
	/**
	 * 移除监听者
	 * @param object
	 */
	private void removeObserver(Object object) {
		for (NotificationHandlers handlers : notificationHandlersMap.values()) {
            handlers.removeHandler(object);
        }
	}
	
	/**
	 * 发送通知提醒
	 * @param notificationName
	 * @param notification
	 */
	public void postNotification(final String notificationName, final Intent notification) {
		final NotificationHandlers handlers = notificationHandlersMap.get(notificationName);
		// 获取通知者
		if (handlers == null){
			return;
		}
		TaskUtils.getMainExecutor().post(new Task() {
			@Override
			public void run() throws Exception {
				handlers.postNitification(notification);;
			}
		});
	}
	
	private static class HandlerPair {

        private final WeakReference<Object> handler;
        private final Method method;

        public HandlerPair(Object handler, Method method) {
            this.handler = new WeakReference<Object>(handler);
            this.method = method;
        }

        public Object getHandler() {
            return handler.get();
        }

        public Method getMethod() {
            return method;
        }
    }
	
	private static class NotificationHandlers {
		
		private final List<HandlerPair> handlers = new ArrayList<NotificationCenter.HandlerPair>();
		
		public void addHandler(Object handler , Method method){
			synchronized (handlers) {
				handlers.add(new HandlerPair(handler, method));
			}
		}
		
		public void removeHandler (Object handler) {
			synchronized (handlers) {
				List<HandlerPair> toBeRemoved = new ArrayList<NotificationCenter.HandlerPair>();
				for (HandlerPair pair : handlers){
					if (pair.getHandler() == handler){
						toBeRemoved.add(pair);
					}
				}
				handlers.removeAll(toBeRemoved);
			}
		}
		
		public void postNitification(final Intent notification){
			List<HandlerPair> validPairs = new ArrayList<NotificationCenter.HandlerPair>();
			// 清空废弃数据
			synchronized (handlers) {
				List<HandlerPair> toBeRemoved = new ArrayList<NotificationCenter.HandlerPair>();
				for (HandlerPair pair : handlers){
					Object handler = pair.getHandler();
					if (handler == null) {
						toBeRemoved.add(pair);
					} else {
						validPairs.add(pair);
					}
				}
				handlers.removeAll(toBeRemoved);
			}
			//便利有效
			for (HandlerPair pair : validPairs){
				try{
					// 设置方法 访问级别
					if (!pair.getMethod().isAccessible()){
						pair.getMethod().setAccessible(true);
					}
					Object handler = pair.getHandler();
					if (handler != null) {
						// 验证处理方法 是否带有参数
						if (pair.getMethod().getParameterTypes().length != 1){
							//处理
						} else {
							pair.getMethod().invoke(handler, notification);
						}
					}
				} catch (IllegalAccessException e) {
					Logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					Logger.error(e.getMessage());
				}
			}
		}
	}
}
