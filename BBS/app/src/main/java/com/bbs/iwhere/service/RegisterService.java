package com.bbs.iwhere.service;

import android.text.TextUtils;
import android.util.Log;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.model.RegisterModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 大森 on 2018/1/30.
 */

public class RegisterService extends BaseService {

    private String TAG = "RegisterService";
    private JsonUtil mJsonUtil = new JsonUtil();
    private RegisterModel registerModel = new RegisterModel();

    public void syncUserInformation(String username, String password) {

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            registerModel.setUsername(username);
            registerModel.setPassword(password);
            reqPostJson(AppConstants.registerUrl, mJsonUtil.postJson(registerModel), new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.e(TAG, "error");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e(TAG, "success:" + response);
                }
            });
        }
    }

}
