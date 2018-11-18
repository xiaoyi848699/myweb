package com.example.demo.map

import com.example.demo.po.Task
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException


class TaskMapper : RowMapper<Task> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Task {
        val tsak = Task()
        tsak.id = rs.getInt("id")
        tsak.title = rs.getString("title")
        tsak.task_describe = rs.getString("task_describe")
        tsak.pictures = rs.getString("pictures")
        tsak.create_uid = rs.getInt("create_uid")
        tsak.browsing_volume = rs.getInt("browsing_volume")
        tsak.create_time = rs.getTimestamp("create_time")
        tsak.end_time = rs.getTimestamp("end_time")
        tsak.status = rs.getInt("status")
        return tsak
    }
}