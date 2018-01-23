package dao.impl;

import dao.BaseDaoImpl;
import dao.DaoHelper;
import dao.OrderDao;
import entity.Order;
import entity.OrderRecord;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import utils.HibernateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
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
//        Session session = HibernateUtil.getSession();
//        Transaction tx = null;
//        ArrayList<Order> orderArrayList=new ArrayList();
//        try {
//            tx = session.beginTransaction();
//            Query query = session.createQuery("select O.id,O.userId,O.orderTime FROM myOrder as O where O.userId=:userId");
//            query.setParameter("userId",userId);
//            List<Object> columnList=query.list();
//
//            tx.commit();
//
//            for(int i=0;i<columnList.size();i++){
//                Object[] objects=(Object[]) columnList.get(i);
//                Order order=new Order();
//                order.setId(Integer.valueOf(String.valueOf(objects[0])));
//                order.setOrderTime(String.valueOf(objects[1]));
//                System.out.println(objects[0]);
//                System.out.println(objects[1]);
//                orderArrayList.add(order);
//            }
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        System.out.println("hibernate........................................");
//        orderArrayList=getOrderRecords(orderArrayList);
//        return orderArrayList;


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
                stmt = con.prepareStatement("select goodsId,name,price,number,supply from orderInfo where orderId = ?");
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

//        Session session = HibernateUtil.getSession();
//        Transaction tx = null;
//        List orderRecordList=new ArrayList();
//        for (int i = 0; i < orderList.size(); i++) {
//            Order order = orderList.get(i);
//            try {
//                tx = session.beginTransaction();
//                Query query = session.createQuery("select O.goodsId,O.name,O.price,O.number,O.supply FROM orderinfo as O where O.orderId=:orderId");
//                query.setParameter("orderId", order.getId());
//                List<Object> columnList= query.list();
//
//                tx.commit();
//
//                ArrayList<OrderRecord> records = new ArrayList<>();
//                for(int j=0;j<columnList.size();j++){
//                    Object[] objects=(Object[]) columnList.get(j);
//                    OrderRecord record=new OrderRecord();
//                    record.setId(Integer.valueOf(String.valueOf(objects[0])));
//                    record.setGoodsId(Integer.valueOf(String.valueOf(objects[1])));
//                    record.setName(String.valueOf(objects[2]));
//                    record.setPrice(Double.valueOf(String.valueOf(objects[3])));
//                    record.setNumber(Integer.valueOf(String.valueOf(objects[4])));
//                    record.setSupply(Integer.valueOf(String.valueOf(objects[5])));
//                    records.add(record);
//                }
//                order.setRecords(records);
//                orderList.set(i, order);
//            } catch (HibernateException e) {
//                if (tx != null) tx.rollback();
//                e.printStackTrace();
//            } finally {
//                session.close();
//            }
//        }
//        System.out.println("hibernate........................................");
//
//
//        return orderList;
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
