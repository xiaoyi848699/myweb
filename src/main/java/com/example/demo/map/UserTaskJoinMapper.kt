package com.example.demo.map

import com.example.demo.po.UserTaskJoinInfo
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException


class UserTaskJoinMapper : RowMapper<UserTaskJoinInfo> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): UserTaskJoinInfo {
        val user = UserTaskJoinInfo()
        user.id = rs.getInt("id")
        user.screen_pic = rs.getString("screen_pic")
        user.taobao_order_id = rs.getString("taobao_order_id")
        user.create_time = rs.getTimestamp("create_time")
        user.user_commit_time = rs.getTimestamp("user_commit_time")
        user.business_deal_time = rs.getTimestamp("business_deal_time")
        user.task_id = rs.getInt("task_id")
        user.user_id = rs.getInt("user_id")
        user.status = rs.getInt("status")
        user.task_title = rs.getString("title")
        user.username = rs.getString("username")
        user.receipt_code = rs.getString("receipt_code")
        return user
    }
}