package com.example.demo.service.impl;

import com.example.demo.map.TaskMapper;
import com.example.demo.map.UserTaskMapper;
import com.example.demo.po.Task;
import com.example.demo.po.UserTask;
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
            System.out.println("count"+userList);
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
            int user_task_status = 2;
            if(status == 1){
                user_task_status = 1;
                sql="select user_task.* from task INNER JOIN user_task on task.id = user_task.task_id where create_uid = ? and user_task.status = ? order by user_task.create_time desc ";
            }else if(status == 3){
                user_task_status = 3;
                sql="select user_task.* from task INNER JOIN user_task on task.id = user_task.task_id where create_uid = ? and user_task.status = ? order by user_task.user_commit_time desc ";
            }else{
                sql="select user_task.* from task INNER JOIN user_task on task.id = user_task.task_id where create_uid = ? and user_task.status = ? order by user_task.user_commit_time desc ";
            }
            List<UserTask> userList= jdbcTemplate.query(sql, new Object[]{uid,user_task_status},new UserTaskMapper());
            System.out.println("count"+userList);
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
            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String addUserTask(UserTask userTask) {
        try {
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
            System.out.println("insert"+count4);
            //返回推荐码
            if(count4 == 1){
                return  "success";
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
            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String updateUserTaskStatus(String userTaskId, String status) {
        try {
            String sql4="update user_task set status = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{status,userTaskId});
            System.out.println("update"+count);
            if(count == 1){
                return "success";
            }
        }catch (Exception e){
            return "error";
        }
        return "error";
    }

    @Override
    public String updateCompeleteUserTask(String userTaskId, String picPath, String orderId) {
        try {
            String sql4="update user_task set status = ?,taobao_order_id = ?,screen_pic = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{2,orderId,picPath,userTaskId});
            System.out.println("update"+count);
            if(count == 1){
                return "success";
            }
        }catch (Exception e){
            return "error";
        }
        return "error";
    }
}
