package com.a91coding.ruankao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionBankBO implements Serializable {

    private String period;
    private String category;
    private String periodToShow;
    private List<QuestionItemSingleAnswerBO> questionItemList;

    public List<QuestionItemSingleAnswerBO> getQuestionItemList() {
        return questionItemList;
    }

    public void setQuestionItemList(List<QuestionItemSingleAnswerBO> questionItemList) {
        this.questionItemList = questionItemList;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriodToShow() {
        return periodToShow;
    }

    public void setPeriodToShow(String periodToShow) {
        this.periodToShow = periodToShow;
    }
}
