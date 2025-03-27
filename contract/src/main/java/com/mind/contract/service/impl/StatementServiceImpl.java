package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.StatementDao;
import com.mind.contract.entity.dto.MaterialDto;
import com.mind.contract.entity.dto.StatementDto;
import com.mind.contract.entity.pojo.Statement;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.StatementService;
import com.mind.contract.utils.EasyExcelConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName：StatementServiceImpl
 *
 * @author:l
 * @Date: 2024/7/30
 * @Description:
 * @version: 1.0
 */
/* 业务接口实现*/
@Service
@Slf4j
public class StatementServiceImpl implements StatementService {
    @Resource
    private StatementDao statementDao;

    @Autowired
    private EasyExcelConfig<StatementDto> easyExcelConfig;

    @Override
    public void exportStatement(HttpServletResponse httpServletResponse) {
        try {
            List<StatementDto> statementDtos = statementExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "收入支出报表", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), StatementDto.class)
                    .sheet("收入支出报表").doWrite(statementDtos);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadStatementExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),StatementDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<StatementDto> date = easyExcelConfig.getData();
            String[] ids = new String[date.size()];
            for (StatementDto datum : date){
                datum.setBalance(datum.getIncome() - datum.getDisburse());
            }
            int result = statementDao.insertStatementList(date);
            return result;
        }catch (Exception e){
            throw new BusinessException("数据异常");
        }
    }

    private List<StatementDto> statementExportList() {
        List<Statement> list = statementDao.selectAllStatement();
        List<StatementDto> resultList=new ArrayList<>();
        for (Statement statement : list) {
            StatementDto statementDto=new StatementDto();
            BeanUtils.copyProperties(statement,statementDto);
            resultList.add(statementDto);
        }
        return resultList;
    }
}
