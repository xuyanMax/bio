package com.JsonGenerator;

import com.JsonGenerator.element.*;
import com.JsonGenerator.type.*;
import com.alibaba.fastjson.JSONObject;
import com.bio.Utils.SSHConnection;
import com.bio.beans.Answer;
import com.bio.service.IAnswerService;
import com.jcraft.jsch.JSchException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.*;

public class FetchData {
    private static Logger logger = Logger.getLogger(FetchData.class);
    private static int NUM_PER_PAGE = 10;
    private static String SQL_ALL = "SELECT * FROM questions";
    private static String SQL_TABLE = "SELECT * FROM questions where types = \'blank\' limit 2";
    private static String SQL = "SELECT a.* FROM questions as a, qtnaire_version as b where a.idquestion=b.idquestion and b.version=?";
    private static String SQL_REPEAT = "SELECT DISTINCT * from (select c.*, d.`sup1` FROM questions as c, qtnaire_version as d where c.idquestion=d.idquestion and d.version=? order by d.idquestion DESC) n\n" +
            "union all\n" +
            "SELECT DISTINCT * from (select a.*, b.`sup1` from questions as a inner join `qtnaire_version` as b on a.idquestion=b.idquestion and b.sup1='repeat' and b.version=? order by b.idquestion DESC) m";
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
    private static String UNDERSCORE = "_";
    private static String QUESTIONMARK = "?";
    private static String AMPERSAND = "&";
    private static String EQUALSIGN = "=";
    public static List<Integer> firstValues;

    //参考 www.cnblogs.com/guodefu909/p/5805667.html
    public static void main(String[] args) {
//        try {
//            SSHConnection sshConnection = new SSHConnection();
//            System.out.println(getSurveyJSON(5));

            String sql = "select `lifetime_risk` from risk_crcmale where factor1=50 and factor2=0 and factor3=1 and factor4=1 and factor5=1 and factor6=0 and factor7=1 and factor8=0 and factor9=1 and factor10=0";
            Connection connection = null;
            try {
                connection = FetchData.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                logger.info(rs.getString(0));
            } catch (SQLException e) {
                e.printStackTrace();
            }

//        } catch (JSchException e) {
//            e.printStackTrace();
//        }
    }

