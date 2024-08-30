package org.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.User;

import java.util.List;

public interface UserDao {
    List<User> selectAllUser();
}
