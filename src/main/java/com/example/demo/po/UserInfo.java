package com.example.demo.po;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfo {
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

    static String getUserStatusStr(int status) {
        switch (status){
            case 1:
                return "已邀请未注册";
            case 2:
                return "正常";
            case 3:
                return "账户不安全";
            case 4:
                return "被禁用";
            case 5:
                return "账户被删除";
            default:
                return "异常";
        }
    }
}
