package com.example.demo.map

import com.example.demo.po.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException


class UserMapper : RowMapper<User> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        val user = User()
        user.id = rs.getInt("id")
        user.username = rs.getString("username")
        user.email = rs.getString("email")
        user.phone = rs.getString("phone")
        user.receipt_code = rs.getString("receipt_code")
        user.role_id = rs.getInt("role_id")
        user.recommend_code = rs.getString("recommend_code")
        user.age = rs.getInt("age")
        user.address = rs.getString("address")
        user.status = rs.getInt("status")
        user.register_time = rs.getTimestamp("register_time")
        user.recommend_user_id = rs.getInt("recommend_user_id")
        user.recommend_time = rs.getTimestamp("recommend_time")
        return user
    }
}