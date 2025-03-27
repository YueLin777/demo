package com.mind.contract.service.impl;

import com.alibaba.excel.EasyExcel;
import com.mind.contract.dao.DeliveryDao;
import com.mind.contract.entity.dto.DeliveryDto;
import com.mind.contract.entity.dto.MaterialDto;
import com.mind.contract.entity.enums.PageSize;
import com.mind.contract.entity.pojo.Delivery;
import com.mind.contract.entity.query.DeliveryQuery;
import com.mind.contract.entity.query.SimplePage;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import com.mind.contract.exception.BusinessException;
import com.mind.contract.service.DeliveryService;
import com.mind.contract.utils.EasyExcelConfig;
import com.mind.contract.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * ClassName：DeliveryServiceImpl
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {
    @Value("/opt/hd")
    private String hdPath;

    @Resource
    private DeliveryDao deliveryDao;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private EasyExcelConfig<DeliveryDto> easyExcelConfig;

    @Override
    public ResponseVo delivery(Delivery delivery) {
        int num = 0;
        num = deliveryDao.delivery(delivery);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public ResponseVo modifyDelivery(Delivery delivery) {
        int num;
        num = deliveryDao.modifyDelivery(delivery);
        //判断是否成功
        if (num > 0){
            return ResponseVo.ok();
        }
        return ResponseVo.fail();
    }

    @Override
    public Integer count(String id) {
        return deliveryDao.count(id);
    }

    @Override
    public ResponseVo delDelivery(String id) {
        Integer i = deliveryDao.delDelivery(id);
        if (i > 0){
            return ResponseVo.ok();
        }
        throw new BusinessException("删除失败");
    }

    @Override
    public PaginationResultVo<Delivery> queryDelivery(DeliveryQuery deliveryQuery) {
        //查询总记录数
        int count = findCount(deliveryQuery);
        //每页显示数量
        int pageSize = deliveryQuery.getPageSize() == null ? PageSize.SIZE20.getSize() : deliveryQuery.getPageSize();
        SimplePage simplePage = new SimplePage(deliveryQuery.getPageNo(),count,pageSize);
        deliveryQuery.setSimplePage(simplePage);
        List<Delivery> deliveries = findList(deliveryQuery);
        PaginationResultVo<Delivery> resultVo = new PaginationResultVo<>(count,simplePage.getPageSize(),simplePage.getPageNo(),simplePage.getPageTotal(),deliveries);
        return resultVo;
    }

    @Override
    public ResponseVo uploadDelivery(MultipartFile file, Integer id) {
        Delivery delivery = deliveryDao.selectId(id);
        if (delivery == null){
            throw new RuntimeException("id不能为空");
        }

        String originalFilename = file.getOriginalFilename();

        String fileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));

        fileUtil.saveFile(hdPath,file,fileName);
        delivery.setHas_file(1);
        deliveryDao.modifyDelivery(delivery);

        return ResponseVo.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo deleteDelivery(Integer id) {
        Delivery delivery = deliveryDao.selectId(id);

        if (delivery == null){
            throw new RuntimeException("id不存在");
        }

        fileUtil.deleteFile(hdPath+"\\"+id+".pdf");
        delivery.setHas_file(0);
        deliveryDao.modifyDelivery(delivery);
        return ResponseVo.ok();
    }

    @Override
    public void exportDelivery(HttpServletResponse httpServletResponse) {
        try {
            List<DeliveryDto> list = deliveryExportList();
            //设置响应头
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //文件名
            String name = String.format("%s%s.xlsx", "发货明细表", LocalDate.now());
            String fileName = URLEncoder.encode(name, "UTF-8");
            //设置中文防止乱码
            httpServletResponse.setHeader("Content-disposition", String.format("attachment;filename=\"%s\";filename*=utf-8''%s", fileName, fileName));
            EasyExcel.write(httpServletResponse.getOutputStream(), DeliveryDto.class)
                    .sheet("发货明细表").doWrite(list);
        }catch (Exception e){
            log.info("错误信息{}",e.getMessage(),e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadDeliveryExcel(MultipartFile file) {
        easyExcelConfig.init();
        try {
            EasyExcel.read(file.getInputStream(),DeliveryDto.class,easyExcelConfig)
                    .sheet()
                    .doRead();
            List<DeliveryDto> data = easyExcelConfig.getData();
            int result = deliveryDao.uploadDeliveryExcel(data);
            return result;
        }catch (Exception e) {
            throw new BusinessException("数据异常");
        }
    }

    private List<DeliveryDto> deliveryExportList() {
        List<Delivery> list = deliveryDao.selectAllDelivery();
        ArrayList<DeliveryDto> resultList = new ArrayList<>();
        for (Delivery delivery : list){
            DeliveryDto deliveryDto = new DeliveryDto();
            BeanUtils.copyProperties(delivery,deliveryDto);
            resultList.add(deliveryDto);
        }
        return resultList;
    }

    private int findCount(DeliveryQuery deliveryQuery) {
        return deliveryDao.findCount(deliveryQuery);
    }

    private List<Delivery> findList(DeliveryQuery deliveryQuery) {
        return deliveryDao.findList(deliveryQuery);
    }
}
