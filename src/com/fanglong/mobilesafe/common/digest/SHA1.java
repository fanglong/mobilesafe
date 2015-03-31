package com.fanglong.mobilesafe.common.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fanglong.mobilesafe.common.log.Logger;

public class SHA1 {

    public static String sha1(String str, String charsetName) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
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

    public static String sha1(String str) {
        return sha1(str, "UTF-8");
    }

}
