package com.a91coding.ruankao.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.a91coding.ruankao.model.QuestionBankBO;
import com.a91coding.ruankao.model.QuestionItemBO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QuestionBankService extends Service {
    private Map<Integer, QuestionItemBO> questionItemBOMap = new HashMap<>();


    public QuestionBankService() {
        String json = getJSONstring();
        JSONObject jsonObject = JSONObject.fromObject(json);
        QuestionBankBO questionBankBO= new QuestionBankBO();
        questionBankBO.setCategory(jsonObject.getString("category"));
        questionBankBO.setPeriod(jsonObject.getString("period"));

        JSONArray jsonArray = jsonObject.getJSONArray("questionItemList");

        for(int i = 0; i < jsonArray.size();i++) {
            JSONObject currentObj = (JSONObject)jsonArray.get(i);
            int id = Integer.valueOf(currentObj.getString("id"));
            int rightAnswer = Integer.valueOf(currentObj.getString("rightAnswer"));
            String questionDesc = currentObj.getString("questionDesc");
            String questionTitle = currentObj.getString("questionTitle");
            String testPoint = currentObj.getString("testPoint");
            String[] answerList = (String[]) currentObj.getJSONArray("answerList").toArray(new String[]{});
            QuestionItemBO itemBO = new QuestionItemBO();
            itemBO.setId(id);
            itemBO.setQuestionTitle(questionTitle);
            itemBO.setQuestionDesc(questionDesc);
            itemBO.setAnswerList(answerList);
            itemBO.setRightAnswer(rightAnswer);
            itemBO.setTestPoint(testPoint);
            questionItemBOMap.put(id, itemBO);
        }

        setCount(questionItemBOMap.size());
    }

    public String getJSONstring() {
        InputStream in = getClass().getResourceAsStream("/assets/data/201612-rjsjs.json");
        byte[] data = inputStreamToByte(in);
        return new String(data);
    }

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
     * @param id
     * @return
     */
    public QuestionItemBO getQuestionItemById(int id) {
        return questionItemBOMap.get(id);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;
}
