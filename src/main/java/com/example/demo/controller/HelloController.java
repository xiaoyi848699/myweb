package com.example.demo.controller;

import com.example.demo.map.UserRowMapper;
import com.example.demo.po.Task;
import com.example.demo.po.User;
import com.example.demo.po.UserTaskJoinU;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserTaskService;
import com.example.demo.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Controller
public class HelloController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserTaskService userTaskService;

	@GetMapping("")
	public String index(){
//		return "HelloController Hello World!";
		System.out.println("HelloController Hello World!");
		return "index";
	}
	@GetMapping("hello")
	public String hello(){
//		return "HelloController Hello World!";
		System.out.println("HelloController Hello World!");
		return "index";
	}


	@RequestMapping("/homepage_info")
	public String getHomepageInfo(HttpServletRequest request, String requestId, Model model){
		//获取总的订单量   获取我的任务和订单量  获取商家任务列表
		if(Utils.isEmpty(requestId)){
			model.addAttribute("message",
					"登录过期，请从新登录！");
			return "index";
		}
		System.out.println("HttpServletRequest--->userId"+request.getSession().getAttribute("userId"));
		Object taskResult = taskService.getSendTask();
		if(null != taskResult && "error".equals(taskResult.toString())){
			return "404";
		}else{
			List<Task> taskList = (List<Task>) taskResult;
			if(null != taskList && taskList.size() > 0){
				model.addAttribute("taskList",
						taskList);
			}else{
				model.addAttribute("taskList_error",
						"暂无任务");
			}
		}
		Object userTaskResult = userTaskService.getUserAllUserTaskList(requestId);
		model.addAttribute("allTaskListCount",
				userTaskService.getAllUserTaskList());
		if(null != userTaskResult && "error".equals(userTaskResult.toString())){
			return "404";
		}else{
			List<UserTaskJoinU>  userTaskList = (List<UserTaskJoinU>) userTaskResult;
			System.out.println("userTaskList"+userTaskList);
			if(null != userTaskList && userTaskList.size() > 0){
				model.addAttribute("userTaskList",
						userTaskList);
				model.addAttribute("userTaskListCount",
						userTaskList.size());
			}else{
				model.addAttribute("userTaskList_error",
						"还未接任务");
				model.addAttribute("userTaskListCount",
						0);
			}
		}
		return "homepage";
	}

	@RequestMapping("/show")
	private String show(){
		log.println("show");
		return "show";
	}
	@RequestMapping("/admin")
	private String admin(){
		log.println("shopkeeper");
		return "shopkeeper";
	}
	@RequestMapping("/header")
	private String header(){
		log.println("header");
		return "header";
	}
	@RequestMapping("/main")
	private String main(){
		log.println("main");
		return "main";
	}
	@RequestMapping("/menu")
	private String menu(){
		log.println("menu");
		return "menu";
	}
	@RequestMapping("/admin_tasklist")
	private String adminTasklist(Model model){
		List<User> userList = getUsers();
		model.addAttribute("list",userList);
		return "admin_tasklist";
	}
	@RequestMapping("/add_task")
	private String add_task(Model model){
		List<User> userList = getUsers();
		model.addAttribute("list",userList);
		return "add_task";
	}
//	@RequestMapping("/deal_task")
//	private String deal_task(Model model){
//		List<User> userList = getUsers();
//		model.addAttribute("list",userList);
//		return "deal_task";
//	}
//	@RequestMapping("/my_recommend_list")
//	private String my_recommend_list(Model model){
//		List<User> userList = getUsers();
//		model.addAttribute("list",userList);
//		return "my_recommend_list";
//	}
	@RequestMapping("/add_recommend")
	private String add_recommend(Model model){
		List<User> userList = getUsers();
		model.addAttribute("list",userList);
		return "add_recommend";
	}

	@NotNull
	private List<User> getUsers() {
		String sql = "select * from user";
		System.out.println("list:"+sql);
		List<User> userList =  jdbcTemplate.query(sql, new UserRowMapper());
		System.out.println("userList:"+userList);
		log.println("admin_tasklist");
		return userList;
	}
//	/**
//	 * 显示单张图片
//	 * @return
//	 */
//	@RequestMapping("showPhotos")
//	public ResponseEntity showPhotos(String fileName){
//		System.out.println("file....:"  + fileName);
//		if(Utils.isEmpty(fileName)){
//			return ResponseEntity.notFound().build();
//		}
//		try {
////			String path = System.getProperty("User.dir") + "/webcontent/";
//			File fileT = new File(FileUtils.saveImgPath);
//			// 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
////			System.out.println("file:" + path + fileName);
////
//			return ResponseEntity.ok(resourceLoader.getResource("file:"  +fileT.getAbsolutePath().replace("\\","/")+"/"+ fileName));
//		} catch (Exception e) {
//			System.out.println("showPhotos Exception:"  + e.getMessage());
//			return ResponseEntity.notFound().build();
//		}
//	}
}
