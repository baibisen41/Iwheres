package com.bbs.iwhere.service;

import com.bbs.iwhere.service.common.BaseLocationService;

/**
 * Created by 大森 on 2017/3/19.
 */

public class LocationService extends BaseLocationService {

    private LocationCallback locationCallback;
    private String strlocation;
    private boolean friendStatus = false;//好友状态默认为false，如果false状态，address和showmap等都传入null值

    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    public void showFriendLocation() {
        reqGetJson();
        locationCallback.showFriendPic();
        locationCallback.showFriendName();
        locationCallback.showFriendLocationStatus();
        locationCallback.showFriendLocation(strlocation);
    }


}
