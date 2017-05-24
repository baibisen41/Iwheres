package com.bbs.iwhere.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 大森 on 2017/4/30.
 */

public class DbFriendHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DB_NAME = "iwhere_friend.db";

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";

    public static final String FRIENDLIST_TABLE_NAME = "friendlist";
    public static final String FRIENDLIST_COLUMN_FRIEND_ID = "friendid";
    public static final String FRIENDLIST_COLUMN_FRIEND_PIC_URL = "friendpicurl";
    public static final String FRIENDLIST_COLUMN_FRIEND_PIC_SD_URL = "friendpicsd";
    public static final String FRIENDLIST_COLUMN_FRIEND_NAME = "friendname";
    public static final String FRIENDLIST_COLUMN_FRIEND_DESCRIPTION = "friendescription";
    public static final String FRIENDLIST_COLUMN_FRIEND_STATUS = "friendstatus";

    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COLUMN_NAME_NICK + " TEXT, "
            + COLUMN_NAME_AVATAR + " TEXT, "
            + COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String FRIENDLIST_TABLE_CREATE = "CREATE TABLE "
            + FRIENDLIST_TABLE_NAME + " ("
            + FRIENDLIST_COLUMN_FRIEND_ID + " TEXT, "
            + FRIENDLIST_COLUMN_FRIEND_PIC_URL + " TEXT, "
            + FRIENDLIST_COLUMN_FRIEND_PIC_SD_URL + " TEXT, "
            + FRIENDLIST_COLUMN_FRIEND_NAME + " TEXT, "
            + FRIENDLIST_COLUMN_FRIEND_DESCRIPTION + " TEXT, "
            + FRIENDLIST_COLUMN_FRIEND_STATUS + " TEXT PRIMARY KEY);";


    public DbFriendHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERNAME_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 6) {

        }
    }
}
