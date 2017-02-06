package com.a91coding.ruankao.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.a91coding.ruankao.model.CategoryItemBO;
import com.a91coding.ruankao.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CategoryService extends Service {
    private Map<String, Map<Integer, CategoryItemBO>> categoryItemBOMap = new LinkedHashMap<>();

    public CategoryService() {
        String json = getJSONstring();
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArray = JSONUtil.getJSONArrayFromObject(jsonObject, "categoryItemList");

        for (int i = 0; i < jsonArray.size(); i++) {
            Map<Integer, CategoryItemBO> currentCategoryItemMap;
            JSONObject currentObj = (JSONObject) jsonArray.get(i);
            int no = Integer.valueOf(JSONUtil.getStringFromObject(currentObj, "no"));
            int categoryId = Integer.valueOf(JSONUtil.getStringFromObject(currentObj, "categoryId"));
            String period = JSONUtil.getStringFromObject(currentObj, "period");
            String periodToShow = JSONUtil.getStringFromObject(currentObj, "periodToShow");
            String categoryName = JSONUtil.getStringFromObject(currentObj, "categoryName");
            String extInfo = JSONUtil.getStringFromObject(currentObj, "extInfo");
            CategoryItemBO itemBO = new CategoryItemBO();
            itemBO.setNo(no);
            itemBO.setCategoryId(categoryId);
            itemBO.setPeriod(period);
            itemBO.setPeriodToShow(periodToShow);
            itemBO.setCategoryName(categoryName);
            itemBO.setExtInfo(extInfo);
            if (categoryItemBOMap.containsKey(categoryName)) {
                currentCategoryItemMap = categoryItemBOMap.get(categoryName);
            } else {
                currentCategoryItemMap = new LinkedHashMap<>();
            }
            currentCategoryItemMap.put(no, itemBO);
            categoryItemBOMap.put(categoryName, currentCategoryItemMap);
        }
        setCount(categoryItemBOMap.size());
    }

    /**
     * @return 当前category的内容
     */
    public String getJSONstring() {
        String CATEGORY_JSON_FILE_PATH = "/assets/data/category.json";
        InputStream in = getClass().getResourceAsStream(CATEGORY_JSON_FILE_PATH);
        byte[] data = inputStreamToByte(in);
        return new String(data);
    }

    /**
     * @param is 输入流
     * @return 输入流的内容
     */
    private byte[] inputStreamToByte(InputStream is) {
        byte[] imgData = null;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                byteStream.write(ch);
            }
            imgData = byteStream.toByteArray();
            byteStream.close();
        } catch (IOException e) {
        }

        return imgData;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<Integer, CategoryItemBO> getCategoryItemBOMapByName(String categoryName) {
        return categoryItemBOMap.get(categoryName);
    }

    public Map<String, Map<Integer, CategoryItemBO>> getAllCategoryItemBOMap() {
        return categoryItemBOMap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;
}
