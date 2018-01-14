package dao.impl;

import dao.BaseDao;
import dao.BaseDaoImpl;
import dao.DaoHelper;
import dao.UserDao;
import entity.ResultMessage;
import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();
//    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public int login(String account, String password) {
//        Connection con = daoHelper.getConnection();
//        PreparedStatement stmt = null;
//        ResultSet result = null;
//        int userId = -1;
//        try {
//            stmt = con.prepareStatement("select id from user where account = ? and password=?");
//            stmt.setString(1, account);
//            stmt.setString(2, password);
//            result = stmt.executeQuery();
//            while (result.next()) {
//                userId = result.getInt("id");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return userId;
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List userIdList=new ArrayList();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("SELECT U.id FROM User as U where U.account=:account and U.password=:password");
            query.setParameter("account",account);
            query.setParameter("password",password);
            userIdList=query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("hibernate........................................");
        System.out.println(userIdList.get(0));
        return Integer.parseInt(String.valueOf(userIdList.get(0)));

    }


}
