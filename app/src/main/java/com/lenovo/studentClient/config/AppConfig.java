
package com.lenovo.studentClient.config;


public class AppConfig {
    private static String IP = "192.168.1.106";
    private static final int PORT = 8088;
    public static final String BASE_URL = "http://" + IP + ":" + PORT
            + "/transportservice/action/";

    public static final String SET_CAR_ACTION = "SetCarMove.do";
    public static final String KEY_CAR_ID = "CarId";
    public static final String KEY_CAR_ACTION = "CarAction";
}
