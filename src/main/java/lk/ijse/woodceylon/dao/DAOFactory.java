package lk.ijse.woodceylon.dao;

import lk.ijse.woodceylon.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private  DAOFactory(){
    }
    public static DAOFactory getInstance(){
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER, EMPLOYEE, SUPPLIER, ORDER,  PRODUCT
    }
    public SuperDAO getDAO(DAOTypes daoType){
        switch(daoType){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            default:
                return null;
        }
    }
}
