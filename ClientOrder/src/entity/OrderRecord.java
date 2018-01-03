package entity;

public class OrderRecord {
    private int goodsId;
    private String name;
    private int number;
    private double price;
    private boolean isShortSupply;

    public OrderRecord() {
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

    public boolean isShortSupply() {
        return isShortSupply;
    }

    public void setShortSupply(boolean shortSupply) {
        isShortSupply = shortSupply;
    }
}
