package com.example.demo.service;

import com.example.demo.po.User;

public interface UserService {
    /**
     * 新增邀请
     * @param username
     * @param addUserId
     * @return
     */
    String addRecommendUser(String username, String addUserId);

    /**
     * 注册
     * @param username
     * @param recommendCode
     * @param password
     * @param phone
     * @param email
     * @return
     */
    String registerUser(String username, String recommendCode, String password, String phone, String email);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 获取我管理的所有用户
     * @param addUserId
     * @return
     */
    Object getMyAllUserList(String addUserId,int status);

    /**
     * 获取指定某个用户信息
     * @param username
     * @return
     */
    Object getUserByName(String username);

    /**
     * 获取指定某个用户信息
     * @param id
     * @return
     */
    Object getUserById(String id);

    /**
     * 更新指定某个用户信息（个人信息）
     * @param user
     * @return
     */
    String updateUser(User user);
    /**
     * 更新指定某个用户信息（个人信息）
     * @param id
     * @return
     */
    String updateUserReceiptCode(String id,String codePath);
    /**
     * 更新指定某个用户信息（状态）
     * @param id
     * @return
     */
    String updateUserStatus(String id,String status);
}
