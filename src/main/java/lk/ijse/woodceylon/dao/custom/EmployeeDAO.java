package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.dto.EmployeeDTO;
import lk.ijse.woodceylon.entity.Employee;

import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee, Integer> {

    boolean save(Employee employee) throws Exception;

    boolean update(Employee employee) throws Exception;

    ArrayList<Employee> getAll() throws Exception;

    void printReport() throws  Exception;



    // boolean exist(Integer id) throws Exception;
    // String generateNextId() throws Exception;
}