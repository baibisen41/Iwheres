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
    protected SlidingMenu leftSlidingMenu;
    protected SlidingMenu rightSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLeftSlidingMenu();
        initRightSlidingMenu();
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        titleLeftBt = (ImageView) findViewById(R.id.leftbutton);
        titleLeftBt.setOnClickListener(this);
        titleRightBt = (ImageView) findViewById(R.id.rightbutton);
        titleRightBt.setOnClickListener(this);
    }

    private void initRightSlidingMenu() {

    }

    private void initLeftSlidingMenu() {

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
                leftSlidingMenu.showMenu();
                break;
            case R.id.rightbutton:
                rightSlidingMenu.showMenu();
                break;
            default:
                break;
        }

    }
}
