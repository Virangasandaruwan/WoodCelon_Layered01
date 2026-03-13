//package lk.ijse.woodceylon.model;
//
//public class ProductModel {
//}

package lk.ijse.woodceylon.model;

import java.io.InputStream;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.ProductDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ProductModel {

    public String addProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Product (Product_ID, Name, Description, Price, Supplier_ID, Qty) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, dto.getProductId());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getDescription());
            ps.setString(4, dto.getPrice());
            ps.setInt(5, dto.getSupplierId());
            ps.setInt(6, dto.getQty());
            return ps.executeUpdate() > 0 ? "Product Added Successfully" : "Add Failed";
        }
    }

    public String updateProduct(ProductDTO dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Product SET Name=?, Description=?, Price=?, Supplier_ID=?, Qty=? WHERE Product_ID=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getDescription());
            ps.setString(3, dto.getPrice());
            ps.setInt(4, dto.getSupplierId());
            ps.setInt(5, dto.getQty());
            ps.setInt(6, dto.getProductId());
            return ps.executeUpdate() > 0 ? "Product Updated Successfully" : "Update Failed";
        }
    }

    public String deleteProduct(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Product WHERE Product_ID=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0 ? "Product Deleted Successfully" : "Delete Failed";
        }
    }

    public ProductDTO searchProduct(int id) throws SQLException, ClassNotFoundException {
        if (id <= 0) {
            return null;
        }
        String sql = "SELECT * FROM Product WHERE Product_ID=?";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProductDTO(
                            rs.getInt("Product_ID"),
                            rs.getString("Name"),
                            rs.getString("Description"),
                            rs.getString("Price"),
                            rs.getInt("Supplier_ID"),
                            rs.getInt("Qty")
                    );
                }
            }
        }
        return null;
    }

    public List<ProductDTO> getAllProducts() throws SQLException, ClassNotFoundException {
        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ProductDTO(
                        rs.getInt("Product_ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Price"),
                        rs.getInt("Supplier_ID"),
                        rs.getInt("Qty")
                ));
            }
        }
        return list;
    }

    public void printReport() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream = getClass().getResourceAsStream("/lk/ijse/WoodCeylon/report/ItemReport.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer.viewReport(jp, false);

    }

    public boolean updateStock(int productId, int newQty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Product SET Qty = Qty + ? WHERE Product_ID = ?";
        return CrudUtil.execute(sql, newQty, productId);
    }

    public ObservableList<String> getLowStockItems() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Name, Qty FROM Product WHERE Qty < 5";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();

        ObservableList<String> lowStockList = FXCollections.observableArrayList();
        while (rst.next()) {
            lowStockList.add(rst.getString("Name") + " (Only " + rst.getInt("Qty") + " left)");
        }
        return lowStockList;
    }
}
