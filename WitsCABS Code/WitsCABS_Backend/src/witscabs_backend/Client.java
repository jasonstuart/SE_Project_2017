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
                out.writeBytes("OK");
                String JSON = in.readLine();
                out.writeBytes("OK");
            }
            else
            {
                out.writeBytes("NOT UNDERSTOOD");
            }
            
            in.close();
            out.close();
            c.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
