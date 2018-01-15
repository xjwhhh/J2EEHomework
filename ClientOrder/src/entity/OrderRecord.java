package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orderinfo")
public class OrderRecord implements Serializable {

    private int id;
    private Order order;
    private int goodsId;
    private String name;
    private int number;
    private double price;
    private int supply;

    public OrderRecord() {
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="orderId")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order=order;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }
}
