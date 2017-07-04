package com.bbs.iwhere.service.LocationService;

import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by IT8000 on 2017/5/16.
 */
/*
关于拉取好友信息思路：
* 1.在splashActivity加载中首先进行一波userid+url+name+description的拉取，存入数据库，
*   再在将头像url进行下载到sd卡缓存，并将sd卡路径存入数据库中
* 2.在进入好友定位第二次拉取好友信息时只需要拉取好友id+status即可，并从数据库中取出userid+name+pic+description+status即可
* */

///////////用于点击好友定位时，拉取好友列表数据
public class LoadFriendListService extends BaseService {

    private String url;//好友信息路径
    private LoadFriendListCallback loadFriendListCallback;

    public void setLoadFriendListCallback(LoadFriendListCallback loadFriendListCallback) {
        this.loadFriendListCallback = loadFriendListCallback;
    }

/*    //直接发送请求 -> 返回 userId + status
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
            saveDbOption(response);
        }
    }*/

    //将id+status存入数据库
    public void getFriendlist() {
        List<FriendListModel> friendlist = null;//id+name+status
        friendlist = DbFriendListManager.getInstance().getFriendList();
        try {
//            friendlist = new JsonUtil().getJson(strjson, FriendListModel.class);
            loadFriendListCallback.showFriendList(friendlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
