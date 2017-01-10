package com.bbs.iwhere.view.util;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beasley on 2017/1/10.
 */

public class MapoiUtil {
    private PoiSearch poiSearch;

    public void getPoiData() {
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(poiListener);
//        poiSearch.searchNearby(new PoiNearbySearchOption().keyword("公交站").pageNum(10));
    }

    public void startPoi() {
        nearbySearch(0);
    }

    private void nearbySearch(int page) {
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
//        nearbySearchOption.location(new LatLng(latitude, longitude));
//        nearbySearchOption.keyword(editSearchKeyEt.getText().toString());
        LatLng latLng = new LatLng(121.529596, 38.867355);
        nearbySearchOption.keyword("公交站");
        nearbySearchOption.radius(1000);// 检索半径，单位是米
        nearbySearchOption.pageNum(page);
        nearbySearchOption.location(latLng);
        poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null
                    || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                Log.e("poi结果", "无结果");
                return;
            }

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                List<PoiInfo> allpoi = new ArrayList<>();
                for (PoiInfo poi : allpoi) {
                    Log.e("poi信息", poi.toString());
                }
            }

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };


}
