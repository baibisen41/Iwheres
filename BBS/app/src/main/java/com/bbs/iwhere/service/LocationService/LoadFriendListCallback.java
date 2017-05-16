package com.bbs.iwhere.service.LocationService;

import com.bbs.iwhere.model.FriendListModel;

import java.util.List;

/**
 * Created by IT8000 on 2017/5/16.
 */

public interface LoadFriendListCallback {

    void showFriendList(List<FriendListModel> friendListModelList);

}
