package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.AcceptanceDao;
import com.mind.contract.entity.dto.AcceptanceDto;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.dto.DeliveryDto;
import com.mind.contract.entity.dto.MaterialDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.Acceptance;
import com.mind.contract.entity.query.AcceptanceQuery;
import com.mind.contract.entity.query.SimplePage;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.AcceptanceService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName：AcceptanceServiceImpl
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@Service
@Slf4j
public class AcceptanceServiceImpl implements AcceptanceService {
    @Value("/opt/wd")
    private String wdOPath;
    @Resource
    private AcceptanceDao acceptanceDao;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private EasyExcelConfig<AcceptanceDto> easyExcelConfig;


    @Override
    public ResponseVo acceptance(Acceptance acceptance) {
        int num = 0;
        num = acceptanceDao.acceptance(acceptance);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modifyAcceptance(Acceptance acceptance) {
        int num;
        num = acceptanceDao.modifyAcceptance(acceptance);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer getAcceptance(Integer id) {
        return acceptanceDao.count(id);
    }

    @Override
    public ResponseVo delAcceptance(Integer id) {
        Integer i = acceptanceDao.delAcceptance(id);
        if (i > 0){
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<Acceptance> queryAcceptance(AcceptanceQuery acceptanceQuery) {
        //查询总记录数
        int count = findCount(acceptanceQuery);
        //每页显示数量
        int pageSize = acceptanceQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : acceptanceQuery.getPageSize();

        SimplePage simplePage = new SimplePage(acceptanceQuery.getPageNo(), count, pageSize);
        acceptanceQuery.setSimplePage(simplePage);
        List<Acceptance> acceptances = findList(acceptanceQuery);
        PaginationResultVo<Acceptance> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),acceptances);
        return resultVo;
    }

    @Override
    public ResponseVo uploadAcceptance(MultipartFile file, Integer id) {
        Acceptance acceptance = acceptanceDao.selectId(id);

        if (acceptance == null){
            throw new RuntimeException("id不存在");
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));

       fileUtil.saveFile(wdOPath,file,fileName);
       acceptance.setHas_file(1);
       acceptanceDao.modifyAcceptance(acceptance);
       return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo deleteAcceptance(Integer id) {
        Acceptance acceptance = acceptanceDao.selectId(id);

        if (acceptance == null){
            throw new RuntimeException("id不存在");
        }

        fileUtil.deleteFile(wdOPath+"\\"+id+".pdf");

        acceptance.setHas_file(0);
        acceptanceDao.modifyAcceptance(acceptance);
        return ResponseVo.ok();
    }

    @Override
    public void exportAcceptance(HttpServletResponse httpServletResponse) {
        try {
            List<AcceptanceDto> list = acceptanceExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "销售合格证验收单明细表", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), AcceptanceDto.class)
                    .sheet("销售合格证验收单明细表").doWrite(list);
        } catch (Exception e){
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadAcceptanceExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),AcceptanceDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<AcceptanceDto> data = easyExcelConfig.getData();
            int result = acceptanceDao.insertAcceptanceList(data);
            return result;
        }catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }

    private List<AcceptanceDto> acceptanceExportList() {
        List<Acceptance> list = acceptanceDao.selectAllAcceptance();
        ArrayList<AcceptanceDto> resultList = new ArrayList<>();
        for (Acceptance acceptance : list){
            AcceptanceDto acceptanceDto = new AcceptanceDto();
            BeanUtils.copyProperties(acceptance,acceptanceDto);
            resultList.add(acceptanceDto);
        }
        return resultList;
    }

    private int findCount(AcceptanceQuery acceptanceQuery) {
        return acceptanceDao.findCount(acceptanceQuery);
    }

    private List<Acceptance> findList(AcceptanceQuery acceptanceQuery) {
        return acceptanceDao.findList(acceptanceQuery);
    }

}
