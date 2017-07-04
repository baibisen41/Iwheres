package com.bbs.iwhere.service.LocationService;

import android.util.Log;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.model.FriendLcationModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by 大森 on 2017/3/19.
 */

public class LocationService extends BaseService {

    private LocationCallback locationCallback;
    private String strlocation;
    private int[] locationData;
    private boolean friendStatus = false;//好友状态默认为false，如果false状态，address和showmap等都传入null值
    private String url = AppConstants.LocationUrl; //发送url


    public void setLocationCallback(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    //真正开启发送请求
    public void postFriendLocation(String userId) {

        String strUrl = url + "?userid=" + userId;
        Log.e("LocationServiceUrl", strUrl);
        reqGetJson(strUrl, new GetUserLocationCallback());//只需要拉取经纬度数据包

    }

    public class GetUserLocationCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("LocationServiceError", e.getMessage().toString());
        }

        @Override
        public void onResponse(String response, int id) {
            showUserLocation(response);
            Log.e("LocationServiceSuccess", response);
        }
    }

    public void showUserLocation(String strjson) {

        List<FriendLcationModel> friendLcationList = null;
        try {
            friendLcationList = new JsonUtil().getJson(strjson, FriendLcationModel.class);
            locationCallback.showFriendLocationData(friendLcationList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从数据库中取出sdUrl+name+status再加上服务器的回包

        // 接口回掉
//        locationCallback.showFriendPic(friendLcationList.get(0).getUserSdUrl());
//        locationCallback.showFriendName(friendLcationList.get(0).getUserName());
//        locationCallback.showFriendLocationStatus(friendLcationList.get(0).getUserStatus());
//        locationCallback.showFriendLocation(strlocation);//待修改
//        locationCallback.showFriendLocationData(locationData);//待修改
    }


}
