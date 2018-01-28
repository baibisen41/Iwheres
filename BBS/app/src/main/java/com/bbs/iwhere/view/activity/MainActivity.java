package com.bbs.iwhere.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.db.MainHelper;
import com.bbs.iwhere.view.fragment.FriendManager.ContactListFragment;
import com.bbs.iwhere.view.fragment.FriendManager.ConversationListFragment;
import com.bbs.iwhere.view.fragment.FriendManager.FriendFragment;
import com.bbs.iwhere.view.fragment.LeftmenuFragment;
import com.bbs.iwhere.view.fragment.LocationopenFragment;
import com.bbs.slidingmenu.SlidingMenu;
import com.bbs.slidingmenu.app.SlidingFragmentActivity;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.chat.EMMessage;

import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private TextView addItemPop;
    private TextView aboutItemPop;
    private ImageView titleLeftBt;
    private ImageView titleRightBt;
    private PopupWindow popupWindow;
    private Fragment leftFragment;
    private Fragment currentFragment = null;
    protected SlidingMenu rightLeftSlidingMenu;
    private boolean isFirst = true;
    private LocalBroadcastManager broadcastManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        setContentView(R.layout.activity_main);
        initView();
//        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
    }

    private void initView() {
        titleLeftBt = (ImageView) findViewById(R.id.leftbutton);
        titleLeftBt.setOnClickListener(this);
        titleRightBt = (ImageView) findViewById(R.id.rightbutton);
        titleRightBt.setOnClickListener(this);
        if (isFirst) {
            switchFragment(new LocationopenFragment());
//            currentFragment = new LocationopenFragment();
            isFirst = false;
            Log.e("isFirst:", String.valueOf(isFirst));
        }
    }

    private void initPop() {
        View popupView = MainActivity.this.getLayoutInflater().inflate(R.layout.popwindow_layout, null);

        addItemPop = (TextView) popupView.findViewById(R.id.addfirenditem);
        addItemPop.setOnClickListener(this);
        aboutItemPop = (TextView) popupView.findViewById(R.id.aboutitem);
        aboutItemPop.setOnClickListener(this);
        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        popupWindow = new PopupWindow(popupView);

        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);// 点击屏幕任意处消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());// 点击屏幕任意处消失
        popupWindow.setFocusable(true);// 点击按钮消失
        popupWindow.showAsDropDown(titleRightBt);
    }

    private void initSlidingMenu() {
        setBehindContentView(R.layout.main_left_layout);
        FragmentTransaction leftFragmentTransaction = getSupportFragmentManager().beginTransaction();
        leftFragment = new LeftmenuFragment();
        leftFragmentTransaction.replace(R.id.main_left_fragment, leftFragment).commit();
//        leftFragmentTransaction.replace(R.id.default_frame, new LocationopenFragment());
//        leftFragmentTransaction.commit();
//        currentFragment = new LocationopenFragment();

        rightLeftSlidingMenu = getSlidingMenu();
        rightLeftSlidingMenu.setMode(SlidingMenu.LEFT);// 设置是左滑还是右滑，还是左右都可以滑
        rightLeftSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 设置菜单宽度
        rightLeftSlidingMenu.setFadeDegree(0.35f);// 设置淡入淡出的比例
//        rightLeftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置手势模式
        rightLeftSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        rightLeftSlidingMenu.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
        rightLeftSlidingMenu.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
        rightLeftSlidingMenu.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            MainActivity.this.moveTaskToBack(true);//退到后台
            return false;
        } else {
            return super.onKeyDown(keyCode, event);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftbutton:
                rightLeftSlidingMenu.showMenu();
                break;
            case R.id.rightbutton:
                initPop();
                break;
            case R.id.addfirenditem:
                startActivity(new Intent(this, NewFriendActivity.class));
                overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.aboutitem:
                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(R.anim.in_to_left, 0);
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
                    Log.e("bbs", "currentFragment not null");
                } else {
                    fragmentTransaction.add(R.id.default_frame, fragment).commit();
                    Log.e("bbs", "currentFragment null");
                }
            } else {
                fragmentTransaction.hide(currentFragment).show(fragment).commit();
            }
            currentFragment = fragment;
        }
        showContent();
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                MainHelper.getInstance().getNotifier().onNewMsg(message);
            }
            broadcastManager.sendBroadcast(new Intent(AppConstants.ACTION_CONVERSATION_CHANAGED));
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
