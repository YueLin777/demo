package com.mind.contract.controller;

import com.mind.contract.entity.pojo.Contract;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.pojo.TotalSum;
import com.mind.contract.entity.query.ContractQuery;
import com.mind.contract.entity.query.SellQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.SellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/contract")
public class SellController {
    @Resource
    private SellService sellService;

    //填写销售合同
    @RequestMapping("/sell")
    public ResponseVo sell(@RequestBody Sell sell, SellInvoice sellInvoice){
        ResponseVo responseVo = sellService.sell(sell,sellInvoice);
        return ResponseVo.ok(responseVo);
    }

    //修改销售合同
    @RequestMapping("/modifySell")
    public ResponseVo modifySell(@RequestBody Sell sell, SellInvoice sellInvoice){
        ResponseVo responseVo = sellService.modifySell(sell,sellInvoice);
        return ResponseVo.ok(responseVo);
    }

    //删除销售合同
    @RequestMapping("/delSell/{id}")
    public ResponseVo delSell(@PathVariable Integer id){
        Integer i = sellService.count(id);
        if(i == 0){
            throw new BusinessException("销售合同不存在");
        }
        return sellService.delSell(id);
    }

    //查询销售合同
    @RequestMapping("/querySell")
    public ResponseVo querySell(@RequestBody(required = false) SellQuery sellQuery){
        PaginationResultVo<Sell> resultVo = sellService.querySell(sellQuery);
        return ResponseVo.ok(resultVo);
    }

    /**
     * 导出
     */
    @RequestMapping("/exportSell")
    @ResponseBody
    public void exportSell(HttpServletResponse httpServletResponse){
        sellService.exportSell(httpServletResponse);
    }

    //上传销售合同文件
    @PostMapping("/uploadSellFile/file")
    public ResponseVo uploadStatement(@RequestBody MultipartFile file, Integer id){
        return ResponseVo.ok(sellService.uploadSell(file,id));
    }

    //删除销售合同文件
    @DeleteMapping("/delSellFile/file/{id}")
    public ResponseVo delStatement(@NotBlank(message = "销售合同id不能为空") @PathVariable("id") Integer id){
        return ResponseVo.ok(sellService.delSellFile(id));
    }

    //各项总金额统计
    @RequestMapping("totalSum")
    public ResponseVo totalSum(@RequestBody TotalSum totalSum){
        List<TotalSum> totalSums = Collections.singletonList(sellService.totalSum(totalSum));
        return ResponseVo.ok();
    }

    //导入销售合同文件
    @PostMapping("/uploadSellExcel")
    private ResponseVo read(MultipartFile file) throws IOException {
        sellService.uploadSellExcel(file);
        return ResponseVo.ok();
    }

}
