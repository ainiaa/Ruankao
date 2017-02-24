package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 * 题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionItemSingleAnswerBO implements Serializable {

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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }
    public String[] getAnswerIllustrations() {
        return answerIllustrations;
    }
    public void setAnswerIllustrations(String[] answerIllustrations) {
        this.answerIllustrations = answerIllustrations;
    }
    private int id;//问题ID
    private int no;//问题序号
    private String questionDesc;//问题详解
    private String questionTitle;//问题题目
    private int rightAnswer;//正确答案
    private String[] answerList;//问题答案
    private String testPoint;//考点
    private int questionType;//试题类型 0:单选题目，一题一问  1:单选题，一题多问
    private String illustration;//插图
    private String[] answerIllustrations;//答案插图
}
