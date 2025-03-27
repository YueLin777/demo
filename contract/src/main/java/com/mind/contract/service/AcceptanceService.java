package com.mind.contract.service;

import com.mind.contract.entity.pojo.Acceptance;
import com.mind.contract.entity.query.AcceptanceQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：AcceptanceService
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
public interface AcceptanceService {
    ResponseVo acceptance(Acceptance acceptance);

    ResponseVo modifyAcceptance(Acceptance acceptance);

    Integer getAcceptance(Integer id);

    ResponseVo delAcceptance(Integer id);

    PaginationResultVo<Acceptance> queryAcceptance(AcceptanceQuery acceptanceQuery);

    ResponseVo uploadAcceptance(MultipartFile file, Integer id);

    ResponseVo deleteAcceptance(Integer id);

    void exportAcceptance(HttpServletResponse httpServletResponse);

    int uploadAcceptanceExcel(MultipartFile file);
}
