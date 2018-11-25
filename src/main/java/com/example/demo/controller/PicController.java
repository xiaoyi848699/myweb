package com.example.demo.controller;

import com.example.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
public class PicController {

//	@Resource
	private ResourceLoader resourceLoader;

	@Autowired
	public PicController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * 显示单张图片
	 * @return
	 */
	@RequestMapping("showPhotos")
	public ResponseEntity showPhotos(String fileName){
		System.out.println("file....:"  + fileName);
		if(Utils.isEmpty(fileName)){
			return ResponseEntity.notFound().build();
		}
		try {
//			String path = System.getProperty("User.dir") + "/webcontent/";
			String path = "/web_save_img/";
//			File fileT = new File(DemoApplication.saveImgPath);
			File fileT = new File(path);
//
			return ResponseEntity.ok(resourceLoader.getResource("file:"  +fileT.getAbsolutePath().replace("\\","/")+"/"+ fileName));
		} catch (Exception e) {
			System.out.println("showPhotos Exception:"  + e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
}
