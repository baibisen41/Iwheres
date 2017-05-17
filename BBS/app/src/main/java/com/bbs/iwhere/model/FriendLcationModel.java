package com.bbs.iwhere.model;

/**
 * Created by 大森 on 2017/5/14.
 */

public class FriendLcationModel {

    private int userId;
    private String userSdUrl;
    private String userName;
    private String userStatus;
    private double userLongitude;
    private double userLatitude;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserSdUrl() {
        return userSdUrl;
    }

    public void setUserSdUrl(String userSdUrl) {
        this.userSdUrl = userSdUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }
}
