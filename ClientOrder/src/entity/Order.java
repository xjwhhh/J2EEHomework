package entity;

public class Order {
    private int id;
    private String orderTime;
    private OrderRecordList records;

    public Order() {
    }

    public OrderRecordList getRecords() {
        return records;
    }

    public void setRecords(OrderRecordList records) {
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
