package com.bbs.iwhere.db;

import com.bbs.iwhere.view.MainApplication;

/**
 * Created by 大森 on 2017/4/30.
 */

public class DbFriendManager {

    DbFriendManager dbFriendManager = new DbFriendManager();
    DbFriendHelper dbFriendHelper = new DbFriendHelper(new MainApplication().getApplicationContext());

}
