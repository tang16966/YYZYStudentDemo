package com.lenovo.studentClient.api;


import com.lenovo.studentClient.config.AppConfig;

public interface ApiService {

    /**
     * 查询“所有传感器”的当前值
     */
    String getAllSense = AppConfig.BASE_URL + "GetAllSense.do";



}
