package com.mind.contract.service;

import com.mind.contract.entity.pojo.TimeSum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName：InvoiceService
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
public interface InvoiceService {
    void exportInvoice(HttpServletResponse httpServletResponse);

    List<TimeSum> timeSum(TimeSum timeSum);

    int uploadInvoiceExcel(MultipartFile file);
}
