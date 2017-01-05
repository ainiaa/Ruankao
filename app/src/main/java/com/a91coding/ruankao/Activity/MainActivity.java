package com.a91coding.ruankao.Activity;
//https://github.com/wyouflf/xUtils3   -- 后面添加(现在使用最原始的方式)

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.a91coding.ruankao.R;
import com.a91coding.ruankao.Service.QuestionBankService;
import com.a91coding.ruankao.adapter.MYViewPagerAdapter;
import com.a91coding.ruankao.model.QuestionItemBO;


public class MainActivity extends AppCompatActivity {
    private QuestionBankService questionBankService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionBankService = new QuestionBankService();

        setContentView(R.layout.activity_main);

        //
        initViewPager();
    }

    private void initViewPager(){
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        ArrayList<View> views = new ArrayList<>();

        //填充内容
        int max = 2;
        for(int id = 1;id <= max;id++) {
            View view = LayoutInflater.from(this).inflate(R.layout.question_layout, null);
            initUI(view, id);
            views.add(view);
        }

        MYViewPagerAdapter adapter = new MYViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
    }

    /**
     * UI初始化
     * @param view
     * @param id
     */
    private void initUI(View view, int id) {
        final LinearLayout answerDescLayout = (LinearLayout) view.findViewById(R.id.answerDescLayout);//试题详解Layout
        final TextView answerDesc = (TextView) view.findViewById(R.id.answerDescTextView);//试题详解内容
        final TextView answerTitle = (TextView) view.findViewById(R.id.answerTitleTextView);//试题标题
        final RadioButton answerA = (RadioButton) view.findViewById(R.id.answerARadioButton);//答案A
        final RadioButton answerB = (RadioButton) view.findViewById(R.id.answerBRadioButton);//答案B
        final RadioButton answerC = (RadioButton) view.findViewById(R.id.answerCRadioButton);//答案C
        final RadioButton answerD = (RadioButton) view.findViewById(R.id.answerDRadioButton);//答案D
        final RadioGroup answerGroup = (RadioGroup) view.findViewById(R.id.answerRadioGroup);//答案group
        QuestionItemBO questionItem = questionBankService.getQuestionItemById(id);

        String questionDesc = questionItem.getQuestionDesc();
        String questionTitle = questionItem.getQuestionTitle();
        String[] answers =  questionItem.getAnswers();
        String answerAText       = answers[0];
        String answerBText       = answers[1];
        String answerCText       = answers[2];
        String answerDText       = answers[3];
        answerDesc.setText(questionDesc);
        answerTitle.setText(questionTitle);
        answerA.setText(answerAText);
        answerB.setText(answerBText);
        answerC.setText(answerCText);
        answerD.setText(answerDText);

        final Map<Integer, Map<String, Object>> answerMap = new HashMap<>();
        Map<String, Object> answerItem= new HashMap<>();
        answerItem.put("answer", "A");
        answerItem.put("RadioButton", answerA);
        answerMap.put(R.id.answerARadioButton, answerItem);
        answerItem.put("answer", "B");
        answerItem.put("RadioButton", answerB);
        answerMap.put(R.id.answerBRadioButton, answerItem);
        answerItem.put("answer", "C");
        answerItem.put("RadioButton", answerC);
        answerMap.put(R.id.answerCRadioButton, answerItem);
        answerItem.put("answer", "D");
        answerItem.put("RadioButton", answerD);
        answerMap.put(R.id.answerDRadioButton, answerItem);

        final String rightAnswer = "B";

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Map<String, Object> currentAnswerItem = answerMap.get(checkedId);
                RadioButton currentButton = (RadioButton)currentAnswerItem.get("RadioButton");
                boolean testingResult = false;//测试结果
                if (!currentButton.isPressed()) {
                    return;
                }
                String currentAnswer = (String)currentAnswerItem.get("answer");
                if (rightAnswer.equals(currentAnswer)) {
                    testingResult = true;
                } else {
                    answerDescLayout.setVisibility(View.VISIBLE);
                }
                if (testingResult) {
                    showMessage(getApplicationContext(), "您选择的答案是：" + currentAnswer);
                } else {
                    showMessage(getApplicationContext(), "您选择的答案是：" + currentAnswer + " 正确答案为:" + rightAnswer);
                }
            }
        });
    }

    /**
     * 信息提示
     * @param context
     * @param msg
     */
    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
