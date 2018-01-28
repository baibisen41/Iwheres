package com.bbs.iwhere.service.LocationOpenService;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.bbs.iwhere.model.UserCloseModel;
import com.bbs.iwhere.model.UserModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.iwhere.util.LocationUtil;
import com.bbs.iwhere.util.PoiUtil;
import com.bbs.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 大森 on 2017/3/16.
 */

public class LocationOpenService extends BaseService implements BDLocationListener, OnGetPoiSearchResultListener {

    private UserModel user = new UserModel();
    private UserCloseModel userClose = new UserCloseModel();
    private LocationUtil mLocationUtil = new LocationUtil();
    private JsonUtil mJsonUtil = new JsonUtil();
    private PoiUtil mPoiUtil = new PoiUtil();
    private double saveLatitude = 0.0;
    private double saveLongitude = 0.0;
    private LocationOpenCallback locationOpenCallback;
    private boolean firstLocation = true;
    private int userId = 2013082401;
    private int statusFlag = 0; //0 打开  1 关闭
    private String url = BaseUrl + "/UserLocationServlet";

    //当用户关闭定位时，发送关闭状态信息
    public void closeLocationOpen() {
        statusFlag = 1;
        userClose.setUserId(userId);
        userClose.setCurrentFlag(statusFlag);
        reqPostJson(url, mJsonUtil.postJson(userClose), new PostMyLocationData());
        mLocationUtil.stopLocation(this);
        Log.e("LocationOpenService","close location");
    }

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
//            if (firstLocation == true) {
            //先获取打开定位时的状态信息发送至网络，避免网络请求过于频繁
            user.setUserId(userId);
            user.setLongitude(bdLocation.getLongitude());
            user.setLatitude(bdLocation.getLatitude());
            user.setCurrentFlag(statusFlag);
            reqPostJson(url, mJsonUtil.postJson(user), new PostMyLocationData());//转化成json，并发送
//                firstLocation = false;
//            }
            saveLatitude = bdLocation.getLatitude();
            saveLongitude = bdLocation.getLongitude();
        }
    }

    public class PostMyLocationData extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("bbsError", e.toString());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e("bbsSuccess", response);
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
