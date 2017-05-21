package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    SplashService splashService = new SplashService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.loading_layout);
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

    }

    protected void init() {

        splashService.getUserPicture();
    }


}
