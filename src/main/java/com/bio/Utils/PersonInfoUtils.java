package com.bio.Utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

public class PersonInfoUtils {
    /**
     * 生成32位md5码
     * 对身份证号进行加密
     * @param ID_code
     * @return
     */

    //获取队列成员信息的- md5(ID_code)
    public static String md5(String ID_code){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //input converted to bytes
            byte[] bytes = ID_code.getBytes();
            md.update(bytes);
            //md5 hash and convert back to bytes
            byte[] res = md.digest();

            //
            return byteArrayToHex(res);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    // convert md5 bytes back to HEXADECIMAL
    // refer: https://blog.csdn.net/xiao__gui/article/details/8148203
    public static String byteArrayToHex(byte[] bytes){
// 首先初始化一个字符数组，用来存放每个16进制字符

        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] res =new char[bytes.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;

        for (byte b:bytes) {
            res[index++] = hexDigits[b>>> 4 & 0xf];
            res[index++] = hexDigits[b& 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(res);
    }
    //获取队列成员信息的-姓
    public static String lastName(Optional<String> name){
        String res = name.orElse("Unknown last name");
        // "Zhang san"->"Zhang"
        // 张三->张
        return checkChineseName(res)?String.valueOf(res.charAt(0)):res.substring(0, res.indexOf(" ", 0));
    }
    //判断姓名是否包含中文，
    // false: 包含
    public static boolean checkChineseName(String name){
        return name.getBytes().length == name.length();
    }


    //身份证号计算: https://blog.csdn.net/dabing69221/article/details/9150819
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }
    public static boolean isID_code(String ID_code){
        return ID_code == null || "".equals(ID_code) ? false : Pattern.matches(
                "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", ID_code);
    }

    //从ID中获取性别
    public static String getGender(String ID_code){
        int size = ID_code.length();
        //报错
        if (size == 0) return null;
        switch (size){
            case 15: return String.valueOf(ID_code.charAt(14));
            case 18: return String.valueOf(ID_code.charAt(16));
        }

        return null;
    }
    //利用ID计算生日
    // 默认18位身份证
    // @return 1: 男, 2: 女
    public static int getAge(String ID_code){
        int size = ID_code.length();
        //报错
        if (ID_code == null || size == 0) return -1;
        /*switch (size){
            case 15:return getAgeHelper(ID_code.substring(6,11), 6);
            case 18:return getAgeHelper(ID_code.substring(6,13), 8);
        }*/
        return getAgeHelper(ID_code.substring(6,13), 8);
    }
    // 年龄范围: 1918年到2018年
    // 通过年，月，日与当前时间做对比，计算实际年龄

    //合并处理15位/18位身份证号，计算年龄的问题
    public static int getAgeHelper(String birth, int size){
        Calendar calendar = Calendar.getInstance();
        int res = calendar.get(Calendar.YEAR) - Integer.valueOf(birth.substring(0, 4));
        int monDiff = calendar.get(Calendar.MONTH)+1 - Integer.valueOf(birth.substring(4, 6));
        if (monDiff < 0)
            res--;
        else if (monDiff == 0)
            res += calendar.get(Calendar.DAY_OF_MONTH) - Integer.valueOf(birth.substring(6)) >= 0 ? 0 : -1;
        return res;
    }

    // relative
    public static int relative(String relative){
        return !relative.equals("参与人")?0:1;
    }
}
