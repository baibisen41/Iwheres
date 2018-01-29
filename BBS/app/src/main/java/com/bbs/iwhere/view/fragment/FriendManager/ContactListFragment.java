package com.bbs.iwhere.view.fragment.FriendManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bbs.iwhere.R;
import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.db.DbFriendManager;
import com.bbs.iwhere.db.MainHelper;
import com.bbs.iwhere.service.ContactListService.ContactListService;
import com.bbs.iwhere.view.activity.ChatActivity;
import com.bbs.iwhere.view.activity.MainActivity;
import com.bbs.iwhere.view.activity.NewFriendActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * Created by 大森 on 2017/3/29.
 */

public class ContactListFragment extends EaseContactListFragment implements View.OnClickListener {

    ContactListService getContactList = new ContactListService();
    private RelativeLayout newFriendLayout;
    private Button testButton;
    private AlertDialog.Builder alertdlg;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastReceiver();
    }

    @Override
    public void refresh() {
        Map<String, EaseUser> m = getContactList.getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        Log.e("contactListFragment", "refresh");
        Log.e("contentMap", String.valueOf(m.size()));
        setContactsMap(m);
        super.refresh();
    }

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        alertdlg = new AlertDialog.Builder(getActivity());
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.contact_list_layout, null);
        testButton = (Button) headerView.findViewById(R.id.testId);
        testButton.setOnClickListener(this);
        newFriendLayout = (RelativeLayout) headerView.findViewById(R.id.new_friend_layout);
        newFriendLayout.setOnClickListener(this);
        listView.addHeaderView(headerView);
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toBeProcessUser = (EaseUser) listView.getItemAtPosition(position);
                toBeProcessUsername = toBeProcessUser.getUsername();
                alertdlg.setMessage("确定删除该好友?");
                alertdlg.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertdlg.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteContact(toBeProcessUser);
//                        getContactList.deleteUser(toBeProcessUsername);
//                        contactList.remove(toBeProcessUser);
//                        contactListLayout.refresh();
//                        Toast.makeText(getActivity(), "已删除", Toast.LENGTH_LONG).show();
                    }
                });
                alertdlg.show();
                return true;
            }
        });
    }


    @Override
    protected void setUpView() {

        //直接从model中取出好友列表数据，如果为null暂时不处理
        Map<String, EaseUser> m = getContactList.getContactList();
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
//        contactSyncListener = new ContactSyncListener();
//        MainHelper.getInstance().addSyncContactListener(contactSyncListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_friend_layout:
                startActivity(new Intent(getActivity(), NewFriendActivity.class));
                getActivity().overridePendingTransition(R.anim.in_to_left, 0);
                break;
            case R.id.testId:
                new DbFriendManager().testDB();
                break;
        }
    }

    public void deleteContact(final EaseUser tobeDeleteUser) {
        String st1 = getResources().getString(R.string.deleting);
        final String st2 = getResources().getString(R.string.Delete_failed);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
                    // remove user from memory and database
                    getContactList.deleteUser(toBeProcessUsername);
                    Log.e("ContactListFragment", "删除成功");
//                    UserDao dao = new UserDao(getActivity());
//                    dao.deleteContact(tobeDeleteUser.getUsername());
//                    MainHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            contactList.remove(tobeDeleteUser);
                            contactListLayout.refresh();
                            Toast.makeText(getActivity(), "已删除", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).start();

    }

    //此处接收来自MainHelper发来的接受好友广播，刷新好友列表
    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.ACTION_CONTACT_CHANAGED);

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("ContactListFragment", "receive broadcast:" + intent.getAction());
                refresh();
                Log.e("ContactListFragment", "contactFragment refresh");

            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }
}
