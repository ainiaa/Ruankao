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
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout baseLayout = (LinearLayout)inflater.inflate(R.layout.single_category_info, null);
        for (Map.Entry<String, Map<Integer, CategoryItemBO>> entry : getAllCategoryItemBOMap.entrySet()) {
            String categoryName = entry.getKey();
            Map<Integer, CategoryItemBO> categoryItemBOMap = entry.getValue();
            LinearLayout tplMainTitleLayout =  (LinearLayout)inflater.inflate(R.layout.single_category_tpl_main_title, null);
            TextView tplMainTextView = (TextView) tplMainTitleLayout.findViewById(R.id.tpl_main_title);
            tplMainTextView.setText(categoryName);
            baseLayout.addView(tplMainTitleLayout);
            for (CategoryItemBO categoryItemBO : categoryItemBOMap.values()) {
                String period = categoryItemBO.getPeriod();
                String extInfo = categoryItemBO.getExtInfo();
                LinearLayout tplSubTitleLayout =  (LinearLayout)inflater.inflate(R.layout.single_category_tpl_sub_title, null);
                TextView tplSubTextView = (TextView) tplSubTitleLayout.findViewById(R.id.tpl_sub_title);
                tplSubTextView.setText(String.format("%s(%s)", period, extInfo));
                baseLayout.addView(tplSubTitleLayout);
            }
        }
        layout.addView(baseLayout);
    }
}
