package com.example.demo.utils;

import com.example.demo.DemoApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtils {
//    public static final String saveImgPath = "D:/webcontent/";
    public static String savePic(String id,MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String savePath = getPicSavePath();
        String filename = getPicSaveName(id,file.getOriginalFilename());
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(savePath+ filename);
            Files.write(path, bytes);
            System.out.println(savePath+ filename);
        } catch (IOException e) {
            return "error";
        }
        return filename;
    }
    public static String saveCompressPic(String id,MultipartFile file,int widthdist, int heightdist) {
        if (file.isEmpty()) {
            return null;
        }
        String savePath = getPicSavePath();
        String filename = getPicSaveName(id,file.getOriginalFilename());
        CompressImg.reduceImg(file,savePath+filename,widthdist,heightdist,null);
//        try {
//            // Get the file and save it somewhere
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(savePath+ filename);
//            Files.write(path, bytes);
//            System.out.println(savePath+ filename);
//        } catch (IOException e) {
//            return "error";
//        }
        return filename;
    }
    public static String getPicSavePath(){
        File fileT = new File(DemoApplication.saveImgPath);
//        File fileT = new File(System.getProperty("User.dir") + "/webcontent/");
//        File fileT = new File(System.getProperty("User.dir") + "/src/main/resources/static/images/");//src\main\resources\static
        if (!fileT.exists()) {
            fileT.mkdirs();
        }
        System.out.println("fileT:"+fileT.getAbsolutePath());
        return fileT.getAbsolutePath()+"/";
    }
    public static String getPicSaveName(String id,String fileName){
        System.out.println(fileName);
        String filename=id+"_";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String date=sdf.format(new Date());
        filename+=date;
        Random r=new Random();
        filename+=r.nextInt(1000)+fileName.substring(fileName.lastIndexOf("."));
        System.out.println(filename);
        return filename;
    }
}
