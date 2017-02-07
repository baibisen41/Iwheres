package com.bbs.iwhere.presenter;

import android.content.Context;

/**
 * Created by ${白碧森} on ${2017/1/18}.
 */

public interface MyLocationPresenter {
    void startLocation(Context context);

    void stopLocation();

    void stopPoi();

}
