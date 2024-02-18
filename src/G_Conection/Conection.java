/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package G_Conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 /**
 *
 * @author mario
 */
public class Conection {
    
    private static Connection mysql_conect;
    public static Connection conection() throws SQLException {
        if (mysql_conect == null) {
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String Url = "jdbc:mysql://localhost:3306/db_reek";
            String User = "root";
            String Pass = "";
            
            mysql_conect = DriverManager.getConnection(Url, User, Pass);
            } catch (ClassNotFoundException | SQLException e) {
                throw new SQLException("Koneksi gagal", e);
            }
        }
        return mysql_conect;
    }
}