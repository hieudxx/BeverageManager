
package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Helper {
    public static ResultSet selectTongQuat(String sql, Object...params) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = DBConnect.getConnect();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            close(con, ps, rs);
            return null;
        } 
    }
    
    public static void close(Connection con, PreparedStatement ps) {
        try {
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            rs.close();
            close(con, ps);
        } catch (SQLException e) {
            e.printStackTrace();
//            Logger.getLogger(JDBC_Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public static int updateTongQuat(String sql, Object...params) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DBConnect.getConnect();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(con, ps);
        }
     }
}
