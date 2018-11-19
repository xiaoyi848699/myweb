package com.example.demo.controller;

import com.example.demo.filter.MyApplicationListener;
import com.example.demo.map.UserRowMapper;
import com.example.demo.po.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.FileUtils;
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
import java.util.List;
import java.util.Map;

//@RequestMapping("/user")   //请求路径/taobao/getUsers
@Controller
public class DbUserController {

    private Logger logger =  LoggerFactory.getLogger(MyApplicationListener.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    //    execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
//    update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
//    query方法及queryForXXX方法：用于执行查询相关语句；
//    call方法：用于执行存储过程、函数相关语句。
    @ResponseBody
    @RequestMapping("/getUsers")
    public List<Map<String, Object>> getDbType() {


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
    public String login(HttpServletResponse response, String username, String password, Model model) {
        String result = userService.login(username, password);
        System.out.println(username+"->result:" + result);
        if ("error".equals(result)) {
            model.addAttribute("message",
                    result);
            return "404";
        } else {
            String[] str = result.split(":");
            if (str.length == 2) {
                Cookie cookie = new Cookie("userId", str[1]);
                cookie.setPath("/");
                cookie.setMaxAge(80000);//过期时间
                response.addCookie(cookie);
                Cookie cookie2 = new Cookie("username", username);
                cookie2.setPath("/");
                cookie2.setMaxAge(80000);//过期时间80000s
                response.addCookie(cookie2);
//                request.getSession().setAttribute("userId",str[1]);

                if ("success".equals(str[0])) {
                    return "homepage";
                } else if ("shopkeeper".equals(str[0])) {
                    return "shopkeeper";
                } else if ("admin".equals(str[0])) {
                    return "admin";
                } else if ("supperAdmin".equals(str[0])) {
                    return "supperAdmin";
                }
            }
            model.addAttribute("message",
                    result);
            return "index";
        }
    }

    @RequestMapping("addUser")
    public String addUser(String username, String addUserId, Model model) {
        String result = userService.addRecommendUser(username, addUserId);
        model.addAttribute("message",
                result);
        if ("error".equals(result)) {
            return "404.html";
        } else {
            return "status";
        }
    }
    @RequestMapping("getMyAllUserList")
    public String getMyAllUserList(String addUserId,int status, Model model) {
        Object result = userService.getMyAllUserList(addUserId,status);
        if ("error".equals(result)) {
            model.addAttribute("message",
                    result);
            return "404";
        } else {
            List<User> userList = (List<User>) result;
            if(null == userList || userList.size() == 0){
                model.addAttribute("message",
                        "还未添加推荐用户");
            }else {
                model.addAttribute("userList",
                        userList);
            }
            return "my_recommend_list";
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

    @RequestMapping("updateUserReceiptCode")
    public String updateUserReceiptCode(String id, @RequestParam("file") MultipartFile file, Model model) {
        String path = FileUtils.saveCompressPic(file,250,340);
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
