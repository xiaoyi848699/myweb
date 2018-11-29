package com.example.demo.controller;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.map.UserRowMapper;
import com.example.demo.po.JsonResult;
import com.example.demo.po.ResponseData;
import com.example.demo.po.ResponseStatus;
import com.example.demo.po.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.FileUtils;
import com.example.demo.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

//@RequestMapping("/user")   //请求路径/taobao/getUsers
@Controller
public class DbUserController {

    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private HelloController helloController;

    //    execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
//    update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
//    query方法及queryForXXX方法：用于执行查询相关语句；
//    call方法：用于执行存储过程、函数相关语句。
    @ResponseBody
    @RequestMapping("/getUsers")
    public List<Map<String, Object>> getDbType(int role){
        logger.info("访问了controller");
//        int i = role;

        //Exception异常会自动拦截，这里只是做个测试自定义异常
//        int j = 0 / i;
//        int x = i / 0;

        String sql2 = "select count(*) from user";
        int count = jdbcTemplate.queryForObject(sql2, Integer.class);
        System.out.println("count" + count);


        String sql = "select * from user";
        System.out.println("list:" + sql);
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper());
        System.out.println("userList:" + userList);

//        String sql3="delete from user where id=?";
//        jdbcTemplate.update(sql3,51);

//        String sql4="insert into user (username,id) values (?,?)";
//        int count4= jdbcTemplate.update(sql4, new Object[]{"caoyc",3});
//        System.out.println(count4);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println("list:" + list);
        return list;
    }

    //    @ResponseBody
    @RequestMapping("login")
    public String login(HttpServletRequest request,HttpServletResponse response, String username, String password, Model model) {
        String result = userService.login(username, password);
        System.out.println(username+"->result:" + result);

        if ("error".equals(result)) {
            model.addAttribute("message",
                    result);
            return "404";
        } else {
            String[] str = result.split(":");
            if (str.length == 2) {
                setCookie(response, "username", username);
                request.getSession().setAttribute("userId",str[1]);

                if ("success".equals(str[0])) {
                    setCookie(response, "userId", str[1]);
                    return helloController.getHomepageInfo(request,str[1],model);
//                    return "homepage";
                } else if ("shopkeeper".equals(str[0])) {
                    setCookie(response, "adUserId", str[1]);
                    return "shopkeeper";
                } else if ("admin".equals(str[0])) {
                    setCookie(response, "adUserId", str[1]);
                    return "shopkeeper";
                } else if ("supperAdmin".equals(str[0])) {
                    setCookie(response, "adUserId", str[1]);
                    return "shopkeeper";
                }
            }
            model.addAttribute("message",
                    result);
            return "index";
        }
    }
    @ResponseBody
    @RequestMapping("mlogin")
    public ResponseData login(String username, String password, String from) {
        if(Utils.isEmpty(username)){
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_param_error);
            responseData.setMessage("参数错误");
            return responseData;
        }
        if(Utils.isEmpty(password)){
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_param_error);
            responseData.setMessage("密码错误");
            return responseData;
        }
        String result = userService.login(username, password);
        System.out.println(username+"->result:" + result);
        if ("error".equals(result)) {
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_fail);
            responseData.setMessage("获取数据失败");
            return responseData;
        } else {
            String[] str = result.split(":");
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_succes);
            responseData.setMessage("获取成功");
            return responseData;
        }
    }

    private void setCookie(HttpServletResponse response, String adUserId, String s) {
        Cookie cookie = new Cookie(adUserId, s);
        cookie.setPath("/");
        cookie.setMaxAge(80000);//过期时间
        response.addCookie(cookie);
    }

    @RequestMapping("addUser")
    public String addUser(HttpServletRequest request,String username, String addUserId, Model model) {
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
        String result = userService.addRecommendUser(username, addUserId);
        model.addAttribute("message",
                result);
        if ("error".equals(result)) {
            return "404.html";
        } else {
            return getMyAllUserList(request,addUserId,0,model);
        }
    }
    @RequestMapping("getMyAllUserList")
    public String getMyAllUserList(HttpServletRequest request,String addUserId,int status, Model model) {
        System.out.println(status+"getMyAllUserList"+addUserId);
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
        Object result = userService.getMyAllUserList(addUserId,status);
        if ("error".equals(result)) {
            model.addAttribute("message",
                    result);
            return "404";
        } else {
            List<User> userList = (List<User>) result;
            if(null == userList || userList.size() == 0){
                model.addAttribute("message",
                        "暂无数据");
            }else {
                model.addAttribute("userList",
                        userList);
            }
            model.addAttribute("status",
                    status);
            return "my_recommend_list";
        }
    }
    @ResponseBody
    @RequestMapping("myAllUserList")
    public ResponseData getMyAllUserListResponseBody(String addUserId,int status,String from,String token) {
        System.out.println(status+"getMyAllUserList"+addUserId);
        if(Utils.isEmpty(addUserId)){
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_param_error);
            responseData.setMessage("用户名不能为空");
            return responseData;
        }
        Object result = userService.getMyAllUserList(addUserId,status);
        if ("error".equals(result)) {
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_fail);
            responseData.setMessage("获取数据失败");
            return responseData;
        } else {
            List<User> userList = (List<User>) result;
            ResponseData responseData = new ResponseData();
            responseData.setStatus(ResponseStatus.request_succes);
            responseData.setMessage("获取成功");
            responseData.setData(userList);
            return responseData;
        }
    }

    @RequestMapping("registerUser")
    public String registerUser(String username, String recommendation, String password, String phone, String email, Model model) {
        String result = userService.registerUser(username, recommendation, password, phone, email);
        model.addAttribute("message",
                result);
        if ("error".equals(result)) {
            return "404.html";
        } else {
            return "index.html";
        }
    }
    @RequestMapping("changeUserStatus")
    public String changeUserStatus(HttpServletRequest request,String operateId,String id, String status , Model model) {//后台调用
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
        //1已推荐未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
        String result = userService.updateUserStatus(id, status);
        if ("error".equals(result)) {
            return "404";
        } else {
            model.addAttribute("message",
                    "操作成功");
            return getMyAllUserList(request,operateId,0,model);
        }
    }

    @RequestMapping("updateUserReceiptCode")
    public String updateUserReceiptCode(String id, @RequestParam("file") MultipartFile file, Model model) {
        String path = FileUtils.saveCompressPic(id,file,500,680);
        String result = userService.updateUserReceiptCode(id, path);
        if ("error".equals(result)) {
            return "404.html";
        } else {
            model.addAttribute("message",
                    result);
            if ("上传成功".equals(result)) {
                List<User> userList = (List<User>) userService.getUserById(id);
                if (null != userList && userList.size() > 0) {
                    model.addAttribute("userInfo",
                            userList.get(0));
                }
            }
            return "person";
        }
    }
    @RequestMapping("/person")
    private String person(String requestId, Model model){
//		Object userId = request.getSession().getAttribute("userId");
        log.println("person"+requestId);
        if(null == requestId){
            return "index";
        }
        List<User> userList = (List<User>) userService.getUserById(requestId);
        if(null != userList && userList.size() > 0){
            model.addAttribute("userInfo",
                    userList.get(0));
        }
        return "person";
    }

