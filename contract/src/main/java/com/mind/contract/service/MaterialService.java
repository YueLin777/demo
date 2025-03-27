package com.mind.contract.service;

import com.mind.contract.entity.pojo.ItemSum;
import com.mind.contract.entity.pojo.Material;
import com.mind.contract.entity.query.MaterialQuery;
import com.mind.contract.entity.vo.PaginationResultVo;
import com.mind.contract.entity.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * ClassName：MaterialService
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
public interface MaterialService {
    ResponseVo material(Material material);

    ResponseVo modifyMaterial(Material material);

    Integer count(Integer id);

    ResponseVo delMaterial(Integer id);

    PaginationResultVo<Material> queryMaterial(MaterialQuery materialQuery);

    void exportMaterial(HttpServletResponse httpServletResponse);

    ItemSum itemSum(ItemSum itemSum);

    int uploadMaterialExcel(MultipartFile file);
}
