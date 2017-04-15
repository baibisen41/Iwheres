package com.bbs.iwhere.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bbs.iwhere.R;
import com.bbs.iwhere.util.MyDirectionListener;

/**
 * Created by beasley on 2017/1/4.
 */

/*地图显示封装类*/
public class LocationShowActivity extends Activity implements View.OnClickListener {

    protected ImageView mapback = null;
    //地图控件
    public MapView mapView = null;
    //百度地图对象
    public BaiduMap baiduMap = null;
    protected Context mContext;
    private MyLocationConfiguration.LocationMode mLocationMode;
    private MyLocationListener mLocationListener;
    //定位相关声明
    public LocationClient locationClient = null;
    //自定义图标
    protected BitmapDescriptor mCurrentMarket;
    private MyDirectionListener myDirectionListener;
    private float mCurrentX;
    //是否首次定位
    boolean isFirstLoc = true;
    //得到经纬度
    private double longitude;
    private double latitude;
    private double radius;

    private int typeFlag = 0;//flag代表该activity显示的是自己的地图还是好友地图，0默认代表自己地图

    //好友定位信息
    private String friendLocationType = "";
    private double[] friendLocation;
    private double friendLongitude = 0.0;
    private double friendLatitude = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylocation);
        this.mContext = this;

        friendLocationType = getIntent().getStringExtra("friendLocationType");//获取从locationFragment中跳转附加的参数
        friendLocation = getIntent().getDoubleArrayExtra("friendLocation");//获取发送过来的数据包
        if (friendLocationType != null && friendLocation != null) {

            typeFlag = Integer.parseInt(friendLocationType);
            Log.e("baibisen1", friendLocationType);

            friendLatitude = friendLocation[0];
            friendLongitude = friendLocation[1];
        } else if (friendLocationType != null && friendLocation == null) {
            Toast.makeText(this, "拉取好友数据失败", Toast.LENGTH_SHORT).show();
        }

        initView();
        initLocation();
    }

    private void initView() {
        mapback = (ImageView) findViewById(R.id.map_back);
        mapback.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.imp);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置为一般地图
        //  baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//设置为卫星地图
        baiduMap.setTrafficEnabled(true);//开启交通图

        //隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
//        //地图上比例尺
//        mapView.showScaleControl(false);
//        // 隐藏缩放控件
//        mapView.showZoomControls(false);

    }

    private void initLocation() {
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
        locationClient = new LocationClient(getApplicationContext());
        mLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(mLocationListener);
        this.setLocationOption();//设置定位参数
        mCurrentMarket = BitmapDescriptorFactory
                .fromResource(R.mipmap.rocket);
        myDirectionListener = new MyDirectionListener(mContext);
        myDirectionListener.setmOnOrientationListener(new MyDirectionListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值是gcj02 是bg09ll 不是bg0911
        option.setScanSpan(1000);//设置发起定位请求的时间间隔为5000ms
        option.setIsNeedAddress(true);//返回的定位结果饱饭地址信息
        option.setNeedDeviceDirect(true);// 返回的定位信息包含手机的机头方向
        locationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_back:
                finish();
                overridePendingTransition(0, R.anim.out_to_right);
                break;
            default:
                break;

        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (typeFlag == 0) {
                //主人态
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                radius = location.getRadius();
                Log.e("bbsbbsLO", String.valueOf(longitude));
                Log.e("bbsbbsLA", String.valueOf(latitude));
            } else if (typeFlag == 1) {
                //好友态
                longitude = friendLongitude;
                latitude = friendLatitude;
            }

            MyLocationConfiguration config = new MyLocationConfiguration(
                    mLocationMode, true, mCurrentMarket);
            baiduMap.setMyLocationConfigeration(config);

            //设置定位参数
            MyLocationData locData = new MyLocationData.Builder()
                    //此处设置开发者获取到的方向信息，顺时针0-360
                    .accuracy(location.getRadius())//设置定位数据的精度
                    .direction(mCurrentX)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
            baiduMap.setMyLocationData(locData);


            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(latitude, longitude);
                //设置地图中心点以及缩放级别  MapStatusUpdate地图状态更新
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(ll, 15.0f);
                //以动画方式更新地图状态，动画耗时 300 ms
                baiduMap.animateMapStatus(mapStatusUpdate);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationClient.start();
        myDirectionListener.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
        myDirectionListener.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        overridePendingTransition(0, R.anim.out_to_right);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this.finish();
            overridePendingTransition(0, R.anim.out_to_right);
        }

        return false;
    }
}

