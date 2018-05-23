package com.bio.Utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

public class Utils {
    /**
     * 生成32位md5码
     * 对身份证号进行加密
     * @param ID_code
     * @return
     */

    //获取队列成员信息的- md5(ID_code)
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

    //上传单个文件
    public static void uploadSingleFile(HttpServletRequest request, MultipartFile multipartFile) {
            //上传文件路径: bio/target
            String path = request.getServletContext().getRealPath("/data/");
            //上传文件名
            System.out.println(path);
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);
            File filePath = new File(path, fileName);
            //判断路径是否存在，不存在则创建
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
            }
    }
    //从ID中获取性别
    public static int getGender(String ID_code){
        int size = ID_code.length();
        //报错
        if (size == 0) return -1;
        switch (size){
            case 15: return ID_code.charAt(14)-'0';
            case 18: return ID_code.charAt(16)-'0';
        }

        return -1;
    }
    // todo: 判断ID_code是否合法...
    //利用ID计算生日
    // 15/18位身份证
    // @return 1: 男, 2: 女
    public static int getAge(String ID_code){
        int size = ID_code.length();
        //报错
        if (size == 0) return -1;
        switch (size){
            case 15:return getAgeHelper15(ID_code.substring(6,12));
            case 18:return getAgeHelper18(ID_code.substring(6,11));
        }
        return -1;

    }
    // 年龄范围: 18年到18年
    public static int getAgeHelper15(String birth){

        return -1;
    }
    public static int getAgeHelper18(String birth){

        return -1;
    }
}
