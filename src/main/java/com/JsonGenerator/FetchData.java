package com.JsonGenerator;

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
    private static String SQL_TABLE = "SELECT * FROM questions where types = \'table\' limit 2";

    public static void main(String[] args)  {
        try {
            getSurveyJSON();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public static String getSurveyJSON() throws JSchException {

        //建立ssh数据库连接, 不需要了
//        SSHConnection sshConnection = new SSHConnection();
        //slqs
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        SurveyJson surveyJson = new SurveyJson();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdcDev","user20182","!user;2018");
            statement = conn.createStatement();

            rs = statement.executeQuery(SQL_ALL);

            //获取列名
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            //获取数据
            int num_quest = 0;
            List<BaseQuestion> elements = null;
            while (rs.next()) {

                System.out.println("========"+num_quest+"=========");
                logger.info(num_quest);

                if (num_quest % 10 == 0){
                    Page page = new Page();
                    page.setName("page"+num_quest / 10 + "");
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

                /*测试*/
//                System.out.println("question " + question);
//                System.out.println("type" + type);
//                System.out.println("description" + description);
//                System.out.println("options " + opts);
//                System.out.println("\n\n");
                /**/

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

                    int size = question.contains(";")?question.split(";").length:-1;
                    if (size == -1) {
                        Text text = new Text("question" + num_quest, question);

                        if (question.matches("_.*_")) {
                            int first = question.indexOf('_');
                            System.out.println("first=" + first);
                            int sec = question.lastIndexOf('_');
                            System.out.println("second=" + sec);

                            String regex = question.substring(first + 1, sec);

                            if (question.contains("#")) {
                                int third = question.indexOf("#");
                                String warning = question.substring(third);
                                if (warning != null) {

                                    ValidatorRegex validatorRegex = new ValidatorRegex(warning);

                                    text.setValidators(validatorRegex);
                                }
                            }
                        }
                        elements.add(text);
                    }else { //size > 0 multipletext
                        MultipleText multipleText = new MultipleText("question" + num_quest, question);
                        multipleText.setItems(addItems(opts));
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
    public static List<Choice> addChoices(String opts){
        if (opts == null || opts.equals(""))
            return new ArrayList<>();
        //拆分选项
        String[] options = opts.split(",");
        List<Choice> choices = new ArrayList<>();
        for (int i=0; i<options.length; i++) {
            //todo
            Choice choice = new Choice(i+"", options[i]);
            choices.add(choice);
        }
        return choices;
    }
    public static List<Item> addItems(String opts){
        if (opts == null || opts.equals(""))
            return new ArrayList<>();
        String[] options = opts.split(";");
        int size = options.length;
        List<Item> items = new ArrayList<>();
        //todo:获取title
        for (int i=0; i<size; i++) {
            items.add(new Item("", ""));//设置名字为空

            if (options[i].contains("_")) {//添加正则表达
                int first = options[i].indexOf('_');
                int second = options[i].lastIndexOf('_');
                String regex = options[i].substring(first + 1, second);
                ValidatorRegex validatorRegex = new ValidatorRegex(regex);

                if (options[i].contains("#")) {//添加错误提示
                    String text = options[i].substring(options[i].indexOf("#") + 1);
                    validatorRegex.setText(text);//错误提示
                }
                items.get(i).getValidators().add(validatorRegex);
            }
        }
        return items;

    }
    public static void AssembleMatrixDynamic(MatrixDynamic matrixDynamic, String description, String opts){
        if (description != null)
            matrixDynamic.setDescription(description);

        String[] splits = opts.split("，");
        List<Column> columns = new ArrayList<>();
        int index = 0;
        for (String split:splits){
            String name;
            if (split.contains("（"))
                name = split.substring(0, split.indexOf("（"));//阶段（小学、中学、大学、硕士、博士）,开始时间（XX年）,结束时间（XX年）,地点（XX省XX市/县）,邮编
            else name = split;

            Column column = new Column();

            if (split.startsWith("d")){
                //remove d from split
                split = split.substring(1);
                System.out.println(split);
                if (index++ != 0){
                    column.setCellType("text");
                    column.setName(name);//XX年
                }else {
                    column.setCellType("dropdown");
                    List<String> choices = new ArrayList<>();
                    String choice = split.substring(split.indexOf("（") + 1, split.indexOf("）"));

                    for (String c : choice.split("、"))
                        choices.add(c);

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