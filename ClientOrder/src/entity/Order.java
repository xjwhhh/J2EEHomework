package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "myOrder")
public class Order {

    private int id;
    private String orderTime;
    private int userId;
    private List<OrderRecord> records;

    public Order() {
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    @OneToMany(mappedBy="order",cascade= CascadeType.REMOVE,fetch= FetchType.LAZY)
    @OrderBy(value="id ASC")
    public List<OrderRecord> getRecords() {
        return records;
    }

    public void setRecords(List<OrderRecord> records) {
        this.records = records;
    }

}
