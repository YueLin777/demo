package com.mind.contract.service;

import com.mind.contract.entity.pojo.Statement;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：StatementService
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
public interface StatementService {
    void exportStatement(HttpServletResponse httpServletResponse);

    int uploadStatementExcel(MultipartFile file);
}
