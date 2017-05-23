package com.bbs.iwhere.service.SplashService;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.okhttp.callback.FileCallBack;
import com.bbs.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by 大森 on 2017/5/21.
 */

public class SplashService extends BaseService {

    private SplashCallback splashCallback;
    private JsonUtil jsonUtil = new JsonUtil();
    private String url = "https://i1.hoopchina.com.cn/blogfile/201705/17/BbsImg149502314118679_800x545.jpg?x-oss-process=image/resize,w_800/format,webp";
    private boolean isFinish = false;
    private boolean isSuccess = false;
    private String pictureName = null;
    private String pictureSdUrl = null;


    public void setSplashCallback(SplashCallback splashCallback) {
        this.splashCallback = splashCallback;
    }

    public Boolean pullFriendList() {
        reqGetJson(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                isFinish = finishFriendListJson(response);
            }
        });
        return isFinish;
    }

    public Boolean finishFriendListJson(String response) {
        List<FriendListModel> friendListModelList = jsonUtil.getJson(response, FriendListModel.class);
        for (FriendListModel friendListModel : friendListModelList) {

            //命名头像名称
            pictureName = String.valueOf(friendListModel.getUserId());

            //下载头像
            downloadFile(friendListModel.getPicUrl(), new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), pictureName + "_userPicture.jpg") {
                @Override
                public void onError(Call call, Exception e, int id) {

                    Log.e("downPictureErrorId", pictureName + "_userPicture.jpg");
                    Log.e("downPictureError", e.getStackTrace().toString());

                }

                @Override
                public void onResponse(File response, int id) {

                    pictureSdUrl = response.getAbsolutePath();
                    isSuccess = true;
                }
            });

            //进行存入数据库部分逻辑


        }
        return isSuccess;
    }


}

