package dao;

import entity.Order;

import java.util.ArrayList;

public interface OrderDao extends BaseDao{

    ArrayList<Order> getOrderListByUserId(int userId);

    void saveOrder(Order order);

}
