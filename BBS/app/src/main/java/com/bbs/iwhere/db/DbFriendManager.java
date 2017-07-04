package com.bbs.iwhere.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bbs.iwhere.view.MainApplication;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by 大森 on 2017/4/30.
 */

public class DbFriendManager {

    static private DbFriendManager dbFriendManager;
    DbFriendHelper dbFriendHelper = new DbFriendHelper(MainApplication.getContext());

    //设为静态方便处理
    public static synchronized DbFriendManager getInstance() {
        if (dbFriendManager == null) {
            dbFriendManager = new DbFriendManager();
        }
        return dbFriendManager;
    }

    synchronized public void testDB() {

        dbFriendHelper.getWritableDatabase();
        Log.e("baibisen", "创建成功");

    }

    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(dbFriendHelper.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(dbFriendHelper.COLUMN_NAME_ID, user.getUsername());
                if (user.getNick() != null)
                    values.put(dbFriendHelper.COLUMN_NAME_NICK, user.getNick());
                if (user.getAvatar() != null)
                    values.put(dbFriendHelper.COLUMN_NAME_AVATAR, user.getAvatar());
                db.replace(dbFriendHelper.TABLE_NAME, null, values);
            }
        }
    }

    synchronized public void deleteContact(String username) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(dbFriendHelper.TABLE_NAME, dbFriendHelper.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }

    public synchronized Integer saveUser(EaseUser user) {
        SQLiteDatabase db = dbFriendHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(dbFriendHelper.COLUMN_NAME_ID, user.getUsername());
            values.put(dbFriendHelper.COLUMN_NAME_NICK, user.getNickname());
            values.put(dbFriendHelper.COLUMN_NAME_AVATAR, user.getAvatar());
            db.insert(dbFriendHelper.TABLE_NAME, null, values);
            id = 0;
        }
        return id;
    }

    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbFriendHelper.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from users", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String nick = cursor.getString(cursor.getColumnIndex("nick"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                EaseUser user = new EaseUser(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                if (username.equals("item_new_friends")) {
                    user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }

}
