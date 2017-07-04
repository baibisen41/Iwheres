package com.bbs.iwhere.service.ContactListService;

import com.bbs.iwhere.db.DbFriendManager;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Map;

/**
 * Created by 大森 on 2017/5/20.
 */

public class ContactListService {

    //拉取好友列表
    public Map<String, EaseUser> getContactList() {

        return DbFriendManager.getInstance().getContactList();
    }

    public int saveUser(EaseUser easeUser) {
        return DbFriendManager.getInstance().saveUser(easeUser);
    }

    public void deleteUser(String username) {
        DbFriendManager.getInstance().deleteContact(username);
    }


}
