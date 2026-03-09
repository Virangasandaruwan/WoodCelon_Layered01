package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.EmployeeBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.EmployeeDAO;
import lk.ijse.woodceylon.dto.EmployeeDTO;

import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private final EmployeeDAO employeeDAO =
            (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public String addEmployee(EmployeeDTO dto) throws Exception {
        boolean added = employeeDAO.save(dto);
        return added ? "Employee Saved Successfully" : "Employee Save Failed";
    }

    @Override
    public String updateEmployee(EmployeeDTO dto) throws Exception {
        boolean updated = employeeDAO.update(dto);
        return updated ? "Employee Updated Successfully" : "Employee Update Failed";
    }

    @Override
    public String deleteEmployee(int employeeId) throws Exception {
        boolean deleted = employeeDAO.delete(employeeId);
        return deleted ? "Employee Deleted Successfully" : "Employee Delete Failed";
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws Exception {
        return employeeDAO.getAll();
    }
}