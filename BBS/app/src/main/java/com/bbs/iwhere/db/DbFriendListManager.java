package com.bbs.iwhere.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bbs.iwhere.model.FriendListModel;
import com.bbs.iwhere.view.MainApplication;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

    synchronized public void testDB() {

        dbFriendHelper.getWritableDatabase();
        Log.e("baibisen", "创建成功");

    }

    public synchronized Integer updateMessage(FriendListModel friendList) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_PIC_SD_URL, friendList.getPicsdurl());
            Log.e("friendlistPicSD", friendList.getPicsdurl());
            db.update(dbFriendHelper.FRIENDLIST_TABLE_NAME, values, "friendid=?", new String[]{friendList.getUserid()});
            id = 0;
        }
        return id;
    }

    //保存拉取好友列表信息
    public synchronized Integer saveMessage(FriendListModel friendList) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_ID, friendList.getUserid());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_PIC_URL, friendList.getPicurl());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_PIC_SD_URL, friendList.getPicsdurl());
            Log.e("friendlistPicSD", friendList.getPicsdurl());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_NAME, friendList.getUsername());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_DESCRIPTION, friendList.getUserdescription());
            values.put(dbFriendHelper.FRIENDLIST_COLUMN_FRIEND_STATUS, friendList.getUserstatus());
            db.insert(dbFriendHelper.FRIENDLIST_TABLE_NAME, null, values);
            id = 0;
        }
        return id;
    }

    synchronized public List<FriendListModel> getFriendList() {
        SQLiteDatabase db = dbFriendHelper.getReadableDatabase();
        FriendListModel friendListModel;
        List<FriendListModel> friendListModelList = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from friendlist", null);
            while (cursor.moveToNext()) {
                friendListModel = new FriendListModel();
                String friendId = cursor.getString(cursor.getColumnIndex("friendid"));
                String friendPic = cursor.getString(cursor.getColumnIndex("friendpicsd"));
                Log.e("friendlistPicSD", friendPic);
                String friendName = cursor.getString(cursor.getColumnIndex("friendname"));
                String friendDescription = cursor.getString(cursor.getColumnIndex("friendescription"));
                String friendStatus = cursor.getString(cursor.getColumnIndex("friendstatus"));
                friendListModel.setUserid(friendId);
                friendListModel.setUsername(friendName);
                friendListModel.setPicsdurl(friendPic);
                friendListModel.setUserdescription(friendDescription);
                friendListModel.setUserstatus(friendStatus);
                friendListModelList.add(friendListModel);
            }
            cursor.close();
        }
        return friendListModelList;
    }
}
