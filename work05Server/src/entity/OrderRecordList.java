package entity;

import java.util.ArrayList;

public class OrderRecordList {

    private ArrayList<OrderRecord> orderRecordList;

    public OrderRecordList() {
    }

    public OrderRecordList(ArrayList<OrderRecord> orderRecordList) {
        this.orderRecordList = orderRecordList;
    }

    public ArrayList<OrderRecord> getOrderRecordList() {
        return orderRecordList;
    }

    public void setOrderRecordList(ArrayList<OrderRecord> orderRecordList) {
        this.orderRecordList = orderRecordList;
    }

    public void setOrderRecordList(OrderRecord orderRecord, int index) {
        orderRecordList.set(index, orderRecord);
    }

    public OrderRecord getOrderRecord(int index) {
        return orderRecordList.get(index);
    }
}
