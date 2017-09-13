/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Jason
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException 
    {
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=70,oregon+street,Northcliff&destinations=6,ian+road,floracliffe&key=AIzaSyB8nyO1LPGEbQexKvRXaVCWKUyVOWcRS-k");
        BufferedReader s = new BufferedReader(new InputStreamReader(url.openStream()));
        String inp;
        try 
        {
            while((inp = s.readLine()) != null)
            {
                System.out.println(inp);
            }
        } 
        finally 
        {
          s.close();
        }
    }
    
}
