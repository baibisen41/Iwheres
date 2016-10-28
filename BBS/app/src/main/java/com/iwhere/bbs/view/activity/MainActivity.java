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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftbutton:
                leftSlidingMenu.showMenu(true);
                break;
            case R.id.rightbutton:
                rightSlidingMenu.showMenu(true);
                break;
            default:
                break;
        }

    }
}
