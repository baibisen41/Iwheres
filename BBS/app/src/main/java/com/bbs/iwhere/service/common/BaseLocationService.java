package com.bbs.iwhere.service.common;

import com.bbs.okhttp.OkHttpUtils;
import com.bbs.okhttp.callback.Callback;

/**
 * Created by 大森 on 2017/3/19.
 */

public abstract class BaseLocationService {

    //发送json数据
    public void reqPostJson(String strJson) {

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
    public String reqGetJson(String url,Callback callback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(callback);
        return null;
    }

    //下载文件
    public void downloadFile() {

    }

    //下载图片
    public void reqGetImage() {

    }

}
