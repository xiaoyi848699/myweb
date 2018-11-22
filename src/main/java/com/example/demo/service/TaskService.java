package com.example.demo.service;

import com.example.demo.po.Task;

public interface TaskService {
    /**
     * 获取我发布的任务列表
     */
    Object getMySendTask(String id,int status);

    /**
     * 用户获取所有的任务列表
     * @return
     */
    Object getSendTask();
    /**
     * 用户获取所有的任务列表
     * @return
     */
    Object getSendTaskLimit(int max);

    /**
     * 获取某个任务详情
     * @param requestId 请求人Id
     * @param id
     * @return
     */
    Object getTaskById(String requestId,String id);

    /**
     * 新增任务
     * @param task
     * @return
     */
    String addTask(Task task);

    /**
     * 修改任务状态
     * @param id
     * @param status
     * @return
     */
    String updateTaskStatus(String id,String status);

}
