package com.bio.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: xyx
 * @Date: 2019-04-27 23:09
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class InformedConsentUtilTest {
    String html ="<html>\n" +
            "\n" +
            "<head>\n" +
            "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" +
            "<meta name=Generator content=\"Microsoft Word 15 (filtered)\">\n" +
            "<style>\n" +
            "<!--\n" +
            " /* Font Definitions */\n" +
            " @font-face\n" +
            "\t{font-family:宋体;\n" +
            "\tpanose-1:2 1 6 0 3 1 1 1 1 1;}\n" +
            "@font-face\n" +
            "\t{font-family:\"Cambria Math\";\n" +
            "\tpanose-1:2 4 5 3 5 4 6 3 2 4;}\n" +
            "@font-face\n" +
            "\t{font-family:\"Arial Unicode MS\";\n" +
            "\tpanose-1:2 11 6 4 2 2 2 2 2 4;}\n" +
            "@font-face\n" +
            "\t{font-family:\"Helvetica Neue\";\n" +
            "\tpanose-1:2 0 5 3 0 0 0 2 0 4;}\n" +
            "@font-face\n" +
            "\t{font-family:\"\\@宋体\";\n" +
            "\tpanose-1:2 1 6 0 3 1 1 1 1 1;}\n" +
            "@font-face\n" +
            "\t{font-family:\"\\@Arial Unicode MS\";\n" +
            "\tpanose-1:2 11 6 4 2 2 2 2 2 4;}\n" +
            " /* Style Definitions */\n" +
            " p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
            "\t{margin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "h1\n" +
            "\t{mso-style-link:\"标题 1 字符\";\n" +
            "\tmargin-top:17.0pt;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:16.5pt;\n" +
            "\tmargin-left:0cm;\n" +
            "\ttext-align:justify;\n" +
            "\ttext-justify:inter-ideograph;\n" +
            "\tline-height:240%;\n" +
            "\tpage-break-after:avoid;\n" +
            "\tfont-size:22.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tfont-weight:bold;}\n" +
            "h2\n" +
            "\t{mso-style-link:\"标题 2 字符\";\n" +
            "\tmargin-top:13.0pt;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:13.0pt;\n" +
            "\tmargin-left:24.0pt;\n" +
            "\ttext-align:justify;\n" +
            "\ttext-justify:inter-ideograph;\n" +
            "\ttext-indent:-24.0pt;\n" +
            "\tline-height:173%;\n" +
            "\tpage-break-after:avoid;\n" +
            "\tfont-size:16.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tfont-weight:bold;}\n" +
            "p.MsoHeader, li.MsoHeader, div.MsoHeader\n" +
            "\t{mso-style-link:\"页眉 字符\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoFooter, li.MsoFooter, div.MsoFooter\n" +
            "\t{mso-style-link:\"页脚 字符\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "a:link, span.MsoHyperlink\n" +
            "\t{text-decoration:underline;}\n" +
            "a:visited, span.MsoHyperlinkFollowed\n" +
            "\t{color:fuchsia;\n" +
            "\ttext-decoration:underline;}\n" +
            "p\n" +
            "\t{margin-right:0cm;\n" +
            "\tmargin-left:0cm;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:宋体;}\n" +
            "p.MsoAcetate, li.MsoAcetate, div.MsoAcetate\n" +
            "\t{mso-style-link:\"批注框文本 字符\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:9.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoRMPane, li.MsoRMPane, div.MsoRMPane\n" +
            "\t{margin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph\n" +
            "\t{margin-top:0cm;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:0cm;\n" +
            "\tmargin-left:36.0pt;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst\n" +
            "\t{margin-top:0cm;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:0cm;\n" +
            "\tmargin-left:36.0pt;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle\n" +
            "\t{margin-top:0cm;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:0cm;\n" +
            "\tmargin-left:36.0pt;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast\n" +
            "\t{margin-top:0cm;\n" +
            "\tmargin-right:0cm;\n" +
            "\tmargin-bottom:0cm;\n" +
            "\tmargin-left:36.0pt;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Times New Roman\",serif;}\n" +
            "p.a, li.a, div.a\n" +
            "\t{mso-style-name:页眉与页脚;\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:12.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tcolor:black;}\n" +
            "p.1, li.1, div.1\n" +
            "\t{mso-style-name:正文1;\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:11.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tcolor:black;}\n" +
            "p.10, li.10, div.10\n" +
            "\t{mso-style-name:副标题1;\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tpage-break-after:avoid;\n" +
            "\tfont-size:20.0pt;\n" +
            "\tfont-family:\"Arial Unicode MS\",sans-serif;\n" +
            "\tcolor:black;}\n" +
            "p.2, li.2, div.2\n" +
            "\t{mso-style-name:\"表格样式 2\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:10.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tcolor:black;}\n" +
            "p.a0, li.a0, div.a0\n" +
            "\t{mso-style-name:大标题;\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tpage-break-after:avoid;\n" +
            "\tfont-size:30.0pt;\n" +
            "\tfont-family:\"Arial Unicode MS\",sans-serif;\n" +
            "\tcolor:black;\n" +
            "\tfont-weight:bold;}\n" +
            "p.a1, li.a1, div.a1\n" +
            "\t{mso-style-name:小标题;\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tpage-break-after:avoid;\n" +
            "\tfont-size:18.0pt;\n" +
            "\tfont-family:\"Arial Unicode MS\",sans-serif;\n" +
            "\tcolor:black;\n" +
            "\tfont-weight:bold;}\n" +
            "p.a2, li.a2, div.a2\n" +
            "\t{mso-style-name:\"正文 问卷\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tline-height:120%;\n" +
            "\tfont-size:11.0pt;\n" +
            "\tfont-family:\"Arial Unicode MS\",sans-serif;\n" +
            "\tcolor:black;}\n" +
            "p.11, li.11, div.11\n" +
            "\t{mso-style-name:\"表格样式 1\";\n" +
            "\tmargin:0cm;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:10.0pt;\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tcolor:black;\n" +
            "\tfont-weight:bold;}\n" +
            "span.a3\n" +
            "\t{mso-style-name:\"批注框文本 字符\";\n" +
            "\tmso-style-link:批注框文本;}\n" +
            "span.a4\n" +
            "\t{mso-style-name:\"页眉 字符\";\n" +
            "\tmso-style-link:页眉;}\n" +
            "span.a5\n" +
            "\t{mso-style-name:\"页脚 字符\";\n" +
            "\tmso-style-link:页脚;}\n" +
            "span.12\n" +
            "\t{mso-style-name:\"标题 1 字符\";\n" +
            "\tmso-style-link:\"标题 1\";\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tfont-weight:bold;}\n" +
            "span.20\n" +
            "\t{mso-style-name:\"标题 2 字符\";\n" +
            "\tmso-style-link:\"标题 2\";\n" +
            "\tfont-family:\"Helvetica Neue\";\n" +
            "\tfont-weight:bold;}\n" +
            ".MsoChpDefault\n" +
            "\t{font-size:10.0pt;}\n" +
            " /* Page Definitions */\n" +
            " @page WordSection1\n" +
            "\t{size:595.3pt 841.9pt;\n" +
            "\tmargin:2.0cm 2.0cm 2.0cm 2.0cm;}\n" +
            "div.WordSection1\n" +
            "\t{page:WordSection1;}\n" +
            " /* List Definitions */\n" +
            " ol\n" +
            "\t{margin-bottom:0cm;}\n" +
            "ul\n" +
            "\t{margin-bottom:0cm;}\n" +
            "-->\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body lang=ZH-CN link=\"#000000\" vlink=fuchsia>\n" +
            "\n" +
            "<div class=WordSection1>\n" +
            "\n" +
            "<p class=1 align=center style='text-align:center'><b><span style='font-size:\n" +
            "12.0pt;font-family:宋体'>受检者知情同意书</span></b></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>尊敬的受检者：</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>       \n" +
            "</span><span style='font-family:宋体'>我们将邀请您参加</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>****</span><span style='font-family:\n" +
            "宋体'>批准开展的《基于职业人群队列的中国常见遗传性肿瘤大规模筛查及分子诊断数据库建设项目》的项目研究。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>本知情同意书提供给您一些信息以帮助您决定是否参加此项研究。请您仔细阅读，如有任何疑问请向负责该项研究的研究者提出。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>您参加本项研究是自愿的。本次研究已通过</span><span\n" +
            "lang=EN-US style='font-family:\"Times New Roman\",serif'>******</span><span\n" +
            "style='font-family:宋体'>（伦理委员会）审查。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>       \n" +
            "</span><span style='font-family:宋体'>如果您愿意，请仔细阅读以下内容：</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>方案名称：</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>******</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>方案版本号与日期：</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>******</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>研究中心：</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>******</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>主要研究者：</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>******</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>一、研究目的</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>恶性肿瘤本质上是基因性疾病，基因发生改变，相应功能丧失或过度激活可引发肿瘤。遗传因素是家族聚集性肿瘤高发的主因，生活习性和各种暴露因素也有一定影响。典型的遗传性肿瘤包括遗传性乳腺癌</span><span\n" +
            "lang=EN-US style='font-family:\"Times New Roman\",serif'>/</span><span\n" +
            "style='font-family:宋体'>卵巢癌和</span><span lang=EN-US style='font-family:\"Times New Roman\",serif'>Lynch</span><span\n" +
            "style='font-family:宋体'>综合征（非息肉性结肠癌）；遗传性弥漫性胃癌发病率不高，但发病年龄早、危害大；部分肺癌也被纳入遗传性肿瘤。响应“关口前移、预防为主”的国家总体导向，基于大数据，对遗传因素和生活方式等外部因素进行相对定量分析，对肿瘤精准防控有重要意义。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>通过构建中国疾控系统</span><span\n" +
            "lang=EN-US style='font-family:\"Times New Roman\",serif'>10</span><span\n" +
            "style='font-family:宋体'>万名职员健康信息队列数据库，对其体检、生活习性、环境暴露、家族病史等的大数据分析，筛选肿瘤高风险者，补充基因型信息，并给予降低发病几率的生活指导。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>二、研究过程</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>       \n" +
            "</span><span style='font-family:宋体'>项目拟构建中国疾控系统</span><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>10</span><span style='font-family:\n" +
            "宋体'>万名职员健康信息队列数据库，对其体检、生活习性、环境暴露、家族病史等的大数据分析，筛选肿瘤高风险者。<span style='background:\n" +
            "#D9D9D9'>本项目以体检时间为周期，进行为期</span></span><span lang=EN-US style='font-family:\n" +
            "\"Times New Roman\",serif;background:#D9D9D9'>**</span><span style='font-family:\n" +
            "宋体;background:#D9D9D9'>年的问卷随访。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>如果您同意参加这项项目，我们对每位受检者编号，建立个人档案。在研究过程中，您将需要填写一份调查问卷，问卷内容包括：生活习惯，基本信息，疾病史，家族史几个部分。男性版本问卷共有</span><span\n" +
            "lang=EN-US style='font-family:\"Times New Roman\",serif'>85</span><span\n" +
            "style='font-family:宋体'>题，女性问卷共有</span><span lang=EN-US style='font-family:\"Times New Roman\",serif'>94</span><span\n" +
            "style='font-family:宋体'>题，预计需要花费您十分钟填写问卷。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>三、风险与不适</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:22.0pt'><span style='font-family:宋体'>本阶段性项目仅采用问卷的方式收集您的相关信息，数据库管理系统将会采取所有当前技术条件下的有效安全措施来保护您的隐私，且每一名项目相关研究者都签署《保密协议》。数据库系统不会保存受检者的姓名及任何敏感性隐私数据（如身份证号码等），且研究成果发布时将对数据匿名，即不能以可识别数据的形式发布。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>四、收益</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>       \n" +
            "</span><span style='font-family:宋体'>我们将根据您的问卷里的部分信息利用多种癌症风险评估模型计算患癌风险，并出具报告反馈评估结果及生活习性指导。</span><span\n" +
            "style='font-family:宋体;color:black'>问卷风险评估为高危的受检者，可申请继续参加项目提供的免费的基因检测进一步筛查。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>       \n" +
            "</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体;color:black'>五、个人权益</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif;\n" +
            "color:black'>        </span><span style='font-family:宋体;color:black'>受检者完全自由参加，若不愿意参与可以拒绝；受检者可以自由退出本项目；问卷风险评估为高危的受检者，可自愿选择是否继续参加基因检测。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif;\n" +
            "color:black'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1 align=center style='text-align:center'><b><span style='font-size:\n" +
            "12.0pt;font-family:宋体'>受检者知情同意书</span></b></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我已充分理解本项目的性质、预期目的、风险和必要性。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我已获得承诺个人信息将得到隐私保护，并同意在信息或数据脱敏后用于本项目及相关的研究，没有个人信息泄漏风险。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我并未获得本项目检测方法百分之百准确率的承诺，并充分理解报告中所涉及的评估结果、生活习性指导及相关报告仅对此次问卷调查负责。不代表临床诊断意见，临床诊断和后续建议需咨询临床医生。若因受检者不当使用该结果，由此带来的心理、生理负担，医院及项目执行机构不承担责任和风险。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我承诺本人提供的个人资料和反映的情况真实可靠。</span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我有权随时退出本项目，但在无特殊情况下，尽可能完整的接受本项目的研究。</span><span\n" +
            "style='font-family:\"Times New Roman\",serif'> </span></p>\n" +
            "\n" +
            "<p class=1 style='text-indent:36.0pt'><span style='font-family:宋体'>我经慎重考虑自愿参加本项目，并按照要求完成本项目。</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>项目负责人：</span></p>\n" +
            "\n" +
            "<p class=1><span style='font-family:宋体'>中国疾病预防控制中心慢性非传染性疾病预防控制中心</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "<p class=1 align=right style='margin-left:252.0pt;text-align:right'><span\n" +
            "style='font-family:宋体'>受检者签名：</span><u><span lang=EN-US style='font-family:\n" +
            "\"Times New Roman\",serif'>               </span></u><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'> </span><span style='font-family:\n" +
            "宋体'>日期：</span><span lang=EN-US style='font-family:\"Times New Roman\",serif'>20<u>   \n" +
            "</u></span><span style='font-family:宋体'>年</span><u><span lang=EN-US\n" +
            "style='font-family:\"Times New Roman\",serif'>    </span></u><span\n" +
            "style='font-family:宋体'>月</span><u><span lang=EN-US style='font-family:\"Times New Roman\",serif'>   \n" +
            "</span></u><span style='font-family:宋体'>日</span></p>\n" +
            "\n" +
            "<p class=1><span lang=EN-US style='font-family:\"Times New Roman\",serif'>&nbsp;</span></p>\n" +
            "\n" +
            "</div>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";

    @Test
    public void getStyle() {
        System.out.println(InformedConsentUtil.getStyle(html));
    }

    @Test
    public void getBody() {
        System.out.println(InformedConsentUtil.getBody(html));
    }
}