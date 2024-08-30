package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.dao.UserDao;


import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml")) {

            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            SqlSession sqlSession = sessionFactory.openSession();
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> users = userDao.selectAllUser();
            for (User user : users) {
                System.out.println(user);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}