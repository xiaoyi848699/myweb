package com.example.demo.controller;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.po.Task;
import com.example.demo.service.TaskService;
import com.example.demo.utils.FileUtils;
import com.example.demo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DbTaskController {
    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);
    @Autowired
    private TaskService taskService;

    @RequestMapping("getMySendTask")
    public String getMySendTask(String addUserId,int status, Model model){
        if(Utils.isEmpty(addUserId)){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        Object result = taskService.getMySendTask(addUserId,status);
        logger.info("getMySendTask:"+result);
        if(null != result && "error".equals(result.toString())){
            return "404";
        }else{
            List<Task> taskList = (List<Task>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("taskList",
                        taskList);
//                Task task = taskList.get(0);
//                logger.info("getMySendTask:"+task.getCreate_time());
//                logger.info("getMySendTask:"+task.getCreate_time().toString());
//                logger.info("getMySendTask:"+task.getCreate_time().getDate());
//                logger.info("getMySendTask:"+task.getCreate_time().getTime());
                return "admin_tasklist";
            }else{
                model.addAttribute("message",
                        "暂未发布任务");
                return "admin_tasklist";
            }
        }
    }
    @RequestMapping("getSendTask")
    public String getSendTask(Model model){
        Object result = taskService.getSendTask();
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<Task> taskList = (List<Task>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("taskList",
                        taskList);
                logger.info("addTask result:"+result);
                return "homepage";
            }else{
                model.addAttribute("message",
                        "暂无任务");
                return "homepage";
            }
        }
    }
    @RequestMapping("getTaskById")
    public String getTaskById(String taskId,Model model){
        Object result = taskService.getTaskById(taskId);

        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<Task>  taskList = (List<Task>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("message",
                        taskList.get(0));
                return "index.html";
            }else{
                model.addAttribute("message",
                        "任务不存在");
                return "index.html";
            }

        }
    }
    @RequestMapping("addTask")
    public String addTask(String title,String task_describe,int uid, @RequestParam("file") MultipartFile file, Model model){
        if(uid <= 0){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        if(Utils.isEmpty(title)){
            return "标题不能为空";
        }
        if(Utils.isEmpty(task_describe)){
            return "描述不能为空";
        }

        String path = FileUtils.saveCompressPic(file,400,600);
        Task task = new Task();
        task.setTitle(title);
        task.setTask_describe(task_describe);
        task.setCreate_uid(uid);

        if(null != path){
            if("error".equals(path)){
                return "404";
            }else {
                task.setPictures(path);
            }
        }
        logger.info("addTask task:"+task);
        String result = taskService.addTask(task);
        logger.info("addTask result:"+result);
        if("error".equals(result)){
            return "404";
        }else{
            model.addAttribute("message",
                    result);
            return "add_task";
        }
    }
    @RequestMapping("updateTaskStatus")
    public String updateTaskStatus(String operateId,String taskId,String status,Model model){
        String result = taskService.updateTaskStatus(taskId,status);
        if( "error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    "操作成功");
            return getMySendTask(operateId,0,model);
        }
    }



}
