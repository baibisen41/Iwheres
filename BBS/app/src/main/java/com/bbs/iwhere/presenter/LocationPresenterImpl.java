package com.bbs.iwhere.presenter;

import com.bbs.iwhere.model.FriendLocation;
import com.bbs.iwhere.view.fragment.LocationView;

/**
 * Created by ${白碧森} on ${2017/1/18}.
 */

public class LocationPresenterImpl implements LocationPresenter {

    private LocationView locationView;
    private FriendLocation friendLocation;

    public LocationPresenterImpl(LocationView locationView) {
        this.locationView = locationView;
        friendLocation = new FriendLocation();
    }

    @Override
    public void onShowFriendLocation() {
        locationView.showFriendLocation(friendLocation.getFriendLocationtx());
    }

    @Override
    public void onChangeFriendStatus() {

    }
}
