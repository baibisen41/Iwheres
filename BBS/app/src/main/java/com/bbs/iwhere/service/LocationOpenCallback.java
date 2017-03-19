package com.bbs.iwhere.service;

import android.content.Context;

import com.baidu.mapapi.search.poi.PoiResult;

/**
 * Created by 大森 on 2017/3/16.
 */

public interface LocationOpenCallback {

    void showUserStatus();

    void showUserLocation(String strLocation);

    void showUserBus(PoiResult poiResult);

}
