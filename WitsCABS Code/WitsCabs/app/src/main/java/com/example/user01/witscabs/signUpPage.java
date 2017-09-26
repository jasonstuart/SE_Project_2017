package com.example.user01.witscabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class signUpPage extends Activity {
    String name, lastName, phone, homeNumber, address, area, carReg, carMake, carColour;
    EditText firstNameET, lastNameET, phoneET, homePhoneET, addressET, carRegET, carMakeET, carColourET;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);
    }
    public void getSignUpDetails(View v){
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
 Toast.makeText(signUpPage.this,"before serrver",Toast.LENGTH_LONG);

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
        }catch(IOException e){System.out.println(e);}
    }
}