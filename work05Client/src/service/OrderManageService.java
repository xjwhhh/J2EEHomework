package service;

import entity.Order;

import javax.ejb.Remote;
import java.util.ArrayList;

@Remote
public interface OrderManageService {

    public ArrayList<Order> getOrderList(int userId);

}
