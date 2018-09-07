package com.JsonGenerator;

import com.JsonGenerator.element.*;
import com.JsonGenerator.type.*;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.SSHConnection;
import com.jcraft.jsch.JSchException;
import org.apache.log4j.Logger;

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
    private static String PERCENTAGE = "%";
    private static String UNDERSCORE ="_";
    private static String QUESTIONMARK = "?";
    private static String AMPERSAND = "&";
    private static String EQUALSIGN = "=";


    //参考 www.cnblogs.com/guodefu909/p/5805667.html
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
            Page page;
            List<BaseQuestion> elements = null;
            while (rs.next()) {

                if (num_quest % NUM_PER_PAGE == 0){
                    page = new Page();
                    page.setName("page" + num_quest / NUM_PER_PAGE);
                    elements = new ArrayList<>();
                    page.setElements(elements);
                    surveyJson.getPages().add(page);
                }
                num_quest++;
                String question = rs.getString("question");
                String type = rs.getString("types");
                String opts= rs.getString("options");
                String description = rs.getString("note");
                String section = rs.getString("section");
                String supporting = rs.getString("supporting");
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
                    int size = question.contains(SEMI_COLUMN)?question.split(SEMI_COLUMN).length:-1;
                    if (size < 0) {
                        Text text = generateSingleText(num_quest, question, opts);
                        if (description != null) text.setDescription(description);
                        elements.add(text);
                    }else {

                        MultipleText multipleText = new MultipleText("question" + num_quest, generateMultiTextTitle(question));

                        multipleText.setItems(multiTextAddItems(question));

                        if (description != null) multipleText.setDescription(description);
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
        return JSONObject.toJSONString(surveyJson);
    }
    public static Text generateSingleText(int num_quest, String question, String opts) {
        String title;
         //todo: 数据库更新后，只需要这一句就够
        title = question.substring(0, question.indexOf(REG_START));
//        logger.info(title);
        Text text = new Text(
                "question" + num_quest,
                title
        );
        //添加正则判断
        if (question.contains(REG_START) && question.contains(REG_END)) {
            System.out.println("\n"+question+"\n");
            int first = question.indexOf(REG_START);
            int sec = question.lastIndexOf(REG_END);//类似^[1-5]\d{1}$|^[1-9]$|^\/$

            String regex = question.substring(first, sec + 1);
            ValidatorRegex validatorRegex = new ValidatorRegex(regex);

            if (question.contains(HASH)) //添加错误提示
                validatorRegex.setText(question.substring(question.indexOf(HASH) + 1));//错误提示

            text.getValidators().add(validatorRegex);
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
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<split.length; i++){
            builder
                    .append(split[i].substring(0, split[i].indexOf(REG_START)))
                    .append(SEMI_COLUMN_);
        }
        return builder.toString().substring(0, builder.length() - 1);
    }
    public static void generateMultiTextValidators(MultipleText multipleText, String question){
        String[] split = question.split(SEMI_COLUMN);
        for (int i=0; i<split.length; i++){
            String regex = split[i].substring(split[i].indexOf(REG_START), split[i].lastIndexOf(REG_END) + 1);
            ValidatorRegex validator = new ValidatorRegex(regex);
            multipleText.getValidators().add(validator);
        }
    }
    public static List<Item> multiTextAddItems(String question){
        if (question == null || question.equals(""))
            return new ArrayList<>();
        String[] subqustions = question.split(SEMI_COLUMN);
        int size = subqustions.length;
        List<Item> items = new ArrayList<>();

        for (int i=0; i<size; i++) {
            items.add(new Item("",""));
            if (subqustions[i].contains(REG_START) && subqustions[i].contains(REG_END)) {//添加正则表达
                int first = subqustions[i].indexOf(REG_START);
                int second = subqustions[i].lastIndexOf(REG_END);
                String regex = subqustions[i].substring(first, second+1);
                ValidatorRegex validatorRegex = new ValidatorRegex(regex);

                if (subqustions[i].contains(HASH)) {//添加错误提示
                    String text = subqustions[i].substring(subqustions[i].indexOf(HASH) + 1);
                    validatorRegex.setText(text);//错误提示
                }
                items.get(i).getValidators().add(validatorRegex);
            }
        }

        if (question.contains(QUESTIONMARK)) {
            //?text1=**&text2=***
            List<String> names = Arrays.asList(question
                    .substring(question.indexOf(QUESTIONMARK) + 1)
                    .split(AMPERSAND));
            for (int j=0;  j<names.size(); j++){
                String name = names.get(j).substring(names.get(j).indexOf(EQUALSIGN) + 1);
//                logger.warn(name);
                items.get(j).setName(name);
                items.get(j).setTitle(name);
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
}
