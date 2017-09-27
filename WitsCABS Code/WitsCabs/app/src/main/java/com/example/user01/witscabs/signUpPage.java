package com.example.user01.witscabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class signUpPage extends Activity {
    String name, lastName, phone, homeNumber, address, area, carReg, carMake, carColour;
    EditText firstNameET, lastNameET, phoneET, homePhoneET, addressET, carRegET, carMakeET, carColourET;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);
    }
    public void getSignUpDetails(final View v){
        firstNameET = (EditText)findViewById(R.id.firstNameE);
        name = firstNameET.getText().toString();
        lastNameET = (EditText)findViewById(R.id.lastNameE);
        lastName = lastNameET.getText().toString();
        phoneET = (EditText)findViewById(R.id.phoneE);
        phone = phoneET.getText().toString();
        homePhoneET = (EditText)findViewById(R.id.homePhoneE);
        homeNumber = homePhoneET.getText().toString();
        addressET = (EditText)findViewById(R.id.addressE);
        address = addressET.getText().toString();
        carRegET = (EditText)findViewById(R.id.carRegE);
        carReg = carRegET.getText().toString();
        carMakeET = (EditText)findViewById(R.id.carMakeE);
        carMake = carMakeET.getText().toString();
        carColourET = (EditText)findViewById(R.id.carColourE);
        carColour = carColourET.getText().toString();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://<ip address>:3000");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("firstName", name));
            nameValuePairs.add(new BasicNameValuePair("lastName", lastName));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("homeNumber", homeNumber));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("carReg", carReg));
            nameValuePairs.add(new BasicNameValuePair("carMake", carMake));
            nameValuePairs.add(new BasicNameValuePair("carColour", carColour));
            AsyncHttpPost asyncHttpPost = new AsyncHttpPost(nameValuePairs, new AsyncHandler() {
                @Override
                public void handleResponse(String response) {
                    response=response.trim();
                    if(response.equals("success")){
                        Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getApplicationContext(),"Not Registered",Toast.LENGTH_SHORT).show();
                }
            });
            asyncHttpPost.execute("http://");

/*        Toast.makeText(signUpPage.this,"before serrver",Toast.LENGTH_LONG);
        Socket client; String feedback;
        DataOutputStream out;
        BufferedReader read;
        try {
            client=new Socket("10.30.1.170",9987);
            read=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new DataOutputStream(client.getOutputStream());
            out.writeBytes(name); out.writeBytes(lastName); out.writeBytes(phone);
            out.writeBytes(homeNumber); out.writeBytes(address); out.writeBytes(carReg);
            out.writeBytes(carMake); out.writeBytes(carColour);
            feedback=read.readLine();
            if(feedback.equals("SUCCESS")) System.out.println("change to main activity");
            else Toast.makeText(signUpPage.this,"Please try again, request failed",Toast.LENGTH_LONG).show();
        }catch(IOException e){System.out.println(e);}*/
    }
}