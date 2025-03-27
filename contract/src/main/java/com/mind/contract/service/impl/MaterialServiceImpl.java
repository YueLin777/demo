package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.mind.contract.dao.MaterialDao;
import com.mind.contract.entity.dto.ContractDto;
import com.mind.contract.entity.dto.MaterialDto;
import com.mind.contract.entity.dto.SellDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.ItemSum;
import com.mind.contract.entity.pojo.Material;
import com.mind.contract.entity.pojo.Sell;
import com.mind.contract.entity.query.MaterialQuery;
import com.mind.contract.entity.query.SimplePage;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.MaterialService;
import com.mind.contract.utils.EasyExcelConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * ClassName：MaterialServiceImpl
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
/* 业务接口实现*/
@Service
@Slf4j
public class MaterialServiceImpl implements MaterialService {
    @Resource MaterialDao materialDao;

    @Autowired
    private EasyExcelConfig<MaterialDto> easyExcelConfig;

    @Override
    public ResponseVo material(Material material) {
        //计算不含税金额
        material.setTaxNotAmount(material.getMoney()/(1+material.getTaxRate()*0.01));
        //计算税额
        material.setTaxAmount(material.getMoney()-material.getTaxNotAmount());
        int num = 0;
            num = materialDao.material(material);
//        //计算不含税金额
//        materialDao.calculateNotAmount(material);
//        //计算税额
//        materialDao.calculateAmount(material);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modifyMaterial(Material material) {
        int num = 0;
            num = materialDao.modifyMaterial(material);
        //计算不含税金额
        materialDao.calculateNotAmount(material);
        //计算税额
        materialDao.calculateAmount(material);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer count(Integer id) {
         return materialDao.count(id);
    }

    @Override
    public ResponseVo delMaterial(Integer id) {
        Integer i = materialDao.delMaterial(id);
        if (i > 0) {
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<Material> queryMaterial(MaterialQuery materialQuery) {
        //查询总记录数
        int count = findCountById(materialQuery);
        //每页显示数量
        int pageSize = materialQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : materialQuery.getPageSize();

        SimplePage simplePage = new SimplePage(materialQuery.getPageNo(),count,pageSize);
        materialQuery.setSimplePage(simplePage);

        List<Material> list = findListMaterial(materialQuery);

        PaginationResultVo<Material> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),list);
        return resultVo;
    }

    @Override
    public void exportMaterial(HttpServletResponse httpServletResponse) {
        try {
            List<MaterialDto> list = materialExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "物料明细表", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), MaterialDto.class)
                    .sheet("物料明细表").doWrite(list);
        } catch (Exception e) {
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    public ItemSum itemSum(ItemSum itemSum) {
        Map<String , ItemSum> map = materialDao.itemSum(itemSum.getItem());
        ItemSum sum = new ItemSum();
        sum.setItem(String.valueOf(map.get("item")));
        sum.setTotalAmount(Double.parseDouble(String.valueOf(map.get("totalAmount"))));
        sum.setTotalDisburse(Double.parseDouble(String.valueOf(map.get("totalDisburse"))));
        return sum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadMaterialExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),MaterialDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<MaterialDto> data = easyExcelConfig.getData();
            String[] ids = new String[data.size()];
            for (MaterialDto datum : data) {
                datum.setTaxNotAmount(datum.getMoney() / (1 + datum.getTaxRate()*0.01));
                datum.setTaxAmount(datum.getMoney() - datum.getTaxNotAmount());
            }
            int result = materialDao.insertMaterialList(data);
            return result;
        }catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }

    private List<MaterialDto> materialExportList() {
        return materialDao.materialExportList();
    }

    private int findCountById(MaterialQuery materialQuery) {
        return materialDao.findCountById(materialQuery);
    }

    private List<Material> findListMaterial(MaterialQuery materialQuery) {
        return materialDao.findListMaterial(materialQuery);
    }
}
