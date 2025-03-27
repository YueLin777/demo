package com.mind.contract.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileUtil {
    public  String saveFile(String savePath, MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            // 获取保存路径
            String path = savePath;
            File files = new File(path,fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName; // 返回重命名后的文件名
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件删除成功！");
            } else {
                System.out.println("文件删除失败！");
            }
        } else {
            System.out.println("文件不存在！");
        }
    }
}
