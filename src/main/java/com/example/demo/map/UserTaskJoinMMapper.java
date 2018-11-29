package com.example.demo.map;

import com.example.demo.po.UserTaskJoinM;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTaskJoinMMapper implements RowMapper<UserTaskJoinM> {
    @Override
    public UserTaskJoinM mapRow(ResultSet rs, int i) throws SQLException {
        UserTaskJoinM userTask = new UserTaskJoinM();
        userTask.setId(rs.getInt("id"));
        userTask.setScreen_pic(rs.getString("screen_pic"));
        userTask.setTaobao_order_id(rs.getString("taobao_order_id"));
        userTask.setCreate_time(rs.getTimestamp("create_time"));
        userTask.setUser_commit_time(rs.getTimestamp("user_commit_time"));
        userTask.setBusiness_deal_time(rs.getTimestamp("business_deal_time"));
        userTask.setTask_id(rs.getInt("task_id"));
        userTask.setUser_id(rs.getInt("user_id"));
        userTask.setStatus(rs.getInt("status"));
        userTask.setTask_title(rs.getString("title"));
        userTask.setUsername(rs.getString("username"));
        userTask.setReceipt_code(rs.getString("receipt_code"));
        return userTask;
    }
}
