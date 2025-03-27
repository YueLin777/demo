package com.mind.contract.dao;

import com.mind.contract.entity.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public User queryUserByUsername(String username);

    List<String> getUserPermissions(String id);
}
