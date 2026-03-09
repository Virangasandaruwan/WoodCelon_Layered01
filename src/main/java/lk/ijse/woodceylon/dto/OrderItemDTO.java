package lk.ijse.woodceylon.dto;

public class OrderItemDTO {

    private int id;
    private int itemId;
    private int qty;
    private double unitPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int itemId, int qty, double unitPrice) {
        this.itemId = itemId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderItemDTO(int id, int itemId, int qty, double unitPrice) {
        this.id = id;
        this.itemId = itemId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{"
                + "id=" + id
                + ", itemId=" + itemId
                + ", qty=" + qty
                + ", unitPrice=" + unitPrice
                + '}';
    }
}
