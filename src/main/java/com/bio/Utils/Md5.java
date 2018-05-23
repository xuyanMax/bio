package com.bio.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    /**
     * 生成32位md5码
     * 对身份证号进行加密
     * @param ID_code
     * @return
     */

    public static String md5(String ID_code){
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(ID_code.getBytes());
            StringBuilder builder = new StringBuilder();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    builder.append("0");
                }
                builder.append(str);
            }
            // 标准的md5加密后的结果
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
