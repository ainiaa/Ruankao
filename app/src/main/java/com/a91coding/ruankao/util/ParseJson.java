package com.a91coding.ruankao.util;

import com.a91coding.ruankao.model.QuestionItemBO;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ParseJson {

    private String jsonStr;

    public ParseJson() {

    }

    public ParseJson(String str){
        this.jsonStr = str;
    }
    /**
     * 解析json字符串
     */
    public void parse(){
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String name = jsonObject.getString("name");
        int num = jsonObject.getInt("num");
        String sex = jsonObject.getString("sex");
        int age = jsonObject.getInt("age");

        System.out.println(name + " " + num + " " + sex + " " + age);
    }
    //将json字符串转换为java对象
    public QuestionItemBO JSON2Object(){
//        //接收{}对象，此处接收数组对象会有异常
//        if(jsonStr.indexOf("[") != -1){
//            jsonStr = jsonStr.replace("[", "");
//        }
//        if(jsonStr.indexOf("]") != -1){
//            jsonStr = jsonStr.replace("]", "");
//        }

        JSONArray array = JSONArray.fromObject(jsonStr);
        JSONObject obj = new JSONObject().fromObject(jsonStr);//将json字符串转换为json对象
        QuestionItemBO jb = (QuestionItemBO)JSONObject.toBean(obj,QuestionItemBO.class);//将建json对象转换为Person对象
        return jb;//返回一个Person对象
    }


}