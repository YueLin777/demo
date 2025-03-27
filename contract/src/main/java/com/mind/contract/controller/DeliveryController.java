package com.mind.contract.controller;

import com.mind.contract.entity.pojo.Delivery;
import com.mind.contract.entity.query.DeliveryQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * ClassName：DeliveryController
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/contract")
public class DeliveryController {
    @Resource
    private DeliveryService deliveryService;

    //填写发货明细表
    @RequestMapping("/delivery")
    public ResponseVo delivery(@RequestBody Delivery delivery){
        ResponseVo responseVo = deliveryService.delivery(delivery);
        return ResponseVo.ok(responseVo);
    }

    //修改发货明细表
    @RequestMapping("/modifyDelivery")
    public ResponseVo modifyDelivery(@RequestBody Delivery delivery){
        if (null == delivery.getId()){
            throw new BusinessException("编号不能为空");
        }

        ResponseVo responseVo = deliveryService.modifyDelivery(delivery);
        return ResponseVo.ok(responseVo);
    }

    //删除发货明细表
    @RequestMapping("/delDelivery/{id}")
    public ResponseVo delDelivery(@PathVariable("id") String id){
        if (null == id){
            throw new BusinessException("编号不能为空");
        }
        Integer i = deliveryService.count(id);
        if(i == 0){
            throw new BusinessException("发票明细数据不存在");
        }
        return deliveryService.delDelivery(id);
    }

    //查询发货明细表
    @RequestMapping("/queryDelivery")
    public ResponseVo queryDelivery(@RequestBody(required = false) DeliveryQuery deliveryQuery){
        PaginationResultVo<Delivery> resultVo = deliveryService.queryDelivery(deliveryQuery);
        return ResponseVo.ok(resultVo);
    }

    //上传回单
    @PostMapping("/uploadDelivery/file")
    public ResponseVo uploadDelivery(@RequestBody MultipartFile file, Integer id){
        return ResponseVo.ok(deliveryService.uploadDelivery(file,id));
    }

    //删除回单
    @DeleteMapping("/deleteDelivery/file/{id}")
    public ResponseVo deleteDelivery(@NotBlank(message = "回单编号不能为空") @PathVariable("id") Integer id){
        return ResponseVo.ok(deliveryService.deleteDelivery(id));
    }

    //导出发货明细表
    @RequestMapping("/exportDelivery")
    @ResponseBody
    public void exportDelivery(HttpServletResponse httpServletResponse){
        deliveryService.exportDelivery(httpServletResponse);
    }

    //导入发货明细表
    @PostMapping("/uploadDeliveryExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        deliveryService.uploadDeliveryExcel(file);
        return ResponseVo.ok();
    }
}
