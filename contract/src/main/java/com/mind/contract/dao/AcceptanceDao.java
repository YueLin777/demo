package com.mind.contract.dao;

import com.mind.contract.entity.dto.AcceptanceDto;
import com.mind.contract.entity.pojo.Acceptance;
import com.mind.contract.entity.query.AcceptanceQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName：AcceptanceDao
 *
 * @author:l
 * @Date: 2024/8/22
 * @Description:
 * @version: 1.0
 */
@Repository
public interface AcceptanceDao {
    Integer acceptance(Acceptance acceptance);

    Integer modifyAcceptance(Acceptance acceptance);

    Integer count(Integer id);

    Integer delAcceptance(Integer id);

    Integer findCount(@Param("query") AcceptanceQuery acceptanceQuery);

    List<Acceptance> findList(@Param("query") AcceptanceQuery acceptanceQuery);

    Acceptance selectId(Integer id);

    List<Acceptance> selectAllAcceptance();

    int insertAcceptanceList(List<AcceptanceDto> data);
}
