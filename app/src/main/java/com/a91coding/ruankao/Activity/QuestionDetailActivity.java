package com.a91coding.ruankao.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
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
import com.a91coding.ruankao.model.QuestionItemMultiAnswerBO;
import com.a91coding.ruankao.model.QuestionItemSingleAnswerBO;
import com.a91coding.ruankao.ui.AnswerDetailView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionDetailActivity extends RKBaseActivity {

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
        int max = questionBankService.getCount();//题目数量
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int id = 1; id <= max; id++) {
            View questionContainerView = inflater.inflate(R.layout.activity_question_detail_question_container, null);

            //设置header start
            TextView headerView = (TextView)questionContainerView.findViewById(R.id.question_common_header_content);
            headerView.setText(questionBankService.getCategory());
            //设置header end

            int questionType = questionBankService.getQuestionTypeById(id);
            if (questionType == 0) { //一题一问
                QuestionItemSingleAnswerBO questionItem = questionBankService.getQuestionItemSingleAnswerById(id);
                initUI(questionContainerView, questionItem);
            } else {//一题多问
                QuestionItemMultiAnswerBO questionItem = questionBankService.getQuestionItemMultiAnswerById(id);
                initUI(questionContainerView, questionItem);
            }

            //设置footer start
            TextView footerView = (TextView)questionContainerView.findViewById(R.id.question_common_footer_content);
            footerView.setText(id + "/" + max);
            //设置footer end

            views.add(questionContainerView);
        }

        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }

    /**
     * 一题多问
     * todo 自动跳转逻辑还没有处理  --- 答案不正正常显示
     * @param questionContainerView
     * @param questionItem
     */
    private void initUI(View questionContainerView, QuestionItemMultiAnswerBO questionItem) {
        LinearLayout questionAnswerAnalysisLayout = (LinearLayout) questionContainerView.findViewById(R.id.question_answer_analysis_ll);//试题详解Layout
        TextView questionAnswerAnalysisTv = (TextView) questionContainerView.findViewById(R.id.question_answer_analysis_tv);//试题详解内容
        TextView questionTitleTv = (TextView) questionContainerView.findViewById(R.id.question_title_tv);//试题标题
        TextView questionNoTv = (TextView) questionContainerView.findViewById(R.id.question_no_tv);//试题No

        String questionDesc = questionItem.getQuestionDesc(); //问题描述
        String questionTitle = questionItem.getQuestionTitle();//问题题干
        String questionNo = String.valueOf(questionItem.getNo());//问题序号
        String[][] answers = questionItem.getAnswerList();//答案列表
        Integer[] rightAnswer = questionItem.getRightAnswer();//问题正确答案
        int questionCount = questionItem.getQuestionCount();//问题个数
        questionTitle = "              " + questionTitle;//空出来icon内容（要不然显示不好看）

        questionAnswerAnalysisTv.setText(questionDesc);
        questionTitleTv.setText(questionTitle);
        questionNoTv.setText(questionNo);

        Integer[] answerIcons = new Integer[]{R.mipmap.ic_a_1, R.mipmap.ic_b_1, R.mipmap.ic_c_1, R.mipmap.ic_d_1, R.mipmap.ic_e_1, R.mipmap.ic_f_1, R.mipmap.ic_g_1};
        LayoutInflater inflater = LayoutInflater.from(this);
        Integer[] rightAnswerIds = new Integer[questionCount];
        LinearLayout questionAnswerListContainerLayout = (LinearLayout) questionContainerView.findViewById(R.id.question_answer_list_container);
        for (int i = 0; i < questionCount; i++) {
            int currentRightAnswer = rightAnswer[i];
            Integer rightAnswerId = currentRightAnswer;
            if (currentRightAnswer >= 0 && currentRightAnswer < answerIcons.length) {
                rightAnswerId = answerIcons[currentRightAnswer];
            }

            rightAnswerIds[i] = rightAnswerId;
            Map<Integer, AnswerDetailView> answerDetailViewMap = new LinkedHashMap<>();
            for (int j = 0; j < answers[i].length; j++) {
                Integer currentId = View.generateViewId();//获得id
                String currentAnswer = answers[i][j];
                //设置一个答案 start
                AnswerDetailView currentAnswerDetailLayout = (AnswerDetailView) inflater.inflate(R.layout.activity_question_detail_single_answer_tpl, null);
                //设置答案icon start
                ImageView currentAnswerIconIv = (ImageView) currentAnswerDetailLayout.findViewById(R.id.answer_ic_iv);
                if (j < answerIcons.length) {
                    Integer answerIconId = answerIcons[j];
                    currentAnswerIconIv.setImageResource(answerIconId);
                } else {// todo 还不清楚怎么处理
                    currentAnswerIconIv.setImageResource(0);
                }
                //设置答案icon end

                //设置答案text start
                TextView currentAnswerTextTv = (TextView) currentAnswerDetailLayout.findViewById(R.id.answer_text_tv);
                currentAnswerTextTv.setText(currentAnswer);
                //设置答案text end
                currentAnswerDetailLayout.setRightAnswer(j == currentRightAnswer);
                currentAnswerDetailLayout.setId(currentId);
                //设置一个答案 end
                answerDetailViewMap.put(currentId, currentAnswerDetailLayout);
            }
            if (i != questionCount -1) {
                AnswerDetailView separatorLaytout = (AnswerDetailView)inflater.inflate(R.layout.common_divider, null);
                Integer nextId = View.generateViewId();
                separatorLaytout.setViewType(1);
                answerDetailViewMap.put(nextId, separatorLaytout);
            }
            //设置各个答案的click事件 并添加到view中 start
            View.OnClickListener onClickListener = new MultiAnswerOnClickListener(questionAnswerAnalysisLayout, rightAnswerId, answerDetailViewMap);
            for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
                if (answerDetailView.getViewType() == 0) {
                    answerDetailView.setOnClickListener(onClickListener);
                }
                questionAnswerListContainerLayout.addView(answerDetailView);
            }
            //设置各个答案的click事件 并添加到view中 end
        }
    }

    /**
     * 一题一问
     * @param questionContainerView
     * @param questionItem
     */
    private void initUI(View questionContainerView, QuestionItemSingleAnswerBO questionItem) {
        LinearLayout questionAnswerAnalysisLayout = (LinearLayout) questionContainerView.findViewById(R.id.question_answer_analysis_ll);//试题详解Layout
        TextView questionAnswerAnalysisTv = (TextView) questionContainerView.findViewById(R.id.question_answer_analysis_tv);//试题详解内容
        TextView questionTitleTv = (TextView) questionContainerView.findViewById(R.id.question_title_tv);//试题标题
        TextView questionNoTv = (TextView) questionContainerView.findViewById(R.id.question_no_tv);//试题No
        ImageView illustrationIv = (ImageView) questionContainerView.findViewById(R.id.question_illustration_iv);//问题插图
        String questionDesc = questionItem.getQuestionDesc(); //问题描述
        String questionTitle = questionItem.getQuestionTitle();//问题题干
        String questionNo = String.valueOf(questionItem.getNo());//问题序号
        String illustration = questionItem.getIllustration();//问题插图
        String[] answers = questionItem.getAnswerList();//答案列表
        int rightAnswer = questionItem.getRightAnswer();//问题正确答案
        questionTitle = "              " + questionTitle;//空出来icon内容（要不然显示不好看）
        questionAnswerAnalysisTv.setText(questionDesc);
        questionTitleTv.setText(questionTitle);
        questionNoTv.setText(questionNo);
        illustrationIv.setImageResource(getResource(illustration));

        Integer[] answerIcons = new Integer[]{R.mipmap.ic_a_1, R.mipmap.ic_b_1, R.mipmap.ic_c_1, R.mipmap.ic_d_1, R.mipmap.ic_e_1, R.mipmap.ic_f_1, R.mipmap.ic_g_1};
        Integer rightAnswerId;//正确答案id
        if (rightAnswer == -1) {
            rightAnswerId = -1;
        }  else {
            rightAnswerId = answerIcons[rightAnswer];
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        Map<Integer, AnswerDetailView> answerDetailViewMap = new LinkedHashMap<>();
        LinearLayout questionAnswerListContainerLayout = (LinearLayout) questionContainerView.findViewById(R.id.question_answer_list_container);
        for (int i = 0; i < answers.length; i++) {
            Integer currentId = View.generateViewId();//当前view Id
            String currentAnswer = answers[i];//当前答案
            AnswerDetailView currentAnswerDetailLayout = (AnswerDetailView) inflater.inflate(R.layout.activity_question_detail_single_answer_tpl, null);
            ImageView currentAnswerIconIv = (ImageView) currentAnswerDetailLayout.findViewById(R.id.answer_ic_iv);

            //设置答案ICON start
            if (i < answerIcons.length) {
                Integer currentAnswerIconId = answerIcons[i];//当前view Id
                currentAnswerIconIv.setImageResource(currentAnswerIconId);
            } else {// todo 还不清楚怎么处理
                currentAnswerIconIv.setImageResource(0);
            }
            //设置答案ICON end

            //设置答案文字 start
            TextView currentAnswerTextTv = (TextView) currentAnswerDetailLayout.findViewById(R.id.answer_text_tv);
            currentAnswerTextTv.setText(currentAnswer);
            //设置答案文字 end

            currentAnswerDetailLayout.setRightAnswer(i == rightAnswer);

            currentAnswerDetailLayout.setId(currentId);

            answerDetailViewMap.put(currentId, currentAnswerDetailLayout);
        }
        //设置各个答案的click事件 并添加到view中 start
        View.OnClickListener onClickListener = new SingleAnswerOnClickListener(questionAnswerAnalysisLayout, rightAnswerId, answerDetailViewMap);
        for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
            answerDetailView.setOnClickListener(onClickListener);
            questionAnswerListContainerLayout.addView(answerDetailView);
        }
        //设置各个答案的click事件 并添加到view中
    }

    /**
     * 一题多问 onClick
     */
    private class MultiAnswerOnClickListener implements View.OnClickListener {
        private int rightAnswerId;
        private LinearLayout answerDescLayout;//试题详解Layout
        private Map<Integer, AnswerDetailView> answerDetailViewMap;

        public MultiAnswerOnClickListener(LinearLayout answerDescLayout, int rightAnswerId, Map<Integer, AnswerDetailView> answerDetailViewMap) {
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
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);//跳转下一页
            } else { //当前选项不正确
                answerDescLayout.setVisibility(View.VISIBLE);
                currentImageView.setImageResource(R.mipmap.ic_error_1);
                //显示正确答案
                AnswerDetailView rightAnswerItem = answerDetailViewMap.get(rightAnswerId);
                if (rightAnswerItem != null) {
                    ImageView rightImageView = (ImageView) rightAnswerItem.findViewById(R.id.answer_ic_iv);
                    rightImageView.setImageResource(R.mipmap.ic_right_1);
                }
            }

            //已经选择了。 去掉onclick事件
            for (AnswerDetailView answerDetailView : answerDetailViewMap.values()) {
                answerDetailView.setOnClickListener(null);
            }
        }
    }

    /**
     * 一题一问 onClick
     */
    private class SingleAnswerOnClickListener implements View.OnClickListener {
        private int rightAnswerId;
        private LinearLayout answerDescLayout;//试题详解Layout
        private Map<Integer, AnswerDetailView> answerDetailViewMap;

        public SingleAnswerOnClickListener(LinearLayout answerDescLayout, int rightAnswerId, Map<Integer, AnswerDetailView> answerDetailViewMap) {
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
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);//跳转下一页
            } else { //当前选项不正确
                answerDescLayout.setVisibility(View.VISIBLE);
                currentImageView.setImageResource(R.mipmap.ic_error_1);
                //显示正确答案
                AnswerDetailView rightAnswerItem = answerDetailViewMap.get(rightAnswerId);
                if (rightAnswerItem != null) {
                    ImageView rightImageView = (ImageView) rightAnswerItem.findViewById(R.id.answer_ic_iv);
                    rightImageView.setImageResource(R.mipmap.ic_right_1);
                }
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
