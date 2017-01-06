package com.bbs.iwhere.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.view.fragment.LeftmenuFragment;
import com.bbs.slidingmenu.SlidingMenu;
import com.bbs.slidingmenu.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private ImageView titleLeftBt;
    private ImageView titleRightBt;
    private Fragment leftFragment;
    private Fragment currentFragment = null;
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
        setBehindContentView(R.layout.main_left_layout);
        FragmentTransaction leftFragmentTransaction = getSupportFragmentManager().beginTransaction();
        leftFragment = new LeftmenuFragment();
        leftFragmentTransaction.replace(R.id.main_left_fragment, leftFragment);
        leftFragmentTransaction.commit();

        rightLeftSlidingMenu = getSlidingMenu();
        rightLeftSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑
        rightLeftSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
        rightLeftSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
        rightLeftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置手势模式
//        rightLeftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        rightLeftSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
        rightLeftSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
        rightLeftSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
//        rightLeftSlidingMenu.setSecondaryMenu(R.layout.main_right_slidingmenu);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftbutton:
                rightLeftSlidingMenu.showMenu();
                break;
            case R.id.rightbutton:

                break;
            default:
                break;
        }
    }

    public void switchFragment(Fragment fragment) {
        //优化选项卡切换   先判断当前fragment是否为空，如果不为空，判断新fragment是否被添加过，如果没有添加，直接add进来
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment != currentFragment) {
            if (!fragment.isAdded()) {
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment).add(R.id.default_frame, fragment).commit();
                } else {
                    fragmentTransaction.add(R.id.default_frame, fragment).commit();
                }
            } else {
                fragmentTransaction.hide(currentFragment).show(fragment).commit();
            }
            currentFragment = fragment;
        }
        showContent();
    }
}
