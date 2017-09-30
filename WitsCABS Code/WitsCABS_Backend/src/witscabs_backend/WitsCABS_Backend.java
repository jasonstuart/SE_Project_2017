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
    public static void main(String[] args) throws SQLException, IOException{ //entry point into server
        connection = setupDatabaseConnection(); //setup database connection
        ServerSocket s = new Server().startServer(9987); //start server to listen for connections
        boolean run = true;
        while(run) //keep checking for new connections.
        {
            Client client = new Client(s.accept());
            System.out.println("Incoming Connection!");
            client.start();
        }
        connection.close();
    }
    
    //method to connect to mysql server to access database. 
    public static Connection setupDatabaseConnection()
    {
        //server specifics
        String url = "jdbc:mysql://localhost:3306/WitsCABS?useSSL=false";
        String username = "root";
        String password = "jason";

        System.out.println("Connecting database...");
        try 
        {
            //try get connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) 
        {
            //connection failed
            System.out.println("Cannot Connect to database, reason:" + ex);
        }
        System.out.println("Database Connected");
        return connection;
    }
    
    //send query to database and get response dependent on query type.
    public static ResultSet sendSQLQuery(String sql)
    {
        try 
        {
            //create a statement.
            Statement s = connection.createStatement();
            if(sql.contains("SELECT")) //check if select (for result set) or anything else (no result set)
            {
                //needs to return results, construct it and return
                ResultSet rs = s.executeQuery(sql);
                System.out.println(rs);
                return rs;
            }
            else
            {
                //no result set, so just execute and output success code.
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
