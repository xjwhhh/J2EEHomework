package service.impl;

import dao.OrderDao;
import entity.Order;
import factory.DaoFactory;
import service.OrderManageService;

import javax.ejb.Stateless;
import java.util.ArrayList;

@Stateless
public class OrderManageServiceImpl implements OrderManageService {

    private static OrderManageService orderService = new OrderManageServiceImpl();

//    public static OrderManageService getInstance() {
//        return orderService;
//    }

    @Override
    public ArrayList<Order> getOrderList(int userId) {
        OrderDao orderDao = DaoFactory.getOrderDao();
        return orderDao.getOrderListByUserId(userId);
    }
}
