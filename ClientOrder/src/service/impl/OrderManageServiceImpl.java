package service.impl;

import dao.OrderDao;
import entity.Order;
import factory.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderManageService;

import java.util.ArrayList;

@Service
public class OrderManageServiceImpl implements OrderManageService {

    @Autowired
    private OrderDao orderDao;

    private static OrderManageService orderService = new OrderManageServiceImpl();

    public static OrderManageService getInstance() {
        return orderService;
    }

    @Override
    public ArrayList<Order> getOrderList(int userId) {
//        OrderDao orderDao = DaoFactory.getOrderDao();
        return orderDao.getOrderListByUserId(userId);
    }
}
