package lk.ijse.woodceylon.dto;

public class EmployeeDTO {
    private int Employee_ID;
    private String Name;
    private String Phone;
    private String Email;
    private String Address;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int Employee_ID, String Name, String Phone, String Email, String Address) {
        this.Employee_ID = Employee_ID;
        this.Name = Name;
        this.Phone = Phone;
        this.Email = Email;
        this.Address = Address;
    }

    public int getEmployee_ID() {
        return Employee_ID;
    }

    public void setEmployee_ID(int Employee_ID) {
        this.Employee_ID = Employee_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    @Override
    public String toString() {
        return "Employee{" + "Employee_ID=" + Employee_ID + ", Name=" + Name + ", Phone=" + Phone + ", Email=" + Email + ", Address=" + Address + '}';
    }
    
    
}


