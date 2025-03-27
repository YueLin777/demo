package com.mind.contract.controller;

import com.mind.contract.entity.pojo.Acceptance;
import com.mind.contract.entity.query.AcceptanceQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.AcceptanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * ClassName：AcceptanceController
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/contract")
public class AcceptanceController {
    @Resource
    private AcceptanceService acceptanceService;

    //填写销售合格证验收单明细表
    @RequestMapping("/acceptance")
    public ResponseVo purchase(@RequestBody Acceptance acceptance){
        ResponseVo responseVo = acceptanceService.acceptance(acceptance);
        return ResponseVo.ok(responseVo);
    }

    //修改销售合格证验收单明细表
    @RequestMapping("/modifyAcceptance")
    public ResponseVo modifyAcceptance(@RequestBody Acceptance acceptance){
        if (null == acceptance.getId()){
            throw new BusinessException("编号不能为空");
        }

        ResponseVo responseVo = acceptanceService.modifyAcceptance(acceptance);
        return ResponseVo.ok(responseVo);
    }

    //删除销售合格证验收单明细表
    @RequestMapping("/delAcceptance/{id}")
    public ResponseVo delAcceptance(@PathVariable("id") Integer id){
        if (null == id){
            throw new BusinessException("编号不能为空");
        }
        Integer i = acceptanceService.getAcceptance(id);
        if(i == 0){
            throw new BusinessException("发票明细数据不存在");
        }
        return acceptanceService.delAcceptance(id);
    }

    //查询销售合格证验收单明细表
    @RequestMapping("/queryAcceptance")
    public ResponseVo queryAcceptance(@RequestBody(required = false) AcceptanceQuery acceptanceQuery){
        PaginationResultVo<Acceptance> resultVo = acceptanceService.queryAcceptance(acceptanceQuery);
        return ResponseVo.ok(resultVo);
    }

    //上传文档
    @PostMapping("/uploadAcceptance/file")
    public ResponseVo uploadAcceptance(@RequestBody MultipartFile file,Integer id){
        return ResponseVo.ok(acceptanceService.uploadAcceptance(file,id));
    }

    //删除文档
    @DeleteMapping("/deleteAcceptance/file/{id}")
    public ResponseVo deleteAcceptance(@NotBlank(message = "id不能为空") @PathVariable Integer id){
        return ResponseVo.ok(acceptanceService.deleteAcceptance(id));
    }

    //导出销售合格证验收单明细表
    @RequestMapping("/exportAcceptance")
    @ResponseBody
    public void exportAcceptance(HttpServletResponse httpServletResponse){
        acceptanceService.exportAcceptance(httpServletResponse);
    }

    //导入销售合格证验收单明细表
    @PostMapping("/uploadAcceptanceExcel")
    public ResponseVo read(MultipartFile file) throws IOException {
        acceptanceService.uploadAcceptanceExcel(file);
        return ResponseVo.ok();
    }
}
