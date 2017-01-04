package com.a91coding.ruankao.Activity;
//https://github.com/wyouflf/xUtils3   -- 后面添加(现在使用最原始的方式)

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.a91coding.ruankao.R;


public class MainActivity extends AppCompatActivity {

    private TextView answerDesc;
    private TextView answerTitle;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;
    private LinearLayout answerDescLayout;

    private String rightAnswer; //正确答案
    private String currentAnswer;//当前答案
    private boolean testingResult = false;//测试结果
    private RadioGroup answerGroup;
    private Map<Integer, RadioButton> answerMap;
    final int RIGHT = 0;
    final int LEFT = 1;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //填充内容
        initUI();

        //添加手势监听事件
        gestureDetector = new GestureDetector(MainActivity.this, onGestureListener);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void doResult(int action) {
        switch (action) {
            case RIGHT: // 右划 // TODO: 2017/01/04  
                showMessage(getApplicationContext(), "go right");
                break;
            case LEFT: // 左划 // TODO: 2017/01/04
                showMessage(getApplicationContext(), "go left");
                break;
        }
    }

    /**
     * 手势事件监听
     */
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    if (x > 0) {
                        doResult(RIGHT);
                    } else if (x < 0) {
                        doResult(LEFT);
                    }
                    return true;
                }
            };

    /**
     * UI初始化
     */
    private void initUI() {
        answerDescLayout = (LinearLayout) findViewById(R.id.answerDescLayout);
        answerDesc = (TextView) findViewById(R.id.answerDescTextView);
        answerTitle = (TextView) findViewById(R.id.answerTitleTextView);
        answerA = (RadioButton) findViewById(R.id.answerARadioButton);
        answerB = (RadioButton) findViewById(R.id.answerBRadioButton);
        answerC = (RadioButton) findViewById(R.id.answerCRadioButton);
        answerD = (RadioButton) findViewById(R.id.answerDRadioButton);
        answerGroup = (RadioGroup) findViewById(R.id.answerRadioGroup);

        answerDesc.setText("initUI  answerDesc answerDesc answerDescanswerDescanswerDescanswerDescanswerDescanswerDesc");
        answerTitle.setText("initUI TextView");
        answerA.setText("initUI answerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerAanswerA");
        answerB.setText("initUI answerB");
        answerC.setText("initUI answerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerCanswerC");
        answerD.setText("initUI answerD");

        answerMap = new HashMap<>();
        answerMap.put(R.id.answerARadioButton, answerA);
        answerMap.put(R.id.answerBRadioButton, answerB);
        answerMap.put(R.id.answerCRadioButton, answerC);
        answerMap.put(R.id.answerDRadioButton, answerD);

        rightAnswer = "B";

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton currentButton = answerMap.get(checkedId);
                if (!currentButton.isPressed()) {
                    return;
                }
                String msg;
                switch (checkedId) {
                    case R.id.answerARadioButton:
                        msg = "A";
                        currentAnswer = "A";
                        break;
                    case R.id.answerBRadioButton:
                        msg = "B";
                        currentAnswer = "B";
                        break;
                    case R.id.answerCRadioButton:
                        msg = "C";
                        currentAnswer = "C";
                        break;
                    case R.id.answerDRadioButton:
                        msg = "D";
                        currentAnswer = "D";
                        break;
                    default:
                        msg = "eeee";
                }
                if (rightAnswer.equals(currentAnswer)) {
                    testingResult = true;
                } else {
                    answerDescLayout.setVisibility(View.VISIBLE);
                }
                if (testingResult) {
                    showMessage(getApplicationContext(), "您选择的答案是：" + msg);
                } else {
                    showMessage(getApplicationContext(), "您选择的答案是：" + msg + " 正确答案为:" + rightAnswer);
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
