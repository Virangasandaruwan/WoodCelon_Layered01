//package lk.ijse.woodceylon.model;
//
//public class DashboardModel {
//}
package lk.ijse.woodceylon.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.woodceylon.db.DBConnection;
import lk.ijse.woodceylon.util.CrudUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class DashboardModel {

    public double getTotalEarnings() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(op.Quantity * CAST(p.Price AS DECIMAL(10,2))) FROM Order_Product op JOIN Product p ON op.Product_ID = p.Product_ID";
        ResultSet rst = CrudUtil.execute(sql);
        if (rst.next()) {
            return rst.getDouble(1);
        }
        return 0;
    }

    public int getTotalItemsSold() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(Quantity) FROM Order_Product";
        ResultSet rst = CrudUtil.execute(sql);
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
    public void printReport() throws SQLException, JRException, ClassNotFoundException {
        Connection conn = DBConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/lk/ijse/WoodCeylon/report/Inventory_Report.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
        JasperViewer.viewReport(jp, false);
    }


}