package com.JsonGenerator;

import com.JsonGenerator.type.*;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.SSHConnection;
import com.jcraft.jsch.JSchException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FetchData {
    private static int NUM_PER_PAGE = 10;
    public static void main(String[] args) throws JSchException, ClassNotFoundException, SQLException {

        //建立ssh数据库连接
        SSHConnection sshConnection = new SSHConnection();
        //slq
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        SurveyJson surveyJson = new SurveyJson();

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdcDev", "root", "root");
        statement = conn.createStatement();
        String sql = "SELECT * FROM questions limit 15";
        rs = statement.executeQuery(sql);

        //获取列名
        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        //获取数据
        int num_quest = 0;
        List<BaseQuestion> elements = null;
        while (rs.next()) {

            System.out.println("========"+num_quest+"=========");

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
            System.out.println(question);
            System.out.println(type);
            System.out.println(description);
            System.out.println(opts);
            System.out.println(section);
            System.out.println("\n\n");
            /**/

            if (type.equals("choice")){

                RadioGroup radioGroup = new RadioGroup("question" + num_quest, question);
                //拆分选项,生成选项
                Choice[] choices = addChoices(opts);
                radioGroup.setChoices(choices);
                //添加到Page.elements
                elements.add(radioGroup);
            } else if (type.equals("double")){
                Checkbox checkBox = new Checkbox("question" + num_quest, question);
                Choice[] choices = addChoices(opts);

                if (description != null) checkBox.setDescription(description);

                checkBox.setChoices(choices);
                //添加到Page.elements
                 elements.add(checkBox);
            } else if (type.equals("table")) {//multi-text
                MultipleText table = new MultipleText("question" + num_quest, question);
                table.setItems(addItems(opts));
                elements.add(table);
            } else if (type.equals("blank")) {
                Text text = new Text("question"+num_quest, question);

                if (question.matches("_.*_")) {
                    int first = question.indexOf('_');
                    System.out.println("first="+first);
                    int sec = question.lastIndexOf('_');
                    System.out.println("second="+sec);

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
            }
        }

        System.out.println(JSONObject.toJSONString(surveyJson));


    }
    public static Choice[] addChoices(String opts){
        //拆分选项
        String[] options = opts.split(",");
        Choice[] choices = new Choice[options.length];
        for (int i=0; i<options.length; i++) {
            //todo
            Choice choice = new Choice(i+"", options[i]);
            choices[i] = choice;
        }
        return choices;
    }
    public static List<Item> addItems(String opts){
        String[] options = opts.split(";");
        int size = options.length;
        List<Item> items = new ArrayList<>();
        //todo:获取title
        for (int i=0; i<size; i++)
            items.add(new Item( "", ""));//设置名字为空
        return items;

    }
    public void output(String JSONString){
        BufferedWriter writer = null;
        try {
             writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/resources/json.txt")));
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
