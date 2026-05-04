
package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Helper {
    public static ResultSet selectTongQuat(String sql, Object...params) {
try {
            
            Connection con = DBConnect.getConnect(); 
            PreparedStatement ps = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void close( PreparedStatement ps) {
try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public static void close(PreparedStatement ps, ResultSet rs) {
try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
     
     public static int updateTongQuat(String sql, Object...params) {
// try-with-resources để tự động đóng ps sau khi chạy xong
        //không để DBConnect.getConnect() vào trong ngoặc này 
        // để tránh việc đóng nhầm kết nối dùng chung.
        try (PreparedStatement ps = DBConnect.getConnect().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
