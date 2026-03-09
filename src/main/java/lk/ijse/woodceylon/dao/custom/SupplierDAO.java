package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.entity.Supplier;

import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier, Integer> {

    ArrayList<Supplier> getAll() throws Exception;

    Supplier search(Integer id) throws Exception;

    int generateNextId() throws Exception;

    void printReport() throws Exception;
}