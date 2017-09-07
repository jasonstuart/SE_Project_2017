/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class WitsCABS_Backend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/WitsCABS?useSSL=false";
        String username = "root";
        String password = "jason";

        System.out.println("Connecting database...");
        try 
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM Driver;");
            while(rs.next())
            {
                System.out.println(rs.getString("Last_Name"));
            }
            
            connection.close();
        } 
        catch (SQLException ex) 
        {
            System.out.println("Failed to connect to Database. Reason:" + ex);
        }
        
    }
    
}
