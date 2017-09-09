/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class Client extends Thread
{
    private Socket c;
    public Client(Socket client)
    {
        c = client;
    }
    
    @Override
    public void run()
    {
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            String function = in.readLine();
            if(function.equalsIgnoreCase("newCustomer"))
            {
                out.writeBytes("OK\n");
                String JSON = in.readLine();
                JSON_Handler temp = new JSON_Handler(JSON);
                String sql = "INSERT INTO CUSTOMER(Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber,"
                        + "Customer_StartStreet, Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb)"
                        + "VALUES("+temp.getWholeValue()+");";
                System.out.println(sql);
                WitsCABS_Backend.sendSQLQuery(sql);
                out.writeBytes("OK\n");
                //TODO add code to assign customer to available driver using routing algorithm
            }
            else if(function.equalsIgnoreCase("newDriver"))
            {
                out.writeBytes("OK\n");
                String JSON = in.readLine();
                JSON_Handler temp = new JSON_Handler(JSON);
                String sql = "INSERT INTO Driver(First_Name, Last_Name,Phone_Number, Car_Registration, Car_Make, Car_Colour,"
                        + "Home_Number, Home_StreetName, Home_Area)"
                        + "VALUES("+temp.getWholeValue()+");";
                System.out.println(sql);
                WitsCABS_Backend.sendSQLQuery(sql);
                out.writeBytes("OK\n");
            }
            else if(function.equalsIgnoreCase("update"))
            {
                //TODO Test this functionality for bugs
                out.writeBytes("OK\n");
                String driver_id = in.readLine();
                String sql = "SELECT Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber, Customer_StartStreet,"
                        + "Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb FROM Customer, Drive"
                        + " WHERE Drive.Driver_ID = " + driver_id + "AND Drive.Drive_Status = \"Assigned\"";
                System.out.println(sql);
                ResultSet returned = WitsCABS_Backend.sendSQLQuery(sql);
                if(returned.getFetchSize() != 0)
                {
                    String temp = "{\"name\":\""+returned.getString("Customer_Name")+"\","
                            + "{\"description\":\""+returned.getString("Customer_Description")+"\","
                            + "{\"phonenumber\":\""+returned.getString("Customer_PhoneNumber")+"\","
                            + "{\"startnumber\":"+returned.getInt("Customer_Start_Number")+","
                            + "{\"startstreet\":\""+returned.getString("Customer_StartStreet")+"\","
                            + "{\"startsuburb\":\""+returned.getString("Customer_StartSuburb")+"\","
                            + "{\"endnumber\":"+returned.getInt("Customer_EndNumber")+","
                            + "{\"endstreet\":\""+returned.getString("Customer_EndStreet")+"\","
                            + "{\"endsuburb\":\""+returned.getString("Customer_EndSuburb")+"\"}";
                    
                    //TODO Implement sending JSON FILE TO Smartphone
                }
                else
                {
                    out.writeBytes("UPTODATE");
                }
            }
            else
            {
                out.writeBytes("NOT UNDERSTOOD\n");
            }
            
            in.close();
            out.close();
            c.close();
            System.out.println("Client Closed!");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
