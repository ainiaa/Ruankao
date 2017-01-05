package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 *  题库Model
 * Created by Administrator on 2017/01/04.
 */
public class QuestionBankBO implements Serializable {

    public QuestionItemBO[] getQuestionItemBOs() {
        return QuestionItemBOs;
    }

    public void setQuestionItemBOs(QuestionItemBO[] questionItemBOs) {
        QuestionItemBOs = questionItemBOs;
    }

    public int getFirstQuestionItemId() {
        return firstQuestionItemId;
    }

    public void setFirstQuestionItemId(int firstQuestionItemId) {
        this.firstQuestionItemId = firstQuestionItemId;
    }

    public int getLastestQuestionItemId() {
        return lastestQuestionItemId;
    }

    public void setLastestQuestionItemId(int lastestQuestionItemId) {
        this.lastestQuestionItemId = lastestQuestionItemId;
    }

    private int firstQuestionItemId;//第一题id
    private int lastestQuestionItemId;//最后一题id
    private QuestionItemBO[] QuestionItemBOs;
}
