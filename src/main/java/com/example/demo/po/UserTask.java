package com.example.demo.po;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class UserTask {
    private int  id;
    private int user_id ;//接任人id
    private int task_id ;//任务id
    private Timestamp create_time;//任务接受时间
    private String taobao_order_id;//订单号
    private String screen_pic;//截图
    private int  status ;//状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
    private Timestamp user_commit_time;//任务完成时间
    private Timestamp business_deal_time;//商家处理时间

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

    @Override
    public String toString() {
        return "UserTask{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", task_id=" + task_id +
                ", create_time=" + create_time +
                ", taobao_order_id='" + taobao_order_id + '\'' +
                ", screen_pic='" + screen_pic + '\'' +
                ", status=" + status +
                ", user_commit_time=" + user_commit_time +
                ", business_deal_time=" + business_deal_time +
                '}';
    }

    @NotNull
    static String getUserTaskStatus(int status) {
        switch (status){
            case 1:
                return "已接任务";
            case 2:
                return "已经提交";
            case 3:
                return "商家已经处理";
            case 4:
                return "任务取消";
            default:
                return "任务异常";
        }
    }
}
