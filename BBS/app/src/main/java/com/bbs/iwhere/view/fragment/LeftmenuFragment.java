package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.MainHelper;
import com.bbs.iwhere.view.activity.MainActivity;
import com.bbs.iwhere.view.activity.MeSettingActivity;
import com.bbs.iwhere.view.fragment.FriendManager.FriendFragment;

/**
 * Created by beasley on 2016/11/7.
 */

public class LeftmenuFragment extends Fragment implements View.OnClickListener {
    private View location_open;
    private View location;
    private View friendmanager;
    private View setting;
    private View usernamePic;
    private View nearbyFriend;
    private Fragment itemFragment = null;
    private TextView username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_left_slidingmenu, container, false);
        username = (TextView) view.findViewById(R.id.username);
        username.setText(MainHelper.getInstance().getCurrentUsernName());
        usernamePic = view.findViewById(R.id.usernamePic);
        usernamePic.setOnClickListener(this);
        location_open = view.findViewById(R.id.location_open);
        location_open.setOnClickListener(this);
        location = view.findViewById(R.id.location);
        location.setOnClickListener(this);
        nearbyFriend = view.findViewById(R.id.nearby);
        nearbyFriend.setOnClickListener(this);
        friendmanager = view.findViewById(R.id.friend_manager);
        friendmanager.setOnClickListener(this);
        setting = view.findViewById(R.id.setting);
        setting.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.usernamePic:
                startActivity(new Intent(getActivity(), MeSettingActivity.class));
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;

            case R.id.location_open:
                itemFragment = new LocationopenFragment();
                location_open.setSelected(true);
                location.setSelected(false);
                nearbyFriend.setSelected(false);
                friendmanager.setSelected(false);
                setting.setSelected(false);
                break;
            case R.id.location:
                itemFragment = new LocationFragment();
                location_open.setSelected(false);
                location.setSelected(true);
                nearbyFriend.setSelected(false);
                friendmanager.setSelected(false);
                setting.setSelected(false);
                break;
            case R.id.nearby:
                itemFragment = new NearbyFragment();
                location_open.setSelected(false);
                location.setSelected(false);
                nearbyFriend.setSelected(true);
                friendmanager.setSelected(false);
                setting.setSelected(false);
                break;

            case R.id.friend_manager:
                itemFragment = new FriendFragment();
                location_open.setSelected(false);
                location.setSelected(false);
                nearbyFriend.setSelected(false);
                friendmanager.setSelected(true);
                setting.setSelected(false);
                break;
            case R.id.setting:
                itemFragment = new SettingFragment();
                location_open.setSelected(false);
                location.setSelected(false);
                nearbyFriend.setSelected(false);
                friendmanager.setSelected(false);
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
