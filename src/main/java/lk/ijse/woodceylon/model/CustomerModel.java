//package lk.ijse.woodceylon.model;
//
//public class CustomerModel {
//}
package lk.ijse.woodceylon.model;

import java.io.InputStream;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.CustomerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.woodceylon.dto.OrderDTO;
import lk.ijse.woodceylon.util.CrudUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.CustomerDTO;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.CustomerDTO;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.util.ArrayList;

import java.util.ArrayList;

import java.util.ArrayList;

import lk.ijse.woodceylon.dto.CustomerDTO;

import lk.ijse.woodceylon.dto.CustomerDTO;

public class CustomerModel {

    public boolean addCustomer(CustomerDTO customerDTO) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO Customer (Name, Phone, Email, Address) VALUES (?,?,?,?)",
                customerDTO.getName(),
                customerDTO.getPhone(),
                customerDTO.getEmail(),
                customerDTO.getAddress()
        );
    }

    public String updateCustomer(CustomerDTO customerDTO) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Customer SET Name=?, Phone=?, Email=?, Address=? WHERE Customer_ID=?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, customerDTO.getName());
        pstm.setString(2, customerDTO.getPhone());
        pstm.setString(3, customerDTO.getEmail());
        pstm.setString(4, customerDTO.getAddress());
        pstm.setInt(5, customerDTO.getCustomerId());

        return pstm.executeUpdate() > 0 ? "Customer Updated Successfully" : "Customer Update Failed";
    }

    public String deleteCustomer(int customerId) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM Customer WHERE Customer_ID=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, customerId);

        return pstm.executeUpdate() > 0 ? "Customer Deleted Successfully" : "Customer Delete Failed";
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws Exception {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();

        while (rst.next()) {
            list.add(new CustomerDTO(
                    rst.getInt("Customer_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            ));
        }
        return list;
    }

    public CustomerDTO searchCustomer(int customerId) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE Customer_ID=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, customerId);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new CustomerDTO(
                    rst.getInt("Customer_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            );
        }
        return null;
    }

    public void printReport() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/lk/ijse/WoodCeylon/report/CustomerReport.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }

}