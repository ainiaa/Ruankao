package com.a91coding.ruankao.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.a91coding.ruankao.R;

/**
 * TODO: document your custom view class.
 */
public class AnswerDetailView extends LinearLayout {


    private boolean isRightAnswer = false;
    private int viewType = 0; //0 普通的view  1：不需要接受click的view

    public AnswerDetailView(Context context) {
        super(context);
        init(null, 0);
    }

    public AnswerDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }


    public AnswerDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.AnswerDetailView, defStyle, 0);

        isRightAnswer = a.getBoolean(R.styleable.AnswerDetailView_isRightAnswer, false);
        viewType = a.getInteger(R.styleable.AnswerDetailView_viewType, 0);
        a.recycle();
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

}
