package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Component
@Transactional
public class OrderBOImpl implements OrderBO {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    CustomerDAO customerDAO;

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {
        orderDAO.add(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()), customerDAO.find(order.getCustomerId())));
        for (OrderDetailTM orderDetail : orderDetails) {
            orderDetailDAO.add(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
        }

    }

    public String autoGeneratePlaceOrderID() throws Exception {
        String id = null;
        String oldID = orderDAO.getLastOrderID();
        oldID = oldID.substring(2, 5);
        int newID = Integer.parseInt(oldID) + 1;
        if (newID < 10) {
            id = "OD00" + newID;
        } else if (newID < 100) {
            id = "OD0" + newID;
        } else {
            id = "OD" + newID;
        }
        return id;
    }
}
