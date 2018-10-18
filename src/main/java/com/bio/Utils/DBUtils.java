package com.bio.Utils;

import com.bio.beans.Person;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static Logger logger = Logger.getLogger(DBUtils.class);
    private static String sheetName = "下载队列成员信息表";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String FILE_EXTENSION = ".xls";
    public static String FILE_NAME = "队列成员信息表" + LocalDateTime.now().format(DATE_TIME_FORMATTER) + FILE_EXTENSION;
    private static final String[] COL_NAMES = new String[10];
    private static final String[] PS = new String[9];
    private static final String[] COL_C_NAMES = new String[10];

    static {
        COL_NAMES[0] = "项目内序号";
        COL_NAMES[1] = "单位内序号(工号)";
        COL_NAMES[2] = "姓名（第一字加*补足长度）";
        COL_NAMES[3] = "性别";
        COL_NAMES[4] = "年龄";
        COL_NAMES[5] = "身份证号末四位";
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

        COL_C_NAMES[0] = "global_sn";
        COL_C_NAMES[0] = "sn_in_center";
        COL_C_NAMES[0] = "name";
        COL_C_NAMES[0] = "gender";
        COL_C_NAMES[0] = "age";
        COL_C_NAMES[0] = "ID_code";
        COL_C_NAMES[0] = "ID_code";
        COL_C_NAMES[0] = "barcode";
        COL_C_NAMES[0] = "relative";
        COL_C_NAMES[0] = "tel1";
    }

    private static int INFO_ROWS = 6;

    /**
     * read xls
     * * 从服务器端读取上传文件，并获取persons数据
     *
     * @return List<person>
     */
    public static List<Person> readXlsFromFileName(String path) throws IOException {

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
        for (int i = 2; i <= uploadQueueInfo.getLastRowNum() - INFO_ROWS; i++) {
            HSSFRow hssfRow = uploadQueueInfo.getRow(i);
            if (hssfRow != null) {
                Person p = new Person();
                HSSFCell sn_in_center = hssfRow.getCell(0);
                HSSFCell name = hssfRow.getCell(1);
                HSSFCell ID_code = hssfRow.getCell(2);
                HSSFCell barcode = hssfRow.getCell(3);
                HSSFCell relative = hssfRow.getCell(4);
                HSSFCell tel1 = hssfRow.getCell(5);

                DataFormatter formatter = new DataFormatter();

                /*
                assemble a person object and add to return list
                Returns the formatted value of a cell as a String regardless of the cell type

                https://stackoverflow.com/questions/30125465/cannot-get-a-text-value-from-a-numeric-cell-poi
                */
                p.setName(formatter.formatCellValue(name));
                //保留原身份证号
                p.setOriginal_ID_code(formatter.formatCellValue(ID_code));
                //md5散列的身份证号
                p.setID_code(PersonInfoUtils.md5(formatter.formatCellValue(ID_code)));
                p.setID_code_cut(p.getOriginal_ID_code().substring(14));

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

    /*upload xls to server*/
    public static void uploadAFileToServer(HttpServletRequest request, MultipartFile multipartFile) {
        String path = request.getServletContext().getRealPath("/data/");
        //测试: 上传文件名
        logger.info(path);
        String fileName = multipartFile.getOriginalFilename();
        logger.info(fileName);
        File filePath = new File(path, fileName);
        //判断路径是否存在，不存在则创建
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdir();
        }
        try {
            multipartFile.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // output an excel file, containing all person's essential info
    public static void createXlsAndDownload(List<Person> persons,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        logger.info(persons.size());
        //1. 创建workbook，对应一个excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2. 添加一个sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //3. 添加内容
        // a. 创建标题
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
        HSSFRow row1 = sheet.createRow(1);
        for (int i = 0; i < COL_C_NAMES.length; i++) {
            cell = row1.createCell(i);
            cell.setCellValue(COL_C_NAMES[i]);
            cell.setCellStyle(style);
        }
        // 第五步，创建单元格，并设置值
        HSSFRow row = null;
        int i;
        for (i = 0; i < persons.size(); i++) {
            row = sheet.createRow(i + 2);
            // 为数据内容设置特点新单元格样式1 自动换行 上下居中
            style = workbook.createCellStyle();
            //设置单元格边框
            setCellStyle(workbook, style);
            //获取row的输入信息
            List<String> rowInfo = getColValuesFromUser(persons.get(i), persons.get(i).getOriginal_ID_code());
            //插入每一列单元格信息
            for (int j = 0; j < COL_NAMES.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(rowInfo.get(j));
                cell.setCellStyle(style);
            }
        }
        // 插入注解信息
        for (int j = 0; j < PS.length; j++, i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(PS[j]);
        }
        // 第六步，存储下载文件到指定位置
        String path = request.getServletContext().getRealPath("/data/");
        logger.info(path);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(path + FILE_NAME);
            workbook.write(os);//导出
            logger.info("已导出: " + FILE_NAME);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();//关闭输出流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void setCellStyle(HSSFWorkbook workbook, CellStyle style) {
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

    public static List<String> getColValuesFromUser(Person person, String ID) {
        if (person == null)
            return new ArrayList<>();
        List<String> res = new ArrayList<>();

        String global_sn = person.getGlobal_sn();
        String sn_in_center = person.getSn_in_center();
        String name = person.getName();
        String gender = person.getGender().equals("男") ? "1" : "0";
        String age = String.valueOf(person.getAge());
        String ID_code = ID;//原身份证号
        String ID_md5 = person.getID_code();//加密身份证号
        //1:男 else女
        String barcode = person.getBarcode();
        String relative = person.getRelative() == 0 ? "participant" : "relative";
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
