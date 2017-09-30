/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package witscabs_backend;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Jason
 */
//Server Class that handles all server settings
public class Server
{
    private ServerSocket s;
    
    public Server()
    {
        
    }
    
    public ServerSocket startServer(int portNum)
    {
        //starts the server to start listening for connections.
        System.out.println("Starting Server Socket...");
        try 
        {
            s = new ServerSocket(portNum);
            System.out.println("Socket Listening for connections...");
            return s;
        } 
        catch (IOException ex) 
        {
            System.out.println("Could Not Start Server On Port: " + portNum);
            return null;
        }
    }
}
