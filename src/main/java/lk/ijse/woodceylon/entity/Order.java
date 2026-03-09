package lk.ijse.woodceylon.entity;

import lk.ijse.woodceylon.dto.OrderItemDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private int customerId;
    private LocalDate orderDate;
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(int customerId, LocalDate orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int customerId, LocalDate orderDate, List<OrderItemDTO> orderItems) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

    public Order(int orderId, int customerId, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    // Getters and Setters – camelCase
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", orderItems=" + orderItems +
                '}';
    }
}