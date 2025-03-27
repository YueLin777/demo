package com.mind.contract.dao;

import com.mind.contract.entity.dto.SellDto;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.Statement;
import com.mind.contract.entity.query.SellQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName：SellDao
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
@Repository
public interface SellDao {
    Integer count(Integer id);

    Integer sell(Sell sell);

    Integer repeat(String contractId);

    String selectOld(Integer id);

    Integer calculateCollection(Sell sell);

    Integer calculateNoCollection(Sell sell);

    Integer calculateInvoiced(Sell sell);

    Integer calculateNoInvoiced(Sell sell);

    Integer modifySell(Sell sell);

    Integer delSell(Integer id);

    Integer findCountById(@Param("query") SellQuery sellQuery);

    List<Sell> findListSell(@Param("query") SellQuery sellQuery);

    List<SellDto> sellExportList();

    Sell selectSid(Integer id);

    Integer updateSell(Sell sell);

    void totalSum();

    Double getSumTicket(String contractId);

    int insertSellList(List<SellDto> data);
}
