package lk.ijse.woodceylon.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lk.ijse.woodceylon.bo.custom.OrderBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.OrderDAO;
import lk.ijse.woodceylon.dao.custom.ProductDAO;
import lk.ijse.woodceylon.dto.OrderDTO;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.entity.Order;
import lk.ijse.woodceylon.entity.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO =
            (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);

    private final ProductDAO productDAO =
            (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRODUCT);

    @Override
    public String addOrder(OrderDTO dto) throws Exception {
        Order order = new Order(
                dto.getCustomerId(),
                dto.getOrderDate()
        );
        boolean isSaved = orderDAO.save(order);
        return isSaved ? "Order Saved Successfully" : "Order Save Failed";
    }

    @Override
    public String updateOrder(OrderDTO dto) throws Exception {
        Order order = new Order(
                dto.getOrderId(),
                dto.getCustomerId(),
                dto.getOrderDate()
        );
        boolean updated = orderDAO.update(order);
        return updated ? "Order Updated Successfully" : "Order Update Failed";
    }

    @Override
    public String deleteOrder(int orderId) throws Exception {
        boolean deleted = orderDAO.delete(orderId);
        return deleted ? "Order Deleted Successfully" : "Order Delete Failed";
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws Exception {
        ArrayList<Order> allOrders = orderDAO.getAll();

        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order o : allOrders) {
            orderDTOs.add(new OrderDTO(
                    o.getOrderId(),
                    o.getCustomerId(),
                    o.getOrderDate()
            ));
        }
        return orderDTOs;
    }

    @Override
    public OrderDTO searchOrder(int orderId) throws Exception {
        Order o = orderDAO.search(orderId);
        if (o != null) {
            return new OrderDTO(
                    o.getOrderId(),
                    o.getCustomerId(),
                    o.getOrderDate()
            );
        }
        return null;
    }

    @Override
    public ProductDTO searchProduct(int productId) throws Exception {
        return orderDAO.searchProduct(productId);
    }

    @Override
    public int placeOrder(OrderDTO dto) throws Exception {
        Order order = new Order(
                dto.getCustomerId(),
                dto.getOrderDate()
        );
        return orderDAO.placeOrder(order, dto.getOrderItems());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception {
        return orderDAO.getAllOrderDetails();
    }

    @Override
    public ArrayList<OrderDTO> getAllOrdersGrouped() throws Exception {
        ArrayList<Order> allOrders = orderDAO.getAllOrdersGrouped();

        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order o : allOrders) {
            orderDTOs.add(new OrderDTO(
                    o.getOrderId(),
                    o.getCustomerId(),
                    o.getOrderDate()
            ));
        }
        return orderDTOs;
    }

    @Override
    public ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    @Override
    public boolean cancelOrder(int orderId, int productId, int qty) throws Exception {
        return orderDAO.cancelOrder(orderId, productId, qty);
    }

    // ─── Dashboard Methods ────────────────────────────────────────────────────

    @Override
    public double getTotalEarnings() throws Exception {
        return orderDAO.getTotalEarnings();
    }

    @Override
    public int getTotalItemsSold() throws Exception {
        return orderDAO.getTotalItemsSold();
    }

    @Override
    public String getTopSellingItem() throws Exception {
        return orderDAO.getTopSellingItem();
    }

    @Override
    public int getTotalCustomers() throws Exception {
        return orderDAO.getTotalCustomers();
    }

    @Override
    public int getTotalSuppliers() throws Exception {
        return orderDAO.getTotalSuppliers();
    }

    @Override
    public ObservableList<String> getLowStockItems() throws Exception {
        List<Product> products = productDAO.getLowStockItems();

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Product p : products) {
            items.add(p.getName() + " — Qty: " + p.getQty());
        }
        return items;
    }

    @Override
    public ObservableList<PieChart.Data> getPieChartData() throws Exception {
        ArrayList<OrderDetailsDTO> salesData = orderDAO.getAllOrderDetails();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        if (salesData == null || salesData.isEmpty()) {
            return pieData;
        }


        Map<String, Double> productSales = new LinkedHashMap<>();
        for (OrderDetailsDTO detail : salesData) {
            String productName = detail.getProductName();
            double qty = detail.getQty();
            productSales.merge(productName, qty, Double::sum);
        }

        for (Map.Entry<String, Double> entry : productSales.entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        return pieData;
    }

    @Override
    public void printReport() throws Exception {
        orderDAO.printReport();
    }
}