package com.bio;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
     public static void main(String[] args){
//         PropertyConfigurator.configure("log4j.properties");
         Logger logger = Logger.getLogger(TestLog4j.class.getName());
         logger.warn("warn");
         logger.debug("debug");
         logger.error("err");
         logger.info("info");
         String str = "select `lifetime_risk` from risk_crcmale where factor1=? and factor2=? and factor3=? and factor4=? and factor5=? and factor6=? and factor7=? and factor8=? and factor9=? and factor10=?";
         String[] strs = str.split("\\?");
         for(String s:strs)
             System.out.println(s);
         int j = 0;
         StringBuilder builder = new StringBuilder();
         for(int i=0; i<strs.length; i++) {
             builder.append(strs[i]).append(j++);
         }
         System.out.println(builder.toString());

     }

}
