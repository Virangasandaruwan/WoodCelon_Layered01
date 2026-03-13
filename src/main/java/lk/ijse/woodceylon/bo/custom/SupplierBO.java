package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.bo.SuperBO;
import lk.ijse.woodceylon.dto.SupplierDTO;
import lk.ijse.woodceylon.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    String addSupplier(SupplierDTO supplier) throws Exception;
    String updateSupplier(SupplierDTO supplier) throws Exception;
    String deleteSupplier(int supplierId) throws Exception;
    SupplierDTO searchSupplier(int supplierId) throws Exception;
    ArrayList<SupplierDTO> getAllSuppliers() throws Exception;
    int getNextSupplierId() throws Exception;
    void printReport() throws SQLException, Exception;
}