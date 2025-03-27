package com.mind.contract.service;

import com.mind.contract.entity.pojo.Delivery;
import com.mind.contract.entity.query.DeliveryQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：DeliveryService
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
public interface DeliveryService {
    ResponseVo delivery(Delivery delivery);

    ResponseVo modifyDelivery(Delivery delivery);

    Integer count(String id);

    ResponseVo delDelivery(String id);

    PaginationResultVo<Delivery> queryDelivery(DeliveryQuery deliveryQuery);

    ResponseVo uploadDelivery(MultipartFile file, Integer id);

    ResponseVo deleteDelivery(Integer id);

    void exportDelivery(HttpServletResponse httpServletResponse);

    int uploadDeliveryExcel(MultipartFile file);
}
