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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiResult;
import com.bbs.iwhere.R;
import com.bbs.iwhere.service.LocationOpenService.LocationOpenCallback;
import com.bbs.iwhere.service.LocationOpenService.LocationOpenService;
import com.bbs.iwhere.util.NetworkUtil;
import com.bbs.iwhere.view.activity.LocationShowActivity;
import com.bbs.iwhere.view.activity.MainActivity;
import com.bbs.iwhere.view.activity.RoutePlanActivity;
import com.bbs.iwhere.view.fragment.common.BaseFragment;


/**
 * Created by 大森 on 2016/11/8.
 * <p>
 * 这个类写的简直很操蛋，有时间重构
 * 目前只支持显示7个站点，为了节约时间成本先写死，后期重新改！！！！！
 */

public class LocationopenFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private RelativeLayout buslayoutA;
    private TextView locationopenaddress;
    private TextView nearby_bus1;
    private TextView nearby_bus2;
    private TextView nearby_bus3;
    private TextView nearby_bus4;
    private TextView nearby_bus5;
    private TextView nearby_bus6;
    private TextView nearby_bus7;
    private TextView nearby_busdetail1;
    private TextView nearby_busdetail2;
    private TextView nearby_busdetail3;
    private TextView nearby_busdetail4;
    private TextView nearby_busdetail5;
    private TextView nearby_busdetail6;
    private TextView nearby_busdetail7;
    private Button showbutton;
    private TextView userstatus;
    private ImageView statusSwitch;
    private boolean bstatusOnorClose = false;
    private LocationOpenService mLocationLocationOpenService = new LocationOpenService();

    private LatLng nearby_bus1_location;
    private LatLng nearby_bus1_location1;
    private LatLng nearby_bus1_location2;
    private LatLng nearby_bus1_location3;
    private LatLng nearby_bus1_location4;
    private LatLng nearby_bus1_location5;
    private LatLng nearby_bus1_location6;
    private double[] my_locationData = {0, 0};
    private double[] nearby_bus1_locationData = {0, 0};


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
        nearby_bus1.setOnClickListener(this);
        nearby_bus2 = findViewId(view, R.id.nearby_busB);
        nearby_bus3 = findViewId(view, R.id.nearby_busC);
        nearby_bus4 = findViewId(view, R.id.nearby_busD);
        nearby_bus5 = findViewId(view, R.id.nearby_busE);
        nearby_bus6 = findViewId(view, R.id.nearby_busF);
        nearby_bus7 = findViewId(view, R.id.nearby_busG);
        nearby_busdetail1 = findViewId(view, R.id.nearby_busdetailA);
        nearby_busdetail1.setOnClickListener(this);
        nearby_busdetail2 = findViewId(view, R.id.nearby_busdetailB);
        nearby_busdetail2.setOnClickListener(this);
        nearby_busdetail3 = findViewId(view, R.id.nearby_busdetailC);
        nearby_busdetail3.setOnClickListener(this);
        nearby_busdetail4 = findViewId(view, R.id.nearby_busdetailD);
        nearby_busdetail4.setOnClickListener(this);
        nearby_busdetail5 = findViewId(view, R.id.nearby_busdetailE);
        nearby_busdetail5.setOnClickListener(this);
        nearby_busdetail6 = findViewId(view, R.id.nearby_busdetailF);
        nearby_busdetail6.setOnClickListener(this);
        nearby_busdetail7 = findViewId(view, R.id.nearby_busdetailG);
        nearby_busdetail7.setOnClickListener(this);
        nearby_bus3 = findViewId(view, R.id.nearby_busdetailC);
        statusSwitch = findViewId(view, R.id.statuswitch);
        statusSwitch.setOnClickListener(this);
        userstatus = findViewId(view, R.id.userstatus);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == true) {
            Log.e("LocationOpen", "hidden=" + hidden);
        } else {
            Log.e("LocationOpen", "hidden=" + hidden);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_switch:
                if (bstatusOnorClose == true) {
                    Intent intent = new Intent(getActivity(), LocationShowActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "请先开启定位开关", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.statuswitch:
                statusOnorClose();
                break;

            case R.id.nearby_busdetailA:
                Intent busIntentA = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location.longitude;
                busIntentA.putExtra("busType", "2");
                busIntentA.putExtra("busAData", nearby_bus1_locationData);
                busIntentA.putExtra("myData", my_locationData);
                startActivity(busIntentA);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailB:
                Intent busIntentB = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location1.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location1.longitude;
                busIntentB.putExtra("busType", "2");
                busIntentB.putExtra("busAData", nearby_bus1_locationData);
                busIntentB.putExtra("myData", my_locationData);
                startActivity(busIntentB);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailC:
                Intent busIntentC = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location2.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location2.longitude;
                busIntentC.putExtra("busType", "2");
                busIntentC.putExtra("busAData", nearby_bus1_locationData);
                busIntentC.putExtra("myData", my_locationData);
                startActivity(busIntentC);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailD:
                Intent busIntentD = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location3.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location3.longitude;
                busIntentD.putExtra("busType", "2");
                busIntentD.putExtra("busAData", nearby_bus1_locationData);
                busIntentD.putExtra("myData", my_locationData);
                startActivity(busIntentD);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailE:
                Intent busIntentE = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location4.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location4.longitude;
                busIntentE.putExtra("busType", "2");
                busIntentE.putExtra("busAData", nearby_bus1_locationData);
                busIntentE.putExtra("myData", my_locationData);
                startActivity(busIntentE);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailF:
                Intent busIntentF = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location5.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location5.longitude;
                busIntentF.putExtra("busType", "2");
                busIntentF.putExtra("busAData", nearby_bus1_locationData);
                busIntentF.putExtra("myData", my_locationData);
                startActivity(busIntentF);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.nearby_busdetailG:
                Intent busIntentG = new Intent(getActivity(), RoutePlanActivity.class);
                nearby_bus1_locationData[0] = nearby_bus1_location6.latitude;
                nearby_bus1_locationData[1] = nearby_bus1_location6.longitude;
                busIntentG.putExtra("busType", "2");
                busIntentG.putExtra("busAData", nearby_bus1_locationData);
                busIntentG.putExtra("myData", my_locationData);
                startActivity(busIntentG);
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("LocationOpen", "onResume");
    }

    private void statusOnorClose() {
        if (!NetworkUtil.isNetSupport(getActivity().getApplicationContext())) {
            Toast.makeText(getActivity(), R.string.no_net_error, Toast.LENGTH_LONG).show();
            return;
        }
        if (bstatusOnorClose == false) {
            bstatusOnorClose = true;
            statusSwitch.setImageResource(R.mipmap.switch_pressed);
            userstatus.setText("在线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.userstatuscolor));
            mLocationLocationOpenService.startLocationService(getActivity());
            mLocationLocationOpenService.setLocationOpenCallback(new LocationOpenCallback() {//实现接口回掉
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

                @Override
                public void showUserData(double latitude, double longitude) {
                    my_locationData[0] = latitude;
                    my_locationData[1] = longitude;
                }

            });

        } else {
            bstatusOnorClose = false;
            statusSwitch.setImageResource(R.mipmap.switch_normal);
            userstatus.setText("离线");
            userstatus.setTextColor(getActivity().getResources().getColor(R.color.cutline));
            mLocationLocationOpenService.closeLocationOpen();
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
        nearby_bus6.setText("");
        nearby_bus7.setText("");
        nearby_busdetail1.setText("");
        nearby_busdetail2.setText("");
        nearby_busdetail3.setText("");
        nearby_busdetail4.setText("");
        nearby_busdetail5.setText("");
        nearby_busdetail6.setText("");
        nearby_busdetail7.setText("");
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
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                Log.e("1111", String.valueOf(nearby_bus1_location.longitude));
                break;
            case 2:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                Log.e("222", String.valueOf(nearby_bus1_location.longitude));
                break;
            case 3:
                nearby_bus1.setText(poiResult.getAllPoi().get(0).name);
                nearby_busdetail1.setText(poiResult.getAllPoi().get(0).address);
                nearby_bus2.setText(poiResult.getAllPoi().get(1).name);
                nearby_busdetail2.setText(poiResult.getAllPoi().get(1).address);
                nearby_bus3.setText(poiResult.getAllPoi().get(2).name);
                nearby_busdetail3.setText(poiResult.getAllPoi().get(2).address);
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                nearby_bus1_location2 = poiResult.getAllPoi().get(2).location;
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
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                nearby_bus1_location2 = poiResult.getAllPoi().get(2).location;
                nearby_bus1_location3 = poiResult.getAllPoi().get(3).location;
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
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                nearby_bus1_location2 = poiResult.getAllPoi().get(2).location;
                nearby_bus1_location3 = poiResult.getAllPoi().get(3).location;
                nearby_bus1_location4 = poiResult.getAllPoi().get(4).location;
                break;
            case 6:
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
                nearby_bus6.setText(poiResult.getAllPoi().get(5).name);
                nearby_busdetail6.setText(poiResult.getAllPoi().get(5).address);
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                nearby_bus1_location2 = poiResult.getAllPoi().get(2).location;
                nearby_bus1_location3 = poiResult.getAllPoi().get(3).location;
                nearby_bus1_location4 = poiResult.getAllPoi().get(4).location;
                nearby_bus1_location5 = poiResult.getAllPoi().get(5).location;
                break;
            case 7:
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
                nearby_bus6.setText(poiResult.getAllPoi().get(5).name);
                nearby_busdetail6.setText(poiResult.getAllPoi().get(5).address);
                nearby_bus7.setText(poiResult.getAllPoi().get(6).name);
                nearby_busdetail7.setText(poiResult.getAllPoi().get(6).address);
                //给坐标进行赋值
                nearby_bus1_location = poiResult.getAllPoi().get(0).location;
                nearby_bus1_location1 = poiResult.getAllPoi().get(1).location;
                nearby_bus1_location2 = poiResult.getAllPoi().get(2).location;
                nearby_bus1_location3 = poiResult.getAllPoi().get(3).location;
                nearby_bus1_location4 = poiResult.getAllPoi().get(4).location;
                nearby_bus1_location5 = poiResult.getAllPoi().get(5).location;
                nearby_bus1_location6 = poiResult.getAllPoi().get(6).location;
                break;
            default:
                Toast.makeText(getActivity(), "超出显示范围", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
