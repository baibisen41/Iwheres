package com.iwhere.bbs.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.iwhere.bbs.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }
}
