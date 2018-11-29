package com.example.demo.map;

import com.example.demo.po.UserTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTaskMapper implements RowMapper<UserTask> {
    @Override
    public UserTask mapRow(ResultSet rs, int i) throws SQLException {
        UserTask user = new UserTask();
        user.setId(rs.getInt("id"));
        user.setScreen_pic( rs.getString("screen_pic"));
        user.setTaobao_order_id(rs.getString("taobao_order_id"));
        user.setCreate_time(rs.getTimestamp("create_time"));
        user.setUser_commit_time(rs.getTimestamp("user_commit_time"));
        user.setBusiness_deal_time( rs.getTimestamp("business_deal_time"));
        user.setTask_id(rs.getInt("task_id"));
        user.setUser_id(rs.getInt("user_id"));
        user.setStatus(rs.getInt("status"));
        return user;
    }
}
