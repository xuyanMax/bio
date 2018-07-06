package com.JsonGenerator;

import java.util.ArrayList;
import java.util.List;

public class SurveyJson {
    String locale;
    String title;
    List<Page> pages = new ArrayList<>();
    boolean sendResultOnPageNext;
    boolean showPageNumbers;
    String showProgressBar;
    String startSurveyText;
    String pagePrevText;
    String pageNextTest;
    String completeText;

    public SurveyJson() {
        this.locale ="zh-cn";
        this.title = "调查问卷";
        this.showPageNumbers = true;
        this.showProgressBar = "top";
        this.pagePrevText = "上一页";
        this.pageNextTest = "下一页";
        this.completeText = "提交";
        this.startSurveyText = "开始做题";
        this.sendResultOnPageNext = true;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public boolean isSendResultOnPageNext() {
        return sendResultOnPageNext;
    }

    public void setSendResultOnPageNext(boolean sendResultOnPageNext) {
        this.sendResultOnPageNext = sendResultOnPageNext;
    }

    public boolean isShowPageNumbers() {
        return showPageNumbers;
    }

    public void setShowPageNumbers(boolean showPageNumbers) {
        this.showPageNumbers = showPageNumbers;
    }

    public String getShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(String showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public String getStartSurveyText() {
        return startSurveyText;
    }

    public void setStartSurveyText(String startSurveyText) {
        this.startSurveyText = startSurveyText;
    }

    public String getPagePrevText() {
        return pagePrevText;
    }

    public void setPagePrevText(String pagePrevText) {
        this.pagePrevText = pagePrevText;
    }

    public String getPageNextTest() {
        return pageNextTest;
    }

    public void setPageNextTest(String pageNextTest) {
        this.pageNextTest = pageNextTest;
    }

    public String getCompleteText() {
        return completeText;
    }

    public void setCompleteText(String completeText) {
        this.completeText = completeText;
    }

    @Override
    public String toString() {
        return "SurveyJson{" +
                "locale='" + locale + '\'' +
                ", title='" + title + '\'' +
                ", pages=" + pages +
                ", sendResultOnPageNext=" + sendResultOnPageNext +
                ", showPageNumbers=" + showPageNumbers +
                ", showProgressBar='" + showProgressBar + '\'' +
                ", startSurveyText='" + startSurveyText + '\'' +
                ", pagePrevText='" + pagePrevText + '\'' +
                ", pageNextTest='" + pageNextTest + '\'' +
                ", completeText='" + completeText + '\'' +
                '}';
    }
}

