package com.xsg.shop.controller;

import com.xsg.shop.common.CommonResult;
import com.xsg.shop.component.PictureUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


/**
 * 接收图片处理接口
 * Created by xsg on 2020/4/9 14:46
 */
@Controller
@RequestMapping("/receive")
public class PictureController {
    @PostMapping(value = "/picture")
    @ResponseBody
    public CommonResult ReceivePicture(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        try{
            if(file.isEmpty()){
               return CommonResult.failed("图片为空");
            }
            PictureUtil pictureUtil = new PictureUtil();
            String result = pictureUtil.singleFileUpload(file);
            return CommonResult.success(result);
        }catch (Exception e){
            return CommonResult.failed("后端问题");
        }
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public CommonResult deletePicture(@RequestParam("picturepath")String picturepath){
        System.out.println(picturepath);
        picturepath = picturepath.replace("http://127.0.0.1:8888/", "D://");
        System.out.println(picturepath);
        File file = new File(picturepath);
        if (file.exists()) {
            if (file.delete()) {
               return CommonResult.success("删除成功");
            } else {
                return CommonResult.failed("删除失败");
            }
        } else {
            return CommonResult.failed("文件不存在");
        }
    }

}
