package com.a91coding.ruankao.model;

import java.io.Serializable;

/**
 * 题库分类
 * Created by Administrator on 2017/01/04.
 */
public class CategoryItemBO implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    private int id;//分类ID
    private String categoryName;//分类名称
    private String period;//期数
    private String extInfo;//扩展信息 -- 上午、下午

}
