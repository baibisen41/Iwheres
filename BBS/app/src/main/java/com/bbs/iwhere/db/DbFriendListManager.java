package com.bbs.iwhere.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.view.MainApplication;

/**
 * Created by 大森 on 2017/5/24.
 */

public class DbFriendListManager {

    DbFriendHelper dbFriendHelper = new DbFriendHelper(MainApplication.getContext());
    static DbFriendListManager dbFriendListManager;

    //设为静态方便处理
    public static synchronized DbFriendListManager getInstance() {
        if (dbFriendListManager == null) {
            dbFriendListManager = new DbFriendListManager();
        }
        return dbFriendListManager;
    }

    //保存拉取好友列表信息
    public synchronized Integer saveMessage(FriendListModel friendList) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_ID, friendList.getUserId());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_PIC_URL, friendList.getPicUrl());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_PIC_SD_URL, friendList.getPicSdUrl());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_NAME, friendList.getUserName());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_DESCRIPTION, friendList.getUserDescription());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_STATUS, friendList.getUserStatus());
            db.insert(dbFriendHelper.FRIENDLIST_TABLE_NAME, null, values);
            id = 0;
        }
        return id;
    }
}
