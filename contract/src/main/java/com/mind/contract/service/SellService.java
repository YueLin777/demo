package com.mind.contract.service;

import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.pojo.SellInvoice;
import com.mind.contract.entity.pojo.TotalSum;
import com.mind.contract.entity.query.SellQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName：SellService
 *
 * @author:l
 * @Date: 2024/8/1
 * @Description:
 * @version: 1.0
 */
public interface SellService {
    ResponseVo sell(Sell sell, SellInvoice sellInvoice);

    ResponseVo modifySell(Sell sell, SellInvoice sellInvoice);

    Integer count(Integer id);

    ResponseVo delSell(Integer id);

    PaginationResultVo<Sell> querySell(SellQuery sellQuery);

    void exportSell(HttpServletResponse httpServletResponse);

    ResponseVo uploadSell(MultipartFile file, Integer id);

    ResponseVo delSellFile(Integer id);

    TotalSum totalSum(TotalSum totalSum);

    int uploadSellExcel(MultipartFile file);
}
