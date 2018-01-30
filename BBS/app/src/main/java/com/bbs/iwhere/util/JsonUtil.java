package com.bbs.iwhere.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大森 on 2017/5/14.
 */

public class JsonUtil {

    private Gson gson = new Gson();

/*    public <T> List<T> getJson(String json, Class<T> clazz) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        List<T> jsonList = gson.fromJson(json, type);
        return jsonList;
    }*/

    public static <T> List<T> getJson(String json, Class<T> clazz) throws Exception {
        List<T> lst = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(new Gson().fromJson(elem, clazz));
        }
        return lst;
    }


    public <T> String postJson(T object) {
        Log.e("JsonUtil", gson.toJson(object));
        return gson.toJson(object);
    }
}
