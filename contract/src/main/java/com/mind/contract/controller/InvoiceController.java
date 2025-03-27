package com.mind.contract.controller;

import com.mind.contract.entity.pojo.ItemSum;
import com.mind.contract.entity.pojo.TimeSum;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contract")
public class InvoiceController {
    @Resource
    private InvoiceService invoiceService;

    /**
     * 导出
     */
    @RequestMapping("/exportInvoice")
    @ResponseBody
    public void export(HttpServletResponse httpServletResponse){
        invoiceService.exportInvoice(httpServletResponse);
    }

    //按发票类型求和
    @RequestMapping("/timeSum")
    public ResponseVo timeSum(@RequestBody TimeSum timeSum){
        List<TimeSum> timeSums = invoiceService.timeSum(timeSum);
        return ResponseVo.ok(timeSums);
    }

    @PostMapping("/uploadInvoiceExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        invoiceService.uploadInvoiceExcel(file);
        return ResponseVo.ok();
    }

}
