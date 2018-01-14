package dao;

import entity.ResultMessage;
import entity.User;

public interface UserDao extends BaseDao {

    int login(String account, String password);
//
//    public void save(User user);
//
//    public void delete(User user);
//
//    public User find(String column,String value);
//
//
//    public void updateByUserid(User user);
//
//
//    public User FindUserById(String id);

}
