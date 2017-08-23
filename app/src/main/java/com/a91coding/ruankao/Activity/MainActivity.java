package com.a91coding.ruankao.Activity;
//https://github.com/wyouflf/xUtils3   -- 后面添加(现在使用最原始的方式)

import android.content.Intent;
import android.os.Bundle;

import com.a91coding.ruankao.R;


public class MainActivity extends RuanKaoBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
        startActivity(intent);
    }
}
