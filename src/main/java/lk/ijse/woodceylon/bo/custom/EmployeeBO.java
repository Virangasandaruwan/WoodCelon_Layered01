package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.dto.EmployeeDTO;
import lk.ijse.woodceylon.entity.Employee;

import java.util.ArrayList;

public interface EmployeeBO {
    String addEmployee(EmployeeDTO dto) throws Exception;
    String updateEmployee(EmployeeDTO dto) throws Exception;
    String deleteEmployee(int employeeId) throws Exception;
    ArrayList<EmployeeDTO> getAllEmployees() throws Exception;


    public EmployeeDTO searchEmployee(int employeeId) throws Exception ;

    void printReport()throws Exception;
}
