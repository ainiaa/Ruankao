package com.a91coding.ruankao.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a91coding.ruankao.R;
import com.a91coding.ruankao.Service.CategoryService;
import com.a91coding.ruankao.model.CategoryItemBO;

import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        initUI();
    }

    private void initUI() {
        CategoryService categoryService = new CategoryService();
        //填充内容
        Map<String, Map<Integer, CategoryItemBO>> getAllCategoryItemBOMap = categoryService.getAllCategoryItemBOMap();
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity_main);
        for (Map.Entry<String, Map<Integer, CategoryItemBO>> entry : getAllCategoryItemBOMap.entrySet()) {
            String categoryName = entry.getKey();
            Map<Integer, CategoryItemBO> categoryItemBOMap = entry.getValue();
            View baseView = LayoutInflater.from(this).inflate(R.layout.single_category_info, null);
            TextView currentCategoryNameTextView = (TextView) baseView.findViewById(R.id.tplMainTextView);
            currentCategoryNameTextView.setText(categoryName);
            for (CategoryItemBO categoryItemBO : categoryItemBOMap.values()) {
                String period = categoryItemBO.getPeriod();
                String extInfo = categoryItemBO.getExtInfo();
                TextView tplSubTextView = (TextView) baseView.findViewById(R.id.tplSubTextView);
                tplSubTextView.setText(String.format("%s(%s)", period, extInfo));
                baseView
            }
            layout.addView(baseView);
        }
    }
}
