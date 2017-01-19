package com.bbs.iwhere.view.util;

import android.util.Log;

import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by beasley on 2017/1/17.
 */

/*封装公交信息工具类*/
public class PoiUtil {

    private PoiSearch poiSearch;
    private String busUid;

    public void startPoi() {
        poiSearch.searchNearby(new PoiNearbySearchOption().keyword("公交车").location(new LatLng(38.887726, 121.562108)).radius(300));
    }

    public void stopPoi() {
        poiSearch.destroy();
    }

    public void getPoiData(OnGetPoiSearchResultListener onGetPoiSearchResultListener) {
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
    }
}
