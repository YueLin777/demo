package com.mind.contract.dao;

import com.mind.contract.entity.dto.MaterialDto;
import com.mind.contract.entity.pojo.ItemSum;
import com.mind.contract.entity.pojo.Material;
import com.mind.contract.entity.query.MaterialQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ClassName：MaterialDao
 *
 * @author:l
 * @Date: 2024/8/7
 * @Description:
 * @version: 1.0
 */
@Repository
public interface MaterialDao {
    Integer material(Material material);

    Integer modifyMaterial(Material material);

    Integer count(Integer id);

    Integer delMaterial(Integer id);

    Integer findCountById(@Param("query") MaterialQuery materialQuery);

    List<Material> findListMaterial(@Param("query") MaterialQuery materialQuery);

    List<MaterialDto> materialExportList();

    Map<String, ItemSum> itemSum(String item);

    Integer calculateNotAmount(Material material);

    Integer calculateAmount(Material material);

    int insertMaterialList(List<MaterialDto> data);
}
