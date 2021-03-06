package com.example.demo.service.impl;

import com.example.demo.map.UserTaskJoinMMapper;
import com.example.demo.map.UserTaskJoinUMapper;
import com.example.demo.map.UserTaskMapper;
import com.example.demo.po.UserTask;
import com.example.demo.po.UserTaskJoinM;
import com.example.demo.po.UserTaskJoinU;
import com.example.demo.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Object getTaskUserTaskList(String taskId) {
        try {
            String sql="select * from user_task where task_id = ?";
            List<UserTask> userList= jdbcTemplate.query(sql, new Object[]{taskId},new UserTaskMapper());
//            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object getUserSendAllTaskAllUserTaskList(String uid,int status) {
        try {
            //1:已接任务 2 已经提交 3 商家已经处理 4任务异常 6任务已删除
            String sql;
            int userTaskStatus = 2;
            if(status == 0){//查询1、2
                sql="select  user_task.*,task.title,user.username,user.receipt_code from task INNER JOIN user_task on task.id = user_task.task_id  INNER JOIN user on user.id = user_task.user_id   where create_uid = ? and user_task.status BETWEEN 1 and 3  order by user_task.create_time desc ";
                List<UserTaskJoinM> userList= jdbcTemplate.query(sql, new Object[]{uid},new UserTaskJoinMMapper());
//                System.out.println("count"+userList);
                return  userList;
            }else if(status == 1){
                userTaskStatus = 1;
                sql="select  user_task.*,task.title,user.username,user.receipt_code from task INNER JOIN user_task on task.id = user_task.task_id  INNER JOIN user on user.id = user_task.user_id   where create_uid = ? and user_task.status = ? order by user_task.create_time desc ";
            }else if(status == 3){
                userTaskStatus = 3;
                sql="select  user_task.*,task.title,user.username,user.receipt_code from task INNER JOIN user_task on task.id = user_task.task_id  INNER JOIN user on user.id = user_task.user_id   where create_uid = ? and user_task.status = ? order by user_task.user_commit_time desc ";
            }else{
                sql="select  user_task.*,task.title,user.username,user.receipt_code from task INNER JOIN user_task on task.id = user_task.task_id  INNER JOIN user on user.id = user_task.user_id   where create_uid = ? and user_task.status = ? order by user_task.user_commit_time desc ";
            }
            List<UserTaskJoinM> userList= jdbcTemplate.query(sql, new Object[]{uid,userTaskStatus},new UserTaskJoinMMapper());
//            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object getUserAllUserTaskList(String uid) {
        try {
            //1:已接任务 2 已经提交 3 商家已经处理 4任务取消 6任务已删除
            String sql="select  user_task.*,task.title,task.pictures,user.username,user.phone from task " +
                    "INNER JOIN user_task on task.id = user_task.task_id  " +
                    "INNER JOIN user on user.id = task.create_uid  " +
                    "where user_task.user_id = ? and user_task.status BETWEEN 1 and 3  order by user_task.create_time desc";
            List<UserTaskJoinU> userList= jdbcTemplate.query(sql, new Object[]{uid},new UserTaskJoinUMapper());
//            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object getMyUserTaskList(String userId) {
        try {
            String sql="select * from user_task where user_id = ?";
            List<UserTask> userList= jdbcTemplate.query(sql, new Object[]{userId},new UserTaskMapper());
//            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public int getAllUserTaskList() {
        try {
            String sql="select count(*) from user_task";
           int count= jdbcTemplate.queryForObject(sql, Integer.class);
//            System.out.println("count"+count);
            return  count;
        }catch (Exception e){
            return 999;
        }
    }

    @Override
    public String addUserTask(UserTask userTask) {
        try {
            //如果还有未完成的任务。不能这里接任务
//            select user_task.* from
//                    (select id from task where create_uid = (SELECT create_uid from task WHERE id = 10037228)and task.status = 1) as alltask  -- 查询出这个商家的所有未关闭订单
//            INNER JOIN  user_task on alltask.id = user_task.task_id  -- 查询出这个商家的所有未关闭订单中的已接收任务
//            where  user_task.user_id =61159 and user_task.status BETWEEN 1 and 2;//我已接收订单中还未完成的任务
            String sql="select count(*) from " +
                    "(select id from task where create_uid = (SELECT create_uid from task WHERE id = ?)and task.status = 1) as alltask " +
                    "INNER JOIN  user_task on alltask.id = user_task.task_id " +
                    "  where  user_task.user_id =? and user_task.status BETWEEN 1 and 2";
            int size= jdbcTemplate.queryForObject(sql, new Object[]{userTask.getTask_id(),userTask.getUser_id()},Integer.class);
            if(size > 0){
//                System.out.println("不能再接受他的任务getTask_id"+userTask.getTask_id()+",getUser_id"+userTask.getUser_id());
                return "您已接收过此发布者的任务，任务在完成前不能再接受他的任务";
            }

            //生成Id，并检查
            int id = 0;
            int maxCheck = 0;
            boolean produceId = false;
            while (maxCheck < 5){
                Random random = new Random();
                id = 1000000+random.nextInt(100000);
                //检查Id
                String sql2="select count(*) from user_task where id ="+id;
                int count= jdbcTemplate.queryForObject(sql2, Integer.class);
                if(count == 0){
                    maxCheck = 6;
                    produceId = true;
                }
                maxCheck++;
            }
            if(!produceId || id < 1000000){
                return "添加失败，请重试";
            }
            String sql4="insert into user_task (id,user_id,task_id,status,create_time) values (?,?,?,?,?)";
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            int count4= jdbcTemplate.update(sql4, new Object[]{id,userTask.getUser_id(),userTask.getTask_id(),1,timeStamp});
//            int count4= jdbcTemplate.update(sql4, new Object[]{id,username,4,1,recommendCode,addUserId,Utils.getTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss.SSS")});
//            System.out.println("insert"+count4);
            //返回邀请码
            if(count4 == 1){
                return  "接受成功，请尽快完成任务，并提交数据";
            }else{
                return "接受失败";
            }
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object getUserTask(String userTaskId) {
        try {
            String sql="select * from user_task where id = ?";
            List<UserTask> userList= jdbcTemplate.query(sql, new Object[]{userTaskId},new UserTaskMapper());
//            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String updateUserTaskStatus(String userTaskId, String status) {
        try {
            int count = 0;
            if("3".equals(status)){
                String sql="update user_task set status = ?,business_deal_time = ? where id = ? and status BETWEEN 1 and 2";
                Date date = new Date();
                Timestamp timeStamp = new Timestamp(date.getTime());
                count= jdbcTemplate.update(sql, new Object[]{status,timeStamp,userTaskId});

//            }else if("2".equals(status)){
//                String sql="update user_task set status = ?,user_commit_time = ? where id = ?";
//                Date date = new Date();
//                Timestamp timeStamp = new Timestamp(date.getTime());
//                count= jdbcTemplate.update(sql, new Object[]{status,timeStamp,userTaskId});

            }else{
                String sql="update user_task set status = ? where id = ?";
                count= jdbcTemplate.update(sql, new Object[]{status,userTaskId});
            }
//            System.out.println("update"+count);
            if(count == 1){
                return "success";
            }else{
                return "操作太频繁";
            }
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String updateCompeleteUserTask(String userTaskId, String picPath, String orderId) {
        try {
            String sql4="update user_task set status = ?,taobao_order_id = ?,screen_pic = ?,user_commit_time = ? where id = ? and status = 1";
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            int count= jdbcTemplate.update(sql4, new Object[]{2,orderId,picPath,timeStamp,userTaskId});
//            System.out.println("update"+count);
            if(count == 1){
                return "提交成功，等待审核";
            }else{
                return "操作太频繁";
            }
        }catch (Exception e){
            return "error";
        }
    }
}
