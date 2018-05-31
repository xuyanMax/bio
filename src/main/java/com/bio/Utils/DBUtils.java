package com.bio.Utils;

import com.bio.beans.Person;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final int CONSTANT = 6;
    private static String sheetName = "下载队列成员信息表";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String FILE_EXTENSION = ".xls";
    private static final String FILE_NAME = "队列成员信息表" + LocalDate.now().format(DATE_TIME_FORMATTER) + FILE_EXTENSION;
    private static final String[] COL_NAMES = new String[10];
    private static final String[] PS =  new String[9];
    static {
        COL_NAMES[0] = "项目内序号";
        COL_NAMES[1] = "单位内序号(工号)";
        COL_NAMES[2] = "姓名";
        COL_NAMES[3] = "性别";
        COL_NAMES[4] = "年龄";
        COL_NAMES[5] = "身份证号";//?
        COL_NAMES[6] = "编译后身份证号";
        COL_NAMES[7] = "样品条形码(登记流水号)";
        COL_NAMES[8] = "身份";
        COL_NAMES[9] = "电话";

        PS[0] = "本表为系统生成，由单位管理员下载";
        PS[1] = "“项目内序号”为系统赋予，唯一不重复，格式为三个数字以_连接：‘postcode’_‘local_num’_‘number’，number是在本单位内从1开始顺序排列的数字";
        PS[2] = "“单位内序号”、“姓名”与输入表相同";
        PS[3] = "“性别”、“年龄”由身份证号读取";
        PS[4] = "“身份证号”与输入表相同";
        PS[5] = "“编译后的身份证号”由身份证号经过MD5加密算法得到，无法回溯得到原身份证号";
        PS[6] = "样品条形码（登记流水号）”、“身份”、“电话”与输入表相同";
        PS[7] = "本表信息由单位管理员下载并保存，全名和身份证号不会存入系统数据库";
        PS[8] = "下载成功说明队列成员信息已入库，如下载失败须重新上传和下载";
    }
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
    // output an excel file, containing all person's essential info
    public static void createExcelSheet(List<Person> persons, String ID) {
        //1. 创建workbook，对应一个excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 添加一个sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
//        sheet.setDefaultColumnWidth(5);//统一设置列宽度

        //3. 添加内容
        // a. 创建标题
//        HSSFRow row0 = sheet.createRow(0);
//        row0.setHeightInPoints(50);//标题高度
//        HSSFCellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//标题居中
//        style.setVerticalAlignment(VerticalAlignment.CENTER);//标题居中
        // b. 创建表头
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeightInPoints(37);
        HSSFCellStyle style = workbook.createCellStyle();
        //设置边框
        setCellStyle(workbook, style);

        HSSFFont headerFont = (HSSFFont) workbook.createFont(); // 创建字体样式
        headerFont.setBold(true); // 字体加粗
        headerFont.setFontName("黑体"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
        style.setFont(headerFont); // 为标题样式设置字体样式

        //单元格
        HSSFCell cell = null;
        // 4.创建表头的列
        for (int i = 0; i < COL_NAMES.length; i++) {
            cell = row0.createCell(i);
            cell.setCellValue(COL_NAMES[i]);
            cell.setCellStyle(style);
        }
        // 第五步，创建单元格，并设置值
        HSSFRow row = null;
        for (int i = 0; i < persons.size(); i++) {
            row = sheet.createRow(i+1);
            // 为数据内容设置特点新单元格样式1 自动换行 上下居中
            style = workbook.createCellStyle();
            //设置单元格边框
            setCellStyle(workbook, style);
            //获取row的输入信息
            List<String> rowInfo = getColValues(persons.get(i), ID);
            //插入每一列单元格信息
            for (int j = 0; j< COL_NAMES.length; j++){
                cell = row.createCell(j);
                cell.setCellValue(rowInfo.get(j));
                cell.setCellStyle(style);
            }
        }
        // 第六步，存储下载文件到指定位置
        // for windows
        String path = System.getProperty("user.home")+"/Downloads";
        // todo: for mac
        try {
            FileOutputStream os = new FileOutputStream(path + FILE_NAME);
            workbook.write();//导出
            System.out.println("已导出: " + FILE_NAME);
            os.close();//关闭输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }/*finally {
            os
        }*/
    }
    public static void setCellStyle(HSSFWorkbook workbook, CellStyle style){
        style.setWrapText(true);// 设置自动换行
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        style.setVerticalAlignment(VerticalAlignment.CENTER); // 创建一个居中格式
        //设置边框
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }
    public static List<String> getColValues(Person person, String ID){
        if (person == null)
            return new ArrayList<>();
        List<String> res = new ArrayList<>();

        String global_sn = person.getGlobal_sn();
        String sn_in_center = person.getSn_in_center();
        String name = person.getName();
        String gender = person.getGender().equals("1")?"男":"女";
        String age = String.valueOf(person.getAge());
        String ID_code = ID;//原身份证号
        String ID_md5 = person.getID_code();//加密身份证号
        //1:男 else女
        String barcode = person.getBarcode();
        String relative = person.getRelative()==0?"participant":"relative";
        String tel1 = person.getTel1();

        res.add(global_sn);
        res.add(sn_in_center);
        res.add(name);
        res.add(gender);
        res.add(age);
        res.add(ID_code);
        res.add(ID_md5);
        res.add(barcode);
        res.add(relative);
        res.add(tel1);

        return res;

    }

}
