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
                
                String rec = "SELECT Driver_ID, Home_Number, Home_StreetName, Home_Area, Rank_ID FROM Driver, TaxiRank WHERE (Driver_Status = 1 "
                        + "AND Home_Area =" + temp.getValueFromVar("startsuburb")+") OR (Driver.Rank_ID = TaxiRank.Rank_ID AND TaxiRank.Rank_Suburb="
                        + temp.getValueFromVar("startsuburb") + ");";
                System.out.println(rec);
                ResultSet drivers = WitsCABS_Backend.sendSQLQuery(sql);
                
                int driver = assignDriver(drivers, temp.getValueFromVar("startnumber"), temp.getValueFromVar("startstreet"), temp.getValueFromVar("startsuburb"));
                
                
                String update = "INSERT INTO Drive(Driver_ID, Customer_ID)"
                        + "VALUES("+driver+"," + WitsCABS_Backend.sendSQLQuery("SELECT Customer_ID FROM Customer WHERE Customer_Name =" + temp.getValueFromVar("name")+";").getInt("Customer_ID") + ");";
                WitsCABS_Backend.sendSQLQuery(update);
            }
            else if(function.equalsIgnoreCase("newDriver"))
            {
                out.writeBytes("OK\n");
                String JSON = in.readLine();
                JSON_Handler temp = new JSON_Handler(JSON);
                String sql = "INSERT INTO Driver(First_Name, Last_Name,Phone_Number, Car_Registration, Car_Make, Car_Colour,"
                        + "Home_Number, Home_StreetName, Home_Area, Driver_Password)"
                        + "VALUES("+temp.getWholeValue()+");";
                System.out.println(sql);
                WitsCABS_Backend.sendSQLQuery(sql);
                out.writeBytes("OK\n");
            }
            else if(function.equalsIgnoreCase("update"))
            {
                //TODO Test this functionality for bugs
                out.writeBytes("OK\n");
                String[] username = in.readLine().split("_");
                
                String sql = "SELECT Customer_ID, Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber, Customer_StartStreet,"
                        + "Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb FROM Customer, Drive"
                        + " WHERE Drive.Driver_ID = Driver.Driver_ID AND Driver.First_Name = " + username[0] + " AND Driver.Last_Name"
                        + "= " + username[1] + "AND Drive.Drive_Status = \"Assigned\"";
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
                    
                    out.writeBytes(temp + "\n");
                    
                    String update = "UPDATE Drive SET Drive_Status = \"InProgress\" WHERE Customer_ID = " + returned.getInt("Customer_ID") + ";";
                    System.out.println(update);
                    WitsCABS_Backend.sendSQLQuery(update);
                }
                else
                {
                    out.writeBytes("UPTODATE");
                }
            }
            else if(function.equalsIgnoreCase("login"))
            {
                String JSON = in.readLine();
                JSON_Handler temp = new JSON_Handler(JSON);
                String[] value = temp.getValueFromVar("username").split("_");
                String sql = "SELECT * FROM Driver WHERE First_Name =" + value[0] + " AND Driver_Last_Name = " + value[1]+ ";";
                System.out.println(sql);
                ResultSet result = WitsCABS_Backend.sendSQLQuery(sql);
                if(result.getString("Driver_Password").equals(temp.getValueFromVar("password")))
                {
                    out.writeBytes("SUCCESS");
                }
                else
                {
                    out.writeBytes("FAIL");
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
    
    public static int assignDriver(ResultSet r, String num, String street, String suburb) throws SQLException
    {
        int bestDriver = 0;
        double bestDistance = Double.MAX_VALUE;
        while(r.next())
        {
            if(r.getString("Rank_ID") != null)
            {
                double d = getDistance(r.getString("Home_Number"), r.getString("Home_Street"), r.getString("Home_Suburb"), num, street, suburb);
                if(d < bestDistance)
                {
                    bestDriver = r.getInt("Driver_ID");
                }
            }
        }
        
        return bestDriver;
    }
    
    public static double getDistance(String dNum, String dStreet, String dSuburb, String cNum, String cStreet, String cSuburb)
    {
        //TODO Implement.
        return -1;
    }
    
}
