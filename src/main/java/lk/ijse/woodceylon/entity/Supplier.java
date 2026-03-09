package lk.ijse.woodceylon.entity;

public class Supplier {
    private int supplierId;
    private String Name;
    private String Address;
    private String Phone;
    private String Email;

    public Supplier(int supplierId, String supplierName, String supplierAddress, String supplierPhone, String supplierEmail) {
        this.supplierId = supplierId;
        this.Name = supplierName;
        this.Address = supplierAddress;
        this.Phone = supplierPhone;
        this.Email = supplierEmail;
    }

    public Supplier() {
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String supplierName) {
        this.Name = supplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String supplierAddress) {
        this.Address = supplierAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String supplierPhone) {
        this.Phone = supplierPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String supplierEmail) {
        this.Email = supplierEmail;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supplierId=" + supplierId + ", supplierName=" + Name + ", supplierAddress=" + Address + ", supplierPhone=" + Phone + ", email=" + Email + '}';
    }
}
