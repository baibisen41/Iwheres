package com.bbs.iwhere.view.fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.baidu.mapapi.search.poi.PoiResult;
import com.bbs.iwhere.R;
import com.bbs.iwhere.service.LocationOpenCallback;
import com.bbs.iwhere.service.LocationOpenService;
import com.bbs.iwhere.view.activity.LocationopenActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;


/**
 * Created by 大森 on 2016/11/8.
 */

public class LocationopenFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView locationopenaddress;
    private TextView nearby_bus1;
    private TextView nearby_bus2;
    private TextView nearby_bus3;
    private TextView nearby_bus4;
    private TextView nearby_bus5;
    private TextView nearby_busdetail1;
    private TextView nearby_busdetail2;
    private TextView nearby_busdetail3;
    private TextView nearby_busdetail4;
    private TextView nearby_busdetail5;
    private Button showbutton;
    private TextView userstatus;
    private ImageView statusSwitch;
    private boolean bstatusOnorClose = false;
    private LocationOpenService mLocationOpenService = new LocationOpenService();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_left_location_open, container, false);
        initLocationView();
        return view;
    }

    private void initLocationView() {
        showbutton = findViewId(view, R.id.show_switch);
        showbutton.setOnClickListener(this);
        locationopenaddress = findViewId(view, R.id.locationdetail);
        nearby_bus1 = findViewId(view, R.id.nearby_busA);
        nearby_bus2 = findViewId(view, R.id.nearby_busB);
        nearby_bus3 = findViewId(view, R.id.nearby_busC);
        nearby_bus4 = findViewId(view, R.id.nearby_busD);
        nearby_bus5 = findViewId(view, R.id.nearby_busE);
        nearby_busdetail1 = findViewId(view, R.id.nearby_busdetailA);
        nearby_busdetail2 = findViewId(view, R.id.nearby_busdetailB);
        nearby_busdetail3 = findViewId(view, R.id.nearby_busdetailC);
        nearby_busdetail4 = findViewId(view, R.id.nearby_busdetailD);
        nearby_busdetail5 = findViewId(view, R.id.nearby_busdetailE);
        nearby_bus3 = findViewId(view, R.id.nearby_busdetailC);
        statusSwitch = findViewId(view, R.id.statuswitch);
        statusSwitch.setOnClickListener(this);
        userstatus = findViewId(view, R.id.userstatus);
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
            mLocationOpenService.startLocationService(getActivity());
            mLocationOpenService.setLocationOpenCallback(new LocationOpenCallback() {//实现接口回掉
                @Override
                public void showUserStatus() {

                }

                @Override
                public void showUserLocation(String strLocation) {
                    if (!strLocation.equals("")) {
                        Log.e("strlocation", strLocation);
                        showLocation(strLocation);
                    }
                }

                @Override
                public void showUserBus(PoiResult poiResult) {
                    if (poiResult != null) {
                        showBus(poiResult);
                    }
                }
            });

        } else {
            bstatusOnorClose = false;
            statusSwitch.setImageResource(R.mipmap.switch_normal);
            userstatus.setText("离线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.cutline));
            clearBus();
        }
    }

    //清空
    public void clearBus() {
        locationopenaddress.setText("");
        nearby_bus1.setText("");
        nearby_bus2.setText("");
        nearby_bus3.setText("");
        nearby_bus4.setText("");
        nearby_bus5.setText("");
        nearby_busdetail1.setText("");
        nearby_busdetail2.setText("");
        nearby_busdetail3.setText("");
        nearby_busdetail4.setText("");
        nearby_busdetail5.setText("");
    }


    public void showLocation(String strLocation) {
        locationopenaddress.setText(strLocation);
    }


    public void showBus(PoiResult poiResult) {
        int poiNumber = poiResult.getTotalPoiNum();
        switch (poiNumber) {
            case 1:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                break;
            case 2:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                break;
            case 3:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                nearby_bus3.setText(poiResult.getAllPoi().get(2).name);
                nearby_busdetail3.setText(poiResult.getAllPoi().get(2).address);
                break;
            case 4:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                nearby_bus3.setText(poiResult.getAllPoi().get(2).name);
                nearby_busdetail3.setText(poiResult.getAllPoi().get(2).address);
                nearby_bus4.setText(poiResult.getAllPoi().get(3).name);
                nearby_busdetail4.setText(poiResult.getAllPoi().get(3).address);
                break;
            case 5:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                nearby_bus3.setText(poiResult.getAllPoi().get(2).name);
                nearby_busdetail3.setText(poiResult.getAllPoi().get(2).address);
                nearby_bus4.setText(poiResult.getAllPoi().get(3).name);
                nearby_busdetail4.setText(poiResult.getAllPoi().get(3).address);
                nearby_bus5.setText(poiResult.getAllPoi().get(4).name);
                nearby_busdetail5.setText(poiResult.getAllPoi().get(4).address);
                break;
            default:
                Toast.makeText(getActivity(), "超出显示范围", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
