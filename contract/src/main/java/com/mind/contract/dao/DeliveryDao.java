package com.mind.contract.dao;

import com.mind.contract.entity.dto.DeliveryDto;
import com.mind.contract.entity.pojo.Delivery;
import com.mind.contract.entity.query.DeliveryQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName：DeliveryDao
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@Repository
public interface DeliveryDao {
    Integer delivery(Delivery delivery);

    Integer modifyDelivery(Delivery delivery);

    Integer count(String id);

    Integer delDelivery(String id);

    Integer findCount(@Param("query") DeliveryQuery deliveryQuery);

    List<Delivery> findList(@Param("query") DeliveryQuery deliveryQuery);

    Delivery selectId(Integer id);

    List<Delivery> selectAllDelivery();

    int uploadDeliveryExcel(List<DeliveryDto> data);
}
