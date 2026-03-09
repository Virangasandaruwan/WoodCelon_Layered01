package lk.ijse.woodceylon.dao.custom.impl;

import lk.ijse.woodceylon.dao.custom.CustomerDAO;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.entity.Customer;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Customer customer) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO Customer ( Name, Phone, Email, Address) VALUES (?,?,?,?)",
//                customer.getCustomerId(),   Customer_ID,
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    @Override
    public boolean update(Customer customer) throws Exception {
        return CrudUtil.execute(
                "UPDATE Customer SET Name=?, Phone=?, Email=?, Address=? WHERE Customer_ID=?",
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCustomerId()
        );
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM Customer WHERE Customer_ID=?",
                id
        );
    }

    @Override
    public boolean exist(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT Customer_ID FROM Customer WHERE Customer_ID = ? LIMIT 1",
                id
        );
        return rst.next();
    }

    @Override
    public Customer search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM Customer WHERE Customer_ID = ?",
                id
        );

        if (rst.next()) {
            return new Customer(
                    rst.getInt("Customer_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            );
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() throws Exception {
        ArrayList<Customer> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");

        while (rst.next()) {
            list.add(new Customer(
                    rst.getInt("Customer_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            ));
        }
        return list;
    }

    @Override
    public void printReport() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream(
                "/lk/ijse/woodceylon/report/CustomerReport.jrxml"
        );

        if (inputStream == null) {
            throw new Exception("Customer report template not found!");
        }

        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
    @Override
    public int generateNextId() throws Exception {
        String sql = "SELECT MAX(customerId) FROM Customer";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();

        if (rst.next()) {
            int max = rst.getInt(1);
            return (max == 0) ? 1 : max + 1;
        }
        return 1;
    }
}