package com.fanglong.mobilesafe.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fanglong.mobilesafe.common.log.Logger;


public class StringUtils {

    public static boolean isEmptyOrNull(final String str) {
        return str == null || str.length() <= 0 || str.trim().length()<=0;
    }

    public static boolean isEqualCaseInsensitive(String s1, String s2) {
        return isEqual(s1, s2, true);
    }

    public static boolean isEqualCaseSensitive(String s1, String s2) {
        return isEqual(s1, s2, false);
    }

    private static boolean isEqual(String s1, String s2, boolean ignoreCase) {
        if (s1 != null && s2 != null) {
            if (ignoreCase) {
                return s1.equalsIgnoreCase(s2);
            } else {
                return s1.equals(s2);
            }
        } else {
            return ((s1 == null && s2 == null) ? true : false);
        }
    }

    public static String inputStreamToString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        }
        return sb.toString();
    }

	public static boolean isPhone(String phone) {
		if (isEmptyOrNull(phone)){
			return false;
		}
		if (phone.matches("\\d{11}")){
			return true;
		}
		return false;
	}
}
