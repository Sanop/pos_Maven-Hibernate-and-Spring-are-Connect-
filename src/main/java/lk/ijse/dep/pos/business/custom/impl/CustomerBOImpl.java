package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.entity.Customer;
import lk.ijse.dep.pos.util.CustomerTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    CustomerDAO customerDAO;

    public String getNewCustomerId() throws Exception {
        String id = "";
        String lastCustomerId = customerDAO.getLastCustomerID();
        System.out.println(lastCustomerId + "cid");
        if (lastCustomerId == null) {
            return "C001";
        } else {
            int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            maxId = maxId + 1;

            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            System.out.println(id);
        }
        return id;
    }

    public List<CustomerTM> getAllCustomers() throws Exception {

        List<CustomerTM> customerTMS = new ArrayList<>();

        List<Customer> allCustomers = customerDAO.findAll();

        for (Customer customerTM : allCustomers) {
            customerTMS.add(new CustomerTM(customerTM.getId(), customerTM.getName(), customerTM.getAddress()));
        }
        return customerTMS;
    }

    public void saveCustomer(String id, String name, String address) throws Exception {
            customerDAO.add(new Customer(id, name, address));

    }

    public void deleteCustomer(String customerId) throws Exception {

            customerDAO.delete(customerId);
    }

    public void updateCustomer(String customerId, String name, String address) throws Exception {

            customerDAO.update(new Customer(customerId, name, address));

    }

}
