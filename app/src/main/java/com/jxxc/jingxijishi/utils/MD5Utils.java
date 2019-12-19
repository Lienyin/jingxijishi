package com.jxxc.jingxijishi.utils;


import java.security.MessageDigest;

/**
 * @author feisher
 */
public class MD5Utils {
    public static final String SALT = "89632E9%^&*67869A26D18FC76"; //标准协议上没有*
    static final String PASSWORD_DELTA_1 = SALT;

    public static String md5Password(String src) {
        return md5(PASSWORD_DELTA_1 + sha(PASSWORD_DELTA_1 + src));
    }

    public static String sha(String src) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA");
            byte[] a = md.digest(src.getBytes("utf-8"));
            return byteToHex(a);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static String md5(String src) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] a = md.digest(src.getBytes("utf-8"));
            return byteToHex(a);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static String byteToHex(byte[] a) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0)
                buf.append(Integer.toHexString(a[i] & 0xff));
            else if (a[i] < 16) {
                buf.append('0');
                buf.append(Integer.toHexString(a[i]));
            } else {
                buf.append(Integer.toHexString(a[i]));
            }
        }

        return buf.toString();
    }

}