package com.bbs.iwhere.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bbs.iwhere.R;

/**
 * Created by 大森 on 2016/11/7.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private ImageView pushSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_left_setting, container, false);
        pushSwitch = (ImageView) view.findViewById(R.id.push_switch);
        pushSwitch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        pushSwitch.setImageResource(R.mipmap.push_pressed);

    }
}
