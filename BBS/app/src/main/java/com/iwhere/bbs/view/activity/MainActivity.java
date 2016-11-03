package com.iwhere.bbs.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.iwhere.bbs.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private ImageView titleLeftBt;
    private ImageView titleRightBt;
    protected SlidingMenu rightLeftSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        titleLeftBt = (ImageView) findViewById(R.id.leftbutton);
        titleLeftBt.setOnClickListener(this);
        titleRightBt = (ImageView) findViewById(R.id.rightbutton);
        titleRightBt.setOnClickListener(this);
    }


    private void initSlidingMenu() {
        setBehindContentView(R.layout.main_left_slidingmenu);
        rightLeftSlidingMenu = getSlidingMenu();
        rightLeftSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置是左滑还是右滑，还是左右都可以滑，我这里只做了左滑
        rightLeftSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
        rightLeftSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
        rightLeftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置手势模式
//        rightLeftSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
        rightLeftSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
        rightLeftSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
        rightLeftSlidingMenu.setSecondaryMenu(R.layout.main_right_slidingmenu);

        setBehindContentView(R.layout.main_left_slidingmenu);
        //左侧SlidingMenu
        leftSlidingMenu = getSlidingMenu();
        leftSlidingMenu.setMode(SlidingMenu.LEFT);
        leftSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset_left);
        leftSlidingMenu.setFadeDegree(0.35f);
        leftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        leftSlidingMenu.setFadeEnabled(true);
        leftSlidingMenu.setBehindScrollScale(0.333f);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftbutton:
<<<<<<< HEAD
                rightLeftSlidingMenu.showMenu();
                break;
            case R.id.rightbutton:
                rightLeftSlidingMenu.showSecondaryMenu(true);
=======
                leftSlidingMenu.showMenu();
                break;
            case R.id.rightbutton:
                rightSlidingMenu.showMenu();
>>>>>>> 2c2cec6833570b11f1a6d80c9f80678bc9ab8f70
                break;
            default:
                break;
        }

    }
}
