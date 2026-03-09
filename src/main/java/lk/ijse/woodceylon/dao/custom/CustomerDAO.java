package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.entity.Customer;

import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer, Integer> {

    ArrayList<Customer> getAll() throws Exception;
    Customer search(Integer id) throws Exception;
    void printReport() throws Exception;
    int generateNextId() throws Exception;

    // boolean exist(Integer id) throws Exception;
    // String generateNextId() throws Exception;
}