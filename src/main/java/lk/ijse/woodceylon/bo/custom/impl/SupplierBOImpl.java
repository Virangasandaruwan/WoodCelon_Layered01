package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.SupplierBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.SupplierDAO;
import lk.ijse.woodceylon.dto.SupplierDTO;
import lk.ijse.woodceylon.entity.Supplier;

import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private final SupplierDAO supplierDAO =
            (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public String addSupplier(SupplierDTO dto) throws Exception {
        Supplier supplier = new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getSupplierAddress(),
                dto.getSupplierPhone(),
                dto.getSupplierEmail()
        );
        boolean added = supplierDAO.save(supplier);
        return added ? "Supplier Saved Successfully" : "Supplier Save Failed";
    }

    @Override
    public String updateSupplier(SupplierDTO dto) throws Exception {
        Supplier supplier = new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getSupplierAddress(),
                dto.getSupplierPhone(),
                dto.getSupplierEmail()
        );
        boolean updated = supplierDAO.update(supplier);
        return updated ? "Supplier Updated Successfully" : "Supplier Update Failed";
    }

    @Override
    public String deleteSupplier(int supplierId) throws Exception {
        boolean deleted = supplierDAO.delete(supplierId);
        return deleted ? "Supplier Deleted Successfully" : "Supplier Delete Failed";
    }

    @Override
    public SupplierDTO searchSupplier(int supplierId) throws Exception {
        Supplier s = supplierDAO.search(supplierId);
        if (s != null) {
            return new SupplierDTO(
                    s.getSupplierId(),
                    s.getName(),
                    s.getPhone(),
                    s.getEmail(),
                    s.getAddress()
            );
        }
        return null;
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws Exception {
        ArrayList<Supplier> entities = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOs = new ArrayList<>();
        for (Supplier s : entities) {
            supplierDTOs.add(new SupplierDTO(
                    s.getSupplierId(),
                    s.getName(),
                    s.getPhone(),
                    s.getEmail(),
                    s.getAddress()
            ));
        }
        return supplierDTOs;
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