package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.service.SplashService.SplashCallback;
import com.bbs.iwhere.service.SplashService.SplashService;
import com.bbs.iwhere.util.NetworkUtil;

/**
 * Created by 大森 on 2017/5/21.
 */

public class SplashActivity extends Activity {

    private static final int FRIENDLIST_ERROR = 0;

    private static final int FRIENDLIST_FINISH = 1;

    SplashService splashService = new SplashService();
    NetworkUtil networkUtil = new NetworkUtil();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case FRIENDLIST_ERROR:
                    //显示一张提示失败的图片
//                    Toast.makeText(SplashActivity.this, "网络状况不好。。。", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                    break;
                case FRIENDLIST_FINISH:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView) findViewById(R.id.loading_layout);
        TextView netError = (TextView) findViewById(R.id.net_error);

        if (networkUtil.isNetworkConnected(getApplicationContext()) == true) {

            new Thread(new Runnable() {
                @Override
                public void run() {

//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    boolean isDownloadFinish = splashService.pullFriendList();
//                    boolean isDownloadFinish = true;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDownloadFinish == true) {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);

                    } else {
                        Message message = Message.obtain();
                        message.what = 0;
                        handler.sendMessage(message);

                    }

                }
            }).start();
        } else {
            imageView.setVisibility(View.GONE);
            netError.setVisibility(View.VISIBLE);
        }
    }


}


