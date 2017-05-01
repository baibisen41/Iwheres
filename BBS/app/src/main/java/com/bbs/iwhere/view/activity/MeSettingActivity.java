package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bbs.iwhere.R;

/**
 * Created by 大森 on 2017/4/25.
 */

public class MeSettingActivity extends Activity implements View.OnClickListener {

    ImageView backview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_setting_layout);
        backview = (ImageView) findViewById(R.id.me_back);
        backview.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_back:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            default:
                break;
        }
    }
}
