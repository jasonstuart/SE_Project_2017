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
    @Override
    protected String doInBackground(String... params) {
        Socket client;
        DataOutputStream out;
        BufferedReader read;
        try {
            client=new Socket("10.30.1.170",9987);
            read=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new DataOutputStream(client.getOutputStream());

        }catch(IOException e){System.out.println(e);}
        return "";
    }
}
