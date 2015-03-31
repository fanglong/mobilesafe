package com.fanglong.mobilesafe.common.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fanglong.mobilesafe.common.log.Logger;


public class MD5 {
	public static String md5(String str, String charsetName) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			final byte data[] = str.getBytes(charsetName);
			md.update(data, 0, data.length);
			final byte[] sha1hash = md.digest();
			return Hex.bytesToHexString(sha1hash);
		} catch (UnsupportedEncodingException e) {
			Logger.error(e);
		} catch (NoSuchAlgorithmException e) {
			Logger.error(e);
		}
		return null;
	}

	public static String md5(String str) {
		return md5(str, "UTF-8");
	}

	/**
	 * 文件md5码
	 * 
	 * @author fenglei
	 * */
	public static String MD5DigestFile(File file) {
		String result = "";
		FileInputStream fis = null;
		try {
			MessageDigest alga = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length;
			while ((length = fis.read(buffer)) != -1) {
				alga.update(buffer, 0, length);
			}
			byte[] digest = alga.digest();
			result = Hex.bytesToHexString(digest);
			alga.reset();
		} catch (FileNotFoundException e) {
			Logger.error("md5 file " + file.getAbsolutePath() + " failed:" + e.getMessage());
		} catch (IOException e) {
			Logger.error("md5 file " + file.getAbsolutePath() + " failed:" + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			Logger.error(e.getMessage());
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
		return result;
	}

}
