package dao;

import entity.ResultMessage;

public interface UserDao {

   ResultMessage login(int account, int password);

}
