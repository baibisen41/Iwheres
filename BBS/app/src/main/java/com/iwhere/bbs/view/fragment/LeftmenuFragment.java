package com.iwhere.bbs.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwhere.bbs.R;
import com.iwhere.bbs.view.activity.MainActivity;

/**
 * Created by beasley on 2016/11/7.
 */

public class LeftmenuFragment extends Fragment implements View.OnClickListener {
    private View username;
    private View location_open;
    private View location;
    private View way;
    private View setting;
    private Fragment itemFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_left_slidingmenu, container, false);
        username = view.findViewById(R.id.username);
        username.setOnClickListener(this);
        location_open = view.findViewById(R.id.location_open);
        location_open.setOnClickListener(this);
        location = view.findViewById(R.id.location);
        location.setOnClickListener(this);
        way = view.findViewById(R.id.way);
        way.setOnClickListener(this);
        setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.username:
                break;
            case R.id.location_open:
                itemFragment = new LocationopenFragment();
                username.setSelected(false);
                location_open.setSelected(true);
                location.setSelected(false);
                way.setSelected(false);
                setting.setSelected(false);
                break;
            case R.id.location:
                itemFragment = new LocationFragment();
                username.setSelected(false);
                location_open.setSelected(false);
                location.setSelected(true);
                way.setSelected(false);
                setting.setSelected(false);
                break;
            case R.id.way:
                itemFragment = new WayFragment();
                username.setSelected(false);
                location_open.setSelected(false);
                location.setSelected(false);
                way.setSelected(true);
                setting.setSelected(false);
                break;
            case R.id.setting:
                itemFragment = new SettingFragment();
                username.setSelected(false);
                location_open.setSelected(false);
                location.setSelected(false);
                way.setSelected(false);
                setting.setSelected(true);
                break;
            default:
                break;
        }
        if (getActivity() != null) {
            MainActivity main = (MainActivity) getActivity();
            main.switchFragment(itemFragment);
        }

    }
}
