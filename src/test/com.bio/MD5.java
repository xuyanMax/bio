import com.bio.Utils.PersonInfoUtils;
import org.junit.Test;

import java.util.Scanner;

public class MD5 {

    public static void main(String[] args){
        //生成md5
            Scanner scanner = new Scanner(System.in);
            System.out.print("输入18位身份证: ");
            String ID_code;
        while (scanner.hasNext()){
                //输出md5 身份证号
                ID_code = scanner.next();
                System.out.print(ID_code+"经md5编码后: ");
                System.out.println(PersonInfoUtils.md5(ID_code));
                System.out.print("\n\n输入18位身份证: ");
            }
    }
}
