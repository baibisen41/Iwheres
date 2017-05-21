package com.bbs.iwhere.service.SplashService;

import android.os.Environment;
import android.util.Log;

import com.bbs.iwhere.service.common.BaseService;
import com.bbs.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 大森 on 2017/5/21.
 */

public class SplashService extends BaseService {

    private SplashCallback splashCallback;
    private String url = "https://i1.hoopchina.com.cn/blogfile/201705/17/BbsImg149502314118679_800x545.jpg?x-oss-process=image/resize,w_800/format,webp";

    public void setSplashCallback(SplashCallback splashCallback) {
        this.splashCallback = splashCallback;
    }

    public void getUserPicture() {

        downloadFile(url, new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "myPicture.jpg") {
            @Override
            public void onBefore(Request request, int id) {
            }

            @Override
            public void inProgress(float progress, long total, int id) {

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("SplashError", "onError :" + e.getMessage());
            }

            @Override
            public void onResponse(File file, int id) {
                Log.e("SplashSuccess", "onResponse :" + file.getAbsolutePath());
            }
        });

    }


}
