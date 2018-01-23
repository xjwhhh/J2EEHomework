package service.impl;

import dao.UserDao;
import entity.ResultMessage;
import factory.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserManageService;

@Service
public class UserManageServiceImpl implements UserManageService {

    @Autowired
    private UserDao userDao;

    private static UserManageService userService = new UserManageServiceImpl();

    public static UserManageService getInstance() {
        return userService;
    }

    @Override
    public int login(String account, String password) {
//        UserDao userDao=DaoFactory.getUserDao();
        return userDao.login(account, password);
    }
}
