import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLog4j {
     public static void main(String[] args){
//         PropertyConfigurator.configure("log4j.properties");
         Logger logger = Logger.getLogger(TestLog4j.class.getName());
         logger.debug("debug");
         logger.error("err");
         logger.warn("warn");
         logger.info("info");
     }

}
