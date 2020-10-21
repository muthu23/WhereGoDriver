package com.wherego.delivery.driver.helpers;


public interface URLHelper {

    String BASE = "https://delivery.wherego.com.my/"; // Base_URl
    String IMAGE_BASE = BASE + "storage/app/public/";
//    int client_id = 2;
//    String client_secret = "Sh0w3TPOmqQlSZDaN5MbFEz4fWbxqFjyNgz88Cw2";
//    String STRIPE_TOKEN = "pk_test_LTXZTPA9yepu9dEodKsJm6GA";

    String RESET_PASSWORD = BASE + "api/provider/reset/password";
    String FORGET_PASSWORD = BASE + "api/provider/forgot/password";

    String HELP = BASE + "api/provider/help";

    String HELP_URL = "";

    String CHANGE_PASSWORD_API = BASE + "api/provider/profile/password";

    String ChatGetMessage = BASE + "api/provider/firebase/getChat?request_id=";

    int LOCATION_REQUEST = 1000;
    int GPS_REQUEST = 1001;
    String REQUEST_WITH = "XMLHttpRequest";
    String DRIVER_SERVICE_TYPE = "api/provider/services";
    String SIGN_UP_REQUEST = "api/provider/register";
    String LOGIN_REQUEST = "api/provider/oauth/token";
    String GET_PROFILE = "api/provider/profile";
    String LOGOUT_USER = "api/provider/logout";

    String DOC_LIST = "api/provider/document/types";
    String UPLOAD_DOC = "api/provider/document/upload";
    String DOC_SUBMIT = "api/provider/document/applied";

    String UPDATE_DRIVER_STATUS = "api/provider/profile/available";
    String CURRENT_TRIP = "api/provider/request/service";
    String CURRENT_TRIP_RATE = "api/provider/trip";
    String CURRENT_TRIP_UPDATE = "api/provider/update";
    String UPLOAD_SIGN = "api/provider/upload-signature";
    String CANCEL_TRIP = "api/provider/cancel";

    String HISTORY_LIST = "api/provider/requests/history";
    String HISTORY_ITEM_DETAIL = "api/provider/requests/history/details";


    String BANK_LIST = "api/provider/BankList";
    String ADD_BANK = "api/provider/addBank";
    String WITHDRAWS_REQUEST = "api/provider/withdrawalRequest";
    String WITHDRAWS_LIST = "api/provider/withdrawaList";

    String UPCOMING_ITEM_DETAIL = "api/provider/requests/upcoming/details";
    String UPCOMING_LIST = "api/provider/requests/upcoming";
    String RIDE_SUMMARY = "api/provider/summary";
    String EARNINGS = "api/provider/target";

    public static final String privacyPolicy = BASE + "/privacy-policy";
    public static final String termcondition = BASE + "/terms-conditions";
    public static final String refund = BASE + "/refund-policy";

}

