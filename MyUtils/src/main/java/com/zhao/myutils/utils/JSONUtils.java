package com.zhao.myutils.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Json Utils
 */
public class JSONUtils {

    public static boolean isPrintException = true;

    /**
     * @see JSONUtils#getLong(JSONObject, String, Long)
     */
    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
        return getLong(jsonObject, key, (Long) defaultValue);
    }

    /**
     * get Long from jsonObject
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @see JSONUtils#getLong(String, String, Long)
     */
    public static long getLong(String jsonData, String key, long defaultValue) {
        return getLong(jsonData, key, (Long) defaultValue);
    }

    /**
     * get Long from jsonData
     */
    public static Long getLong(String jsonData, String key, Long defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @see JSONUtils#getInt(JSONObject, String, Integer)
     */
    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        return getInt(jsonObject, key, (Integer) defaultValue);
    }

    /**
     * get Int from jsonObject
     */
    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @see JSONUtils#getInt(String, String, Integer)
     */
    public static int getInt(String jsonData, String key, int defaultValue) {
        return getInt(jsonData, key, (Integer) defaultValue);
    }

    /**
     * get Int from jsonData
     */
    public static Integer getInt(String jsonData, String key, Integer defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @see JSONUtils#getDouble(JSONObject, String, Double)
     */
    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
        return getDouble(jsonObject, key, (Double) defaultValue);
    }

    /**
     * get Double from jsonObject
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @see JSONUtils#getDouble(String, String, Double)
     */
    public static double getDouble(String jsonData, String key, double defaultValue) {
        return getDouble(jsonData, key, (Double) defaultValue);
    }

    /**
     * get Double from jsonData
     */
    public static Double getDouble(String jsonData, String key, Double defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String from jsonData
     */
    public static String getString(String jsonData, String key, String defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String from jsonObject
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String array from jsonData
     */
    public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String array from jsonObject
     */
    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                String[] value = new String[statusArray.length()];
                for (int i = 0; i < statusArray.length(); i++) {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * get JSONObject from jsonData
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonObject
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonData
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonObject
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get boolean from jsonData
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
        if (TextUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get boolean from jsonObject
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {
        if (jsonObject == null || TextUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get map from jsonData.
     */
    public static Map<String, String> getMap(String jsonData, String key) {
        if (jsonData == null) {
            return null;
        }
        if (jsonData.length() == 0) {
            return new HashMap<>();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getMap(jsonObject, key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * get map from jsonObject.
     */
    public static Map<String, String> getMap(JSONObject jsonObject, String key) {
        return JSONUtils.parseKeyAndValueToMap(JSONUtils.getString(jsonObject, key, null));
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     */
    public static Map<String, String> parseKeyAndValueToMap(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(source);
            return parseKeyAndValueToMap(jsonObject);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
        if (sourceObj == null) {
            return null;
        }

        Map<String, String> keyAndValueMap = new HashMap<>();
        for (Iterator iter = sourceObj.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            putMapNotEmptyKey(keyAndValueMap, key, getString(sourceObj, key, ""));

        }
        return keyAndValueMap;
    }

    /**
     * add key-value pair to map, and key need not null or empty
     */
    public static boolean putMapNotEmptyKey(Map<String, String> map, String key, String value) {
        if (map == null || TextUtils.isEmpty(key)) {
            return false;
        }

        map.put(key, value);
        return true;
    }
}
