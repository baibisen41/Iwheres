package com.bbs.iwhere.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by 大森 on 2017/5/24.
 */

public class NetworkUtil {

    private boolean isConnected = false;

    //判断是否有网络连接
    public Boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            isConnected = true;
            Log.e("networkInfo", "有网");
        }

        return isConnected;
    }

    //判断网络是否可用
    public static boolean isNetSupport(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        try {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            Log.e("NetworkUtil", "isNetSupport: " + e.toString());
        }
        return false;
    }

}
