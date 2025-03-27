package com.mind.contract.controller;

import com.mind.contract.entity.pojo.ItemSum;
import com.mind.contract.entity.pojo.Material;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.query.MaterialQuery;
import com.mind.contract.entity.query.SellQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.InvoiceService;
import com.mind.contract.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * ClassName：MaterialController
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/contract")
public class MaterialController {
    @Resource
    private MaterialService materialService;

    //填写物料明细表
    @RequestMapping("/material")
    public ResponseVo material(@RequestBody Material material){
        ResponseVo responseVo = materialService.material(material);
        return ResponseVo.ok(responseVo);
    }

    //修改物料明细表
    @RequestMapping("/modifyMaterial")
    public ResponseVo modifyMaterial(@RequestBody Material material){
        ResponseVo responseVo = materialService.modifyMaterial(material);
        return ResponseVo.ok(responseVo);
    }

    //删除物料明细表
    @RequestMapping("/delMaterial/{id}")
    public ResponseVo delMaterial(@PathVariable Integer id){
        Integer i = materialService.count(id);
        if(i == 0){
            throw new BusinessException("物料明细表不存在");
        }
        return materialService.delMaterial(id);
    }

    //查询物料明细表
    @RequestMapping("/queryMaterial")
    public ResponseVo queryMaterial(@RequestBody(required = false) MaterialQuery materialQuery){
        PaginationResultVo<Material> resultVo = materialService.queryMaterial(materialQuery);
        return ResponseVo.ok(resultVo);
    }

    //导出物料明细表
    @RequestMapping("/exportMaterial")
    @ResponseBody
    public void exportMaterial(HttpServletResponse httpServletResponse){
        materialService.exportMaterial(httpServletResponse);
    }

    //对应项目求和
    @RequestMapping("/itemSum")
    public ResponseVo itemSum(@RequestBody ItemSum itemSum){
        List<ItemSum> itemSums = Collections.singletonList(materialService.itemSum(itemSum));
        return ResponseVo.ok(itemSums);
    }

    @PostMapping("/uploadMaterialExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        materialService.uploadMaterialExcel(file);
        return ResponseVo.ok();
    }

}
