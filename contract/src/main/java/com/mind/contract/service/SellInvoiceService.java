package com.mind.contract.service;

import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.query.SellInvoiceQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：SellInvoiceService
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
public interface SellInvoiceService {
    ResponseVo sellInvoice(SellInvoice sellInvoice, Sell sell);

    ResponseVo modifySellInvoice(SellInvoice sellInvoice, Sell sell);

    Integer count(Integer id);

    ResponseVo delSellInvoice(Integer id);

    PaginationResultVo<SellInvoice> querySellInvoice(SellInvoiceQuery sellInvoiceQuery);

    void exportSellInvoice(HttpServletResponse httpServletResponse);

    ResponseVo uploadSellInvoice(MultipartFile file, Integer id);

    ResponseVo delSellInvoiceFile(Integer id);

    int uploadSellInvoiceExcel(MultipartFile file);
}
