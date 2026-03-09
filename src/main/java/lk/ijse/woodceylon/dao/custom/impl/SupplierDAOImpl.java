package lk.ijse.woodceylon.dao.custom.impl;

import lk.ijse.woodceylon.dao.custom.SupplierDAO;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.entity.Supplier;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier supplier) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO Supplier (Supplier_ID, Name, Address, Phone, Email) VALUES (?,?,?,?,?)",
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail()
        );
    }

    @Override
    public boolean update(Supplier supplier) throws Exception {
        return CrudUtil.execute(
                "UPDATE Supplier SET Name=?, Address=?, Phone=?, Email=? WHERE Supplier_ID=?",
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getSupplierId()
        );
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM Supplier WHERE Supplier_ID=?",
                id
        );
    }

    @Override
    public boolean exist(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT Supplier_ID FROM Supplier WHERE Supplier_ID = ? LIMIT 1",
                id
        );
        return rst.next();
    }

    @Override
    public Supplier search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM Supplier WHERE Supplier_ID = ?",
                id
        );

        if (rst.next()) {
            return new Supplier(
                    rst.getInt("Supplier_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email")
            );
        }
        return null;
    }

    @Override
    public ArrayList<Supplier> getAll() throws Exception {
        ArrayList<Supplier> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");

        while (rst.next()) {
            list.add(new Supplier(
                    rst.getInt("Supplier_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email")
            ));
        }
        return list;
    }


    @Override
    public int generateNextId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT Supplier_ID FROM Supplier ORDER BY Supplier_ID DESC LIMIT 1");

        if (rst.next()) {
            return rst.getInt(1) + 1;
        }
        return 1;
    }

    @Override
    public void printReport() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream(
                "/lk/ijse/woodceylon/report/SupplierReport.jrxml"
        );

        if (inputStream == null) {
            throw new Exception("Supplier report template not found!");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}