/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DBConnect {

    public static final String HOSTNAME = "localhost";
    public static final String PORT = "1433";
    public static final String DBNAME = "QuanLyBanNuoc";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "123";
    static String url = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";" + "databaseName=" + DBNAME + ";encrypt=true;trustServerCertificate=true;";
    static String user = "sa";
    static String pass = "123";
    private static Connection conn;
    
    public static Connection getConnect() {
try {
            // Nếu kết nối chưa tồn tại hoặc đã bị đóng thì mới tạo mới
            if (conn == null || conn.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(url, user, pass);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection con = getConnect();
        if (con.equals("")) {
            System.out.println("loi ket noi");
        } else {
            System.out.println("ket noi thanh cong");
        }

    }
}
