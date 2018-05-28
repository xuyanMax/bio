package com.bio.Utils;

import com.bio.beans.Person;
import javafx.scene.control.Cell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static int CONSTANT = 6;
    /**
    * read xls
    * @return List<person>
    * */
    public static List<Person> readXls (String path) throws IOException {

        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Person person = null;
        // return list
        List<Person> res = new ArrayList<>();
        // sheet1: 上传队列成员信息表
        // sheet2: 下载队列成员信息表
        HSSFSheet uploadQueueInfo = hssfWorkbook.getSheetAt(0);
        //sanity check
        if (uploadQueueInfo == null) return res;
        // iterate every row
        for (int i=2; i<uploadQueueInfo.getLastRowNum()-6; i++){
            HSSFRow hssfRow = uploadQueueInfo.getRow(i);
            if (hssfRow != null){
                Person p = new Person();
                HSSFCell sn_in_center = hssfRow.getCell(0);
                HSSFCell name = hssfRow.getCell(1);
                HSSFCell ID_code = hssfRow.getCell(2);
                HSSFCell barcode = hssfRow.getCell(3);
                HSSFCell relative = hssfRow.getCell(4);
                HSSFCell tel1 = hssfRow.getCell(5);

                DataFormatter formatter = new DataFormatter();

                // assemble a person object and add to return list
                /*Returns the formatted value of a cell as a String regardless of the cell type
                https://stackoverflow.com/questions/30125465/cannot-get-a-text-value-from-a-numeric-cell-poi*/
                p.setName(formatter.formatCellValue(name));
                p.setID_code(PersonInfoUtils.md5(formatter.formatCellValue(ID_code)));
                p.setBarcode(formatter.formatCellValue(barcode));
                p.setRelative(PersonInfoUtils.relative(formatter.formatCellValue(relative)));
                p.setSn_in_center(formatter.formatCellValue(sn_in_center));
                p.setTel1(formatter.formatCellValue(tel1));

                // generated person info, age and sex
                p.setGender(PersonInfoUtils.getGender(formatter.formatCellValue(ID_code)));
                p.setAge(PersonInfoUtils.getAge(formatter.formatCellValue(ID_code)));
                res.add(p);
                // assemble done
            }
        }
        return res;
    }

    /**
     * save to db
     * only applicable to: 2003 Excel, e.g., *.xls
     * 1. read xls file from the server
     * 2. save it to db
    * */
    public static void save(String path){
    }
    /*upload xls to db*/
    public static void uploadSingleFile(HttpServletRequest request, MultipartFile multipartFile) {
        //上传文件路径: bio/target/WEB-INFO/data
        String path = request.getServletContext().getRealPath("/data/");

        //测试: 上传文件名
        System.out.println(path);

        String fileName = multipartFile.getOriginalFilename();
        System.out.println(fileName);
        File filePath = new File(path, fileName);
        //判断路径是否存在，不存在则创建
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdir();
        }
        try {
            multipartFile.transferTo(new File(path+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
