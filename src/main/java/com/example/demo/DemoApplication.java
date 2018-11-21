package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;

@Configuration
@ServletComponentScan(basePackages = "com.example.demo.filter")
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    @Value("${multipart.maxFileSize}")
    private String maxFileSize;
    @Value("${multipart.maxRequestSize}")
    private String maxRequestSize;


    public static  String saveImgPath = "";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        initData();
        SpringApplication.run(DemoApplication.class, args);
    }

    private static void initData() {
        File directory = new File("");// 参数为空
        try {
            saveImgPath = directory.getCanonicalPath()+"_save_img";
            System.out.println(saveImgPath);
        } catch (IOException e) {
           System.err.println("DemoApplication initData IOException"+e.getMessage());
        }
    }

    /**
     * * 文件上传配置
     * * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(maxFileSize);
        // 设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
