package lk.ijse.woodceylon.dto;

public class OrderDetailsDTO {

    private int orderId;
    private int customerId;
    private String orderDate;
    private int productId;
    private String productName;
    private int qty;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(int orderId, int customerId, String orderDate, int productId, String productName, int qty) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
    }

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
