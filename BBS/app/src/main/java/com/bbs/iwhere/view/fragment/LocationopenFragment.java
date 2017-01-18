package com.bbs.iwhere.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bbs.iwhere.R;
import com.bbs.iwhere.view.activity.LocationopenActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;
import com.bbs.iwhere.view.util.LocationUtil;
import com.bbs.iwhere.view.util.PoiUtil;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationopenFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView locationopenaddress;
    private Button showbutton;
    private TextView userstatus;
    private ImageView statusSwitch;
    private PoiSearch poiSearch;
    private boolean bstatusOnorClose = false;
    private double locationX;
    private double locationY;
    LocationUtil locationUtil;
    PoiUtil poiUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationUtil = new LocationUtil();
        poiUtil = new PoiUtil();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location_open, container, false);
        initLocationView();
        return view;
    }

    private void initLocationView() {
        showbutton = (Button) view.findViewById(R.id.show_switch);
        showbutton.setOnClickListener(this);
        locationopenaddress = (TextView) view.findViewById(R.id.locationdetail);
        statusSwitch = (ImageView) view.findViewById(R.id.statuswitch);
        statusSwitch.setOnClickListener(this);
        userstatus = (TextView) view.findViewById(R.id.userstatus);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_switch:
                Intent intent = new Intent(getActivity(), LocationopenActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.statuswitch:
                statusOnorClose();
                break;
        }
    }

    BDLocationListener bdLocationListeners = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e("abcdesf", bdLocation.getAddrStr());
            locationopenaddress.setText(bdLocation.getAddrStr());
        }
    };

    private void statusOnorClose() {
        if (bstatusOnorClose == false) {
            bstatusOnorClose = true;
            statusSwitch.setImageResource(R.mipmap.switch_pressed);
            userstatus.setText("在线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.userstatuscolor));
            locationUtil.getLocationData(getActivity().getApplicationContext(), bdLocationListeners);
            locationUtil.startLocation();
            poiUtil.getPoiData();
            poiUtil.startPoi();

        } else {
            bstatusOnorClose = false;
            statusSwitch.setImageResource(R.mipmap.switch_normal);
            userstatus.setText("离线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.cutline));
            locationUtil.stopLocation(bdLocationListeners);
            poiUtil.stopPoi();
            locationopenaddress.setText("");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
