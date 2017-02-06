package com.a91coding.ruankao.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a91coding.ruankao.R;
import com.a91coding.ruankao.Service.CategoryService;
import com.a91coding.ruankao.model.CategoryItemBO;

import java.util.Map;

/**
 * 分类列表
 */
public class CategoryListActivity extends RKBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        initUI();
    }

    /**
     * 初始化分类列表
     */
    private void initUI() {
        CategoryService categoryService = new CategoryService();
        //获取所有的category详情
        Map<String, Map<Integer, CategoryItemBO>> allCategoryItemBOMap = categoryService.getAllCategoryItemBOMap();
        LinearLayout categoryListLayout = (LinearLayout)findViewById(R.id.category_list_ll);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout singleCategoryInfoLayout = (LinearLayout)inflater.inflate(R.layout.activity_category_list_single_category_info, null);
        for (Map.Entry<String, Map<Integer, CategoryItemBO>> entry : allCategoryItemBOMap.entrySet()) {
            String categoryName = entry.getKey();
            Map<Integer, CategoryItemBO> categoryItemBOMap = entry.getValue();
            LinearLayout cateTitleTplLayout =  (LinearLayout)inflater.inflate(R.layout.activity_category_list_cate_title_tpl, null);
            TextView categoryNameTv = (TextView) cateTitleTplLayout.findViewById(R.id.category_name_tv);
            categoryNameTv.setText(categoryName);
            singleCategoryInfoLayout.addView(cateTitleTplLayout);
            for (CategoryItemBO categoryItemBO : categoryItemBOMap.values()) {
                String period = categoryItemBO.getPeriod();
                String periodToShow= categoryItemBO.getPeriodToShow();
                String extInfo = categoryItemBO.getExtInfo();
                Integer categoryId = categoryItemBO.getCategoryId();
                LinearLayout catePeriodTplLayout =  (LinearLayout)inflater.inflate(R.layout.activity_category_list_cate_period_tpl, null);
                TextView categoryPeriodTv = (TextView) catePeriodTplLayout.findViewById(R.id.category_period_tv);
                categoryPeriodTv.setText(String.format("%s(%s)", periodToShow, extInfo));
                singleCategoryInfoLayout.addView(catePeriodTplLayout);
                View.OnClickListener onClickListener = new CategoryListActivity.CategoryItemOnClickListener(categoryId, period, extInfo, periodToShow);
                catePeriodTplLayout.setOnClickListener(onClickListener);
            }
        }
        categoryListLayout.addView(singleCategoryInfoLayout);
    }

    private class CategoryItemOnClickListener implements View.OnClickListener {
        private int categoryId;
        private String period;
        private String extInfo;
        private String periodToShow;
        public CategoryItemOnClickListener(int categoryId, String period, String extInfo, String periodToShow) {
            this.categoryId = categoryId;
            this.period = period;
            this.extInfo = extInfo;
            this.periodToShow = periodToShow;
        }

        public void onClick(View v) {
            Intent intent = new Intent(CategoryListActivity.this, QuestionDetailActivity.class);
            intent.putExtra("categoryId", categoryId);
            intent.putExtra("period", period);
            intent.putExtra("extInfo", extInfo);
            intent.putExtra("periodToShow", periodToShow);
            startActivity(intent);
        }
    }
}
