package com.mind.contract.controller;

import com.mind.contract.entity.pojo.Contract;
import com.mind.contract.entity.pojo.Invoice;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.query.InvoiceQuery;
import com.mind.contract.entity.query.SellInvoiceQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.SellInvoiceService;
import com.mind.contract.service.SellService;
import com.mind.contract.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * ClassName：SellInvoiceController
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/contract")
public class SellInvoiceController {
    @Resource
    private SellInvoiceService sellInvoiceService;

    //填写销售发票
    @RequestMapping("/sellInvoice")
    public ResponseVo sellInvoice(@RequestBody SellInvoice sellInvoice,Sell sell){
        ResponseVo responseVo = sellInvoiceService.sellInvoice(sellInvoice,sell);
        return ResponseVo.ok(responseVo);
    }

    //修改销售发票
    @RequestMapping("/modifySellInvoice")
    public ResponseVo modifySellInvoice(@RequestBody SellInvoice sellInvoice, Sell sell){
        ResponseVo responseVo = sellInvoiceService.modifySellInvoice(sellInvoice,sell);
        return ResponseVo.ok(responseVo);
    }

    //删除销售发票
    @RequestMapping("/delSellInvoice/{id}")
    public ResponseVo delSellInvoice(@PathVariable Integer id){
        if (null == id) {
            throw new BusinessException("编号不能为空");
        }
        int i = sellInvoiceService.count(id);
        if (i == 0) {
            throw new BusinessException("销售发票不存在");
        }
        return sellInvoiceService.delSellInvoice(id);
    }

    //查询销售发票
    @RequestMapping("/querySellInvoice")
    public ResponseVo querySellInvoice(@RequestBody(required = false) SellInvoiceQuery sellInvoiceQuery){
        PaginationResultVo<SellInvoice> resultVo = sellInvoiceService.querySellInvoice(sellInvoiceQuery);
        return ResponseVo.ok(resultVo);
    }

    /**
     * 导出
     */
    @RequestMapping("/exportSellInvoice")
    @ResponseBody
    public void exportSellInvoice(HttpServletResponse httpServletResponse){
        sellInvoiceService.exportSellInvoice(httpServletResponse);
    }

    //上传销售发票文件
    @PostMapping("/uploadSellInvoice/file")
    public ResponseVo uploadSellInvoice(@RequestBody MultipartFile file, Integer id){
        return ResponseVo.ok(sellInvoiceService.uploadSellInvoice(file,id));
    }

    //删除销售发票文件
    @DeleteMapping("/delSellInvoiceFile/file/{id}")
    public ResponseVo delSellInvoiceFile(@NotBlank(message = "销售发票id不能为空") @PathVariable("id") Integer id){
        return ResponseVo.ok(sellInvoiceService.delSellInvoiceFile(id));
    }

    //导入销售发票文件
    @PostMapping("/uploadSellInvoiceExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        sellInvoiceService.uploadSellInvoiceExcel(file);
        return ResponseVo.ok();
    }


}
