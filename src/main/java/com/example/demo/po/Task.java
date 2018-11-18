package com.example.demo.po;

import java.sql.Timestamp;

public class Task {
    private int id;
    private String title;
    private String pictures;
    private String task_describe;
    private Timestamp create_time;
    private int create_uid;
    private Timestamp end_time;
    private int  browsing_volume;//浏览记录
    private int status;//1正常  2 已经关闭  3 删除

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pictures='" + pictures + '\'' +
                ", task_describe='" + task_describe + '\'' +
                ", create_time=" + create_time +
                ", create_uid=" + create_uid +
                ", end_time=" + end_time +
                ", browsing_volume=" + browsing_volume +
                ", status=" + status +
                '}';
    }
}
