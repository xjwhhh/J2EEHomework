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
    public ResultMessage login(int account, int password) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            stmt = con.prepareStatement("select id from user where account = ? and password=?");
            stmt.setString(1, String.valueOf(account));
            stmt.setString(2, String.valueOf(password));
            result = stmt.executeQuery();
            while (result.next()) {
                return ResultMessage.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResultMessage.FAIL;
    }
}
