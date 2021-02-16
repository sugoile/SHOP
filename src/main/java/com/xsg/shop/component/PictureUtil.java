package com.xsg.shop.component;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by xsg on 2020/4/9 16:03
 */
public class PictureUtil {
    private static String staticPath = "D://images/";
    public String singleFileUpload(MultipartFile picture){
        if (Objects.isNull(picture) || picture.isEmpty()) {
            return "文件为空，请重新上传";
        }
        try {
            String filesPath = staticPath + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            String picuuid = str.substring(0, 8)+str.substring(9, 13)+str.substring(14, 18)+str.substring(19, 23)+str.substring(24);
            String imgName = picuuid + ".png";
            File fileDir = new File(filesPath + "\\");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String fileNamePath = filesPath+"/"+imgName;
            Thumbnails.of(picture.getInputStream()).size(200, 200)    //写入
                    .outputQuality(0.8f).toFile(fileNamePath);
            //删除D://
            return fileNamePath.substring(4);
        } catch (Exception e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }

}
