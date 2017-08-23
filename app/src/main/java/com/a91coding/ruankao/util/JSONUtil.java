package com.a91coding.ruankao.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/02/06.
 */

public class JSONUtil {
    public static String getStringFromObject(JSONObject object, String key) {
        try {
            return object.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getStringFromObject(JSONObject object, String key, String defaultVal) {
        try {
            return object.getString(key);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static JSONArray getJSONArrayFromObject(JSONObject object, String key) {
        try {
            return object.getJSONArray(key);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public static int getIntFromObject(JSONObject object, String key) {
        try {
            return object.getInt(key);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getIntFromObject(JSONObject object, String key, int defaultValue) {
        try {
            return object.getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
