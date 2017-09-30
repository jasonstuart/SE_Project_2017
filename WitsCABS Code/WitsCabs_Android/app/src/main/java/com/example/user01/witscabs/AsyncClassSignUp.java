package com.example.user01.witscabs;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by User01 on 9/27/2017.
 */

public class AsyncClassSignUp extends AsyncTask<String,Void,String> {
    String jsonString, feedback;
    AsyncHandler asyncHandler;
    public AsyncClassSignUp(String s){
        this.jsonString=s;
    }
    @Override
    protected String doInBackground(String... params) {
        Socket client;
        DataOutputStream out;
        BufferedReader read;
        try {
            client=new Socket("192.168.8.100",9987);
            read=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new DataOutputStream(client.getOutputStream());
            out.writeBytes("newDriver\n");
            read.readLine();
            out.writeBytes(jsonString+"\n");
            feedback=read.readLine();
            out.close(); read.close(); client.close();
        }catch(IOException e){feedback="failed to try";}
        return feedback;
    }
/*    @Override
    protected void onPostExecute(String feedback) {
        asyncHandler.handleResponse((String) feedback);
    }*/
}
