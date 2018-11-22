package com.example.demo.po;

import java.sql.Timestamp;

import static com.example.demo.po.UserTask.getUserTaskStatus;

public class UserTaskJoinM {
    private int  id;
    private int user_id ;//接任人id
    private int task_id ;//任务id
    private Timestamp create_time;//任务接受时间
    private String taobao_order_id;//淘宝订单号
    private String screen_pic;//截图
    private int  status ;//状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
    private Timestamp user_commit_time;//任务完成时间
    private Timestamp business_deal_time;//商家处理时间
    private String task_title;
    private String username;
    private String receipt_code;

    public String getReceipt_code() {
        return receipt_code;
    }

    public void setReceipt_code(String receipt_code) {
        this.receipt_code = receipt_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getTaobao_order_id() {
        return taobao_order_id;
    }

    public void setTaobao_order_id(String taobao_order_id) {
        this.taobao_order_id = taobao_order_id;
    }

    public String getScreen_pic() {
        return screen_pic;
    }

    public void setScreen_pic(String screen_pic) {
        this.screen_pic = screen_pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getUser_commit_time() {
        return user_commit_time;
    }

    public void setUser_commit_time(Timestamp user_commit_time) {
        this.user_commit_time = user_commit_time;
    }

    public Timestamp getBusiness_deal_time() {
        return business_deal_time;
    }

    public void setBusiness_deal_time(Timestamp business_deal_time) {
        this.business_deal_time = business_deal_time;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserTaskJoinM{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", task_id=" + task_id +
                ", create_time=" + create_time +
                ", taobao_order_id='" + taobao_order_id + '\'' +
                ", screen_pic='" + screen_pic + '\'' +
                ", status=" + status +
                ", user_commit_time=" + user_commit_time +
                ", business_deal_time=" + business_deal_time +
                ", task_title='" + task_title + '\'' +
                ", username='" + username + '\'' +
                ", receipt_code='" + receipt_code + '\'' +
                '}';
    }

    /**
     * //状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
     * @return
     */
    public String getStatusStr(){
        return getUserTaskStatus(status);
    }
}
