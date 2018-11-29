package com.example.demo.map;

import com.example.demo.po.TaskJoin;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskJoinMapper implements RowMapper<TaskJoin> {
    @Override
    public TaskJoin mapRow(ResultSet rs, int i) throws SQLException {
        TaskJoin tsak = new TaskJoin();
        tsak.setTask_id(rs.getInt("id"));
        tsak.setTitle(rs.getString("title"));
        tsak.setTask_describe(rs.getString("task_describe"));
        tsak.setPictures(rs.getString("pictures"));
        tsak.setCreate_uid(rs.getInt("create_uid"));
        tsak.setBrowsing_volume(rs.getInt("browsing_volume"));
        tsak.setCreate_time(rs.getTimestamp("create_time"));
        tsak.setEnd_time(rs.getTimestamp("end_time"));
        tsak.setStatus(rs.getInt("status"));
        tsak.setUsertask_id(rs.getInt("utaskid"));
        tsak.setUsertask_create_time(rs.getTimestamp("u_create_time"));
        tsak.setTaobao_order_id(rs.getString("taobao_order_id"));
        tsak.setScreen_pic(rs.getString("screen_pic"));
        tsak.setUsertask_status(rs.getInt("utask_status"));  //状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
        tsak.setUser_commit_time(rs.getTimestamp("user_commit_time"));
        tsak.setBusiness_deal_time(rs.getTimestamp("business_deal_time"));
        return tsak;
    }
}
