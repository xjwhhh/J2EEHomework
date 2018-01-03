package dao;

import entity.ResultMessage;

public interface UserDao {

    int login(String account, String password);

}
