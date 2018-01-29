package com.bbs.iwhere.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.service.ContactListService.ContactListService;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 大森 on 2018/1/24.
 */

public class MainHelper {

    private static final String TAG = MainHelper.class.getSimpleName();
    private static MainHelper instance = null;
    private String username;
    Queue<String> msgQueue = new ConcurrentLinkedQueue<>();
    private ExecutorService executor;
    private Context contexts;
    protected android.os.Handler handler;
    ContactListService dbFriendModel = new ContactListService();
    private EaseUser easeUser;
    private LocalBroadcastManager broadcastManager;
    private EaseUI easeUI;
    private Map<String, EaseUser> contactList;
    EMConnectionListener connectionListener;
    private boolean isContactsSyncedWithServer = false;
    private DbFriendManager dbFriendManager;


    private MainHelper() {
        executor = Executors.newCachedThreadPool();
    }

    public synchronized static MainHelper getInstance() {
        if (instance == null) {
            instance = new MainHelper();
        }
        return instance;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public void init(Context context) {
        dbFriendManager = DbFriendManager.getInstance();
        contexts = context;
        EMOptions options = initChatOptions();
        if (EaseUI.getInstance().init(contexts, options)) {
            Log.e(TAG, "init");
            easeUI = EaseUI.getInstance();
            PreferenceManager.init(context);
            broadcastManager = LocalBroadcastManager.getInstance(context);
            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        }
    }

    public void setGlobalListeners() {

        //判断是否同步过联系人，如果同步过则不重复进行
        isContactsSyncedWithServer = dbFriendManager.isContactSynced();
        Log.e(TAG, "是否同步过联系人：" + isContactsSyncedWithServer);

        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
                if (isContactsSyncedWithServer) {
                    EMLog.d(TAG, "group and contact already synced with servre");
                } else {

                    if (!isContactsSyncedWithServer) {
                        asyncFetchContactsFromServer(null);
                    }
                }
            }
        };

        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        //       Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
//                Log.d(TAG, "logout: onSuccess");
                Log.e("logout", "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.e("logout", "logout: onError");
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = new ContactListService().getContactList();
        }

        // return a empty non-null object to avoid app crash
        if (contactList == null) {
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    public void asyncFetchContactsFromServer(final EMValueCallBack<List<String>> callback) {
        Log.e(TAG, "fetchContacts");

        new Thread() {
            @Override
            public void run() {
                List<String> usernames = null;
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.e(TAG, "MainHelper name size:" + String.valueOf(usernames.size()));
                    // in case that logout already before server returns, we should return immediately
                    Log.e(TAG, String.valueOf(isLoggedIn()));
                    if (!isLoggedIn()) {
                        Log.e(TAG, "not login");
                        isContactsSyncedWithServer = false;
                        return;
                    }

                    Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
                    for (String username : usernames) {
                        EaseUser user = new EaseUser(username);
                        EaseCommonUtils.setUserInitialLetter(user);
                        userlist.put(username, user);
                    }
                    // save the contact list to cache
                    getContactList().clear();
                    getContactList().putAll(userlist);
                    // save the contact list to database
                    List<EaseUser> users = new ArrayList<EaseUser>(userlist.values());
                    dbFriendManager.saveContactList(users);
                    dbFriendManager.setContactSynced(true);
                    isContactsSyncedWithServer = true;

                    if (callback != null) {
                        callback.onSuccess(usernames);
                    }
                } catch (HyphenateException e) {
                    dbFriendManager.setContactSynced(false);
                    isContactsSyncedWithServer = false;
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    private EMOptions initChatOptions() {
        Log.d("MainHelper", "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        //set custom servers, commonly used in private deployment
//        if(demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
//            options.setRestServer(demoModel.getRestServer());
//            options.setIMServer(demoModel.getIMServer());
//            if(demoModel.getIMServer().contains(":")) {
//                options.setIMServer(demoModel.getIMServer().split(":")[0]);
//                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
//            }
//        }
//
//        if (demoModel.isCustomAppkeyEnabled() && demoModel.getCutomAppkey() != null && !demoModel.getCutomAppkey().isEmpty()) {
//            options.setAppKey(demoModel.getCutomAppkey());
//        }
//
//        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
//        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
//        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());

        return options;
    }

    synchronized void reset() {
//        isSyncingGroupsWithServer = false;
//        isSyncingContactsWithServer = false;
//        isSyncingBlackListWithServer = false;
//
//        demoModel.setGroupsSynced(false);
        dbFriendManager.setContactSynced(false);
//        demoModel.setBlacklistSynced(false);
//
//        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
//        isBlackListSyncedWithServer = false;
//
//        isGroupAndContactListenerRegisted = false;
//
//        setContactList(null);
//        setRobotList(null);
//        getUserProfileManager().reset();
//        DemoDBManager.getInstance().closeDB();
    }

    public void setCurrentUserName(String username) {
        this.username = username;
        dbFriendManager.setCurrentUserName(username);
    }

    /**
     * get current user's id
     */
    public String getCurrentUsernName() {
        if (username == null) {
            username = dbFriendManager.getCurrentUsernName();
        }
        return username;
    }

    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    void showToast(final String message) {
        Log.d("MainHelper", "receive invitation to join the group：" + message);
        if (handler != null) {
            Message msg = Message.obtain(handler, 0, message);
            handler.sendMessage(msg);
        } else {
            msgQueue.add(message);
        }
    }

    public void initHandler(Looper looper) {
        handler = new android.os.Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                String str = (String) msg.obj;
                Toast.makeText(contexts, str, Toast.LENGTH_LONG).show();
            }
        };
        while (!msgQueue.isEmpty()) {
            showToast(msgQueue.remove());
        }
    }

    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {

            easeUser = new EaseUser(username);
            easeUser.setNickname(username);
            dbFriendModel.saveUser(easeUser);
            Log.e("MainHelper", "add name:" + username);
            broadcastManager.sendBroadcast(new Intent(AppConstants.ACTION_CONTACT_CHANAGED));
            showToast("成功添加好友:" + username);
        }

        @Override
        public void onContactDeleted(String username) {
            Map<String, EaseUser> localUsers = new ContactListService().getContactList();
            localUsers.remove(username);
            dbFriendManager.deleteContact(username);

            EMClient.getInstance().chatManager().deleteConversation(username, false);

            broadcastManager.sendBroadcast(new Intent(AppConstants.ACTION_CONTACT_CHANAGED));
            broadcastManager.sendBroadcast(new Intent(AppConstants.ACTION_CONVERSATION_CHANAGED));
            showToast("已删除好友:" + username);
        }

        @Override
        public void onContactInvited(String username, String reason) {

            Log.e("MainHelper", "invited name:" + username + "reason:" + reason);
            showToast(username + "apply to be your friend,reason: " + reason);
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            showToast("MainHelper accept:" + username);
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            showToast("MainHelper refused:" + username);
        }
    }


}
