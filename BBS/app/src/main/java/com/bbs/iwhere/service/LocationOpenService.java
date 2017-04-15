package com.bbs.iwhere.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.bbs.iwhere.service.common.BaseLocationService;
import com.bbs.iwhere.util.LocationUtil;
import com.bbs.iwhere.util.PoiUtil;

/**
 * Created by 大森 on 2017/3/16.
 */

public class LocationOpenService extends BaseLocationService implements BDLocationListener, OnGetPoiSearchResultListener {

    private LocationUtil mLocationUtil = new LocationUtil();
    private PoiUtil mPoiUtil = new PoiUtil();
    private double saveLatitude = 0.0;
    private double saveLongitude = 0.0;
    private LocationOpenCallback locationOpenCallback;
    private boolean firstLocation = true;

    //使用接口回调传递数据
    public void setLocationOpenCallback(LocationOpenCallback locationOpenCallback) {
        this.locationOpenCallback = locationOpenCallback;
    }

    public void startLocationService(Context context) {
        mLocationUtil.getLocationData(context, this);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.e("testlocationstr", bdLocation.getAddrStr());
        if (bdLocation.getLatitude() != saveLatitude && bdLocation.getLongitude() != saveLongitude) {
            locationOpenCallback.showUserLocation(bdLocation.getAddrStr());
            locationOpenCallback.showUserData(bdLocation.getLatitude(), bdLocation.getLongitude());
            mPoiUtil.getPoiData(this);
            mPoiUtil.startPoi(bdLocation.getLatitude(), bdLocation.getLongitude());
            if (firstLocation == true) {
                reqPostJson();//先获取打开定位时的状态信息发送至网络，避免网络请求过于频繁
                firstLocation = false;
            }
            saveLatitude = bdLocation.getLatitude();
            saveLongitude = bdLocation.getLongitude();
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null
                || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Log.e("LogPoiUtilerror", "无结果");
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            locationOpenCallback.showUserBus(poiResult);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
