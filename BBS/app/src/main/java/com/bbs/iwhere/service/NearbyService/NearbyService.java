package com.bbs.iwhere.service.NearbyService;

import com.bbs.iwhere.db.DbFriendListManager;
import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.service.common.BaseService;

import java.util.List;

/**
 * Created by 大森 on 2017/6/6.
 */

public class NearbyService extends BaseService {

    NearbyCallback nearbyCallback;

    public NearbyService(NearbyCallback nearbyCallback) {
        this.nearbyCallback = nearbyCallback;
    }


}
