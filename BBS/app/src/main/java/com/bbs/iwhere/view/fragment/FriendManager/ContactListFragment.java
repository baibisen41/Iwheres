package com.bbs.iwhere.view.fragment.FriendManager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.bbs.iwhere.R;
import com.bbs.iwhere.db.DbFriendManager;
import com.bbs.iwhere.model.DbFriendModel;
import com.bbs.iwhere.view.activity.ChatActivity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.Hashtable;
import java.util.Map;


/**
 * Created by 大森 on 2017/3/29.
 */

public class ContactListFragment extends EaseContactListFragment implements View.OnClickListener {

    DbFriendModel dbFriendModel = new DbFriendModel();

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.contact_list_layout, null);
        listView.addHeaderView(headerView);
        registerForContextMenu(listView);
    }

    @Override
    protected void setUpView() {

        //直接从model中取出好友列表数据，如果为null暂时不处理
        Map<String, EaseUser> m = dbFriendModel.getContactList();
        if (m != null) {
            if (m instanceof Hashtable<?, ?>) {
                m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
            }
            setContactsMap(m);
            super.setUpView();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);
                if (easeUser != null) {
                    String username = easeUser.getUsername();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
/*        switch (v.getId()) {
            case R.id.bt1:
                DbFriendManager.getInstance().getContactList();
                break;
        }*/
    }
}
