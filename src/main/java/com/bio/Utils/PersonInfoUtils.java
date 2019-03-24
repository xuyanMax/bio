package com.bio.Utils;

import javax.persistence.Id;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Optional;

public class PersonInfoUtils {
    /**
     * 生成32位md5码
     * 对身份证号进行加密
     *
     * @param ID_code
     * @return
     */

    //获取队列成员信息的- md5(ID_code)
    public static String md5(String ID_code) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //input converted to bytes
            byte[] bytes = ID_code.getBytes();
            md.update(bytes);
            //md5 hash and convert back to bytes
            byte[] res = md.digest();

            return byteArrayToHex(res);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    // convert md5 bytes back to HEXADECIMAL
    // refer: https://blog.csdn.net/xiao__gui/article/details/8148203
    public static String byteArrayToHex(byte[] bytes) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] res = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            res[index++] = hexDigits[b >>> 4 & 0xf];
            res[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(res);
    }

    //获取队列成员信息的-姓
    public static String lastName(Optional<String> name) {
        String res = name.orElse("Unknown last name");
        // "Zhang san"->"Zhang"
        // 张三->张
        return checkChineseName(res) ? String.valueOf(res.charAt(0)) : res.substring(0, res.indexOf(" ", 0));
    }

    //判断姓名是否包含中文，
    // false: 包含
    public static boolean checkChineseName(String name) {
        return name.getBytes().length == name.length();
    }

    //从ID中获取性别
    public static String getGender(String ID_code) {
        int size = ID_code.length();
        if (size == 0) return null;
        switch (size) {
            case 15:
                return Integer.valueOf(ID_code.charAt(14)) % 2 == 0 ? "女" : "男";
            case 18:
                return Integer.valueOf(ID_code.charAt(16)) % 2 == 0 ? "女" : "男";
        }

        return null;
    }

    // 默认18位身份证
    // @return 1: 男, 2: 女
    public static int getAge(String ID_code) {
        int size = ID_code.length();
        //报错
        if (ID_code == null || size == 0) return -1;
        return getAgeHelper(ID_code.substring(6, 13), 8);
    }

    //合并处理15位/18位身份证号，计算年龄的问题
    public static int getAgeHelper(String birth, int size) {
        Calendar calendar = Calendar.getInstance();
        int res = calendar.get(Calendar.YEAR) - Integer.valueOf(birth.substring(0, 4));
        int monDiff = calendar.get(Calendar.MONTH) + 1 - Integer.valueOf(birth.substring(4, 6));
        if (monDiff < 0)
            res--;
        else if (monDiff == 0)
            res += calendar.get(Calendar.DAY_OF_MONTH) - Integer.valueOf(birth.substring(6)) >= 0 ? 0 : -1;
        return res;
    }

    public static int relative(String relative) {
        return !relative.equals("参与人") ? 0 : 1;
    }

    public static String getBirth(String ID_code) {
        return ID_code.substring(6, 12);
    }

}
