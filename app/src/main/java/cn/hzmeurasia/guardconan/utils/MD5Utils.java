package cn.hzmeurasia.guardconan.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类名: MD5Utils<br>
 * 功能:(MD5加密工具类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/14 10:34
 */
public class MD5Utils {
    public static String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                // 获取低8位内容
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);

                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }

                sb.append(hexString);
            }

            String md5 = sb.toString();
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
