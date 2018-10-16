package com.wechat.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheckTokenUtils {
    private static String ACCESS_TOKEN = "brbxyxzyz";
    private static Logger logger = Logger.getLogger(CheckTokenUtils.class);

    /*
     * 验证签名
     * */
    public static boolean checkSignature(String TOKEN, String timestamp, String nonce, String signature) throws NoSuchAlgorithmException {
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        //对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        Arrays.sort(arr);
        StringBuilder builder = new StringBuilder();
        builder.append(arr[0]).append(arr[1]).append(arr[2]);


        String sign = new String(
                org.apache.commons.codec.binary.Hex.
                        encodeHex(MessageDigest.getInstance("SHA-1").
                                digest((builder.toString()).
                                        getBytes()), true));
        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sign)) {
            logger.info("获取加密字符串与微信:" + signature + "对比一致.");
            return true;
        }
        return false;
    }
}
