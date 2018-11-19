package com.example.demo.service;

import com.example.demo.po.UserTask;

public interface UserTaskService {
    /**
     * 获取某个任务下的所有 用户接受任务列表
     * @param taskId
     * @return
     */
    Object getTaskUserTaskList(String taskId);

    /**
     * 获取某个人发布的所有任务列表中的所有用户接受任务列表（根据状态）
     */
    Object getUserSendAllTaskAllUserTaskList(String uid,int status);

    /**
     * 获取某个人的 用户接受任务列表
     * @param userId
     * @return
     */
    Object getMyUserTaskList(String userId);

    /**
     * 新增接受任务
     * @param userTask
     * @return
     */
    String addUserTask(UserTask userTask);

    /**
     * 查询单个接受任务信息
     * @param userTaskId
     * @return
     */
    Object getUserTask(String userTaskId);
    /**
     * 修改任务状态
     * @param userTaskId
     * @param status
     * @return
     */
    String updateUserTaskStatus(String userTaskId,String status);

    /**
     * 提交接受完成任务
     * @return
     */
    String updateCompeleteUserTask(String userTaskId,String picPath,String orderId);


}
