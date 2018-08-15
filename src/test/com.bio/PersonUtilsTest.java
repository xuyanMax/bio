package com.bio;
import com.bio.Utils.PersonInfoUtils;
import com.bio.beans.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class PersonUtilsTest {
      @Test
      public void testAge(){
          Calendar calendar = Calendar.getInstance();
          System.out.println(calendar.get(Calendar.YEAR));
          System.out.println(calendar.get(Calendar.MONTH)+1);
          System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
          Assert.assertEquals(PersonInfoUtils.getAge("13010419920618241X"), 25);
          Assert.assertEquals(PersonInfoUtils.getAge("13010419920518241X"), 26);
          Assert.assertEquals(PersonInfoUtils.getAge("13010419920418241X"), 26);
      }

      @Test
    public void testGender(){
          Assert.assertEquals(PersonInfoUtils.getGender("13010419920518241X"), "1");
          Assert.assertEquals(PersonInfoUtils.getGender("13010419920518242X"), "2");
      }
    @Test
    public void md5(){
        System.out.println(PersonInfoUtils.md5("123qweasd"));
    }
    @Test
    public void testIdToLowerCase(){
          String id = "123X";
          String id2 = "123x";
        Assert.assertEquals(id2, id.toLowerCase());
        Assert.assertEquals(PersonInfoUtils.md5(id2), PersonInfoUtils.md5(id.toLowerCase()));
    }
}
