package com.bio.Utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Optional;
import java.util.regex.Pattern;

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
    //身份证号计算: https://blog.csdn.net/dabing69221/article/details/9150819
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }
    public static boolean isID_code(String ID_code){
        return ID_code == null || "".equals(ID_code) ? false : Pattern.matches(
                "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", ID_code);
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
            case 15:return getAgeHelper(ID_code.substring(6,11), 6);
            case 18:return getAgeHelper(ID_code.substring(6,13), 8);
        }
        return -1;

    }
    // 年龄范围: 1918年到2018年
    // 通过年，月，日与当前时间做对比，计算实际年龄

    //合并处理15位/18位身份证号，计算年龄的问题
    public static int getAgeHelper(String birth, int size){
        int offset = (size==6) ? 0 : 2;
        Calendar calendar = Calendar.getInstance();
        int res = calendar.get(Calendar.YEAR) - Integer.valueOf(birth.substring(0, 2+offset));
        int monDiff = calendar.get(Calendar.MONTH) - Integer.valueOf(birth.substring(2+offset, 4+offset));
        if (monDiff < 0)
            res--;
        else if (monDiff == 0)
            res += calendar.get(Calendar.DAY_OF_MONTH) - Integer.valueOf(birth.substring(4+offset)) >= 0 ? 1 : -1;
        return res;
    }
}
