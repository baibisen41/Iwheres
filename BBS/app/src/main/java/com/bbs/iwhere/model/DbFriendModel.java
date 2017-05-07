package com.bbs.iwhere.model;

import com.bbs.iwhere.db.DbFriendManager;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Map;

/**
 * Created by 大森 on 2017/4/30.
 */

public class DbFriendModel {

    public Map<String, EaseUser> getContactList() {

        return DbFriendManager.getInstance().getContactList();
    }

}
