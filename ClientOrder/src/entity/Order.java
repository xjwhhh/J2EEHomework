package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int id;
    private String orderTime;
    private ArrayList<OrderRecord> records;

    public ArrayList<OrderRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<OrderRecord> records) {
        this.records = records;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
