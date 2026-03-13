package lk.ijse.woodceylon.bo.custom;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lk.ijse.woodceylon.bo.SuperBO;
import lk.ijse.woodceylon.dto.OrderDTO;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.ProductDTO;

import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    String addOrder(OrderDTO orderDTO) throws Exception;

    String updateOrder(OrderDTO orderDTO) throws Exception;
    String deleteOrder(int orderId) throws Exception;
    ArrayList<OrderDTO> getAllOrders() throws Exception;
    OrderDTO searchOrder(int orderId) throws Exception;
    ProductDTO searchProduct(int id) throws Exception;
    int placeOrder(OrderDTO orderDTO) throws Exception;
    ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception;
    void printReport() throws Exception;
    double getTotalEarnings() throws Exception;
    int getTotalItemsSold() throws Exception;
    String getTopSellingItem() throws Exception;
    int getTotalCustomers() throws Exception;
    int getTotalSuppliers() throws Exception;
    ArrayList<OrderDTO> getAllOrdersGrouped() throws Exception;
    ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception;
    boolean cancelOrder(int orderId, int productId, int qty) throws Exception;

    ObservableList<String> getLowStockItems() throws Exception;

    ObservableList<PieChart.Data> getPieChartData() throws Exception;
}