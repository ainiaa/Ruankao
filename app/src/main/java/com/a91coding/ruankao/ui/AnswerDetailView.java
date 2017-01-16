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

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

    private boolean isRightAnswer = false;
    private int viewType = 0;

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
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.AnswerDetailView, defStyle, 0);

        isRightAnswer = a.getBoolean(R.styleable.AnswerDetailView_isRightAnswer, false);

        a.recycle();
    }
}
