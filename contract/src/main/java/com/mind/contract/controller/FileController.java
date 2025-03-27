package com.mind.contract.controller;

import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.utils.FileUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class FileController {

    @Value("${file.save.path}")
    private String savePath;

    @Value("${file.save.zpPath}")
    private String savezpPath;

    @Value("${file.save.xsPath}")
    private String savexsPath;

    @Value("${file.save.xsfpPath}")
    private String savexsfpPath;

    @Value("/opt/hd")
    private String hdPath;

    @Value("/opt/wd")
    private String wdPath;

    @Autowired
    private FileUtil fileUtil;

    private final ResourceLoader resourceLoader;

    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable String id, HttpServletResponse response) throws IOException {

        File outputFile = new File(savePath +File.separator+id+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(outputFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadInvoice/{invoice_code}/{invoice_number}")
    public void downloadStatement(@PathVariable("invoice_code") String invoice_code, @PathVariable("invoice_number") String invoice_number, HttpServletResponse response) throws IOException {
        File outputFile = new File(savezpPath + File.separator+invoice_code+invoice_number+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(outputFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadSell/{id}")
    public void downloadSell(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File outputFile = new File(savexsPath + File.separator+id+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(outputFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadSellInvoice/{id}")
    public void downloadSellInvoice(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File outputFile = new File(savexsfpPath + File.separator+id+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(outputFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadDelivery/{id}")
    public void downloadDelivery(@PathVariable Integer id, HttpServletResponse response) throws IOException{
        File deliveryFile = new File(hdPath + File.separator+id+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(deliveryFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadAcceptance/{id}")
    public void downloadAcceptance(@PathVariable Integer id, HttpServletResponse response) throws IOException{
        File acceptanceFile = new File(wdPath + File.separator+id+".pdf");
        try {
            response.setContentType("application/pdf;charset=utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            // 读取文件
            InputStream in = new FileInputStream(acceptanceFile);
            // copy流数据,i为字节数
            int i = (int) IOUtils.copy(in, outputStream);
            in.close();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
