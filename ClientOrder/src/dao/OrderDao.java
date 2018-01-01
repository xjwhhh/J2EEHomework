package dao;

import entity.Order;

import java.util.ArrayList;

public interface OrderDao {

    public ArrayList<Order> getOrderListByUserId(int userId);

    public void saveOrder(Order order);



}
