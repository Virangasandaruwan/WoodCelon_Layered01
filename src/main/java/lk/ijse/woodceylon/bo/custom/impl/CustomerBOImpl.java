package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.CustomerBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.CustomerDAO;
import lk.ijse.woodceylon.dto.CustomerDTO;
import lk.ijse.woodceylon.entity.Customer;

import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String addCustomer(CustomerDTO dto) throws Exception {
        Customer customer = new Customer(
                dto.getCustomerId(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        );
        boolean isSaved = customerDAO.save(customer);
        return isSaved ? "Customer Saved Successfully" : "Customer Save Failed";
    }

    @Override
    public String updateCustomer(CustomerDTO dto) throws Exception {
        Customer customer = new Customer(
                dto.getCustomerId(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        );
        boolean updated = customerDAO.update(customer);
        return updated ? "Customer Updated Successfully" : "Customer Update Failed";
    }

    @Override
    public String deleteCustomer(int customerId) throws Exception {
        boolean deleted = customerDAO.delete(customerId);
        return deleted ? "Customer Deleted Successfully" : "Customer Delete Failed";
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws Exception {

        ArrayList<Customer> allCustomers = customerDAO.getAll();

        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer c : allCustomers) {
            customerDTOs.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getName(),
                    c.getPhone(),
                    c.getEmail(),
                    c.getAddress()
            ));
        }
        return customerDTOs;
    }

    @Override
    public CustomerDTO searchCustomer(int customerId) throws Exception {
        Customer c = customerDAO.search(customerId);
        if (c != null) {
            return new CustomerDTO(c.getCustomerId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress());
        }
        return null;
    }

    @Override
    public void printCustomerReport() throws Exception {
        customerDAO.printReport();
    }

    @Override
    public int getNextCustomerId() throws Exception {
        return customerDAO.generateNextId();
    }
}
