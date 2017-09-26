/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
//client class to handle client side networking details. Required threading to prevent server lockup
public class Client extends Thread
{
    private Socket c;
    public Client(Socket client)//constructor
    {
        c = client;
    }
    
    @Override
    public void run() //part of thread, each time this is called, a new thread is started.
    {
        try 
        {
            //construct input and output streams.
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            String function = in.readLine(); //read function name in.
            if(function.equalsIgnoreCase("newCustomer")) //test for function sent through
            {
                newCustomer(in, out); //run newCustomer code
            }
            else if(function.equalsIgnoreCase("newDriver"))
            {
                newDriver(in, out); //run new Driver code;
            }
            else if(function.equalsIgnoreCase("update"))
            {
                update(in, out); //run check for update code
            }
            else if(function.equalsIgnoreCase("login"))
            {
                login(in, out); //check if login details are correct;
            }
            else if(function.equalsIgnoreCase("status"))
            {
                status(in, out); //change driver status
            }
            else
            {
                out.writeBytes("NOT UNDERSTOOD\n"); //not understood functionality.
            }
            
            //close all that needs to be closed
            in.close();
            out.close();
            c.close();
            System.out.println("Client Closed!");
        } 
        catch (IOException | SQLException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void status(BufferedReader in, DataOutputStream out) throws IOException, SQLException
    {
        String input = in.readLine(); //read in JSON containing username and status
        JSON_Handler temp = new JSON_Handler(input);

        //change true/false to 1/0 for storage in database
        int value = 0;
        if(temp.getValueFromVar("status").equalsIgnoreCase("true"))
        {
            value = 1;
        }

        //get username for query
        String[] username = temp.getValueFromVar("username").split("_");

        //construct sql query using JSON variables and execute.
        String sql = "UPDATE Driver SET Driver_Status = " + value+ ", Driver_StatusTime = Now() WHERE First_Name = " + username[0] + " AND Last_Name = " + username[1] +";";
        System.out.println(sql);
        out.writeBytes("OK\n");
        WitsCABS_Backend.sendSQLQuery(sql);
    }
    
    public static void login(BufferedReader in, DataOutputStream out) throws IOException, SQLException
    {
        //read JSON file of username and password for validation. Return success or fail accordingly
        String JSON = in.readLine();
        JSON_Handler temp = new JSON_Handler(JSON);
        String[] value = temp.getValueFromVar("username").split("_"); //get first and last name
        //construct sql query
        String sql = "SELECT * FROM Driver WHERE First_Name =" + value[0] + " AND Driver_Last_Name = " + value[1]+ ";";
        System.out.println(sql);
        //get resultset back, i.e. the password and check if password is the same as submitted
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
    
    public static void update(BufferedReader in, DataOutputStream out) throws IOException, SQLException
    {
        //test if there is a new drive for the driver assigned.
        String[] username = in.readLine().split("_");

        //get customer details if drive assigned.
        String sql = "SELECT Customer_ID, Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber, Customer_StartStreet,"
                + "Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb FROM Customer, Drive"
                + " WHERE Drive.Driver_ID = Driver.Driver_ID AND Driver.First_Name = " + username[0] + " AND Driver.Last_Name"
                + "= " + username[1] + "AND Drive.Drive_Status = \"Assigned\"";
        System.out.println(sql);
        ResultSet returned = WitsCABS_Backend.sendSQLQuery(sql);
        //if result returned has at least 1 entry, there is a drive waiting.
        if(returned.getFetchSize() != 0)
        {
            //construct JSON file of customer details to send back
            String temp = "{\"name\":\""+returned.getString("Customer_Name")+"\","
                    + "{\"description\":\""+returned.getString("Customer_Description")+"\","
                    + "{\"phonenumber\":\""+returned.getString("Customer_PhoneNumber")+"\","
                    + "{\"startnumber\":"+returned.getInt("Customer_Start_Number")+","
                    + "{\"startstreet\":\""+returned.getString("Customer_StartStreet")+"\","
                    + "{\"startsuburb\":\""+returned.getString("Customer_StartSuburb")+"\","
                    + "{\"endnumber\":"+returned.getInt("Customer_EndNumber")+","
                    + "{\"endstreet\":\""+returned.getString("Customer_EndStreet")+"\","
                    + "{\"endsuburb\":\""+returned.getString("Customer_EndSuburb")+"\"}";
            //send to device
            out.writeBytes(temp + "\n");

            //update drive status as this has been sent to device.
            String update = "UPDATE Drive SET Drive_Status = \"InProgress\" WHERE Customer_ID = " + returned.getInt("Customer_ID") + ";";
            System.out.println(update);
            WitsCABS_Backend.sendSQLQuery(update);
        }
        else
        {
            //since no results returned, no drive yet.
            out.writeBytes("UPTODATE");
        }
    }
    
    public static void newDriver(BufferedReader in, DataOutputStream out) throws IOException, SQLException
    {
        //new driver being created, so read JSON file with all details and insert into the database for use lated
        System.out.println("newDriver called");
        String JSON = in.readLine();
        JSON_Handler temp = new JSON_Handler(JSON);
        String sql = "INSERT INTO Driver(First_Name, Last_Name,Phone_Number, Car_Registration, Car_Make, Car_Colour,"
                + "Home_Number, Home_StreetName, Home_Area, Driver_Password)"
                + "VALUES("+temp.getWholeValue()+");";
        System.out.println(sql);
        WitsCABS_Backend.sendSQLQuery(sql);
        out.writeBytes("OK\n");
    }
    
    public static void newCustomer(BufferedReader in, DataOutputStream out) throws IOException, SQLException
    {
        //received from call center app, insert customer into database first from JSON file
        out.writeBytes("OK\n");
        String JSON = in.readLine();
        JSON_Handler temp = new JSON_Handler(JSON);
        String sql = "INSERT INTO CUSTOMER(Customer_Name, Customer_Description, Customer_PhoneNumber, Customer_StartNumber,"
                + "Customer_StartStreet, Customer_StartSuburb, Customer_EndNumber, Customer_EndStreet, Customer_EndSuburb)"
                + "VALUES("+temp.getWholeValue()+");";
        System.out.println(sql);
        WitsCABS_Backend.sendSQLQuery(sql);
        out.writeBytes("OK\n");

        //now assign customer to an available driver in database.
        String rec = "SELECT Driver_ID, Home_Number, Home_StreetName, Home_Area, Rank_ID FROM Driver WHERE (Driver_Status = 1 "
                + "AND Home_Area =" + temp.getValueFromVar("startsuburb")+") ORDER BY Driver_StatusTime;";
        System.out.println(rec);
        ResultSet drivers = WitsCABS_Backend.sendSQLQuery(sql);

        //run the assign driver algorithm to find closest driver.
        int driver = assignDriver(drivers, temp.getValueFromVar("startnumber"), temp.getValueFromVar("startstreet"), temp.getValueFromVar("startsuburb"));


        //insert the drive entry to match driver to customer
        String update = "INSERT INTO Drive(Driver_ID, Customer_ID)"
                + "VALUES("+driver+"," + WitsCABS_Backend.sendSQLQuery("SELECT Customer_ID FROM Customer WHERE Customer_Name =" + temp.getValueFromVar("name")+";").getInt("Customer_ID") + ");";
        WitsCABS_Backend.sendSQLQuery(update);
    }
    
    public static int assignDriver(ResultSet r, String num, String street, String suburb) throws SQLException, IOException
    {
        //iterates over the list of drivers to find the closest one to customer
        int bestDriver = 0;
        double bestDistance = Double.MAX_VALUE;
        while(r.next()) //while still more drivers...
        {
            //get distance from google api and if better, assign driver temporarily.
            double d = getDistance(r.getString("Home_Number"), r.getString("Home_Street"), r.getString("Home_Suburb"), num, street, suburb);
            if(d < bestDistance)
            {
                bestDriver = r.getInt("Driver_ID");
            }
        }
        
        return bestDriver; //return the best driver.
    }
    
    public static double getDistance(String dNum, String dStreet, String dSuburb, String cNum, String cStreet, String cSuburb) throws MalformedURLException, IOException
    {
        //connects to googles webservice to check distance in kilometers and returns this
        String u = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" +dNum + "," + dStreet.replace(" ", "+")
                + "," +dSuburb + "&destinations=" + cNum + "," + cStreet.replace(" ", "+") + "," + cSuburb + "&key=AIzaSyB8nyO1LPGEbQexKvRXaVCWKUyVOWcRS-k";
        URL url = new URL(u);
        BufferedReader s = new BufferedReader(new InputStreamReader(url.openStream()));
        String inp;
        double time;
        try 
        {
            while((inp = s.readLine()) != null && !inp.contains("duration"))
            {
                System.out.println(inp);
            }
            inp = s.readLine();
            inp = s.readLine();
            time = Double.parseDouble(inp.split(":")[1].replace(" ", ""));
        } 
        finally 
        {
          s.close();
        }
        return time;
    }
    
}
