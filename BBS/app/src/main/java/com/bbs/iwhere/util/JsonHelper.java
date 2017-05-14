package com.bbs.iwhere.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 大森 on 2017/5/14.
 */

public class JsonHelper {

    private Gson gson = new Gson();

    public <T> List<T> getJson(String json, Class<T> tClass) {
        List<T> jsonList = gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return jsonList;
    }

    public <T> String postJson(T object) {
        return gson.toJson(object);
    }
}
