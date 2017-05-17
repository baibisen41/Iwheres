package com.bbs.iwhere.service.LocationService;

/**
 * Created by 大森 on 2017/3/19.
 */

public interface LocationCallback {

    void showFriendLocationStatus(String userStatus);

    void showFriendPic(String url);

    void showFriendName(String userName);

    void showFriendLocation(String text);

    void showFriendLocationData(int[] data);

}
