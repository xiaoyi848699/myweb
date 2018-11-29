package com.example.demo.map;

import com.example.demo.po.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setReceipt_code(rs.getString("receipt_code"));
        user.setRole_id(rs.getInt("role_id"));
        user.setRecommend_code(rs.getString("recommend_code"));
        user.setAge(rs.getInt("age"));
        user.setAddress(rs.getString("address"));
        user.setStatus(rs.getInt("status"));
        user.setRegister_time( rs.getTimestamp("register_time"));
        user.setRecommend_user_id(rs.getInt("recommend_user_id"));
        user.setRecommend_time(rs.getTimestamp("recommend_time"));
        return user;
    }
}
