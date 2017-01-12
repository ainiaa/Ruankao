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

    /**
     * 初始化viewPager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.activity_question_detail_view_pager);
        ArrayList<View> views = new ArrayList<>();

        Intent intent = getIntent();
        Integer categoryId = intent.getIntExtra("categoryId", 0);
        String period = intent.getStringExtra("period");
        String extInfo = intent.getStringExtra("extInfo");
        QuestionBankService questionBankService = new QuestionBankService(categoryId, period, extInfo);
        //填充内容
        int max = questionBankService.getCount();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int id = 1; id <= max; id++) {
            View questionContainerView = inflater.inflate(R.layout.activity_question_detail_question_container, null);
            QuestionItemBO questionItem = questionBankService.getQuestionItemById(id);
            initUI(questionContainerView, questionItem);
            views.add(questionContainerView);
        }

        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }

    private void initUI(View questionContainerView, QuestionItemBO questionItem) {
        LinearLayout questionAnswerAnalysisLayout = (LinearLayout) questionContainerView.findViewById(R.id.question_answer_analysis_ll);//试题详解Layout
        TextView questionAnswerAnalysisTv = (TextView) questionContainerView.findViewById(R.id.question_answer_analysis_tv);//试题详解内容
        TextView questionTitleTv = (TextView) questionContainerView.findViewById(R.id.question_title_tv);//试题标题
        TextView questionNoTv = (TextView) questionContainerView.findViewById(R.id.question_no_tv);//试题No

        String questionDesc = questionItem.getQuestionDesc(); //问题描述
        String questionTitle = questionItem.getQuestionTitle();//问题题干
        String questionNo    = String.valueOf(questionItem.getNo());//问题序号
        String[] answers = questionItem.getAnswerList();//答案列表
        int rightAnswer = questionItem.getRightAnswer();//问题正确答案
        questionTitle = "              "  + questionTitle;//空出来icon内容（要不然显示不好看）
        questionAnswerAnalysisTv.setText(questionDesc);
        questionTitleTv.setText(questionTitle);
        questionNoTv.setText(questionNo);

        Integer[] answerIcons = new Integer[]{R.mipmap.ic_a_1, R.mipmap.ic_b_1, R.mipmap.ic_c_1, R.mipmap.ic_d_1, R.mipmap.ic_e_1, R.mipmap.ic_f_1, R.mipmap.ic_g_1};
        Integer rightAnswerId = answerIcons[rightAnswer];

        LayoutInflater inflater = LayoutInflater.from(this);
        Map<Integer, AnswerDetailView> answerDetailViewMap = new LinkedHashMap<>();
        LinearLayout questionAnswerListContainerLayout = (LinearLayout)questionContainerView.findViewById(R.id.question_answer_list_container);
        for (int i = 0; i < answers.length; i++) {
            Integer currentId = answerIcons[i];
            String currentAnswer = answers[i];
            AnswerDetailView currentAnswerDetailLayout = (AnswerDetailView) inflater.inflate(R.layout.activity_question_detail_single_answer_tpl, null);
            ImageView currentAnswerIconIv = (ImageView) currentAnswerDetailLayout.findViewById(R.id.answer_ic_iv);
            if (i < answerIcons.length) {
                currentAnswerIconIv.setImageResource(currentId);
            } else {// todo 还不清楚怎么处理
                currentAnswerIconIv.setImageResource(0);
            }
            TextView currentAnswerTextTv = (TextView) currentAnswerDetailLayout.findViewById(R.id.answer_text_tv);
            currentAnswerTextTv.setText(currentAnswer);
            currentAnswerDetailLayout.setRightAnswer(i == rightAnswer);
            currentAnswerDetailLayout.setId(currentId);
            answerDetailViewMap.put(currentId, currentAnswerDetailLayout);
        }
        View.OnClickListener onClickListener = new QuestionDetailActivity.AnswerOnClickListener(questionAnswerAnalysisLayout, rightAnswerId, answerDetailViewMap);
        for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
            answerDetailView.setOnClickListener(onClickListener);
            questionAnswerListContainerLayout.addView(answerDetailView);
        }
    }

    private class AnswerOnClickListener implements View.OnClickListener {
        private int rightAnswerId;
        private LinearLayout answerDescLayout;//试题详解Layout
        private Map<Integer, AnswerDetailView> answerDetailViewMap;

        public AnswerOnClickListener(LinearLayout answerDescLayout, int rightAnswerId, Map<Integer, AnswerDetailView> answerDetailViewMap) {
            this.answerDescLayout = answerDescLayout;
            this.rightAnswerId = rightAnswerId;
            this.answerDetailViewMap = answerDetailViewMap;
        }

        public void onClick(View v) {
            int id = v.getId();
            AnswerDetailView currentAnswerItem = answerDetailViewMap.get(id);
            ImageView currentImageView = (ImageView) currentAnswerItem.findViewById(R.id.answer_ic_iv);
            if (((AnswerDetailView) v).isRightAnswer()) {//当前为正确答案
                currentImageView.setImageResource(R.mipmap.ic_right_1);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);//跳转下一页
            } else { //当前选项不正确
                answerDescLayout.setVisibility(View.VISIBLE);
                currentImageView.setImageResource(R.mipmap.ic_error_1);
                //显示正确答案
                AnswerDetailView rightAnswerItem = answerDetailViewMap.get(rightAnswerId);
                ImageView rightImageView = (ImageView) rightAnswerItem.findViewById(R.id.answer_ic_iv);
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
