package com.bbs.iwhere.view;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by beasley on 2017/1/3.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);

    }
}
