package com.bbs.iwhere.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.bbs.iwhere.constants.AppConstants;
import com.bbs.iwhere.service.ContactListService.ContactListService;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import java.util.HashMap;
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
        contexts = context;
        EMOptions options = initChatOptions();
        if (EaseUI.getInstance().init(contexts, options)) {
            easeUI = EaseUI.getInstance();
            PreferenceManager.init(context);
            broadcastManager = LocalBroadcastManager.getInstance(context);
            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        }
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
/*        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        demoModel.setGroupsSynced(false);
        demoModel.setContactSynced(false);
        demoModel.setBlacklistSynced(false);

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        isGroupAndContactListenerRegisted = false;

        setContactList(null);
        setRobotList(null);
        getUserProfileManager().reset();
        DemoDBManager.getInstance().closeDB();*/
    }

    public void setCurrentUserName(String username) {
        this.username = username;
        DbFriendManager.getInstance().setCurrentUserName(username);
    }

    /**
     * get current user's id
     */
    public String getCurrentUsernName() {
        if (username == null) {
            username = DbFriendManager.getInstance().getCurrentUsernName();
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
            new DbFriendManager().deleteContact(username);

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
