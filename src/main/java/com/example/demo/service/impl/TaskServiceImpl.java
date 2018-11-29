package com.example.demo.service.impl;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.map.TaskJoinMapper;
import com.example.demo.map.TaskMapper;
import com.example.demo.po.ResponseStatus;
import com.example.demo.po.Task;
import com.example.demo.po.TaskJoin;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger =  LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object getMySendTask(String uid,int status) {
        try {
            ////1正常  2 已经关闭  3 删除
            if(status == 0){
                String sql="select * from task where create_uid = ? and status between 1 and 2 order by create_time desc";
                List<Task> userList= jdbcTemplate.query(sql, new Object[]{uid},new TaskMapper());
                System.out.println("count"+userList);
                return  userList;
            }
            String sql="select * from task where create_uid = ? and status = ? order by create_time desc";
            List<Task> userList= jdbcTemplate.query(sql, new Object[]{uid,status},new TaskMapper());
            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }


    @Override
    public Object getSendTask() {
        if(redisTemplate.hasKey(ResponseStatus.REDIS_ALL_TASK_LIST)){
            logger.info("getSendTask from redis");
            return redisTemplate.opsForValue().get(ResponseStatus.REDIS_ALL_TASK_LIST);
        }else{
            try {
                String sql="select * from task where status = 1 order by create_time desc";
                List<Task> userList= jdbcTemplate.query(sql,new TaskMapper());

                redisTemplate.opsForValue().set(ResponseStatus.REDIS_ALL_TASK_LIST,userList);
                redisTemplate.expire(ResponseStatus.REDIS_ALL_TASK_LIST,1, TimeUnit.MINUTES);
                logger.info("getSendTask from mysql");
//                System.out.println("count"+userList);
                return  userList;
            }catch (Exception e){
                return "error";
            }
        }
    }

    @Override
    public Object getSendTaskLimit(int max) {
        //最新推荐
        if(redisTemplate.hasKey(ResponseStatus.REDIS_HOS_TASK_LIST)){
            logger.info("getSendTaskLimit from redis");
//            System.out.println("getSendTaskLimit from redis");
            return redisTemplate.opsForValue().get(ResponseStatus.REDIS_HOS_TASK_LIST);
        }else{
            try {
                String sql="select * from task where status = 1 order by create_time desc limit ?";
                List<Task> userList= jdbcTemplate.query(sql,new Object[]{max},new TaskMapper());
                redisTemplate.opsForValue().set(ResponseStatus.REDIS_HOS_TASK_LIST,userList);
                redisTemplate.expire(ResponseStatus.REDIS_HOS_TASK_LIST,1, TimeUnit.MINUTES);
                logger.info("getSendTaskLimit from mysql");
//                System.out.println("getSendTaskLimit from mysql"+userList);
                return  userList;
            }catch (Exception e){
                return "error";
            }
        }
    }

    @Override
    public Object getTaskById(String requestId,String taskId) {
        try {
            String sql="select t.*,ut.id as utaskid,ut.create_time as u_create_time,ut.taobao_order_id,ut.screen_pic,ut.status as utask_status,ut.user_commit_time,ut.business_deal_time" +
                    " from (select * from task where id = ?) as t LEFT JOIN (select * from user_task where user_id = ?) as ut on t.id=ut.task_id ";
            List<TaskJoin> userList= jdbcTemplate.query(sql, new Object[]{taskId,requestId},new TaskJoinMapper());
            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String addTask(Task task) {
        try {
            //生成Id，并检查
            int id = 0;
            int maxCheck = 0;
            boolean produceId = false;
            while (maxCheck < 5){
                Random random = new Random();
                id = 10000000+random.nextInt(100000);
                //检查Id
                String sql2="select count(*) from task where id ="+id;
                int count= jdbcTemplate.queryForObject(sql2, Integer.class);
                if(count == 0){
                    maxCheck = 6;
                    produceId = true;
                }
                maxCheck++;
            }
            if(!produceId || id < 10000000){
                return "添加失败，请重试";
            }
            String sql4="insert into task (id,title,pictures,task_describe,status,create_uid,create_time/*,end_time*/) values (?,?,?,?,?,?,?/*,?*/)";
            Date date = new Date();
            Timestamp timeStamp = new Timestamp(date.getTime());
            int count4= jdbcTemplate.update(sql4, new Object[]{id,task.getTitle(),task.getPictures(),task.getTask_describe(),1,task.getCreate_uid(),timeStamp/*,task.getEnd_time()*/});
//            int count4= jdbcTemplate.update(sql4, new Object[]{id,username,4,1,recommendCode,addUserId,Utils.getTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss.SSS")});
            System.out.println("insert"+count4);
            //返回推荐码
            if(count4 == 1){
                return  "添加成功";
            }else{
                return "接受失败";
            }
        }catch (Exception e){
            logger.info("addTask Exception:"+e.getMessage());
            return "error";
        }
    }

    @Override
    public String updateTaskStatus(String taskId, String status) {
        try {
            String sql4="update task set status = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{status,taskId});
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
