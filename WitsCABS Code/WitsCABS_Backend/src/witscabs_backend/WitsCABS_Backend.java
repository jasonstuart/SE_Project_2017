/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason
 */
public class WitsCABS_Backend {

    private static Connection connection;
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException{
        connection = setupDatabaseConnection(); //setup database connection
        ServerSocket s = new Server().startServer(9987); //start server to listen for connections
        boolean run = true;
        while(run)
        {
            Client client = new Client(s.accept());
            System.out.println("Incoming Connection!");
            client.start();
        }
        //test(connection);
        connection.close();
    }
    
    public static Connection setupDatabaseConnection()
    {
        String url = "jdbc:mysql://localhost:3306/WitsCABS?useSSL=false";
        String username = "root";
        String password = "jason";

        System.out.println("Connecting database...");
        try 
        {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) 
        {
            System.out.println("Cannot Connect to database, reason:" + ex);
        }
        System.out.println("Database Connected");
        return connection;
    }
    
    public static ResultSet sendSQLQuery(String sql)
    {
        try 
        {
            Statement s = connection.createStatement();
            if(sql.contains("SELECT"))
            {
                ResultSet rs = s.executeQuery(sql);
                System.out.println(rs);
                return rs;
            }
            else
            {
                int r = s.executeUpdate(sql);
                System.out.println(r);
                return null;
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Failed to connect to Database. Reason:" + ex);
        }
        return null;
    }
    
}
