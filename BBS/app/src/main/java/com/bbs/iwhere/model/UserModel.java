package com.bbs.iwhere.model;

/**
 * Created by 大森 on 2017/5/14.
 */

public class UserModel {

    private int userId;
    private double longitude;
    private double latitude;
    private int currentFlag;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(int currentFlag) {
        this.currentFlag = currentFlag;
    }
}
