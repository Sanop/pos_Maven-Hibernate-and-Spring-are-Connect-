package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order,String> implements OrderDAO {

    @Override
    public String getLastOrderID() throws Exception {
        List list = getSession().createQuery("SELECT O.id FROM lk.ijse.dep.pos.entity.Order O ORDER BY O.id DESC").setMaxResults(1).list();

        return (list.size() > 0) ? (String) list.get(0) : null;
    }
}
