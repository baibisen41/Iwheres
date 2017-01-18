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
public class PoiUtil implements OnGetPoiSearchResultListener {

    private PoiSearch poiSearch;
    private String busUid;

    public void startPoi() {
        poiSearch.searchNearby(new PoiNearbySearchOption().keyword("公交车").location(new LatLng(38.887726, 121.562108)).radius(500));
    }

    public void stopPoi() {
        poiSearch.destroy();
    }

    public void getPoiData() {
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
    }

    public PoiResult getPoiResult(PoiResult poiResult) {
        return poiResult;
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null
                || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Log.e("LogPoiUtilerror", "无结果");
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回

            for (PoiInfo poiInfo : poiResult.getAllPoi()) {
                Log.e("LogPoiUtil", poiInfo.address);//通过poiInfo.address可以查出该站点的所有公交线路
            }
        }

        getPoiResult(poiResult);
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
