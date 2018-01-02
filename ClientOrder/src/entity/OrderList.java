package entity;

import java.util.ArrayList;

public class OrderList {

    private ArrayList<Order> orderList;

    public OrderList(ArrayList<Order> orderList){
        this.orderList=orderList;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }


    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }


    public void setOrderList(Order order, int index) {
        orderList.set(index, order);
    }

    public Order getOrder(int index) {
        return orderList.get(index);
    }

}
