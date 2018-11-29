package com.example.demo.po;

public class ResponseStatus {
    public static final int request_login_expired = 100;
    public static final int request_fail = 101;
    public static final int request_param_error = 102;
    public static final int request_succes = 200;

    public static final String REDIS_HOS_TASK_LIST = "hotTaskList";
    public static final String REDIS_ALL_TASK_LIST = "allTaskList";
}
