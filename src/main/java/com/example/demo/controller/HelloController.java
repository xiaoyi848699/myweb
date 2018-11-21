package com.example.demo.controller;

import com.example.demo.map.UserRowMapper;
import com.example.demo.po.User;
import com.example.demo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Controller
public class HelloController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserService userService;

//	@Resource
	private ResourceLoader resourceLoader;

	@Autowired
	public HelloController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@GetMapping("/hello")
	public String index(){
//		return "HelloController Hello World!";
		return "index.html";
	}


	@RequestMapping("/homepage_info")
	private String homepage(String userId, Model model){


		return "homepage";
	}
	@RequestMapping("/list")
	private String list(){
		log.println("lists");
		return "list";
	}
	@RequestMapping("/person")
	private String person(String userId, Model model){
//		Object userId = request.getSession().getAttribute("userId");
		log.println("person"+userId);
		if(null == userId){
			return "index";
		}
		List<User> userList = (List<User>) userService.getUserById(userId);
		if(null != userList && userList.size() > 0){
			model.addAttribute("userInfo",
					userList.get(0));
		}
		return "person";
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
