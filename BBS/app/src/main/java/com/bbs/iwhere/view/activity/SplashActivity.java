package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.service.SplashService.SplashCallback;
import com.bbs.iwhere.service.SplashService.SplashService;

/**
 * Created by 大森 on 2017/5/21.
 */

public class SplashActivity extends Activity {

    private static final int FRIENDLIST_ERROR = 0;

    private static final int FRIENDLIST_FINISH = 1;

    SplashService splashService = new SplashService();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case FRIENDLIST_ERROR:
                    //显示一张提示失败的图片
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
        //     ImageView imageView = (ImageView) findViewById(R.id.loading_layout);

        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean isDownloadFinish = splashService.pullFriendList();
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
        });

        /*
        // 设置加载动画透明度渐变从（0.1不显示-1.0完全显示）
        AlphaAnimation animation = new AlphaAnimation(0.8f, 1.0f);
        // 设置动画时间5s
        animation.setDuration(5000);
        // 将组件与动画关联
        imageView.setAnimation(animation);

            animation.setAnimationListener(new Animation.AnimationListener() {
            // 动画开始时执行
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                // 初始化
                init();

            }

            // 动画重复时执行
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            // 动画结束时执行
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
        */

    }


}


