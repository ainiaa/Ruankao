package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 *  题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionItemBO implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String[] getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String[] answerList) {
        this.answerList = answerList;
    }
    public String getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(String testPoint) {
        this.testPoint = testPoint;
    }


    private int id;//问题ID
    private String questionDesc;//问题详解
    private String questionTitle;//问题题目
    private int rightAnswer;//正确答案
    private String[] answerList;//问题答案
    private String testPoint;//考点
}
