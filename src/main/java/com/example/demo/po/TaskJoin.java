package com.example.demo.po;

import java.sql.Timestamp;

import static com.example.demo.po.Task.getTaskStatus;
import static com.example.demo.po.UserTask.getUserTaskStatus;

public class TaskJoin {
    private int task_id;
    private String title;
    private String pictures;
    private String task_describe;
    private Timestamp create_time;
    private int create_uid;
    private Timestamp end_time;
    private int  browsing_volume;//浏览记录
    private int status;//1正常  2 已经关闭  3 删除

    private int usertask_id;
    private Timestamp usertask_create_time;//任务接受时间
    private String taobao_order_id;//淘宝订单号
    private String screen_pic;//截图
    private int  usertask_status ;//状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
    private Timestamp user_commit_time;//任务完成时间
    private Timestamp business_deal_time;//商家处理时间

    public int getUsertask_id() {
        return usertask_id;
    }

    public void setUsertask_id(int usertask_id) {
        this.usertask_id = usertask_id;
    }

    public String getTask_describe() {
        return task_describe;
    }

    public void setTask_describe(String task_describe) {
        this.task_describe = task_describe;
    }

    public int getCreate_uid() {
        return create_uid;
    }

    public void setCreate_uid(int create_uid) {
        this.create_uid = create_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public int getBrowsing_volume() {
        return browsing_volume;
    }

    public void setBrowsing_volume(int browsing_volume) {
        this.browsing_volume = browsing_volume;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public Timestamp getUsertask_create_time() {
        return usertask_create_time;
    }

    public void setUsertask_create_time(Timestamp usertask_create_time) {
        this.usertask_create_time = usertask_create_time;
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

    public int getUsertask_status() {
        return usertask_status;
    }

    public void setUsertask_status(int usertask_status) {
        this.usertask_status = usertask_status;
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
        return "TaskJoin{" +
                "task_id=" + task_id +
                ", title='" + title + '\'' +
                ", pictures='" + pictures + '\'' +
                ", task_describe='" + task_describe + '\'' +
                ", create_time=" + create_time +
                ", create_uid=" + create_uid +
                ", end_time=" + end_time +
                ", browsing_volume=" + browsing_volume +
                ", status=" + status +
                ", usertask_id=" + usertask_id +
                ", usertask_create_time=" + usertask_create_time +
                ", taobao_order_id='" + taobao_order_id + '\'' +
                ", screen_pic='" + screen_pic + '\'' +
                ", usertask_status=" + usertask_status +
                ", user_commit_time=" + user_commit_time +
                ", business_deal_time=" + business_deal_time +
                '}';
    }

    public String getStatusStr(){
        return getTaskStatus(status);
    }
    public String getUserTaskStatusStr(){
        return getUserTaskStatus(usertask_status);
    }
}
