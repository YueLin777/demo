package com.mind.contract.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mind.contract.dao.StatementDao;
import com.mind.contract.entity.dto.StatementDto;
import com.mind.contract.entity.pojo.Statement;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.service.StatementService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * ClassName：StatementController
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/contract")
public class StatementController {
    @Resource
    private StatementService statementService;

//    /**
//     * 导出
//     */
//    @PostMapping("/exportStatement")
//    public void export(String fileName , HttpServletResponse response){
//      try{
//          this.setExcelResponseProp(response,fileName);
//          List<StatementDto> list = CommonUtil.buildDemoExcel(StatementDto.class);
//          EasyExcel.write(response.getOutputStream())
//                  .head(StatementDto.class)
//                  .excelType(ExcelTypeEnum.XLSX)
//                  .sheet(fileName)
//                  .doWrite(list);
//      }catch (IOException e){
//          throw new RuntimeException(e);
//      }
//    }
//
//    @PostMapping("/statementData")
//    @SneakyThrows
//    public Object statementData(@RequestPart("file") MultipartFile file){
//        List<StatementDto> list = EasyExcel.read(file.getInputStream())
//                .head(StatementDto.class).sheet().doReadSync();
//        return list;
//    }
//
//    private void setExcelResponseProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode(rawFileName,"UTF-8").replaceAll("\\+","%20");
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//    }


    /**
     * 导出
     */
    @RequestMapping("/exportStatement")
    @ResponseBody
    public void exportStatement(HttpServletResponse httpServletResponse){
        statementService.exportStatement(httpServletResponse);
    }

    /**
    * 导入
    * */
    @PostMapping("/uploadStatementExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        statementService.uploadStatementExcel(file);
        return ResponseVo.ok();
    }

//    /*
//    * 导出收入支出报表
//    * */
//    @GetMapping("/exportStatement")
//    public void exportStatement(HttpServletResponse response){
//        try {
//            this.setExcelResponseProp(response,"收入支出报表");
//            List<StatementDto> list = this.getStatementList();
//            EasyExcel.write(response.getOutputStream())
//                    .head(StatementDto.class)
//                    .excelType(ExcelTypeEnum.XLSX)
//                    .sheet("收入支出列表")
//                    .doWrite(list);
//        }catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void setExcelResponseProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = URLEncoder.encode(rawFileName,"UTF-8").replaceAll("\\+ ", "%20");
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//    }
//
//    private List<StatementDto> getStatementList() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ClassPathResource classPathResource = new ClassPathResource("mock/statement.json");
//        InputStream inputStream = classPathResource.getInputStream();
//        return objectMapper.readValue(inputStream, new TypeReference<List<StatementDto>>() {
//        });
//    }
}
