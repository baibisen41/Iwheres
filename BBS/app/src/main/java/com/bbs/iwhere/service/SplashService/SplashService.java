package com.bbs.iwhere.service.SplashService;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.iwhere.view.MainApplication;
import com.bbs.okhttp.callback.FileCallBack;
import com.bbs.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 大森 on 2017/5/21.
 */

public class SplashService extends BaseService {

    private SplashCallback splashCallback;
    private JsonUtil jsonUtil = new JsonUtil();
    private String url = AppConstants.SplashPullUrl;
    private boolean isFinish = false;
    private boolean isSuccess = false;
    private String pictureName = null;
    private String pictureSdUrl = null;


    public void setSplashCallback(SplashCallback splashCallback) {
        this.splashCallback = splashCallback;
    }

    /***
     * 从服务端拉取好友列表
     * @return
     */
    public Boolean pullFriendList() {
        reqGetJson(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("splashjson", response);
                isFinish = finishFriendListJson(response);
            }
        });
        return isFinish;
    }

    /**
     * 解析好友列表写入数据库，并通过头像url下载图片
     * @param response
     * @return
     */
    public Boolean finishFriendListJson(String response) {
        List<FriendListModel> friendListModelList = new ArrayList<FriendListModel>();
        try {
            //解析数据
            friendListModelList = jsonUtil.getJson(response, FriendListModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("test", friendListModelList.get(0).getUsername());
        for (final FriendListModel friendListModel : friendListModelList) {

            //命名头像名称
            pictureName = friendListModel.getUserid();
            Log.e("name", pictureName);
            Log.e("url", friendListModel.getPicurl());
            //下载头像
            downloadFile(friendListModel.getPicurl(), new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), pictureName + "_userPicture.jpg") {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.e("downPictureErrorId", pictureName + "_userPicture.jpg");
                    Log.e("downPictureError", e.getStackTrace().toString());
                }

                @Override
                public void onResponse(File response, int id) {

                    pictureSdUrl = response.getAbsolutePath();
                    Log.e("pictureSdUrl", pictureSdUrl);
                    friendListModel.setPicsdurl(pictureSdUrl);
                    int i = DbFriendListManager.getInstance().saveMessage(friendListModel);
                    if (i == 0) {
                        Log.e("SplashServiceDB", "数据存入成功");
                    } else {
                        Log.e("SplashServiceDBError", "数据存入失败");
                    }
                    int k = DbFriendListManager.getInstance().updateMessage(friendListModel);
                    if(k == 0){
                        Log.e("updataSplashServiceDB", "数据更新成功");
                    }else{
                        Log.e("updateSplasherror", "数据更新失败");
                    }
                    isSuccess = true;
                }
            });

            //进行存入数据库部分逻辑
//            friendListModel.setPicsdurl(pictureSdUrl);
//            DbFriendListManager.getInstance().testDB();

        }
        return isSuccess;
    }


}

