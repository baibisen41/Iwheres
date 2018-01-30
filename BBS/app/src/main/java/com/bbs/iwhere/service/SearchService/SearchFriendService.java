package com.bbs.iwhere.service.SearchService;

import android.util.Log;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.model.SearchModel;
import com.bbs.iwhere.service.common.BaseService;
import com.bbs.iwhere.util.JsonUtil;
import com.bbs.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 大森 on 2018/1/30.
 */

public class SearchFriendService extends BaseService {

    private String TAG = "SearchFriendService";
    private JsonUtil mJsonUtil = new JsonUtil();
    private SearchModel searchModel = new SearchModel();
    private SearchFriendCallback searchFriendCallback;

    public void setSearchFriendCallback(SearchFriendCallback searchFriendCallback) {
        this.searchFriendCallback = searchFriendCallback;
    }

    public void searchFriendHandler(String username) {
        Log.e(TAG, "search username:" + username);
        searchModel.setUserId(username);
        reqPostJson(AppConstants.searchUrl, mJsonUtil.postJson(searchModel), new SearchCallback());
    }

    public class SearchCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e(TAG, "error");
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            //后台只需要返回是否存在该用户即可
            //注意返回值为0：无该用户   1：存在
            Log.e(TAG, "response:" + response);
            getSearchResult(response);
        }
    }

    public void getSearchResult(String resp) {
        searchFriendCallback.showSearchResult(resp);
    }

}
