package entity;

import java.util.List;

public class Order {
    private int id;
    private String orderTime;
    private int userId;
    private List<OrderRecord> records;

    public Order() {
    }

    public List<OrderRecord> getRecords() {
        return records;
    }

    public void setRecords(List<OrderRecord> records) {
        this.records = records;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
