package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 * 题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionItemMultiAnswerBO implements Serializable {

    private int id;//问题ID
    private int no;//问题序号
    private String questionDesc;//问题详解
    private String descIllustration;//问题详解图片
    private String questionTitle;//问题题目
    private String testPoint;//考点
    private int questionType;//试题类型 0:单选题目，一题一问  1:单选题，一题多问
    private Integer[] rightAnswer;//多题正确答案
    private String[][] answers;//多题答案列表
    private int questionCount;//问题个数
    private String illustration;//插图
    private String[][] answerIllustrations;//多题答案插图

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

    public String getDescIllustration() { return descIllustration; }

    public void setDescIllustration(String descIllustration) { this.descIllustration = descIllustration;}

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
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

    public Integer[] getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer[] rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String[][] getAnswers() {
        return answers;
    }

    public void setAnswers(String[][] answers) {
        this.answers = answers;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String[][] getAnswerIllustrations() {
        return answerIllustrations;
    }

    public void setAnswerIllustrations(String[][] answerIllustrations) {
        this.answerIllustrations = answerIllustrations;
    }
}
