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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
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
    private TextView nearby_bus1;
    private TextView nearby_bus2;
    private TextView nearby_bus3;
    private TextView nearby_busdetail1;
    private TextView nearby_busdetail2;
    private TextView nearby_busdetail3;
    private Button showbutton;
    private TextView userstatus;
    private ImageView statusSwitch;
    private boolean bstatusOnorClose = false;
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
        nearby_bus1 = (TextView) view.findViewById(R.id.nearby_busA);
        nearby_bus2 = (TextView) view.findViewById(R.id.nearby_busB);
        nearby_bus3 = (TextView) view.findViewById(R.id.nearby_busC);
        nearby_busdetail1 = (TextView) view.findViewById(R.id.nearby_busdetail);
        nearby_busdetail2 = (TextView) view.findViewById(R.id.nearby_busdetailB);
        nearby_bus3 = (TextView) view.findViewById(R.id.nearby_busdetailC);
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
            locationUtil.getLocationData(getActivity().getApplicationContext(), bdLocationListeners);
            locationUtil.startLocation();
            poiUtil.getPoiData(onGetPoiSearchResultListener);
            poiUtil.startPoi();

        } else {
            bstatusOnorClose = false;
            statusSwitch.setImageResource(R.mipmap.switch_normal);
            userstatus.setText("离线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.cutline));
            locationUtil.stopLocation(bdLocationListeners);
            poiUtil.stopPoi();
            locationopenaddress.setText("");
            nearby_bus2.setText("");
            nearby_bus1.setText("");
            nearby_busdetail1.setText("");
            nearby_busdetail2.setText("");
        }
    }

    /*定位功能返回结果*/
    BDLocationListener bdLocationListeners = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e("abcdesf", bdLocation.getAddrStr());
            locationopenaddress.setText(bdLocation.getAddrStr());
            //使用SharedPreferences进行保存定位信息，然后在POI中获取展示

        }
    };

    /*附近公交站返回结果*/
    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null
                    || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                Log.e("LogPoiUtilerror", "无结果");
                return;
            }

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回

                if (poiResult.getAllPoi().size() <= 3) {
                    nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                    nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                    nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                    nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                } else {

                }
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
}
