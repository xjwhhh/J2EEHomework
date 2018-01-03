package service;

import entity.Order;

import java.util.ArrayList;

public interface OrderManageService {

    public ArrayList<Order> getOrderList(int userId);

}
