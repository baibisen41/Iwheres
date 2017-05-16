package com.bbs.iwhere.model;

/**
 * Created by IT8000 on 2017/5/16.
 */

public class FriendListModel {

    private int userId;
    private String friendPic;
    private String friendName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFriendPic() {
        return friendPic;
    }

    public void setFriendPic(String friendPic) {
        this.friendPic = friendPic;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
