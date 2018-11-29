package com.example.demo.map;

import com.example.demo.po.UserTaskJoinU;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTaskJoinUMapper implements RowMapper<UserTaskJoinU> {
    @Override
    public UserTaskJoinU mapRow(ResultSet rs, int i) throws SQLException {
        UserTaskJoinU user = new UserTaskJoinU();
        user.setId(rs.getInt("id"));
        user.setScreen_pic(rs.getString("screen_pic"));
        user.setTaobao_order_id(rs.getString("taobao_order_id"));
        user.setCreate_time(rs.getTimestamp("create_time"));
        user.setUser_commit_time(rs.getTimestamp("user_commit_time"));
        user.setBusiness_deal_time(rs.getTimestamp("business_deal_time"));
        user.setTask_id(rs.getInt("task_id"));
        user.setUser_id(rs.getInt("user_id"));
        user.setStatus( rs.getInt("status"));
        user.setTask_title(rs.getString("title"));
        user.setUsername(rs.getString("username"));
        user.setTask_pic(rs.getString("pictures"));
        user.setUserPhone(rs.getString("phone"));
        return user;
    }
}
