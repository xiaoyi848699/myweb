package com.example.demo.po;

import java.sql.Timestamp;

import static com.example.demo.po.UserInfo.getUserStatusStr;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceipt_code() {
        return receipt_code;
    }

    public void setReceipt_code(String receipt_code) {
        this.receipt_code = receipt_code;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRecommend_code() {
        return recommend_code;
    }

    public void setRecommend_code(String recommend_code) {
        this.recommend_code = recommend_code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Timestamp register_time) {
        this.register_time = register_time;
    }

    public int getRecommend_user_id() {
        return recommend_user_id;
    }

    public void setRecommend_user_id(int recommend_user_id) {
        this.recommend_user_id = recommend_user_id;
    }

    public Timestamp getRecommend_time() {
        return recommend_time;
    }

    public void setRecommend_time(Timestamp recommend_time) {
        this.recommend_time = recommend_time;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", receipt_code='" + receipt_code + '\'' +
                ", role_id=" + role_id +
                ", recommend_code='" + recommend_code + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", register_time=" + register_time +
                ", recommend_user_id=" + recommend_user_id +
                ", recommend_time=" + recommend_time +
                '}';
    }
    public String getStatusStr(){
        //1已推荐未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
        return getUserStatusStr(status);
    }

}
