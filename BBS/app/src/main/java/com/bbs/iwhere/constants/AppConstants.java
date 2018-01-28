package com.bbs.iwhere.constants;

/**
 * Created by 大森 on 2017/5/22.
 */

public interface AppConstants {

    public String BaseUrl = "http://192.168.1.103:8080/IwhereServer";

    public String SplashPullUrl = BaseUrl + "/LoadFriendListServlet";

    public String LocationOpenUrl = BaseUrl + "/UserLocationServlet";

    public String LocationUrl = BaseUrl + "/FriendLocationServlet";

    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String ACTION_CONVERSATION_CHANAGED = "action_conversation_changed";

}
