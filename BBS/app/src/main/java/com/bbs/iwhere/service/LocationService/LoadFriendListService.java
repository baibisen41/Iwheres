package com.bbs.iwhere.service.LocationService;

import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.common.BaseLocationService;
import com.bbs.iwhere.util.JsonHelper;
import com.bbs.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by IT8000 on 2017/5/16.
 */
/*
关于拉取好友信息思路：
* 在splashActivity加载中首先进行一波好友id+头像的拉取，存入数据库，将头像的命名成id+image的形式
* 在进入好友定位第二次拉取好友信息时只需要拉取好友id+name即可，到时候根据id进行图片的索引显示
* */

///////////用于点击好友定位时，拉取好友列表数据
public class LoadFriendListService extends BaseLocationService {

    private String url;//好友信息路径
    private LoadFriendListCallback loadFriendListCallback;

    public void setLoadFriendListCallback(LoadFriendListCallback loadFriendListCallback) {
        this.loadFriendListCallback = loadFriendListCallback;
    }

    public void loadFriendList() {
        reqGetJson(url, new MyStringCallback());
    }

    //响应事件
    public class MyStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {
            List<FriendListModel> friendlist = new JsonHelper().getJson(response, FriendListModel.class);//id+name+status
            loadFriendListCallback.showFriendList(friendlist);
        }


    }
}
