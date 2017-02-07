package com.bbs.iwhere.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.bbs.iwhere.model.MyLocation;
import com.bbs.iwhere.model.MyLocationPoi;
import com.bbs.iwhere.view.fragment.IMyLocationView;
import com.bbs.iwhere.view.util.LocationUtil;
import com.bbs.iwhere.view.util.PoiUtil;

import java.util.List;

/**
 * Created by ${白碧森} on ${2017/1/18}.
 */

public class MyLocationPresenterImpl implements MyLocationPresenter, BDLocationListener, OnGetPoiSearchResultListener {

    private IMyLocationView myLocationView;
    private MyLocation myLocation = new MyLocation();
    private MyLocationPoi myLocationPoi = new MyLocationPoi();
    private LocationUtil locationUtil = new LocationUtil();
    private PoiUtil poiUtil = new PoiUtil();
    boolean firstShow = true;
    double saveLatitude = 0.0;
    double saveLongitude = 0.0;
    List<PoiAddrInfo> poiAddrInfoList = null;

    public MyLocationPresenterImpl(IMyLocationView myLocationView) {
        this.myLocationView = myLocationView;
    }

    @Override
    public void startLocation(Context context) {
        locationUtil.getLocationData(context, this);
    }

    @Override
    public void stopLocation() {
        locationUtil.stopLocation(this);
    }

    @Override
    public void stopPoi() {
        poiUtil.stopPoi();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.e("abcdesf", bdLocation.getAddrStr());
        if (bdLocation.getLatitude() != saveLatitude && bdLocation.getLongitude() != saveLongitude) {
            myLocationView.showLocation(bdLocation.getAddrStr());
            poiUtil.getPoiData(this);
            poiUtil.startPoi(bdLocation.getLatitude(), bdLocation.getLongitude());
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
            myLocationView.showBus(poiResult);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
