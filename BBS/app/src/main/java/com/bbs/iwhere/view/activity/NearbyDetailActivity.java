package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbs.iwhere.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by 大森 on 2017/6/7.
 */

public class NearbyDetailActivity extends Activity implements View.OnClickListener {

    private ImageView back;
    private ImageView strimage;
    private TextView strname;
    private String userpicsd;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_detail);
        userpicsd = getIntent().getStringExtra("userpic");
        username = getIntent().getStringExtra("username");
        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.map_back);
        back.setOnClickListener(this);
        strimage = (ImageView) findViewById(R.id.nearby_select_pic);
        strname = (TextView) findViewById(R.id.nearby_select_name);
        try {
            strimage.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(userpicsd)));
            strname.setText(username);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_back:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            default:
                break;
        }

    }
}
