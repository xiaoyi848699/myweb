package com.example.demo.controller;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.po.UserTask;
import com.example.demo.po.UserTaskJoinM;
import com.example.demo.po.UserTaskJoinU;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserTaskService;
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
public class DbUserTaskController {

    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);

    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DbTaskController dbTaskController;

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
                return "index";
            }else{
                model.addAttribute("message",
                        "暂无人接任务");
                return "index";
            }
        }
    }
    @RequestMapping("getMyTaskUserTaskList")//后台端使用
    public String getMyTaskUserTaskList(HttpServletRequest request,String uid,int status, Model model){
        System.out.println("getUserSendAllTaskAllUserTaskList:"+uid+status);
        logger.debug("getUserSendAllTaskAllUserTaskList:"+uid);
//        if(Utils.isEmpty(uid)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == uid || null == sessionUid || !uid.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        Object result = userTaskService.getUserSendAllTaskAllUserTaskList(uid,status);
        if(null != result && "error".equals(result.toString())){
            return "404.html";
        }else{
            List<UserTaskJoinM> taskList = (List<UserTaskJoinM>) result;
            logger.debug("taskList:"+taskList);
            if(null != taskList && taskList.size() > 0){
                model.addAttribute("taskList",
                        taskList);
                return "deal_task";
            }else{
                String strHint = "暂无人接任务";
               switch (status){
                   case 1:
                       strHint = "暂无人接任务";
                       break;
                   case 2:
                       strHint = "暂无人完成任务";
                       break;
                   case 3:
                       strHint = "暂无处理完成任务";
                       break;

               }
                model.addAttribute("message",
                        strHint);
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
    @RequestMapping("my_list")
    private String getMyList(HttpServletRequest request, String requestId, Model model){
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == requestId || null == sessionUid || !requestId.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        Object userTaskResult = userTaskService.getUserAllUserTaskList(requestId);
        if(null != userTaskResult && "error".equals(userTaskResult.toString())){
            return "404";
        }else{
            List<UserTaskJoinU>  userTaskList = (List<UserTaskJoinU>) userTaskResult;
            getNewTask(model);
//            System.out.println("userTaskList"+userTaskList);
            if(null != userTaskList && userTaskList.size() > 0){
                model.addAttribute("userTaskList",
                        userTaskList);
            }else{
                model.addAttribute("userTaskList_error",
                        "还未接任务");
            }
            return "list";
        }
    }

    private void getNewTask(Model model) {
        Object newTaskResult = taskService.getSendTaskLimit(5);
//        System.out.println("getNewTask"+taskResult);
        DbTaskController.addNewTaskToModel(model, newTaskResult);
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
    public String addUserTask(HttpServletRequest request,String user_id,String task_id, Model model){
//        if(Utils.isEmpty(user_id)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == user_id || null == sessionUid || !user_id.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        if(Utils.isEmpty(task_id)){
            model.addAttribute("message",
                    "接受任务失败，请重试");
            System.out.println("addUserTask error--->task_id"+task_id);
            return dbTaskController.getTaskById(request,user_id,task_id,model);
        }
        UserTask task = new UserTask();
        try {
            task.setUser_id(Integer.parseInt(user_id));
            task.setTask_id(Integer.parseInt(task_id));
        } catch (NumberFormatException e) {
            System.out.println("addUserTask NumberFormatException--->task_id"+task_id);
            model.addAttribute("message",
                    "接受任务失败，请重试");
            return dbTaskController.getTaskById(request,user_id,task_id,model);
        }
        String result = userTaskService.addUserTask(task);
        if("error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    result);
            return dbTaskController.getTaskById(request,user_id,task_id,model);
        }
    }
    @RequestMapping("updateUserTaskStatus")
    public String updateUserTaskStatus(HttpServletRequest request,String operateId,String userTaskId,String status,Model model){//后台使用
//        if(Utils.isEmpty(operateId)){
//            model.addAttribute("message",
//                    "登录过期，请从新登录！");
//            return "index";
//        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == operateId || null == sessionUid || !operateId.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        String result = userTaskService.updateUserTaskStatus(userTaskId,status);
        if( "error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    "操作成功");
            return getMyTaskUserTaskList(request,operateId,0,model);
        }
    }
    @RequestMapping("updateCompeleteUserTask")
    public String updateCompeleteUserTask(HttpServletRequest request,String user_id,String userTaskId,String taskId, String orderId,@RequestParam("file") MultipartFile file, Model model){
        if(Utils.isEmpty(orderId)){
            return "订单Id不能为空";
        }
        Object sessionUid = request.getSession().getAttribute("userId");
        System.out.println("HttpServletRequest--->userId"+sessionUid);
        if(null == user_id || null == sessionUid || !user_id.equals(sessionUid.toString())){
            model.addAttribute("message",
                    "登录过期，请从新登录！");
            return "index";
        }
        String path = FileUtils.savePic(user_id+"_"+userTaskId,file);
        String result = userTaskService.updateCompeleteUserTask(userTaskId,path,orderId);
        if("error".equals(result)){
            return "404.html";
        }else{
            model.addAttribute("message",
                    result);
            return dbTaskController.getTaskById(request,user_id,taskId,model);
        }
    }


}
