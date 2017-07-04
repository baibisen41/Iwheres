package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bbs.iwhere.R;

/**
 * Created by 大森 on 2017/4/25.
 */

public class MeSettingActivity extends Activity implements View.OnClickListener {

    ImageView backview;
    ImageView loginIcon1;
    ImageView loginIcon2;
    ImageView loginIcon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_layout);
        backview = (ImageView) findViewById(R.id.me_back);
        loginIcon1 = (ImageView) findViewById(R.id.loginIcon1);
        loginIcon2 = (ImageView) findViewById(R.id.loginIcon2);
        loginIcon3 = (ImageView) findViewById(R.id.loginIcon3);
        backview.setOnClickListener(this);
        loginIcon1.setOnClickListener(this);
        loginIcon2.setOnClickListener(this);
        loginIcon3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_back:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            case R.id.loginIcon1:
                Toast.makeText(this, "服务器出现问题", Toast.LENGTH_LONG).show();
                break;
            case R.id.loginIcon2:
                Toast.makeText(this, "服务器出现问题", Toast.LENGTH_LONG).show();
                break;
            case R.id.loginIcon3:
                Toast.makeText(this, "服务器出现问题", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
