package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    CustomerDAO customerDAO;

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        orderDAO.setSession(session);
        orderDetailDAO.setSession(session);
        itemDAO.setSession(session);
        customerDAO.setSession(session);

        try{
            tx = session.beginTransaction();
            orderDAO.add(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()), customerDAO.find(order.getCustomerId())));
            for (OrderDetailTM orderDetail : orderDetails) {
                orderDetailDAO.add(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
            throw t;
        }finally {
            session.close();
        }

    }

    public String autoGeneratePlaceOrderID()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        orderDAO.setSession(session);
        Transaction tx = null;
        String id = null;
        try{
            tx = session.beginTransaction();
            String oldID = orderDAO.getLastOrderID();
            oldID = oldID.substring(2, 5);
            int newID = Integer.parseInt(oldID) + 1;
            if (newID < 10) {
                id =   "OD00" + newID;
            } else if (newID < 100) {
                id =   "OD0" + newID;
            } else {
                id =   "OD" + newID;
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
        return id;
    }
}
