package com.bbs.iwhere.view;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bbs.iwhere.db.MainHelper;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by beasley on 2017/1/3.
 */

public class MainApplication extends Application {

    public static Context context = null;
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        //options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);

        context = getApplicationContext();
        MainHelper.getInstance().init(context);
    }

    public static Context getContext() {
        return context;
    }

}
