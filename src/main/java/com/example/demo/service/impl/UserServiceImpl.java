package com.example.demo.service.impl;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.map.UserMapper;
import com.example.demo.map.UserRowMapper;
import com.example.demo.po.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.EncryptionUtils;
import com.example.demo.utils.Utils;
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
public class UserServiceImpl implements UserService {

    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String addRecommendUser(String username, String addUserId) {
        //数据验证
        if(Utils.isEmpty(username)){
            return "用户名不能为空";
        }
        try {
            //检查用户是否已经存在   已存在返回  查看是否注册   未注册提示已推荐未激活注册  已注册 提示已经注册
            String sql="select * from user where username = ?";
            List<User> userList= jdbcTemplate.query(sql, new Object[]{username},new UserRowMapper());
            System.out.println("count"+userList);
            if(null != userList && userList.size()>0){
                User user =  userList.get(0);
                if(Utils.isEmpty(user.getPassword())){
                    // 未注册提示已推荐未激活注册
                    return "此账号已推荐未激活,推荐码:"+user.getRecommend_code();
                }else{
                    // 已注册
                    return "此账号已注册";
                }
            }else{
                //生成Id，并检查
                int id = 0;
                int maxCheck = 0;
                boolean produceId = false;
                while (maxCheck < 5){
                    Random random = new Random();
                    id = 10000+random.nextInt(100000);
                    //检查Id
                    String sql2="select count(*) from user where id ="+id;
                    int count= jdbcTemplate.queryForObject(sql2, Integer.class);
                    if(count == 0){
                        maxCheck = 6;
                        produceId = true;
                    }
                    maxCheck++;
                }
                if(!produceId || id < 10000){
                    return "添加失败，请重试";
                }
                //生成注册码
                String recommendCode = Utils.getRandomString(6);
                System.out.println("regiser id"+id+",recommendCode"+recommendCode);
                //注册推荐用户
                String sql4="insert into user (id,username,role_id,status,recommend_code,recommend_user_id,recommend_time) values (?,?,?,?,?,?,?)";
                Date date = new Date();
                Timestamp timeStamp = new Timestamp(date.getTime());
                int count4= jdbcTemplate.update(sql4, new Object[]{id,username,4,1,recommendCode,addUserId,timeStamp});
//            int count4= jdbcTemplate.update(sql4, new Object[]{id,username,4,1,recommendCode,addUserId,Utils.getTime(new Date().getTime(),"yyyy-MM-dd HH:mm:ss.SSS")});
                System.out.println("insert"+count4);
                //返回推荐码
                if(count4 == 1){
                    return  username+"，添加成功，推荐码为:" +recommendCode;
                }else{
                    return "添加失败，请重试";
                }
            }
        }catch (Exception e){
            logger.error("addRecommendUser Exception"+e.getMessage());
            return "error";
        }
    }

    @Override
    public String registerUser(String username, String recommendCode, String password, String phone, String email) {
        //数据验证
        if(Utils.isEmpty(username)){
            return "用户名不能为空";
        }
        if(Utils.isEmpty(password)||password.trim().length() < 6){
            return "密码长度至少6位";
        }
        if(password.contains("-")||password.contains("=")){//防sql注入
            return "密码不能使用特殊符合";
        }
        if(Utils.isEmpty(recommendCode)){
            return "推荐码不能为空";
        }
        if(Utils.isEmpty(phone)){
            return "手机号码不能为空";
        }
        if(Utils.isEmpty(email)){
            return "邮箱地址不能为空";
        }
        try {
            //如果已注册  提示已经注册
            String sql2="select * from user where username = ?";
            List<User> userList= jdbcTemplate.query(sql2, new Object[]{username},new UserRowMapper());
            System.out.println("count"+userList);
            if(null != userList && userList.size()>0){
                User user =  userList.get(0);
                if(Utils.isEmpty(user.getPassword())){
                    //验证推荐码是否正确，正确的话 进行注册
                    if(user.getRecommend_code().equals(recommendCode)){
                        String sql4="update user set password = ?, phone = ?,  email = ?, register_time = ?, status = ? where username = ? and recommend_code = ?";
                        Date date = new Date();
                        Timestamp timeStamp = new Timestamp(date.getTime());
                        int count= jdbcTemplate.update(sql4, new Object[]{EncryptionUtils.getMD5Str(password),phone,email,timeStamp,2,username,recommendCode});
                        System.out.println("update"+count);
                        if(count == 1){
                            return "注册成功";
                        }
                    }else{
                        return "邀请码错误";
                    }
                }else{
                    return "账号已经注册";
                }
            }else{
                //注册失败（非邀请用户注册，提示邀请码错误）
                return "邀请码错误";
            }
        }catch (Exception e){
            logger.error("registerUser Exception"+e.getMessage());
            return "error";
        }
        return "error";
    }

