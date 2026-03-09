package lk.ijse.woodceylon.bo;

import lk.ijse.woodceylon.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory instance;
    private BOFactory(){}
    public static BOFactory getInstance() {
        return instance==null?instance=new BOFactory():instance;
    }
    public enum BOTypes{
        CUSTOMER,
        ITEM,
        PLACE_ORDER,
        EMPLOYEE,
        SUPPLIER,
        ORDER,
        PRODUCT
    }
    public SuperBO getBOFactory(BOTypes boType){
        switch (boType){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
//                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            default:
                return null;
        }
    }
}
