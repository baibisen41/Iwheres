package com.bbs.iwhere.model;

/**
 * Created by IT8000 on 2017/5/16.
 */

public class FriendListModel {

    private int userId;
    private String picUrl; //头像网络地址
    private String picSdUrl; //头像本地地址
    private String userName;
    private String userDescription;
    private String userStatus;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicSdUrl() {
        return picSdUrl;
    }

    public void setPicSdUrl(String picSdUrl) {
        this.picSdUrl = picSdUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
