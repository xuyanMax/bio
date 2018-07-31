package com.JsonGenerator;

import com.JsonGenerator.element.*;
import com.JsonGenerator.type.*;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.SSHConnection;
import com.jcraft.jsch.JSchException;
import org.apache.log4j.Logger;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetchData {
    private static Logger logger = Logger.getLogger(FetchData.class);
    private static int NUM_PER_PAGE = 10;
    private static String SQL_ALL = "SELECT * FROM questions";
    private static String SQL_TABLE = "SELECT * FROM questions where types = \'blank\' limit 2";
    private static String LEFT_BRACKET = "（";
    private static String RIGHT_BRACKET = "）";
    private static String REG_START = "^";
    private static String REG_END = "$";
    private static String SEMI_COLUMN = ";";
    private static String SEMI_COLUMN_ = "；";
    private static String COMMA = ",";//分割选项
    private static String HASH = "#";
    private static String DUNHAO = "、";



    public static void main(String[] args)  {
        try {
            SSHConnection sshConnection = new SSHConnection();
            getSurveyJSON();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public static String getSurveyJSON() throws JSchException {

        //单独跑main()方法连接远程库时候需要本句，连接本地库不需要
        //配合SpringMVC使用，则注释下掉该句
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        SurveyJson surveyJson = new SurveyJson();

        try {
            Class.forName(SSHConnection.JDBC_DRIVER);
            //远程库
            conn = DriverManager.getConnection(SSHConnection.JDBC_URL,SSHConnection.DB_USERNAME,SSHConnection.DB_PASSWORD);
            //本地库
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdcDev","root","root");
            statement = conn.createStatement();

            rs = statement.executeQuery(SQL_ALL);

            //获取列名
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            //获取数据
            int num_quest = 0;
            List<BaseQuestion> elements = null;
            while (rs.next()) {
//                logger.info(num_quest);

                if (num_quest % NUM_PER_PAGE == 0){
                    Page page = new Page();
                    page.setName("page"+num_quest / NUM_PER_PAGE + "");
                    elements = new ArrayList<>();
                    page.setElements(elements);
                    surveyJson.getPages().add(page);
                }
                num_quest++;
                String question = rs.getString("question");
                String type = rs.getString("types");
                String description = rs.getString("note");
                String opts= rs.getString("options");
                String section = rs.getString("section");
                //todo: 按照Section显示
                if (type.equals("choice")){

                    RadioGroup radioGroup = new RadioGroup("question" + num_quest, question);
                    //拆分选项,生成选项
                    List<Choice> choices =  addChoices(opts);
                    radioGroup.setChoices(choices);
                    //添加到Page.elements
                    elements.add(radioGroup);
                } else if (type.equals("double")){
                    Checkbox checkBox = new Checkbox("question" + num_quest, question);
                    List<Choice> choices = addChoices(opts);

                    if (description != null) checkBox.setDescription(description);

                    checkBox.setChoices(choices);
                    //添加到Page.elements
                    elements.add(checkBox);
                } else if (type.equals("table")) {

                    MatrixDynamic matrixDynamic = new MatrixDynamic("question" + num_quest, question);
                    AssembleMatrixDynamic(matrixDynamic, description, opts);
                    elements.add(matrixDynamic);

                } else if (type.equals("blank")) {
                    //todo:判断是text还是multipletext
                    int size = question.contains(SEMI_COLUMN)?question.split(SEMI_COLUMN).length:-1;
                    if (size < 0) {
                        Text text = generateSingleText(num_quest, question, opts);
                        elements.add(text);
                    }else { //size > 0 multipletext

                        MultipleText multipleText = new MultipleText("question" + num_quest, generateMultiTextTitle(question));

                        multipleText.setItems(multiTextAddItems(question));
                        generateMultiTextValidators(multipleText, question);
                        elements.add(multipleText);
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn!=null)
                    conn.close();
                if (statement != null)
                    statement.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        logger.info(JSONObject.toJSONString(surveyJson));
        return JSONObject.toJSONString(surveyJson).replace("//", "/");
    }
    public static Text generateSingleText(int num_quest, String question, String opts) {
        Text text = new Text("question" + num_quest, question.substring(0, question.indexOf('_')) + "_" + question.substring(question.lastIndexOf('_')));
        //添加正则判断
        if (question.contains(REG_START) && question.contains(REG_END)) {
            int first = question.indexOf(REG_START);
            int sec = question.lastIndexOf(REG_END);//类似^[1-5]\d{1}$|^[1-9]$|^\/$
            //正则截取包含起止^$符号

            String regex = question.substring(first, sec + 1);
            ValidatorRegex validatorRegex = new ValidatorRegex(regex);

            if (opts.contains(HASH)) //添加错误提示
                validatorRegex.setText(opts.substring(opts.indexOf(HASH) + 1));//错误提示

            text.setValidators(validatorRegex);
        }
        return text;
    }
    public static List<Choice> addChoices(String opts){
        if (opts == null || opts.equals(""))
            return new ArrayList<>();
        //拆分选项
        String[] options = opts.split(COMMA);
        List<Choice> choices = new ArrayList<>();
        for (int i=0; i<options.length; i++) {
            //todo
            Choice choice = new Choice(i+"", options[i]);
            choices.add(choice);
        }
        return choices;
    }
    public static String generateMultiTextTitle(String question){
        String[] split = question.split(SEMI_COLUMN);
        //todo: generate multitext title
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<split.length; i++){
            builder.
                    append(split[i].substring(0, split[i].indexOf(REG_START))).
                    append(split[i].substring(split[i].lastIndexOf(REG_END)+ 1)).
                    append(SEMI_COLUMN_);
        }
        return builder.toString().substring(0, builder.length() - 1);
    }
    public static void generateMultiTextValidators(MultipleText multipleText, String question){
        String[] split = question.split(SEMI_COLUMN);
        for (int i=0; i<split.length; i++){
            String regex = split[i].substring(split[i].indexOf(REG_START), split[i].indexOf(REG_END) + 1);
            ValidatorRegex validator = new ValidatorRegex(regex);
            multipleText.getValidators().add(validator);
        }
    }
    public static List<Item> multiTextAddItems(String question){
        if (question == null || question.equals(""))
            return new ArrayList<>();
        String[] options = question.split(SEMI_COLUMN);
        int size = options.length;
        List<Item> items = new ArrayList<>();
        //todo:获取title
        for (int i=0; i<size; i++) {
            items.add(new Item("", ""));//设置名字为空

            if (options[i].contains(REG_START) && options[i].contains(REG_END)) {//添加正则表达
                int first = options[i].indexOf(REG_START);
                int second = options[i].indexOf(REG_END);
                String regex = options[i].substring(first, second+1);
                ValidatorRegex validatorRegex = new ValidatorRegex(regex);

                if (options[i].contains(HASH)) {//添加错误提示
                    String text = options[i].substring(options[i].indexOf(HASH) + 1);
                    validatorRegex.setText(text);//错误提示
                }
                items.get(i).getValidators().add(validatorRegex);
            }
        }
        return items;

    }
    public static void AssembleMatrixDynamic(MatrixDynamic matrixDynamic, String description, String opts){
        if (description != null && !description.equals(""))
            matrixDynamic.setDescription(description);
        if (opts == null || opts.equals(""))
            return;
        String[] splits = opts.split(COMMA);
        int index = 0;
        for (String split:splits){
            String name;
            if (split.contains(LEFT_BRACKET))
                name = split.substring(0, split.indexOf(LEFT_BRACKET));//阶段（小学、中学、大学、硕士、博士）,开始时间（XX年）,结束时间（XX年）,地点（XX省XX市/县）,邮编
            else name = split;

            Column column = new Column();

            if (split.contains("d")){//table类型第一列标识为dropdown下拉菜单
                if (index++ != 0){
                    column.setCellType("text");
                    column.setName(name);
                }else {
                    column.setCellType("dropdown");
                    List<String> choices = new ArrayList<>();
                    String choice = split.substring(split.indexOf(LEFT_BRACKET) + 2, split.indexOf(RIGHT_BRACKET));

                    Arrays.stream(choice.split(DUNHAO)).forEach((c)->{choices.add(c);});
                    column.setChoices(choices);
                }
            }else {
                column.setCellType("text");
                column.setName(name);//XX年
            }
            column.setName(name);
            matrixDynamic.getColumns().add(column);
        }
    }
    public void output(String JSONString){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/apple/Downloads/json.txt")));
            writer.write(JSONString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
