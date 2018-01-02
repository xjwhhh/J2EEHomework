package dao.impl;

import dao.DaoHelper;
import dao.UserDao;
import entity.ResultMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return userDao;
    }



    @Override
    public int  login(String account, String password) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        int userId=-1;
        try {
            stmt = con.prepareStatement("select id from user where account = ? and password=?");
            stmt.setString(1, account);
            stmt.setString(2, password);
            result = stmt.executeQuery();
            while (result.next()) {
               userId=result.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
