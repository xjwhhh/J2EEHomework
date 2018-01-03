package factory;

import dao.OrderDao;
import dao.UserDao;
import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;

public class DaoFactory {

    public static OrderDao getOrderDao() {
        return OrderDaoImpl.getInstance();
    }

    public static UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

}
