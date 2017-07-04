package com.bbs.iwhere.service.LocationService;

import com.bbs.iwhere.model.FriendLcationModel;

import java.util.List;

/**
 * Created by 大森 on 2017/3/19.
 */

public interface LocationCallback {


    void showFriendLocationData(List<FriendLcationModel> friendLcationModelList);

}
