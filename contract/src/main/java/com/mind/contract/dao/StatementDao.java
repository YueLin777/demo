package com.mind.contract.dao;

import com.mind.contract.entity.dto.StatementDto;
import com.mind.contract.entity.pojo.Statement;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * 数据库接口层
 * */
@Repository
public interface StatementDao {
    List<Statement> selectAllStatement();

    int insertStatementList(List<StatementDto> date);
}
