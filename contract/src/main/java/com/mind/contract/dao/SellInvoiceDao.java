package com.mind.contract.dao;

import com.mind.contract.entity.dto.SellInvoiceDto;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.query.SellInvoiceQuery;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName：SellInvoiceDao
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
@Repository
public interface SellInvoiceDao {
    Integer count(Integer id);

    Integer repeat(String contractId);

    Integer sellInvoice(SellInvoice sellInvoice);

    Integer calculateInvoiced(Sell sell);

    Integer calculateNoInvoiced(Sell sell);

    Integer calculateAmount(SellInvoice sellInvoice);

    Integer calculateNoAmount(SellInvoice sellInvoice);

    Integer modifySellInvoice(SellInvoice sellInvoice);

    SellInvoice querySellInvoice(Integer id);

    SellInvoice querySellInvoiceByCid(String contractId);

    Sell querySell(String sellId);

    Integer updateSell(Sell sell);

    Integer delSellInvoice(Integer id);

    Integer findCountById(@Param("query") SellInvoiceQuery sellInvoiceQuery);

    List<SellInvoice> findListById(@Param("query") SellInvoiceQuery sellInvoiceQuery);

    String selectOld(Integer id);

    List<SellInvoiceDto> sellInvoiceExportList();

    SellInvoice selectSid(Integer id);

    Integer updateSellInvoice(SellInvoice sellInvoice);

    int insertSellInvoiceList(List<SellInvoiceDto> data);

    Integer invoiced();

    Integer updateTicketAmountReceived(String[] ids);
}
