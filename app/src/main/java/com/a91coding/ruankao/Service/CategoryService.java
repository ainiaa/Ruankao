package com.a91coding.ruankao.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.a91coding.ruankao.model.CategoryItemBO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CategoryService extends Service {
    private Map<Integer, CategoryItemBO> categoryItemBOMap = new HashMap<>();
    private Map<String, Map<Integer, CategoryItemBO>> categoryItemBOMap2 = new HashMap<>();

    public CategoryService() {
        String json = getJSONstring();
        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("categoryItemList");

        for(int i = 0; i < jsonArray.size();i++) {
            Map<Integer, CategoryItemBO> currentCategoryItemMap;
            JSONObject currentObj = (JSONObject)jsonArray.get(i);
            int id = Integer.valueOf(currentObj.getString("id"));
            String period = currentObj.getString("period");
            String categoryName = currentObj.getString("categoryName");
            String extInfo      = currentObj.getString("extInfo");
            CategoryItemBO itemBO = new CategoryItemBO();
            itemBO.setId(id);
            itemBO.setPeriod(period);
            itemBO.setCategoryName(categoryName);
            itemBO.setExtInfo(extInfo);
            if (categoryItemBOMap2.containsKey(categoryName)) {
                currentCategoryItemMap = categoryItemBOMap2.get(categoryName);
            } else {
                currentCategoryItemMap = new HashMap<>();
            }
            currentCategoryItemMap.put(id, itemBO);
            categoryItemBOMap.put(id, itemBO);
            categoryItemBOMap2.put(categoryName, currentCategoryItemMap);
        }

        setCount(categoryItemBOMap2.size());
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
     *
     * @param is 输入流
     * @return 输入流的内容
     */
    private byte[] inputStreamToByte(InputStream is) {
        byte[] imgdata = null;
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            imgdata = bytestream.toByteArray();
            bytestream.close();
        }catch (IOException e) {}

        return imgdata;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 根据ID 获得对应的题目
     * @param id 需要查询的id
     * @return 当前需要查询的id的详细内容
     */
    public CategoryItemBO getCategoryItemById(int id) {
        return categoryItemBOMap.get(id);
    }
    public Map<Integer, CategoryItemBO> getCategoryItemBOMapByName(String categoryName) {
        return categoryItemBOMap2.get(categoryName);
    }
    public Map<String, Map<Integer, CategoryItemBO>> getAllCategoryItemBOMap() {
        return categoryItemBOMap2;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;
}
