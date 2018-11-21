package com.example.demo.service.impl;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.map.TaskMapper;
import com.example.demo.po.Task;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        try {
            String sql="select * from task where status = 1 ";
            List<Task> userList= jdbcTemplate.query(sql,new TaskMapper());
            System.out.println("count"+userList);
            return  userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public Object getTaskById(String taskId) {
        try {
            String sql="select * from task where id = ?";
            List<Task> userList= jdbcTemplate.query(sql, new Object[]{taskId},new TaskMapper());
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
