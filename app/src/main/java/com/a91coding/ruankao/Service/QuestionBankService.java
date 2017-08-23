package com.a91coding.ruankao.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.a91coding.ruankao.model.QuestionBankBO;
import com.a91coding.ruankao.model.QuestionItemMultiAnswerBO;
import com.a91coding.ruankao.model.QuestionItemSingleAnswerBO;
import com.a91coding.ruankao.ui.LogToFile;
import com.a91coding.ruankao.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionBankService extends Service {
    private Map<Integer, QuestionMapping> questionItemBOMap = new HashMap<>();
    private Map<Integer, QuestionItemSingleAnswerBO> questionItemSingleAnswerBOMap = new LinkedHashMap<>();
    private Map<Integer, QuestionItemMultiAnswerBO> questionItemMultiAnswerBOMap = new LinkedHashMap<>();


    private Integer categoryId = 0;
    private String period = "default";
    private String extInfo = "default";
    private String periodToShow = "default";
    private QuestionBankBO questionBankBO;
    private int count = 0;

    public QuestionBankService(Integer categoryId, String period, String extInfo, String periodToShow, Context ctx) {
        this.categoryId = categoryId;
        this.period = period;
        this.extInfo = extInfo;
        this.periodToShow = periodToShow;
        LogToFile.init(ctx);
        initData();
    }

    public QuestionBankService() {
        initData();
    }

    private void initData() {
        String json = getJSONstring();
        JSONObject jsonObject = JSONObject.fromObject(json);
        questionBankBO = new QuestionBankBO();
        questionBankBO.setCategory(JSONUtil.getStringFromObject(jsonObject, "category"));
        questionBankBO.setPeriod(JSONUtil.getStringFromObject(jsonObject, "period"));
        questionBankBO.setPeriodToShow(periodToShow);
        JSONArray jsonArray = JSONUtil.getJSONArrayFromObject(jsonObject, "questionItemList");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject currentObj = (JSONObject) jsonArray.get(i);
            int id = Integer.valueOf(JSONUtil.getStringFromObject(currentObj, "id"));
            int no = Integer.valueOf(JSONUtil.getStringFromObject(currentObj, "no"));
            id = i + 1;

            String questionDesc = JSONUtil.getStringFromObject(currentObj, "questionDesc");
            String questionTitle = JSONUtil.getStringFromObject(currentObj, "questionTitle");
            String testPoint = JSONUtil.getStringFromObject(currentObj, "testPoint");
            String illustration = JSONUtil.getStringFromObject(currentObj, "illustration");
            String descIllustration = JSONUtil.getStringFromObject(currentObj, "descIllustration");
            int questionType = JSONUtil.getIntFromObject(currentObj, "questionType");
            if (questionType == 0) {//一题一问
                int rightAnswer = Integer.valueOf(JSONUtil.getStringFromObject(currentObj, "rightAnswer"));
                String[] answerList = (String[]) JSONUtil.getJSONArrayFromObject(currentObj, "answerList").toArray(new String[]{});
                String[] answerIllustration = (String[]) JSONUtil.getJSONArrayFromObject(currentObj, "answerIllustration").toArray(new String[]{});
                QuestionItemSingleAnswerBO itemBO = new QuestionItemSingleAnswerBO();
                itemBO.setId(id);
                itemBO.setNo(no);
                itemBO.setQuestionTitle(questionTitle);
                itemBO.setQuestionDesc(questionDesc);
                itemBO.setAnswerList(answerList);
                itemBO.setRightAnswer(rightAnswer);
                itemBO.setTestPoint(testPoint);
                itemBO.setIllustration(illustration);
                itemBO.setQuestionType(questionType);
                itemBO.setAnswerIllustrations(answerIllustration);
                itemBO.setDescIllustration(descIllustration);
                questionItemBOMap.put(id, new QuestionMapping(id, questionType));
                questionItemSingleAnswerBOMap.put(id, itemBO);
            } else {//一题多问
                try {
                    String[] r = (String[]) JSONUtil.getJSONArrayFromObject(currentObj, "rightAnswer").toArray(new String[]{});
                    Integer[] rightAnswer = new Integer[r.length];
                    for (int j = 0; j < r.length; j++) {
                        rightAnswer[j] = Integer.valueOf(r[j]);
                    }
                    JSONArray answerListArray = JSONUtil.getJSONArrayFromObject(currentObj, "answerList");
                    String[][] answerList = new String[answerListArray.size()][];
                    int questionCount = answerListArray.size();
                    for (int j = 0; j < questionCount; j++) {
                        answerList[j] = (String[]) (answerListArray.getJSONArray(j).toArray(new String[]{}));
                    }

                    JSONArray answerIllustrationArray = JSONUtil.getJSONArrayFromObject(currentObj, "answerIllustration");
                    String[][] answerIllustration = new String[answerIllustrationArray.size()][];
                    int questionCount1 = answerIllustrationArray.size();
                    for (int j = 0; j < questionCount1; j++) {
                        answerIllustration[j] = (String[]) (answerIllustrationArray.getJSONArray(j).toArray(new String[]{}));
                    }

                    QuestionItemMultiAnswerBO itemBO = new QuestionItemMultiAnswerBO();
                    itemBO.setId(id);
                    itemBO.setNo(no);
                    itemBO.setQuestionTitle(questionTitle);
                    itemBO.setQuestionDesc(questionDesc);
                    itemBO.setAnswers(answerList);
                    itemBO.setRightAnswer(rightAnswer);
                    itemBO.setTestPoint(testPoint);
                    itemBO.setIllustration(illustration);
                    itemBO.setQuestionType(questionType);
                    itemBO.setQuestionCount(questionCount > questionCount1 ? questionCount : questionCount1);
                    itemBO.setAnswerIllustrations(answerIllustration);
                    itemBO.setDescIllustration(descIllustration);
                    questionItemBOMap.put(id, new QuestionMapping(id, questionType));
                    questionItemMultiAnswerBOMap.put(id, itemBO);
                } catch (Exception e) {
                    LogToFile.e("initData", e.getMessage() + "current:" + currentObj.toString());
                }

            }
        }
        setCount(questionItemBOMap.size());
    }

    /**
     * todo 这个需要根本period和category进行分类
     *
     * @return
     */
    public String getJSONstring() {
        String dataPath = getDataPath();
        InputStream in = getClass().getResourceAsStream(dataPath);
        byte[] data = inputStreamToByte(in);
        return new String(data);
    }

    /**
     * @return
     */
    private String getDataPath() {
        String currentExtInfo;
        if (extInfo.equals("上午")) {
            currentExtInfo = "am";
        } else {
            currentExtInfo = "pm";
        }
        return String.format("/assets/data/%d-%s-%s.json", categoryId, period, currentExtInfo);
    }

    /**
     * @param is
     * @return
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
        } catch (IOException e) {
        }

        return imgdata;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 根据ID 获得对应的题目
     *
     * @param id
     * @return
     */
    public Integer getQuestionTypeById(int id) {
        QuestionMapping mapping = questionItemBOMap.get(id);
        if (mapping == null) {
            LogToFile.e("getQuestionTypeById", "id:" + String.valueOf(id) + " size:" + questionItemBOMap.size());
            for (Map.Entry entry : questionItemBOMap.entrySet()) {
                LogToFile.e("keys", String.valueOf(entry.getKey()));
            }

        }
        return mapping.questionType;
    }

    public QuestionItemMultiAnswerBO getQuestionItemMultiAnswerById(int id) {
        return questionItemMultiAnswerBOMap.get(id);
    }

    public QuestionItemSingleAnswerBO getQuestionItemSingleAnswerById(int id) {
        return questionItemSingleAnswerBOMap.get(id);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        if (questionBankBO == null) {
            return "";
        }
        return questionBankBO.getCategory() + "(" + periodToShow + "(" + extInfo + "))";
    }

    private class QuestionMapping {
        private Integer id;
        private int questionType;

        public QuestionMapping(Integer id, int questionType) {
            this.id = id;
            this.questionType = questionType;
        }
    }
}
