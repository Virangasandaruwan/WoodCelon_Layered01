package lk.ijse.woodceylon.bo.custom.impl;

import lk.ijse.woodceylon.bo.custom.EmployeeBO;
import lk.ijse.woodceylon.dao.DAOFactory;
import lk.ijse.woodceylon.dao.custom.EmployeeDAO;
import lk.ijse.woodceylon.dto.EmployeeDTO;
import lk.ijse.woodceylon.entity.Employee;

import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private final EmployeeDAO employeeDAO =
            (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public String addEmployee(EmployeeDTO dto) throws Exception {
        Employee employee = new Employee(
                dto.getEmployee_ID(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()

        );
        boolean isSaved = employeeDAO.save(employee);
        return isSaved ? "Employee Saved Successfully" : "Employee Save Failed";
    }

    @Override
    public String updateEmployee(EmployeeDTO dto) throws Exception {
        Employee employee = new Employee(
                dto.getEmployee_ID(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()

        );
        boolean updated = employeeDAO.update(employee);
        return updated ? "Employee Updated Successfully" : "Employee Update Failed";
    }

    @Override
    public String deleteEmployee(int employeeId) throws Exception {
        boolean deleted = employeeDAO.delete(employeeId);
        return deleted ? "Employee Deleted Successfully" : "Employee Delete Failed";
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws Exception {
        ArrayList<Employee> allEmployees = employeeDAO.getAll();

        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee e : allEmployees) {
            employeeDTOs.add(new EmployeeDTO(
                    e.getEmployee_ID(),
                    e.getName(),
                    e.getPhone(),
                    e.getEmail(),
                    e.getAddress()

            ));
        }
        return employeeDTOs;
    }



    @Override
    public EmployeeDTO searchEmployee(int employeeId) throws Exception {
        Employee e = employeeDAO.search(employeeId);
        if (e != null) {
            return new EmployeeDTO(
                    e.getEmployee_ID(),
                    e.getName(),
                    e.getPhone(),
                    e.getEmail(),
                    e.getAddress()
            );
        }
        return null;
    }

    @Override
    public void printReport() throws Exception {

    }




}