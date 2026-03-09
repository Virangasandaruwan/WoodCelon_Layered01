package lk.ijse.woodceylon.dao.custom.impl;

import lk.ijse.woodceylon.dao.CrudUtil;
import lk.ijse.woodceylon.dao.custom.OrderDAO;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.OrderItemDTO;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.entity.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean save(Order order) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO `order` (Customer_ID, Order_Date) VALUES (?, ?)",
                order.getCustomerId(),
                java.sql.Date.valueOf(order.getOrderDate())
        );
    }

    @Override
    public boolean update(Order order) throws Exception {
        return CrudUtil.execute(
                "UPDATE `order` SET Customer_ID = ?, Order_Date = ? WHERE Order_ID = ?",
                order.getCustomerId(),
                java.sql.Date.valueOf(order.getOrderDate()),
                order.getOrderId()
        );
    }

    @Override
    public boolean delete(Integer orderId) throws Exception {
        return CrudUtil.execute("DELETE FROM `order` WHERE Order_ID = ?", orderId);
    }

    @Override
    public boolean exist(Integer orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT Order_ID FROM `order` WHERE Order_ID = ? LIMIT 1", orderId);
        return rst.next();
    }

    @Override
    public Order search(Integer orderId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM `order` WHERE Order_ID = ?", orderId);
        if (rst.next()) {
            return new Order(
                    rst.getInt("Order_ID"),
                    rst.getInt("Customer_ID"),
                    rst.getDate("Order_Date").toLocalDate()
            );
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAll() throws Exception {
        ArrayList<Order> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM `order`");
        while (rst.next()) {
            list.add(new Order(
                    rst.getInt("Order_ID"),
                    rst.getInt("Customer_ID"),
                    rst.getDate("Order_Date").toLocalDate()
            ));
        }
        return list;
    }

    @Override
    public ProductDTO searchProduct(int productId) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Product WHERE Product_ID = ?", productId);
        if (rst.next()) {
            return new ProductDTO(
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
    public int placeOrder(Order order, List<OrderItemDTO> items) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            if (CrudUtil.execute("INSERT INTO `order` (Customer_ID, Order_Date) VALUES (?, ?)",
                    order.getCustomerId(), java.sql.Date.valueOf(order.getOrderDate()))) {
                conn.rollback();
                return -1;
            }

            ResultSet rs = CrudUtil.execute("SELECT LAST_INSERT_ID()");
            if (!rs.next()) {
                conn.rollback();
                return -1;
            }
            int orderId = rs.getInt(1);

            for (OrderItemDTO item : items) {
                if (/*!*/CrudUtil.execute("INSERT INTO Order_Product (Order_ID, Product_ID, Quantity) VALUES (?, ?, ?)",
                        orderId, item.getItemId(), item.getQty())) {
                    conn.rollback();
                    return -1;
                }

                if (/*!*/CrudUtil.execute("UPDATE Product SET Qty = Qty - ? WHERE Product_ID = ? AND Qty >= ?",
                        item.getQty(), item.getItemId(), item.getQty())) {
                    conn.rollback();
                    throw new SQLException("Not enough stock");
                }
            }

            conn.commit();
            return orderId;
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception {
        ArrayList<OrderDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT o.Order_ID, o.Customer_ID, o.Order_Date, p.Product_ID, p.Name AS Product_Name, op.Quantity " +
                "FROM `order` o JOIN Order_Product op ON o.Order_ID = op.Order_ID " +
                "JOIN Product p ON op.Product_ID = p.Product_ID ORDER BY o.Order_ID DESC";

        ResultSet rst = CrudUtil.execute(sql);
        while (rst.next()) {
            list.add(new OrderDetailsDTO(
                    rst.getInt("Order_ID"),
                    rst.getInt("Customer_ID"),
                    rst.getDate("Order_Date").toString(),
                    rst.getInt("Product_ID"),
                    rst.getString("Product_Name"),
                    rst.getInt("Quantity")
            ));
        }
        return list;
    }

    @Override
    public ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception {
        ArrayList<OrderDetailsDTO> list = new ArrayList<>();
        String sql = "SELECT op.Order_ID, o.Customer_ID, o.Order_Date, op.Product_ID, p.Name AS Product_Name, op.Quantity " +
                "FROM Order_Product op JOIN `order` o ON op.Order_ID = o.Order_ID " +
                "JOIN Product p ON op.Product_ID = p.Product_ID WHERE op.Order_ID = ?";

        ResultSet rst = CrudUtil.execute(sql, orderId);
        while (rst.next()) {
            list.add(new OrderDetailsDTO(
                    rst.getInt("Order_ID"),
                    rst.getInt("Customer_ID"),
                    rst.getDate("Order_Date").toString(),
                    rst.getInt("Product_ID"),
                    rst.getString("Product_Name"),
                    rst.getInt("Quantity")
            ));
        }
        return list;
    }

    @Override
    public boolean cancelOrder(int orderId, int productId, int qty) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            boolean deleted = CrudUtil.execute("DELETE FROM Order_Product WHERE Order_ID = ? AND Product_ID = ?",
                    orderId, productId);

            boolean updated = CrudUtil.execute("UPDATE Product SET Qty = Qty + ? WHERE Product_ID = ?",
                    qty, productId);

            if (deleted && updated) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void printReport() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream is = getClass().getResourceAsStream("/lk/ijse/woodceylon/report/CompleatedOrders.jrxml");
        if (is == null) throw new Exception("Report template not found");

        JasperReport jr = JasperCompileManager.compileReport(is);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }

    @Override
    public double getTotalEarnings() throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT SUM(op.Quantity * CAST(p.Price AS DECIMAL(10,2))) FROM Order_Product op JOIN Product p ON op.Product_ID = p.Product_ID"
        );
        return rst.next() ? rst.getDouble(1) : 0;
    }

    @Override
    public int getTotalItemsSold() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT SUM(Quantity) FROM Order_Product");
        return rst.next() ? rst.getInt(1) : 0;
    }

    @Override
    public String getTopSellingItem() throws Exception {
        ResultSet rst = CrudUtil.execute(
                "SELECT p.Name FROM Order_Product op JOIN Product p ON op.Product_ID = p.Product_ID " +
                        "GROUP BY op.Product_ID ORDER BY SUM(op.Quantity) DESC LIMIT 1"
        );
        return rst.next() ? rst.getString(1) : "No Sales Yet";
    }

    @Override
    public int getTotalCustomers() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Customer");
        return rst.next() ? rst.getInt(1) : 0;
    }

    @Override
    public int getTotalSuppliers() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Supplier");
        return rst.next() ? rst.getInt(1) : 0;
    }

    @Override
    public ArrayList<Order> getAllOrdersGrouped() throws Exception {
        ArrayList<Order> list = new ArrayList<>();
        String sql = "SELECT o.Order_ID, o.Customer_ID, o.Order_Date FROM `order` o " +
                "JOIN Order_Product op ON o.Order_ID = op.Order_ID GROUP BY o.Order_ID ORDER BY o.Order_ID DESC";

        ResultSet rst = CrudUtil.execute(sql);
        while (rst.next()) {
            list.add(new Order(
                    rst.getInt("Order_ID"),
                    rst.getInt("Customer_ID"),
                    rst.getDate("Order_Date").toLocalDate()
            ));
        }
        return list;
    }
}