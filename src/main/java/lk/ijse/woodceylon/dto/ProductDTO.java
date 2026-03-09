package lk.ijse.woodceylon.dto;

public class ProductDTO {
    private int productId;
    private String name;
    private String description;
    private String price;     
    private int supplierId;
    private int qty;          

    public ProductDTO() {}

    public ProductDTO(int productId, String name, String description, String price, int supplierId, int qty) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.supplierId = supplierId;
        this.qty = qty;
    }

   
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                '}';
    }
}