//    @RequestMapping("updateUserReceiptCode2")
//    public String updateUserReceiptCode2(String id, String fileName,String fileBase64, Model model) {
//        String path = FileUtils.getPicSavePath();
//        String saveName = FileUtils.getPicSaveName(fileName);
//        generateImage(fileBase64,path+saveName);
//        String result = userService.updateUserReceiptCode(id, saveName);
//        if ("error".equals(result)) {
//            return "404.html";
//        } else {
//            model.addAttribute("message",
//                    result);
//            if ("上传成功".equals(result)) {
//                List<User> userList = (List<User>) userService.getUserById(id);
//                if (null != userList && userList.size() > 0) {
//                    model.addAttribute("userInfo",
//                            userList.get(0));
//                }
//            }
//            return "person";
//        }
//    }
//
//    /**
//     * @param imgStr base64编码字符串
//     * @param path   图片路径-具体到文件
//     * @return
//     * @Description: 将base64编码字符串转换为图片
//     * @Author:
//     * @CreateTime:
//     */
//    public static boolean generateImage(String imgStr, String path) {
//        System.out.println(imgStr);
//        System.out.println(path);
//        if (imgStr == null) {
//            return false;
//        }
//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//            // 解密
//            byte[] b = decoder.decodeBuffer(imgStr);
//            // 处理数据
//            for (int i = 0; i < b.length; ++i) {
//                if (b[i] < 0) {
//                    b[i] += 256;
//                }
//            }
//            OutputStream out = new FileOutputStream(path);
//            out.write(b);
//            out.flush();
//            out.close();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
