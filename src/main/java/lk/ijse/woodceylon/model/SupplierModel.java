//package lk.ijse.woodceylon.model;
//
//public class SupplierModel {
//}

package lk.ijse.woodceylon.model;

import java.io.InputStream;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.SupplierDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import lk.ijse.woodceylon.util.CrudUtil;

public class SupplierModel {


    public String addSupplier(SupplierDTO supplierDTO) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Supplier (Supplier_ID, Name, Address, Phone, Email) VALUES (?,?,?,?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, supplierDTO.getSupplierId());
        pstm.setString(2, supplierDTO.getSupplierName());
        pstm.setString(3, supplierDTO.getSupplierAddress());
        pstm.setString(4, supplierDTO.getSupplierPhone());
        pstm.setString(5, supplierDTO.getSupplierEmail());

        return pstm.executeUpdate() > 0 ? "Supplier Saved Successfully" : "Supplier Save Failed";
    }

    public String updateSupplier(SupplierDTO supplierDTO) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE Supplier SET Name=?, Address=?, Phone=?, Email=? WHERE Supplier_ID=?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, supplierDTO.getSupplierName());
        pstm.setString(2, supplierDTO.getSupplierAddress());
        pstm.setString(3, supplierDTO.getSupplierPhone());
        pstm.setString(4, supplierDTO.getSupplierEmail());
        pstm.setInt(5, supplierDTO.getSupplierId());

        return pstm.executeUpdate() > 0 ? "Supplier Updated Successfully" : "Supplier Update Failed";
    }

    public String deleteSupplier(String supplierId) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM Supplier WHERE Supplier_ID=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, supplierId);

        return pstm.executeUpdate() > 0 ? "Supplier Deleted Successfully" : "Supplier Delete Failed";
    }


    public SupplierDTO searchSupplier(String supplierId) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier WHERE Supplier_ID=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, supplierId);

        ResultSet rst = pstm.executeQuery();

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getInt("Supplier_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email")
            );
        }
        return null;
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws Exception {

        ArrayList<SupplierDTO> list = new ArrayList<>();

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier";
        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()) {
            list.add(new SupplierDTO(
                    rst.getInt("Supplier_ID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email")
            ));
        }
        return list;
    }

    public int getNextSupplierId() throws Exception {
        String sql = "SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1";
        ResultSet rst = CrudUtil.execute(sql);

        if (rst.next()) {
            return rst.getInt(1) + 1;
        }
        return 1;
    }

    public void printReport() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream = getClass().getResourceAsStream("/lk/ijse/WoodCeylon/report/SupplierReport.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer.viewReport(jp, false);

    }
}

