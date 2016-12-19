package com.bbs.iwhere.model;

/**
 * Created by beasley on 2016/11/29.
 */

public class LocationOpen {

    private int locationStatus;  //是否打开定位开关
    private String myLongitude;  //我的经度
    private String myLatitude;    //我的纬度
    private String myDetiallocation;  //我的详细地址
    private String myMaplocation;  //地图信息

    public int getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(int locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(String myLongitude) {
        this.myLongitude = myLongitude;
    }

    public String getMyDetiallocation() {
        return myDetiallocation;
    }

    public void setMyDetiallocation(String myDetiallocation) {
        this.myDetiallocation = myDetiallocation;
    }

    public String getMyLatitude() {
        return myLatitude;
    }

    public void setMyLatitude(String myLatitude) {
        this.myLatitude = myLatitude;
    }

    public String getMyMaplocation() {
        return myMaplocation;
    }

    public void setMyMaplocation(String myMaplocation) {
        this.myMaplocation = myMaplocation;
    }
}
