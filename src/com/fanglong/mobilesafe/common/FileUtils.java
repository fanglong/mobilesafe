package com.fanglong.mobilesafe.common;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import android.os.Environment;

public class FileUtils {

	public static boolean storageIsWritable() {
		boolean externalStorageAvailable = false;
		boolean externalStorageWritable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			externalStorageAvailable = externalStorageWritable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			externalStorageAvailable = true;
			externalStorageWritable = false;
		} else {
			externalStorageAvailable = externalStorageWritable = false;
		}
		return externalStorageAvailable && externalStorageWritable;
	}

	public static boolean makeDirUnderExternalStorage(String relativePath) {
		String externalStorageDir = getExternalStorageDir();
		String logDirectory = combineFilePath(externalStorageDir, relativePath);
		File newDir = new File(logDirectory);
		if (newDir.exists()) {
			return true;
		}
		return newDir.mkdirs();
	}

	public static String combineFilePath(String dirPath, String fileName) {
		if (StringUtils.isEmptyOrNull(dirPath)) {
			return "";
		}
		if (StringUtils.isEmptyOrNull(fileName)) {
			return "";
		}
		String filePath;
		if (dirPath.endsWith(File.separator)) {
			filePath = dirPath;
		} else {
			filePath = dirPath + File.separator;
		}
		if (fileName.startsWith(File.separator)) {
			filePath += fileName.substring(1);
		} else {
			filePath += fileName;
		}
		return filePath;
	}

	public static String getExternalStorageDir() {
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		return externalStorageDirectory.getAbsolutePath();
	}

	public static final String FILE_BINARY_MIME = "application/octet-stream";

	private static Map<String, String> FILE_MIMES = new HashMap<String, String>();

	static {
		FILE_MIMES.put(".bmp", "image/bmp");
		FILE_MIMES.put(".gif", "image/gif");
		FILE_MIMES.put(".jpe", "image/jpeg");
		FILE_MIMES.put(".jpeg", "image/jpeg");
		FILE_MIMES.put(".jpg", "image/jpeg");
		FILE_MIMES.put(".png", "image/png");
		FILE_MIMES.put(".speex", "audio/speex");
		FILE_MIMES.put(".spx", "audio/speex");
		FILE_MIMES.put(".zip", "application/zip");
	}

	public static String getFileMime(String fileName) {
		Set<String> keys = FILE_MIMES.keySet();
		for (String key : keys) {
			if (fileName.toLowerCase(Locale.getDefault()).endsWith(key)) {
				return FILE_MIMES.get(key);
			}
		}
		return "*/*";
	}

	public static String getName(String filename) {
		if (filename == null) {
			return null;
		}
		int index = indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}

	private static final char UNIX_SEPARATOR = '/';
	private static final char WINDOWS_SEPARATOR = '\\';

	public static int indexOfLastSeparator(String filename) {
		if (filename == null) {
			return -1;
		}
		int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
		int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}
}
