package com.example.demo.map

import com.example.demo.po.Task
import com.example.demo.po.TaskJoin
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp


class TaskJoinMapper : RowMapper<TaskJoin> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): TaskJoin {
        val tsak = TaskJoin()
        tsak.task_id = rs.getInt("id")
        tsak.title = rs.getString("title")
        tsak.task_describe = rs.getString("task_describe")
        tsak.pictures = rs.getString("pictures")
        tsak.create_uid = rs.getInt("create_uid")
        tsak.browsing_volume = rs.getInt("browsing_volume")
        tsak.create_time = rs.getTimestamp("create_time")
        tsak.end_time = rs.getTimestamp("end_time")
        tsak.status = rs.getInt("status")
        tsak.usertask_create_time = rs.getTimestamp("u_create_time")
        tsak.taobao_order_id = rs.getString("taobao_order_id")
        tsak.screen_pic = rs.getString("screen_pic")
        tsak.usertask_status = rs.getInt("utask_status")//状态 1:已接任务 2 已经提交 3 商家已经处理 4任务取消 5任务已删除
        tsak.user_commit_time = rs.getTimestamp("user_commit_time")
        tsak.business_deal_time = rs.getTimestamp("business_deal_time")
        return tsak
    }
//select t.*,ut.id as utaskid,ut.create_time as u_create_time,ut.taobao_order_id,ut.screen_pic,ut.status as utask_status,ut.user_commit_time,ut.business_deal_time
//    private var usertask_id: Int = 0
//    private var usertask_create_time: Timestamp? = null//任务接受时间
//    private var taobao_order_id: String? = null//淘宝订单号
//    private var screen_pic: String? = null//截图
//    private var usertask_status: Int = 0
//    private var user_commit_time: Timestamp? = null//任务完成时间
//    private var business_deal_time: Timestamp? = null//商家处理时间
}