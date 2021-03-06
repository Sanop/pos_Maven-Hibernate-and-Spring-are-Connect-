package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;

import java.util.List;

public interface OrderBO extends SuperBO {

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails)throws Exception;

    public String autoGeneratePlaceOrderID()throws Exception;
}
