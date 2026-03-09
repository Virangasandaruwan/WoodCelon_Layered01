package lk.ijse.woodceylon.dao.custom.impl;

import lk.ijse.woodceylon.dao.CrudUtil;
import lk.ijse.woodceylon.dao.custom.ProductDAO;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.entity.Product;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean save(Product product) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO Product (Product_ID, Name, Description, Price, Supplier_ID, Qty) VALUES (?,?,?,?,?,?)",
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSupplierId(),
                product.getQty()
        );
    }

    @Override
    public boolean update(Product product) throws Exception {
        return CrudUtil.execute(
                "UPDATE Product SET Name=?, Description=?, Price=?, Supplier_ID=?, Qty=? WHERE Product_ID=?",
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSupplierId(),
                product.getQty(),
                product.getProductId()
        );
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM Product WHERE Product_ID=?",
                id
        );
    }

    @Override
    public boolean exist(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT Product_ID FROM Product WHERE Product_ID=?", id);
        return rst.next();
    }

    @Override
    public Product search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM Product WHERE Product_ID=?",
                id
        );
        if (rst.next()) {
            return new Product(
                    rst.getInt("Product_ID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getString("Price"),
                    rst.getInt("Supplier_ID"),
                    rst.getInt("Qty")
            );
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAll() throws Exception {
        ArrayList<Product> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Product");

        while (rst.next()) {
            list.add(new Product(
                    rst.getInt("Product_ID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getString("Price"),
                    rst.getInt("Supplier_ID"),
                    rst.getInt("Qty")
            ));
        }
        return list;
    }

    @Override
    public boolean updateStock(int productId, int qtyChange) throws Exception {
        String sql = "UPDATE Product SET Qty = Qty + ? WHERE Product_ID = ? AND Qty + ? >= 0";
        return CrudUtil.execute(sql, qtyChange, productId, qtyChange);
    }

    @Override
    public List<Product> getLowStockItems() throws Exception {
        List<Product> lowStock = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Product WHERE Qty < 5");

        while (rst.next()) {
            lowStock.add(new Product(
                    rst.getInt("Product_ID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getString("Price"),
                    rst.getInt("Supplier_ID"),
                    rst.getInt("Qty")
            ));
        }
        return lowStock;
    }

    @Override
    public void printReport() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream is = getClass().getResourceAsStream("/lk/ijse/woodceylon/report/ItemReport.jrxml");

        if (is == null) {
            throw new Exception("Report template not found: ItemReport.jrxml");
        }

        JasperReport jr = JasperCompileManager.compileReport(is);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}