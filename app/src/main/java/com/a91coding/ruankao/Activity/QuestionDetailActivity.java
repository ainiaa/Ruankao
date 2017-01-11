package com.a91coding.ruankao.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a91coding.ruankao.R;
import com.a91coding.ruankao.Service.QuestionBankService;
import com.a91coding.ruankao.adapter.MYViewPagerAdapter;
import com.a91coding.ruankao.model.QuestionItemBO;
import com.a91coding.ruankao.ui.AnswerDetailView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initViewPager();
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ArrayList<View> views = new ArrayList<>();

        Intent intent = getIntent();
        Integer categoryId = intent.getIntExtra("categoryId", 0);
        String period = intent.getStringExtra("period");
        String extInfo = intent.getStringExtra("extInfo");
        QuestionBankService questionBankService = new QuestionBankService(categoryId, period, extInfo);
        //填充内容
        int max = questionBankService.getCount();
        for (int id = 1; id <= max; id++) {
            View view = LayoutInflater.from(this).inflate(R.layout.question_layout, null);
            initUI(view, id, questionBankService);
            views.add(view);
        }

        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }

    private void initUI(View view, int id, QuestionBankService questionBankService) {
        LinearLayout answerDescLayout = (LinearLayout) view.findViewById(R.id.answerDescLayout);//试题详解Layout
        TextView answerDesc = (TextView) view.findViewById(R.id.answerDescTextView);//试题详解内容
        TextView answerTitle = (TextView) view.findViewById(R.id.answerTitleTextView);//试题标题

        QuestionItemBO questionItem = questionBankService.getQuestionItemById(id);

        String questionDesc = questionItem.getQuestionDesc(); //问题描述
        String questionTitle = questionItem.getQuestionTitle();//问题题干
        String[] answers = questionItem.getAnswerList();//答案列表
        int rightAnswer = questionItem.getRightAnswer();//问题正确答案

        answerDesc.setText(questionDesc);
        answerTitle.setText(questionTitle);

        Integer[] answerIcon = new Integer[]{R.mipmap.ic_a_1, R.mipmap.ic_b_1, R.mipmap.ic_c_1, R.mipmap.ic_d_1, R.mipmap.ic_e_1, R.mipmap.ic_f_1, R.mipmap.ic_g_1};
        Integer rightAnswerId = answerIcon[rightAnswer];

        LayoutInflater inflater = LayoutInflater.from(this);
        Map<Integer, AnswerDetailView> answerDetailViewMap = new LinkedHashMap<>();
        for (int i = 0; i < answers.length; i++) {
            Integer currentId = answerIcon[i];
            String currentAnswer = answers[i];
            AnswerDetailView currentAnswerDetailLayout = (AnswerDetailView) inflater.inflate(R.layout.single_answer_detail_tpl, null);
            ImageView currentAnswerImageView = (ImageView) currentAnswerDetailLayout.findViewById(R.id.answerImageView);
            if (i < answerIcon.length) {
                currentAnswerImageView.setImageResource(currentId);
            } else {// todo 还不清楚怎么处理
                currentAnswerImageView.setImageResource(0);
            }
            TextView currentAnswerTextView = (TextView) currentAnswerDetailLayout.findViewById(R.id.answerTextView);
            currentAnswerTextView.setText(currentAnswer);
            currentAnswerDetailLayout.setRightAnswer(i == rightAnswer);
            currentAnswerDetailLayout.setId(currentId);
            answerDetailViewMap.put(currentId, currentAnswerDetailLayout);
        }
        View.OnClickListener onClickListener = new QuestionDetailActivity.AnswerOnClickListener(answerDescLayout, rightAnswerId, answerDetailViewMap);
        for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
            answerDetailView.setOnClickListener(onClickListener);
            View answerListContainer = view.findViewById(R.id.answerListContainer);
            ((LinearLayout) answerListContainer).addView(answerDetailView);
        }
    }

    private class AnswerOnClickListener implements View.OnClickListener {
        private int rightAnswerId;
        LinearLayout answerDescLayout;//试题详解Layout
        Map<Integer, AnswerDetailView> answerDetailViewMap;

        public AnswerOnClickListener(LinearLayout answerDescLayout, int rightAnswerId, Map<Integer, AnswerDetailView> answerDetailViewMap) {
            this.answerDescLayout = answerDescLayout;
            this.rightAnswerId = rightAnswerId;
            this.answerDetailViewMap = answerDetailViewMap;
        }

        public void onClick(View v) {
            int id = v.getId();
            AnswerDetailView currentAnswerItem = answerDetailViewMap.get(id);
            ImageView currentImageView = (ImageView) currentAnswerItem.findViewById(R.id.answerImageView);
            if (((AnswerDetailView) v).isRightAnswer()) {//当前为正确答案
                currentImageView.setImageResource(R.mipmap.ic_right_1);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);//跳转下一页
            } else { //当前选项不正确
                answerDescLayout.setVisibility(View.VISIBLE);
                currentImageView.setImageResource(R.mipmap.ic_error_1);
                //显示正确答案
                AnswerDetailView rightAnswerItem = answerDetailViewMap.get(rightAnswerId);
                ImageView rightImageView = (ImageView) rightAnswerItem.findViewById(R.id.answerImageView);
                rightImageView.setImageResource(R.mipmap.ic_right_1);
            }

            //已经选择了。 去掉onclick事件
            for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
                answerDetailView.setOnClickListener(null);
            }
        }
    }

    /**
     * 信息提示
     *
     * @param context
     * @param msg
     */
    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
