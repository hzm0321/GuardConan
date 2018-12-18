package cn.hzmeurasia.guardconan.utils;

import java.io.File;
import java.io.FileInputStream;
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

    /**
     * 获取文件的md5值，
     * @param path 文件的路径
     * @return null文件不存在
     */
    public static String getFileMd5(String path ){
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer,0,len);
            }
            byte[] result = digest.digest();
            StringBuilder sb  =new StringBuilder();
            for(byte b : result){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length()==1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