    @Override
    public String login(String username, String password) {
        //数据验证
        if(Utils.isEmpty(username)){
            return "用户名不能为空";
        }
        if(Utils.isEmpty(password)){
            return "密码错误";
        }

        System.out.println("username:"+username+password);
        try {
            String sql="select * from user where username = ? and password =?";
            List<User> userList= jdbcTemplate.query(sql, new Object[]{username,EncryptionUtils.getMD5Str(password)},new UserRowMapper());
            System.out.println("count"+userList);
            if(null != userList && userList.size()>0){
                User user = userList.get(0);
                //登录成功进入主界面
                switch (user.getStatus()){
                    case 1:
                        return "未注册激活";
                    case 2:
                        if(user.getRole_id() == 4){//用户
                            return "success:"+user.getId();
                        }else if(user.getRole_id() == 3){//店主
                            return "shopkeeper:"+user.getId();
                        }else if(user.getRole_id() == 2){//管理员
                            return "admin:"+user.getId();
                        }else if(user.getRole_id() == 1){//炒鸡管理员
                            return "supperAdmin:"+user.getId();
                        }else{
                            return "error";
                        }
                    default:
                        return "账户被禁用";
                }
            }else{
                //登录失败通知用户名或密码错误
                return "用户名或密码错误";
            }
        }catch (Exception e){
            logger.error("login Exception"+e.getMessage());
            return "error";
        }
    }

    @Override
    public Object getMyAllUserList(String addUserId,int status) {
        try {
            // 1已推荐未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
            String sql="select * from user where recommend_user_id = ? and status = ? order by recommend_time desc";
            List<User> userList= jdbcTemplate.query(sql, new Object[]{addUserId,status},new UserMapper());
            System.out.println("count"+userList);
            return userList;
        }catch (Exception e){
            logger.error("getAllUserList Exception"+e.getMessage());
            return "error";
        }
    }

    @Override
    public Object getUserByName(String username) {
        try {
            String sql="select * from user where username = ? ";
            List<User> userList= jdbcTemplate.query(sql, new Object[]{username},new UserMapper());
            System.out.println("count"+userList);
            return userList;
        }catch (Exception e){
            logger.error("getUserByName Exception"+e.getMessage());
            return "error";
        }
    }

    @Override
    public Object getUserById(String id) {
        try {
            String sql="select * from user where id = ? ";
            List<User> userList= jdbcTemplate.query(sql, new Object[]{id},new UserMapper());
            System.out.println("count"+userList);
            return userList;
        }catch (Exception e){
            return "error";
        }
    }

    @Override
    public String updateUser(User user) {
        try {
            String sql4="update user set phone = ?,  email = ?, status = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{user.getPhone(),user.getEmail(),user.getStatus(),user.getId()});
            System.out.println("update"+count);
            if(count == 1){
                return "success";
            }
        }catch (Exception e){
            return "error";
        }
        return "error";
    }

    @Override
    public String updateUserReceiptCode(String id, String codePath) {
        try {
            String sql4="update user set receipt_code = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{codePath,id});
            System.out.println("update"+count);
            if(count == 1){
                return "上传成功";
            }
        }catch (Exception e){
            return "error";
        }
        return "error";
    }

    @Override
    public String updateUserStatus(String id, String status) {
        try {
            String sql4="update user set status = ? where id = ?";
            int count= jdbcTemplate.update(sql4, new Object[]{status,id});
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
