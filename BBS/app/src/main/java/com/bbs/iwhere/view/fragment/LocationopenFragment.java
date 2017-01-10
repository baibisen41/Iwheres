package com.bbs.iwhere.view.fragment;

import android.app.Activity;
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
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bbs.iwhere.R;
import com.bbs.iwhere.view.activity.LocationopenActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;
import com.bbs.iwhere.view.util.MapoiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationopenFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView locationopenaddress;
    private Button showbutton;
    private TextView userstatus;
    private ImageView statusSwitch;
    private LocationClient locationClient;
    private PoiSearch poiSearch;
    private boolean bstatusOnorClose = false;
    //private MapoiUtil mapoiUtil = new MapoiUtil();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location_open, container, false);
        poiSearch = PoiSearch.newInstance();
        getPoiData();
        initLocationView();
        getLocationData();
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

    private void statusOnorClose() {
        if (bstatusOnorClose == false) {
            bstatusOnorClose = true;
            statusSwitch.setImageResource(R.mipmap.switch_pressed);
            userstatus.setText("在线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.userstatuscolor));
            getLocationData();
            locationClient.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startPoi();
                }
            }).start();
        } else {
            bstatusOnorClose = false;
            statusSwitch.setImageResource(R.mipmap.switch_normal);
            userstatus.setText("离线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.cutline));
            locationClient.stop();
            locationopenaddress.setText("");
        }
    }

    private void getLocationData() {
        locationClient = new LocationClient(getActivity().getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值是gcj02 是bg09ll 不是bg0911
        option.setScanSpan(5000);//设置发起定位请求的时间间隔为5000ms
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                locationopenaddress.setText(bdLocation.getAddrStr());
            }
        });
    }

    private void getPoiData() {
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Log.e("poi结果", "无结果");
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    List<PoiInfo> allpoi = new ArrayList<>();
                    for (PoiInfo poi : allpoi) {
                        Log.e("poi信息", poi.toString());
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    private void startPoi() {
        LatLng latLng = new LatLng(121.529596, 38.867355);
        poiSearch.searchNearby(new PoiNearbySearchOption().location(latLng).radius(1000).pageNum(1).keyword("公交站"));
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
