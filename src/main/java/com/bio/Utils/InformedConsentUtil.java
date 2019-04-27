package com.bio.Utils;


import org.apache.log4j.Logger;

/**
 * @Author: xyx
 * @Date: 2019-04-27 22:39
 * @Version 1.0
 */
public class InformedConsentUtil {

    private static Logger logger = Logger.getLogger(InformedConsentUtil.class);

    private static String STYLE_START = "<style>";
    private static String STYLE_END = "</style>";
    private static String BODY_START = "<body";
    private static String BODY_END = "</body>";

    /**
     * @param html
     * @return return <style>info including the tag</style>
     */
    public static String getStyle(String html) {
        int start = html.indexOf(STYLE_START);
        int end = html.lastIndexOf(STYLE_END);
        logger.info("start=" + start + ", end=" + end);
        return html.substring(start, end + STYLE_END.length());
    }

    /**
     * @param html
     * @return <body>return info inside the tag</body>
     */
    public static String getBody(String html) {
        int start = html.indexOf(BODY_START);
        int end = html.lastIndexOf(BODY_END);
        start = html.indexOf(">", start);
        logger.info("start=" + start + ", end=" + end);

        return html.substring(start + 1, end);
    }
}
