package com.example.demo.map;

import com.example.demo.po.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {
        Task tsak = new Task();
        tsak.setId(rs.getInt("id"));
        tsak.setTitle(rs.getString("title"));
        tsak.setTask_describe(rs.getString("task_describe"));
        tsak.setPictures( rs.getString("pictures"));
        tsak.setCreate_uid(rs.getInt("create_uid"));
        tsak.setBrowsing_volume(rs.getInt("browsing_volume"));
        tsak.setCreate_time(rs.getTimestamp("create_time"));
        tsak.setEnd_time(rs.getTimestamp("end_time"));
        tsak.setStatus(rs.getInt("status"));
        return tsak;
    }
}
