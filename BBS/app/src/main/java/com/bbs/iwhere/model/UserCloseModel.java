package com.bbs.iwhere.model;

/**
 * Created by 大森 on 2017/5/14.
 */

public class UserCloseModel {
    private int userId;
    private int currentFlag;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(int currentFlag) {
        this.currentFlag = currentFlag;
    }
}
