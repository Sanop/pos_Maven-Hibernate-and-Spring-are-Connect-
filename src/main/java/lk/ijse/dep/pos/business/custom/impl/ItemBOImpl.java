package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import lk.ijse.dep.pos.util.ItemTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    ItemDAO itemDAO;

    public String getNewItemCode()throws Exception{

        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        String id = "";
        try{
            tx = session.beginTransaction();

            String lastItemCode = itemDAO.getLastItemID();
            if (lastItemCode == null){
                return "I001";
            }else{
                int maxId=  Integer.parseInt(lastItemCode.replace("I",""));
                maxId = maxId + 1;

                if (maxId < 10) {
                    id = "I00" + maxId;
                } else if (maxId < 100) {
                    id = "I0" + maxId;
                } else {
                    id = "I" + maxId;
                }
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

    public List<ItemTM> getAllItems()throws Exception{

        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        List<ItemTM> itemTMS = new ArrayList<>();
        try{
            tx = session.beginTransaction();
            List<Item> allItems = itemDAO.findAll();


            for (Item allItem : allItems) {
                itemTMS.add(new ItemTM(allItem.getCode(),allItem.getDescription(),allItem.getUnitPrice().doubleValue(),allItem.getQtyOnHand()));
            }
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
        return itemTMS;
    }

    public void saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            itemDAO.add(new Item(code,description,unitPrice,qtyOnHand));
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
    }

    public void deleteItem(String itemCode)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            itemDAO.delete(itemCode);
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
    }

    public void updateItem(String description, int qtyOnHand, BigDecimal unitPrice, String itemCode){
        Session session = HibernateUtil.getSessionFactory().openSession();
        itemDAO.setSession(session);
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            itemDAO.update(new Item(itemCode,description,unitPrice,qtyOnHand));
            tx.commit();
        }catch (Throwable t){
            t.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
        }
    }
}
