package com.bbs.iwhere.model;

/**
 * Created by IT8000 on 2017/5/16.
 */

public class FriendListModel {

    private String userid;
    private String picurl; //头像网络地址
    private String picsdurl; //头像本地地址
    private String username;
    private String userdescription;
    private String userstatus;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getPicsdurl() {
        return picsdurl;
    }

    public void setPicsdurl(String picsdurl) {
        this.picsdurl = picsdurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserdescription() {
        return userdescription;
    }

    public void setUserdescription(String userdescription) {
        this.userdescription = userdescription;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }
}
