package service.impl;

import dao.UserDao;
import factory.DaoFactory;
import service.UserManageService;

public class UserManageServiceImpl implements UserManageService {

    private static UserManageService userService = new UserManageServiceImpl();

    public static UserManageService getInstance() {
        return userService;
    }

    @Override
    public int login(String account, String password) {
        UserDao userDao = DaoFactory.getUserDao();
        return userDao.login(account, password);
    }
}
