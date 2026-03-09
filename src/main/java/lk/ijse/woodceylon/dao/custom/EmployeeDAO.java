package lk.ijse.woodceylon.dao.custom;

import lk.ijse.woodceylon.dao.CrudDAO;
import lk.ijse.woodceylon.dto.EmployeeDTO;

import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<EmployeeDTO, Integer> {

    ArrayList<EmployeeDTO> getAll() throws Exception;


    // boolean exist(Integer id) throws Exception;
    // String generateNextId() throws Exception;
}