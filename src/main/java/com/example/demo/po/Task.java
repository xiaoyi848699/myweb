package com.example.demo.po;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Task implements Serializable {
    private int id;
    private String title;
    private String pictures;
    private String task_describe;
    private Timestamp create_time;
    private int create_uid;
    private Timestamp end_time;
    private int  browsing_volume;//浏览记录
    private int status;//1正常  2 已经关闭  3 删除

    public String getStatusStr(){
       return getTaskStatus(status);
    }
    public  static String getTaskStatus(int status){
        switch (status){
            case 1:
                return "进行中";
            case 2:
                return "已关闭";
            case 3:
                return "已删除";
        }
        return  "";
    }
}
