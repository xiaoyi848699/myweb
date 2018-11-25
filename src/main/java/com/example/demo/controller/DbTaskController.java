package com.example.demo.controller;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.po.Task;
import com.example.demo.po.TaskJoin;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DbTaskController {
    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);
    @Autowired
    private TaskService taskService;

    @RequestMapping("getMySendTask")
    public String getMySendTask(HttpServletRequest request,String addUserId,int status, Model model){
//        if(Utils.isEmpty(addUserId)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == addUserId || null == sessionUid || !addUserId.equals(sessionUid.toString())){
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
                return "admin_tasklist";
            }else{
                model.addAttribute("message",
                        "暂未发布任务");
                return "admin_tasklist";
            }
        }
    }
//    @ResponseBody
    @RequestMapping("getSendTask")
    public String getSendTask(HttpServletRequest request,String requestId, Model model){
//        if(Utils.isEmpty(requestId)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == requestId || null == sessionUid || !requestId.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        System.out.println("HttpServletRequest--->userId"+request.getSession().getAttribute("userId"));
        Object result = taskService.getSendTask();
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
//            model.addAttribute("getDateStatus",
//                    "success");
            List<Task> taskList = (List<Task>) result;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("taskList",
                        taskList);
                logger.info("addTask result:"+result);
                return "homepage";
//                return "homepage :: taskListFragment";
            }else{
                model.addAttribute("message",
                        "暂无任务");
                return "homepage";
            }
        }
    }
    @RequestMapping("getTaskById")
    public String getTaskById(HttpServletRequest request,String requestId,String taskId,Model model){
//        if(Utils.isEmpty(requestId)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == requestId || null == sessionUid || !requestId.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        Object result = taskService.getTaskById(requestId,taskId);

        if(null != result && "error".equals(result.toString())){
            return "show";
        }else{
            List<TaskJoin>  taskInfo = (List<TaskJoin>) result;
            getNewTask(model);
            if(null != taskInfo && taskInfo.size() > 0){
                model.addAttribute("taskInfo",
                        taskInfo.get(0));
                return "show";
            }else{
                model.addAttribute("taskInfo_error",
                        "任务不存在");
                return "show";
            }
        }
    }

    private void getNewTask(Model model) {
        Object taskResult = taskService.getSendTaskLimit(5);
//        System.out.println("getNewTask"+taskResult);
        addNewTaskToModel(model, taskResult);
    }

    static void addNewTaskToModel(Model model, Object taskResult) {
        if(null != taskResult && "error".equals(taskResult.toString())){
            model.addAttribute("newTaskList_error",
                    "暂无新任务");
        }else{
            List<Task> taskList = (List<Task>) taskResult;
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("newTaskList",
                        taskList);
            }else{
                model.addAttribute("newTaskList_error",
                        "暂无新任务");
            }
        }
    }

    @RequestMapping("addTask")
    public String addTask(HttpServletRequest request,String title,String task_describe,int uid, @RequestParam("file") MultipartFile file, Model model){
//        if(uid <= 0){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == String.valueOf(uid) || null == sessionUid || !String.valueOf(uid).equals(sessionUid.toString())){
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

        String path = FileUtils.saveCompressPic(String.valueOf(uid),file,400,600);
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
    public String updateTaskStatus(HttpServletRequest request,String operateId,String taskId,String status,Model model){
        String result = taskService.updateTaskStatus(taskId,status);
        if( "error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    "操作成功");
            return getMySendTask(request,operateId,0,model);
        }
    }



}
