package com.example.demo.controller;

import com.example.demo.po.Task;
import com.example.demo.po.UserTask;
import com.example.demo.service.UserTaskService;
import com.example.demo.utils.FileUtils;
import com.example.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DbUserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    @RequestMapping("getTaskUserTaskList")
    public String getTaskUserTaskList(String taskId, Model model){
        Object result = userTaskService.getTaskUserTaskList(taskId);
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<UserTask> taskList = (List<UserTask>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("message",
                        taskList);
                return "index.html";
            }else{
                model.addAttribute("message",
                        "暂无人接任务");
                return "index.html";
            }
        }
    }
    @RequestMapping("getMyTaskUserTaskList")
    public String getUserSendAllTaskAllUserTaskList(String uid,int status, Model model){//后台端使用
        if(Utils.isEmpty(uid)){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        Object result = userTaskService.getUserSendAllTaskAllUserTaskList(uid,status);
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<UserTask> taskList = (List<UserTask>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("taskList",
                        taskList);
                return "deal_task";
            }else{
                model.addAttribute("message",
                        "暂无人接任务");
                return "deal_task";
            }
        }
    }
    @RequestMapping("getMyUserTaskList")
    public String getMyUserTaskList(String userId,Model model){
        Object result = userTaskService.getMyUserTaskList(userId);
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<UserTask>  userTaskList = (List<UserTask>) result;
            if(null != userTaskList && userTaskList.size() > 0){
                model.addAttribute("message",
                        userTaskList);
                return "index.html";
            }else{
                model.addAttribute("message",
                        "还未接任务");
                return "index.html";
            }

        }
    }
    @RequestMapping("getUserTask")
    public String getUserTask(String userTaskId,Model model){
        Object result = userTaskService.getUserTask(userTaskId);
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<UserTask>  userTaskList = (List<UserTask>) result;
            if(null != userTaskList && userTaskList.size() > 0){
                model.addAttribute("message",
                        userTaskList.get(0));
                return "index2.html";
            }else{
                model.addAttribute("message",
                        "任务不存在");
                return "index2.html";
            }

        }
    }
    @RequestMapping("addUserTask")
    public String addUserTask(int user_id,int task_id, Model model){
        if(0 == user_id){
            return "未登陆";
        }
        if(0 == task_id){
            return "任务错误";
        }
        UserTask task = new UserTask();
        task.setUser_id(user_id);
        task.setTask_id(task_id);
        String result = userTaskService.addUserTask(task);
        if("error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    result);
            return "index.html";
        }
    }
    @RequestMapping("updateUserTaskStatus")
    public String updateUserTaskStatus(String userTaskId,String status,Model model){
        String result = userTaskService.updateUserTaskStatus(userTaskId,status);
        if( "error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    result);
            return "index.html";
        }
    }
    @RequestMapping("updateCompeleteUserTask")
    public String updateCompeleteUserTask(String userTaskId, String orderId,@RequestParam("file") MultipartFile file, Model model){
        if(Utils.isEmpty(orderId)){
            return "订单Id不能为空";
        }
        String path = FileUtils.savePic(file);
        String result = userTaskService.updateCompeleteUserTask(userTaskId,path,orderId);
        if("error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    result);
            return "index.html";
        }
    }


}
