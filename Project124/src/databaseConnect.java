/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
import java.sql.*;
import javax.swing.*;

public class databaseConnect {
    Connection conn=null;
    public static Connection ConnectDb(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\ChattingLive\\ChattingLive.sqlite");  //include the path of sqlite file
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
