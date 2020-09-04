package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.util.ItemTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ItemBOImpl implements ItemBO {

    @Autowired
    ItemDAO itemDAO;

    public String getNewItemCode() throws Exception {

        String id = "";

        String lastItemCode = itemDAO.getLastItemID();
        if (lastItemCode == null) {
            return "I001";
        } else {
            int maxId = Integer.parseInt(lastItemCode.replace("I", ""));
            maxId = maxId + 1;

            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
        }
        return id;
    }

    public List<ItemTM> getAllItems() throws Exception {

        List<ItemTM> itemTMS = new ArrayList<>();
        List<Item> allItems = itemDAO.findAll();


        for (Item allItem : allItems) {
            itemTMS.add(new ItemTM(allItem.getCode(), allItem.getDescription(), allItem.getUnitPrice().doubleValue(), allItem.getQtyOnHand()));
        }
        return itemTMS;
    }

    public void saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws Exception {
        itemDAO.add(new Item(code, description, unitPrice, qtyOnHand));
    }

    public void deleteItem(String itemCode) throws Exception {
        itemDAO.delete(itemCode);
    }

    public void updateItem(String description, int qtyOnHand, BigDecimal unitPrice, String itemCode) throws Exception {
        itemDAO.update(new Item(itemCode, description, unitPrice, qtyOnHand));
    }
}
