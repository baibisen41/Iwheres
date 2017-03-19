package com.bbs.iwhere.util;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by beasley on 2017/1/17.
 */

/*封装公交信息工具类*/
public class PoiUtil {

    private PoiSearch poiSearch;

    public void startPoi(double poiX, double poiY) {
        poiSearch.searchNearby(new PoiNearbySearchOption().keyword("公交车").location(new LatLng(poiX, poiY)).radius(300));
    }

    public void stopPoi() {
        poiSearch.destroy();
    }

    public void getPoiData(OnGetPoiSearchResultListener onGetPoiSearchResultListener) {
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
    }
}
