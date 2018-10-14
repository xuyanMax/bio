package com.bio;

import org.junit.Test;

public class RegexTest {
    @Test
    public void test(){
        String str = "asdasiexsda";
        String str2 = "asdasedgexsda";
        String regex = "^.*(ie|edge).*$";
        if (str.matches(regex))
            System.out.println(true);
        else
            System.out.println(false);

        if (str2.matches(regex))
            System.out.println(true);
        else
            System.out.println(false);

    }
    @Test
    public void testUseAgent(){
        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.";

    }
}
