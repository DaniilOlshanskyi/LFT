package com.example.luke.lft_lookingforteam;

/**
 * Helper class containing useful constant values for the application such as JSON object keys, URLs, etc.
 */
public class Const {
    // Logcat Tags
    public static final String LOGTAG_FILE_CREATE = "File creation";
    public static final String LOGTAG_FILE_READ = "File read";
    public static final String LOGTAG_FILE_WRITE = "File write";
    public static final String LOGTAG_JSONOBJECT_CREATION = "JSONObject creation";
    public static final String LOGTAG_JSONOBJECT_READ = "Reading JSON";
    public static final String LOGTAG_WEBSOCKET = "Websocket";

    // Websocket message elements
    public static final String WEBSOCKET_CHAT_TAG = "m:";
    public static final String WEBSOCKET_CHAT_CACHE_TAG = "g:";
    public static final String WEBSOCKET_SWIPING_TAG = "L:";
    public static final String WEBSOCKET_MATCHING_TAG = "F:";
    public static final String WEBSOCKET_EMPTY_TAG = "E:";
    public static final String WEBSOCKET_DATA_SEPARATOR = "&";

    // profile URLs
    public static final String URL_POST_PROFILE = "http://proj309-ad-08.misc.iastate.edu:8080/post_profile";
    public static final String URL_GET_PROFILE_BY_USERNAME = "http://proj309-ad-08.misc.iastate.edu:8080/all/username/";    // append username of desired profile at end of URL
    public static final String URL_GET_PROFILE_BY_ID = "http://proj309-ad-08.misc.iastate.edu:8080/all/id/";    // append user ID of desired profile at end of URL
    public static final String URL_PROFILE_PICTURES = "http://proj309-ad-08.misc.iastate.edu/home/bin/userpic/";  // append profile picture URL from JSON object to get direct path to picture
    public static final String URL_DEFAULT_PROFILE_PIC = "placeholder.JPG";

    //report URLs
    public static final String URL_GET_REPORT_BY_ID = "http://proj309-ad-08.misc.iastate.edu:8080/reports/";    // append user ID of desired profile at end of URL
    public static final String URL_POST_REPORT = "http://proj309-ad-08.misc.iastate.edu:8080/post_report/";     //add profId of violator

    //websocket URL
    public static final String URL_OPEN_WEBSOCKET = "ws://proj309-ad-08.misc.iastate.edu:8080/websocket/";   // append username of current user at end of URL

    // profile JSON object key names
    public static final String PROFILE_ID_KEY= "profId";
    public static final String PROFILE_USERNAME_KEY = "profUsername";
    public static final String PROFILE_PASSWORD_KEY = "profPassword";
    public static final String PROFILE_PERIOD_KEY = "profPeriod";
    public static final String PROFILE_PHOTO_KEY = "profPhoto";
    public static final String PROFILE_REPORT_FLAG_KEY = "profReportFlag";
    public static final String PROFILE_MOD_FLAG_KEY = "profModFlag";
    public static final String PROFILE_REPUTATION_KEY = "profRep";
    public static final String PROFILE_LATEST_LOGIN_DATE_KEY = "profRecentlogin";
    public static final String PROFILE_SUSPENDED_KEY = "profSuspend";

    //report JSON object key names
    public static final String REPORT_USER_ID = "profId";
    public static final String REPORT_CHATLOG = "reportChatlog";
    public static final String REPORT_RESOLVE_FLAG = "reportResolveFlag";
    public static final String REPORT_RESOLVE_DATE = "reportResolveDate";
    public static final String REPORT_MESSAGE = "reportMessage";

    //Intent object key names
    public static final String INTENT_CONVERSATION_USERNAME = "convoUsername";
    public static final String INTENT_PROFILE_VIEW_USERNAME = "profileUsername";

    //Registration Screen EditText field character limits
    public static final int USERNAME_CHARACTER_LIMIT = 15;
    public static final int PASSWORD_CHARACTER_LIMIT = 30;

    //Report Screen EditText field character limits
    public static final int REPORT_CHARACTER_LIMIT = 1200;

    //SharedPreferences key names
    public static final String SHAREDPREFS_USERNAME_KEY = "username";
    public static final String SHAREDPREFS_USERTYPE_KEY = "usertype";

    //usertypes
    public static final int USERTYPE_BASIC_USER = 0;
    public static final int USERTYPE_MODERATOR = 1;
    public static final int USERTYPE_ADMIN = 2;
}
