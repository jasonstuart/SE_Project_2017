package com.example.user01.witscabs;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by User01 on 9/28/2017.
 */

public class AsyncClassLogin extends AsyncTask<String,Void,String> {
    String jsonString, feedback; //variables: jsonString to store the JSON string to be sent to the server, and feedback stores the response from server

    //sets the JSON string variable
    public AsyncClassLogin(String s){
        this.jsonString=s;
    }

    //deals with the notworking
    @Override
    protected String doInBackground(String... params) {
        Socket client;
        DataOutputStream out;
        BufferedReader read;
        try {//tries the following code
            client=new Socket("192.168.8.100",9987); //initiating socket with ip address and port no.
            read=new BufferedReader(new InputStreamReader(client.getInputStream())); //initiates buffferedreader to get response from server
            out=new DataOutputStream(client.getOutputStream()); //initiates outputstream to send data to the server
            out.writeBytes("Login\n"); //sends function required to be performeed by server
            read.readLine();
            out.writeBytes(jsonString+"\n"); //sends JSON string to server
            feedback=read.readLine(); //gets response from server
            out.close(); read.close(); client.close(); //closes all communications to server
        }catch(IOException e){feedback="failed to try";}//if above code fails this is executed
        return feedback;
    }
}
