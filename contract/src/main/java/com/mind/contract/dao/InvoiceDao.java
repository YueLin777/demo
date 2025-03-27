package com.mind.contract.dao;

import com.mind.contract.entity.dto.InvoiceDto;
import com.mind.contract.entity.pojo.Invoice;
import com.mind.contract.entity.pojo.TimeSum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
 * 数据库接口层
 * */
@Repository
public interface InvoiceDao {
    List<Invoice> selectAllInvoice();

    List<Map<String, Object>> timeSum(String year, String month);

    int insertInvoiceList(List<InvoiceDto> data);
}
