package com.bbs.iwhere.service;

import com.bbs.iwhere.service.common.BaseLocationService;

/**
 * Created by 大森 on 2017/3/19.
 */

public class LocationService extends BaseLocationService {

    private LocationCallback locationCallback;

    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    public void showFriendLocation() {
        reqGetJson();
        locationCallback.showFriendPic();
        locationCallback.showFriendName();
        locationCallback.showFriendLocationStatus();
        locationCallback.showFriendLocation();
    }


}
