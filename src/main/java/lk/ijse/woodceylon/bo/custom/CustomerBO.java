package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.bo.SuperBO;
import lk.ijse.woodceylon.dto.CustomerDTO;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    String addCustomer(CustomerDTO dto) throws Exception;
    String updateCustomer(CustomerDTO dto) throws Exception;
    String deleteCustomer(int customerId) throws Exception;
    ArrayList<CustomerDTO> getAllCustomers() throws Exception;
    CustomerDTO searchCustomer(int customerId) throws Exception;
    int getNextCustomerId() throws Exception;
    void printCustomerReport() throws Exception;
}