    public static List<Integer> getFirstValues() {
        return firstValues;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SSHConnection.JDBC_URL, SSHConnection.DB_USERNAME, SSHConnection.DB_PASSWORD);
    }

    public static String getSurveyJSON(int version) throws JSchException {

        //单独跑main()方法连接远程库时候需要本句，连接本地库不需要
        //配合SpringMVC使用，则注释掉该句
        Connection conn = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        SurveyJson surveyJson = new SurveyJson();
        firstValues = new ArrayList<>();

        try {
            Class.forName(SSHConnection.JDBC_DRIVER);
            //远程库
            conn = getConnection();
            //本地库
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cdcDev",SSHConnection.DB_USERNAME,SSHConnection.DB_PASSWORD);
//            statement = conn.createStatement();
            Set<Integer> repeated = selectRepeatQuestions(conn, version);
            firstValues.addAll(repeated);
            preparedStatement = conn.prepareStatement(SQL_REPEAT);
            preparedStatement.setInt(1, version);
            preparedStatement.setInt(2, version);
            rs = preparedStatement.executeQuery();
//            rs = statement.executeQuery(SQL);
//            rs.last();
//            int RsSize = rs.getRow();
//            logger.info(RsSize);
//            rs.beforeFirst();

            //获取数据
            int num_quest = 0;
            Page page;
            List<BaseQuestion> elements = null;
            boolean flag = false;
            while (rs.next()) {
                if (num_quest % NUM_PER_PAGE == 0) {
                    page = new Page();
                    page.setName("page" + num_quest / NUM_PER_PAGE);
                    elements = new ArrayList<>();
                    page.setElements(elements);
                    surveyJson.getPages().add(page);
                }
                num_quest++;

                Integer idquestion = rs.getInt("idquestion");
                String question = rs.getString("question");
                String type = rs.getString("types");
                String opts = rs.getString("options");
                String description = rs.getString("note");
                String section = rs.getString("section");
                String supporting = rs.getString("supporting");

                if (repeated != null && !repeated.isEmpty() && repeated.contains(idquestion))
                    repeated.remove(idquestion);

                if (type.equals("choice")) {

                    RadioGroup radioGroup = new RadioGroup("" + idquestion + (flag ? "_" : ""), question);
                    //拆分选项,生成选项
                    List<Choice> choices = addChoices(opts);
                    radioGroup.setChoices(choices);
                    //添加到Page.elements
                    elements.add(radioGroup);
                } else if (type.equals("double")) {
                    Checkbox checkBox = new Checkbox("" + idquestion + (flag ? "_" : ""), question);
                    List<Choice> choices = addChoices(opts);

                    if (description != null) checkBox.setDescription(description);

                    checkBox.setChoices(choices);
                    //添加到Page.elements
                    elements.add(checkBox);
                } else if (type.equals("table")) {

                    MatrixDynamic matrixDynamic = new MatrixDynamic("" + idquestion + (flag ? "_" : ""), question);
                    AssembleMatrixDynamic(matrixDynamic, description, opts);
                    elements.add(matrixDynamic);

                } else if (type.equals("blank")) {
                    int size = question.contains(SEMI_COLUMN) ? question.split(SEMI_COLUMN).length : -1;
                    if (size < 0) {
                        Text text = generateSingleText(idquestion, question, opts);
                        if (description != null) text.setDescription(description);
                        elements.add(text);
                    } else {

                        MultipleText multipleText = new MultipleText("" + idquestion + (flag ? "_" : ""),
                                generateMultiTextTitle(question));

                        multipleText.setItems(multiTextAddItems(question));

                        if (description != null) multipleText.setDescription(description);
                        elements.add(multipleText);
                    }
                }
                if (repeated.isEmpty())
                    flag = true;
            }
            logger.info("【问卷题目数目】=" + num_quest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
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

    public static Text generateSingleText(int idquestion, String question, String opts) {
        String title;
        title = question.substring(0, question.indexOf(REG_START));
        Text text = new Text(
                "" + idquestion,
                title
        );
        //添加正则判断
        if (question.contains(REG_START) && question.contains(REG_END)) {
            System.out.println("\n" + question + "\n");
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

    public static List<Choice> addChoices(String opts) {
        if (opts == null || opts.equals(""))
            return new ArrayList<>();
        //拆分选项
        String[] options = opts.split(COMMA);
        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            Choice choice = new Choice(i + "", options[i]);
            choices.add(choice);
        }
        return choices;
    }

    public static String generateMultiTextTitle(String question) {
        String[] split = question.split(SEMI_COLUMN);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            builder
                    .append(split[i].substring(0, split[i].indexOf(REG_START)))
                    .append(SEMI_COLUMN_);
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    public static void generateMultiTextValidators(MultipleText multipleText, String question) {
        String[] split = question.split(SEMI_COLUMN);
        for (int i = 0; i < split.length; i++) {
            String regex = split[i].substring(split[i].indexOf(REG_START), split[i].lastIndexOf(REG_END) + 1);
            ValidatorRegex validator = new ValidatorRegex(regex);
            multipleText.getValidators().add(validator);
        }
    }

    public static List<Item> multiTextAddItems(String question) {
        if (question == null || question.equals(""))
            return new ArrayList<>();
        String[] subqustions = question.split(SEMI_COLUMN);
        int size = subqustions.length;
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            items.add(new Item("", ""));
            if (subqustions[i].contains(REG_START) && subqustions[i].contains(REG_END)) {//添加正则表达
                int first = subqustions[i].indexOf(REG_START);
                int second = subqustions[i].lastIndexOf(REG_END);
                String regex = subqustions[i].substring(first, second + 1);
                ValidatorRegex validatorRegex = new ValidatorRegex(regex);

                if (subqustions[i].contains(HASH)) {//添加错误提示
                    String text = subqustions[i].substring(subqustions[i].indexOf(HASH) + 1);
                    //todo 错误提示错误地将后面地?text1=name1&text2=name2传入提示
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
            for (int j = 0; j < names.size(); j++) {
                String name = names.get(j).substring(names.get(j).indexOf(EQUALSIGN) + 1);
                items.get(j).setName(name);
                items.get(j).setTitle(name);
            }
        }
        return items;

    }

    public static void AssembleMatrixDynamic(MatrixDynamic matrixDynamic, String description, String opts) {
        if (description != null && !description.equals(""))
            matrixDynamic.setDescription(description);
        if (opts == null || opts.equals(""))
            return;
        String[] splits = opts.split(COMMA);
        int index = 0;
        for (String split : splits) {
            String name;
            if (split.contains(LEFT_BRACKET))
                name = split.substring(0, split.indexOf(LEFT_BRACKET));//阶段（小学、中学、大学、硕士、博士）,开始时间（XX年）,结束时间（XX年）,地点（XX省XX市/县）,邮编
            else name = split;

            Column column = new Column();

            if (split.contains("d")) {//table类型第一列标识为dropdown下拉菜单
                if (index++ != 0) {
                    column.setCellType("text");
                    column.setName(name);
                } else {
                    column.setCellType("dropdown");
                    List<String> choices = new ArrayList<>();
                    String choice = split.substring(split.indexOf(LEFT_BRACKET) + 2, split.indexOf(RIGHT_BRACKET));

                    Arrays.stream(choice.split(DUNHAO)).forEach((c) -> {
                        choices.add(c);
                    });
                    column.setChoices(choices);
                }
            } else {
                column.setCellType("text");
                column.setName(name);//XX年
            }
            column.setName(name);
            matrixDynamic.getColumns().add(column);
        }
    }

    public static String sql = "select a.idquestion from questions as a, qtnaire_version as b " +
            "where a.idquestion=b.idquestion and b.sup1='repeat' and b.version=?";

    public static Set<Integer> selectRepeatQuestions(Connection connection, int version) {

        Set<Integer> repeatIdquestions = new HashSet<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, version);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) repeatIdquestions.add(rs.getInt("idquestion"));

            logger.info(repeatIdquestions);
            return repeatIdquestions;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return repeatIdquestions;
        }
    }
}
