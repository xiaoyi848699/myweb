package com.example.demo.po;

import lombok.Data;

import java.sql.Timestamp;

import static com.example.demo.po.UserInfo.getUserStatusStr;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String receipt_code;
    private int role_id;
    private String recommend_code;
    private int age;
    private String address;
    private int status;
    private Timestamp register_time;
    private int recommend_user_id;
    private Timestamp recommend_time;

    public String getStatusStr(){
        //1已邀请未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
        return getUserStatusStr(status);
    }

}
