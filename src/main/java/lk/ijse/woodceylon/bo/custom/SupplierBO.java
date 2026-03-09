package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.bo.SuperBO;
import lk.ijse.woodceylon.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    String addSupplier(Supplier supplier) throws Exception;
    String updateSupplier(Supplier supplier) throws Exception;
    String deleteSupplier(int supplierId) throws Exception;
    Supplier searchSupplier(int supplierId) throws Exception;
    ArrayList<Supplier> getAllSuppliers() throws Exception;
    int getNextSupplierId() throws Exception;
    void printReport() throws SQLException, Exception;
}