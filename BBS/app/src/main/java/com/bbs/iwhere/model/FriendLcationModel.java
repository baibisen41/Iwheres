package com.bbs.iwhere.model;

/**
 * Created by 大森 on 2017/5/14.
 */

public class FriendLcationModel {

    private String userid;
    private String userlongitude;
    private String userlatitude;
    private String userstatus;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserlongitude() {
        return userlongitude;
    }

    public void setUserlongitude(String userlongitude) {
        this.userlongitude = userlongitude;
    }

    public String getUserlatitude() {
        return userlatitude;
    }

    public void setUserlatitude(String userlatitude) {
        this.userlatitude = userlatitude;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }
}
