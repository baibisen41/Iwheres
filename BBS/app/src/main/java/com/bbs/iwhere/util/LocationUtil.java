package com.bbs.iwhere.util;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by beasley on 2017/1/17.
 */

/*封装定位功能工具类*/
public class LocationUtil {

    private LocationClient locationClient = null;
    private LocationClientOption locationClientOption;

    public void startLocation() {

    }

    public void stopLocation(BDLocationListener bdLocationListener) {
        locationClient.stop();
        locationClient.unRegisterLocationListener(bdLocationListener);
    }

    public void locationOption() {
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setScanSpan(5000);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setAddrType("all");
    }

    public void getLocationData(Context context, BDLocationListener bdLocationListener) {
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(bdLocationListener);
        locationOption();
        locationClient.setLocOption(locationClientOption);
        locationClient.start();
    }
}
