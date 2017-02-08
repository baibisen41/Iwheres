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
import com.bbs.iwhere.presenter.LocationPresenter;
import com.bbs.iwhere.presenter.LocationPresenterImpl;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationFragment extends Fragment implements LocationView {

    protected Context mcontext;
    private View view = null;
    private TextView friendLocation;
    private LocationPresenter locationPresenter = new LocationPresenterImpl(this);

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
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showFriendStatus() {

    }

    @Override
    public void showFriendLocation(String strFriendLocation) {
        friendLocation.setText(strFriendLocation);
    }
}
