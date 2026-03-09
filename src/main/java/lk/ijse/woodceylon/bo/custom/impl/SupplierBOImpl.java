package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.SupplierBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.SupplierDAO;
import lk.ijse.woodceylon.entity.Supplier;

import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private final SupplierDAO supplierDAO =
            (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public String addSupplier(Supplier supplier) throws Exception {
        boolean added = supplierDAO.save(supplier);
        return added ? "Supplier Saved Successfully" : "Supplier Save Failed";
    }

    @Override
    public String updateSupplier(Supplier supplier) throws Exception {
        boolean updated = supplierDAO.update(supplier);
        return updated ? "Supplier Updated Successfully" : "Supplier Update Failed";
    }

    @Override
    public String deleteSupplier(int supplierId) throws Exception {
        boolean deleted = supplierDAO.delete(supplierId);
        return deleted ? "Supplier Deleted Successfully" : "Supplier Delete Failed";
    }

    @Override
    public Supplier searchSupplier(int supplierId) throws Exception {
        return supplierDAO.search(supplierId);
    }

    @Override
    public ArrayList<Supplier> getAllSuppliers() throws Exception {
        return supplierDAO.getAll();
    }

    @Override
    public int getNextSupplierId() throws Exception {
        return supplierDAO.generateNextId();
    }

    @Override
    public void printReport() throws Exception {
        supplierDAO.printReport();
    }
}