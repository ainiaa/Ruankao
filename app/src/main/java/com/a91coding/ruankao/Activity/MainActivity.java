package com.a91coding.ruankao.Activity;
//https://github.com/wyouflf/xUtils3   -- 后面添加(现在使用最原始的方式)

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.a91coding.ruankao.R;
import com.a91coding.ruankao.Service.QuestionBankService;
import com.a91coding.ruankao.adapter.MYViewPagerAdapter;
import com.a91coding.ruankao.model.QuestionItemBO;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

        //todo for test
        Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
        startActivity(intent);
    }

    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ArrayList<View> views = new ArrayList<>();
        QuestionBankService questionBankService = new QuestionBankService();
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

    /**
     * UI初始化
     *
     * @param view
     * @param id
     */
    private void initUI(View view, int id, QuestionBankService questionBankService) {
         Map<Integer, Map<String, Object>> answerMap = new HashMap<>();//试题答案相关内容
         LinearLayout answerDescLayout = (LinearLayout) view.findViewById(R.id.answerDescLayout);//试题详解Layout
         TextView answerDesc = (TextView) view.findViewById(R.id.answerDescTextView);//试题详解内容
         TextView answerTitle = (TextView) view.findViewById(R.id.answerTitleTextView);//试题标题

         LinearLayout answerALayout = (LinearLayout) view.findViewById(R.id.answerALinearLayout);//答案A layout
         ImageView answerAImageView = (ImageView) view.findViewById(R.id.answerAImageView);//答案A ImageView
         TextView answerATextView = (TextView) view.findViewById(R.id.answerATextView);//答案A ImageView

         LinearLayout answerBLayout = (LinearLayout) view.findViewById(R.id.answerBLinearLayout);//答案B layout
         ImageView answerBImageView = (ImageView) view.findViewById(R.id.answerBImageView);//答案B ImageView
         TextView answerBTextView = (TextView) view.findViewById(R.id.answerBTextView);//答案B ImageView

         LinearLayout answerCLayout = (LinearLayout) view.findViewById(R.id.answerCLinearLayout);//答案C layout
         ImageView answerCImageView = (ImageView) view.findViewById(R.id.answerCImageView);//答案C ImageView
         TextView answerCTextView = (TextView) view.findViewById(R.id.answerCTextView);//答案C ImageView

         LinearLayout answerDLayout = (LinearLayout) view.findViewById(R.id.answerDLinearLayout);//答案D layout
         ImageView answerDImageView = (ImageView) view.findViewById(R.id.answerDImageView);//答案D ImageView
         TextView answerDTextView = (TextView) view.findViewById(R.id.answerDTextView);//答案D ImageView

        QuestionItemBO questionItem = questionBankService.getQuestionItemById(id);

        //// TODO: 2017/01/05  答案可以是任意多个
        String questionDesc = questionItem.getQuestionDesc();
        String questionTitle = questionItem.getQuestionTitle();
        String[] answers = questionItem.getAnswerList();
        String answerAText = answers[0];
        String answerBText = answers[1];
        String answerCText = answers[2];
        String answerDText = answers[3];
        answerDesc.setText(questionDesc);
        answerTitle.setText(questionTitle);
        answerATextView.setText(answerAText);
        answerBTextView.setText(answerBText);
        answerCTextView.setText(answerCText);
        answerDTextView.setText(answerDText);


        int rightAnswerId = 0;
        Map<String, Object> answerItemA = new HashMap<>();
        answerItemA.put("answer", "A");
        answerItemA.put("LinearLayout", answerALayout);
        answerItemA.put("ImageView", answerAImageView);
        answerMap.put(R.id.answerALinearLayout, answerItemA);
        Map<String, Object> answerItemB = new HashMap<>();
        answerItemB.put("answer", "B");
        answerItemB.put("ImageView", answerBImageView);
        answerItemB.put("LinearLayout", answerBLayout);
        answerMap.put(R.id.answerBLinearLayout, answerItemB);
        Map<String, Object> answerItemC = new HashMap<>();
        answerItemC.put("answer", "C");
        answerItemC.put("LinearLayout", answerCLayout);
        answerItemC.put("ImageView", answerCImageView);
        answerMap.put(R.id.answerCLinearLayout, answerItemC);
        Map<String, Object> answerItemD = new HashMap<>();
        answerItemD.put("answer", "D");
        answerItemD.put("LinearLayout", answerDLayout);
        answerItemD.put("ImageView", answerDImageView);
        answerMap.put(R.id.answerDLinearLayout, answerItemD);

        final String rightAnswer = "B";
        rightAnswerId = R.id.answerBLinearLayout;

        View.OnClickListener onClickListener = new AnswerOnClickListener(answerMap, rightAnswer, answerDescLayout, rightAnswerId);
        answerALayout.setOnClickListener(onClickListener);
        answerBLayout.setOnClickListener(onClickListener);
        answerCLayout.setOnClickListener(onClickListener);
        answerDLayout.setOnClickListener(onClickListener);
    }

    private class AnswerOnClickListener implements View.OnClickListener {
        private String rightAnswer;
        private int rightAnswerId;
        Map<Integer, Map<String, Object>> answerMap;
        LinearLayout answerDescLayout;//试题详解Layout
        public AnswerOnClickListener(Map<Integer, Map<String, Object>> answerMap, String rightAnswer, LinearLayout answerDescLayout, int rightAnswerId) {
            this.answerMap = answerMap;
            this.rightAnswer = rightAnswer;
            this.answerDescLayout = answerDescLayout;
            this.rightAnswerId = rightAnswerId;
        }

        public void onClick(View v) {
            int id = v.getId();
            Map<String, Object> currentAnswerItem = answerMap.get(id);
            boolean testingResult = rightAnswerId == id;//测试结果
            String currentAnswer = (String) currentAnswerItem.get("answer");
            ImageView currentImageView = (ImageView)currentAnswerItem.get("ImageView");
            if (rightAnswer.equals(currentAnswer)) {
                testingResult = true;
            } else {
                answerDescLayout.setVisibility(View.VISIBLE);
            }
            if (testingResult) {
                currentImageView.setImageResource(R.mipmap.ic_right);
                showMessage(getApplicationContext(), "您选择的答案是：" + currentAnswer);
            } else {
                currentImageView.setImageResource(R.mipmap.ic_error);
                Map<String, Object> rightAnswerItem = answerMap.get(rightAnswerId);
                ImageView rightImageView = (ImageView)rightAnswerItem.get("ImageView");
                rightImageView.setImageResource(R.mipmap.ic_right);
                showMessage(getApplicationContext(), "您选择的答案是：" + currentAnswer + " 正确答案为:" + rightAnswer);
            }

            //已经选择了。 去掉onclick事件

            //遍历map中的值
            for (Map<String, Object> value : answerMap.values()) {
                LinearLayout currentLinearLayout = (LinearLayout)value.get("LinearLayout");
                currentLinearLayout.setOnClickListener(null);
//                currentLinearLayout.setClickable(false);
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
