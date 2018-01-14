package dao.impl;

import dao.BaseDaoImpl;
import dao.DaoHelper;
import dao.OrderDao;
import entity.Order;
import entity.OrderRecord;

import java.sql.*;
import java.util.ArrayList;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private OrderDaoImpl() {

    }

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }

    @Override
    public ArrayList<Order> getOrderListByUserId(int userId) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            stmt = con.prepareStatement("select id,orderTime from myOrder where userId = ?");
            System.out.println("test");
            stmt.setString(1, String.valueOf(userId));
            result = stmt.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("id"));
                order.setOrderTime(result.getString("orderTime"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(con);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        orderList = getOrderRecords(orderList);
        return orderList;
    }

    @Override
    public void saveOrder(Order order) {

    }

    private ArrayList<Order> getOrderRecords(ArrayList<Order> orderList) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            for (int i = 0; i < orderList.size(); i++) {
                Order order = orderList.get(i);
                stmt = con.prepareStatement("select goodsId,name,price,number from orderInfo where orderId = ?");
                stmt.setString(1, String.valueOf(order.getId()));
                result = stmt.executeQuery();
                ArrayList<OrderRecord> records = new ArrayList<>();
                while (result.next()) {
                    OrderRecord record = new OrderRecord();
                    record.setGoodsId(result.getInt("goodsId"));
                    record.setName(result.getString("name"));
                    record.setNumber(result.getInt("number"));
                    record.setPrice(result.getDouble("price"));
                    record.setSupply(result.getInt("supply"));
                    records.add(record);
                }
                order.setRecords(records);
                orderList.set(i, order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    //检验是否有货
    private boolean checkOrder(OrderRecord record) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        int supplyNumber = 0;
        try {
            stmt = con.prepareStatement("select number from supplyInfo where goodsId = ?");
            stmt.setString(1, String.valueOf(record.getGoodsId()));
            result = stmt.executeQuery();
            while (result.next()) {
                supplyNumber = result.getInt("number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplyNumber < record.getNumber();
    }


}
