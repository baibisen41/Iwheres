package com.bbs.iwhere.service.common;

import android.os.Environment;
import android.util.Log;

import com.bbs.okhttp.OkHttpUtils;
import com.bbs.okhttp.callback.Callback;
import com.bbs.okhttp.callback.FileCallBack;

import okhttp3.MediaType;

/**
 * Created by 大森 on 2017/3/19.
 */

public abstract class BaseService {

    public String BaseUrl = "http://192.168.191.1:8080/IwhereServer";

    //发送json数据
    public void reqPostJson(String url, String strJson, Callback callback) {

        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(strJson)
                .build()
                .execute(callback);
        Log.e("123", "123");

    }

    //发送文件
    public void reqPostFile() {

    }

    //发送键值
    public void reqPostUser() {

    }

    //多表单发送数据
    public void multiFileUpload() {

    }

    ////////////////////////////////////////////
    //获取json数据
    public void reqGetJson(String url, Callback callback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(callback);
    }

    //下载文件
    public void downloadFile(String url, Callback callback) {
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(callback);
    }

    //下载图片
    public void reqGetImage(String url) {

    }

}
