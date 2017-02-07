package com.bbs.iwhere.view.fragment;

import com.baidu.mapapi.search.poi.PoiResult;


/**
 * Created by ${白碧森} on ${2017/1/18}.
 */

public interface IMyLocationView {

    void showLocation(String strLocation);

    void showBus(PoiResult poiResult);

}
