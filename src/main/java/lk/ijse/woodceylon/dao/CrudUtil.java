package lk.ijse.woodceylon.dao;

import lk.ijse.woodceylon.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T  execute(String sql, Object...object) throws SQLException, ClassNotFoundException{

        Connection conn = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = conn.prepareStatement(sql);

        for(int i = 0; i < object.length; i++){
            pstm.setObject(i + 1, object[i]);
        }

        if((sql.startsWith("select") || sql.startsWith("SELECT"))){
            ResultSet results = pstm.executeQuery();
            return (T)results;
        }
        else{
            int result = pstm.executeUpdate();
            return (T)(Boolean)(result > 0);
        }
    }
}
