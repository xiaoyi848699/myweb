package com.example.demo.po;


import lombok.Data;

import java.sql.Timestamp;

@Data
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
