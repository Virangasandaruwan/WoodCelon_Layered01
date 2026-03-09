package lk.ijse.woodceylon.dao.custom.impl;

import lk.ijse.woodceylon.dao.custom.EmployeeDAO;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.dto.EmployeeDTO;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean save(EmployeeDTO dto) throws Exception {
        return CrudUtil.execute(
                "INSERT INTO Employee (Employee_ID, Name, Phone, Email, Address) VALUES (?,?,?,?,?)",
                dto.getEmployee_ID(),
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        );
    }

    @Override
    public boolean update(EmployeeDTO dto) throws Exception {
        return CrudUtil.execute(
                "UPDATE Employee SET Name=?, Phone=?, Email=?, Address=? WHERE Employee_ID=?",
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getEmployee_ID()
        );
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM Employee WHERE Employee_ID=?",
                id
        );
    }

    @Override
    public boolean exist(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT Employee_ID FROM Employee WHERE Employee_ID=?", id);
        return rst.next();
    }

    @Override
    public EmployeeDTO search(Integer id) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE Employee_ID=?", id);
        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getInt("Employee_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            );
        }
        return null;
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws Exception {
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            list.add(new EmployeeDTO(
                    rst.getInt("Employee_ID"),
                    rst.getString("Name"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address")
            ));
        }
        return list;
    }

    public void printReport() throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream(
                "/lk/ijse/woodceylon/report/EmployeeReport.jrxml"
        );
        if (inputStream == null) {
            throw new Exception("Employee report template not found!");
        }
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }
}