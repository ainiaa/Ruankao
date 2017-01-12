package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 * 题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionItemMultiAnswerBO implements Serializable {

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

    public int[] getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int[] rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String[][] getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String[][] answerList) {
        this.answerList = answerList;
    }
    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
    private int id;//问题ID
    private int no;//问题序号
    private String questionDesc;//问题详解
    private String questionTitle;//问题题目
    private String testPoint;//考点
    private int questionType;//试题类型 0:单选题目，一题一问  1:单选题，一题多问
    private int[] rightAnswer;//多题正确答案
    private String[][] answerList;//多题答案列表
    private int questionCount;//问题个数
}
