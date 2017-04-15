package com.bbs.iwhere.service;

/**
 * Created by 大森 on 2017/3/19.
 */

public interface LocationCallback {

    void showFriendLocationStatus();

    void showFriendPic();

    void showFriendName();

    void showFriendLocation(String text);

    void showFriendLocationData(int[] data);

}
