package com.example.demo;

import com.example.demo.po.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//如果使用RedisTemplate需要更改序列化方式

    @Test
    public void set() {
//        redisTemplate.opsForValue();//操作字符串
//        redisTemplate.opsForHash();//操作hash
//        redisTemplate.opsForList();//操作list
//        redisTemplate.opsForSet();//操作set
//        redisTemplate.opsForZSet();//操作有序set
        ArrayList<Task> taskList = new ArrayList<>();
        Task task = new Task();
        task.setCreate_uid(123);
        task.setPictures("aaaa");
        task.setTitle("按时吃饭睡觉打豆豆");
        task.setCreate_time(new Timestamp(new Date().getTime()));
        taskList.add(task);
        Task task1 = new Task();
        task1.setCreate_uid(321);
        task1.setPictures("a11aaa睡觉");
        task1.setTitle("111睡觉打豆豆");
        task1.setCreate_time(new Timestamp(new Date().getTime()));
        taskList.add(task1);
        redisTemplate.opsForValue().set("kkk", "按时吃饭睡觉打豆豆1");
        redisTemplate.opsForValue().set("ggg", "按时吃饭睡觉打豆豆2");
        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("test:set"));
        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("kkk"));
        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("myket"));
        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("ggg"));
        redisTemplate.opsForValue().set("myTask",task);
        redisTemplate.expire("myTask",30, TimeUnit.SECONDS);

        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("myTask"));
        redisTemplate.opsForValue().set("taskList",taskList);
        redisTemplate.expire("taskList",60, TimeUnit.SECONDS);
        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("taskList"));
        System.out.println("hasKey:"+ redisTemplate.hasKey("taskList"));
        System.out.println("hasKey:"+ redisTemplate.hasKey("myTask"));
//        Object taskListT1 = redisTemplate.opsForValue().get("taskList");
        ArrayList<Task> taskListT = (ArrayList<Task>) redisTemplate.opsForValue().get("taskList");
        if(null != taskListT){
            for (Task tt:taskListT){
                System.out.println("tt:"+ tt);
            }
        }

//        redisTemplate.opsForList().leftPush("mylist",task1);
//        System.out.println("redisTemplate:"+ redisTemplate.opsForValue().get("mylist"));
    }
}