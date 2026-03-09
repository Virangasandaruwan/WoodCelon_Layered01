package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.OrderItemDTO;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.entity.Order;

import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order, Integer> {

    ProductDTO searchProduct(int productId) throws Exception;

    int placeOrder(Order order, List<OrderItemDTO> items) throws Exception;

    ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception;

    ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception;

    boolean cancelOrder(int orderId, int productId, int qty) throws Exception;

    double getTotalEarnings() throws Exception;

    int getTotalItemsSold() throws Exception;

    String getTopSellingItem() throws Exception;

    int getTotalCustomers() throws Exception;

    int getTotalSuppliers() throws Exception;

    void printReport() throws Exception;

    ArrayList<Order> getAllOrdersGrouped() throws Exception;
}