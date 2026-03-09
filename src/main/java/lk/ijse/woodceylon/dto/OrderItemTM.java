package lk.ijse.woodceylon.dto;

public class OrderItemTM {
    
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int qty;
    private double totalPrice;

    public OrderItemTM() {
    }

    public OrderItemTM(int itemId, String itemName, double itemPrice, int qty, double totalPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItemTM{" + "itemId=" + itemId + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", qty=" + qty + ", totalPrice=" + totalPrice + '}';
    }
    
}