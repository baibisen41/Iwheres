package com.bbs.iwhere.view;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by beasley on 2017/1/3.
 */

public class MainApplication extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
