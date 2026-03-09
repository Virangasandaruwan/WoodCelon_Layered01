package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.OrderBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.OrderDAO;
import lk.ijse.woodceylon.dto.OrderDTO;
import lk.ijse.woodceylon.dto.OrderDetailsDTO;
import lk.ijse.woodceylon.dto.ProductDTO;
import lk.ijse.woodceylon.entity.Order;
import java.util.ArrayList;


public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance()
            .getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String addOrder(OrderDTO dto) throws Exception {
        Order entity = new Order(dto.getCustomerId(), dto.getOrderDate());
        return orderDAO.save(entity) ? "Order added successfully" : "Failed to add order";
    }

    @Override
    public String updateOrder(OrderDTO dto) throws Exception {
        Order entity = new Order(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate());
        return orderDAO.update(entity) ? "Order updated successfully" : "Failed to update order";
    }

    @Override
    public String deleteOrder(int orderId) throws Exception {
        return orderDAO.delete(orderId) ? "Order deleted successfully" : "Failed to delete order";
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws Exception {
        ArrayList<Order> entities = orderDAO.getAll();
        ArrayList<OrderDTO> dtos = new ArrayList<>();
        for (Order o : entities) {
            dtos.add(new OrderDTO(o.getOrderId(), o.getCustomerId(), o.getOrderDate()));
        }
        return dtos;
    }

    @Override
    public OrderDTO searchOrder(int orderId) throws Exception {
        Order entity = orderDAO.search(orderId);
        return entity != null ? new OrderDTO(entity.getOrderId(), entity.getCustomerId(), entity.getOrderDate()) : null;
    }

    @Override
    public ProductDTO searchProduct(int id) throws Exception {
        return orderDAO.searchProduct(id);
    }

    @Override
    public int placeOrder(OrderDTO orderDTO) throws Exception {
        Order entity = new Order(orderDTO.getCustomerId(), orderDTO.getOrderDate());
        return orderDAO.placeOrder(entity, orderDTO.getOrderItems());
    }

    @Override
    public ArrayList<OrderDetailsDTO> getAllOrderDetails() throws Exception {
        return orderDAO.getAllOrderDetails();
    }

    @Override
    public void printReport() throws Exception {
        orderDAO.printReport();
    }

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
    public ArrayList<OrderDTO> getAllOrdersGrouped() throws Exception {
        ArrayList<Order> entities = orderDAO.getAllOrdersGrouped();
        ArrayList<OrderDTO> dtos = new ArrayList<>();
        for (Order o : entities) {
            dtos.add(new OrderDTO(o.getOrderId(), o.getCustomerId(), o.getOrderDate()));
        }
        return dtos;
    }

    @Override
    public ArrayList<OrderDetailsDTO> getOrderItemsByOrderId(int orderId) throws Exception {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    @Override
    public boolean cancelOrder(int orderId, int productId, int qty) throws Exception {
        return orderDAO.cancelOrder(orderId, productId, qty);
    }
}