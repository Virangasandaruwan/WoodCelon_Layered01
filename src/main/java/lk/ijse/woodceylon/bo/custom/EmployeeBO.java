package lk.ijse.woodceylon.bo.custom;

import lk.ijse.woodceylon.dto.EmployeeDTO;

import java.util.ArrayList;

public interface EmployeeBO {
    String addEmployee(EmployeeDTO dto) throws Exception;
    String updateEmployee(EmployeeDTO dto) throws Exception;
    String deleteEmployee(int employeeId) throws Exception;
    ArrayList<EmployeeDTO> getAllEmployees() throws Exception;
}
