package com.bbs.iwhere.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbs.iwhere.R;
import com.bbs.iwhere.service.LocationCallback;
import com.bbs.iwhere.service.LocationService;
import com.bbs.iwhere.view.fragment.common.BaseFragment;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationFragment extends BaseFragment {

    private View view;
    private TextView friendLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.content_left_location, container, false);
        friendLocation = (TextView) view.findViewById(R.id.friendaddress);
        new LocationService().setLocationCallback(new LocationCallback() {
            @Override
            public void showFriendLocationStatus() {

            }

            @Override
            public void showFriendPic() {

            }

            @Override
            public void showFriendName() {

            }

            @Override
            public void showFriendLocation() {

            }
        });
        return view;
    }


}
