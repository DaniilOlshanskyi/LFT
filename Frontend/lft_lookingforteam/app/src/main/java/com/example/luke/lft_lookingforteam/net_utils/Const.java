package com.example.luke.lft_lookingforteam.net_utils;

public class Const {
    // profile URLs
    public static final String URL_POST_PROFILE = "http://proj309-ad-08.misc.iastate.edu:8080/post_profile";
    public static final String URL_GET_PROFILE_BY_USERNAME = "http://proj309-ad-08.misc.iastate.edu:8080/all/username/";

    // profile JSON object key names
    public static final String PROFILE_USERNAME_KEY = "profUsername";
    public static final String PROFILE_PASSWORD_KEY = "profPassword";
    public static final String PROFILE_PERIOD_KEY = "profPeriod";
    public static final String PROFILE_PHOTO_KEY = "profPhoto";
    public static final String PROFILE_REPORT_FLAG_KEY = "profReportFlag";
    public static final String PROFILE_MOD_FLAG_KEY = "profModFlag";
    public static final String PROFILE_REPUTATION_KEY = "profRep";
    public static final String PROFILE_LATEST_LOGIN_DATE_KEY = "profRecentlogin";
    public static final String PROFILE_SUSPENDED_KEY = "profSuspend";

    //report URLs
    public static final String URL_POST_REPORT = "http://proj309-ad-08.misc.iastate.edu:8080/post_report";

    //report JSON object key names
    public static final String REPORT_CHATLOG = "reportChatlog";
    public static final String REPORT_RESOLVE_FLAG = "reportResolveFlag";
    public static final String REPORT_RESOLVE_DATE = "reportResolveDate";
    public static final String REPORT_MESSAGE = "reportMessage";

    // EditText field character limits
    public static final int USERNAME_CHARACTER_LIMIT = 15;
    public static final int PASSWORD_CHARACTER_LIMIT = 30;
    public static final int REPORT_CHARACTER_LIMIT = 1200;
}
