package com.example.user01.witscabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/*import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;*/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class signUpPage extends Activity {
    String Home_Area, First_Name, Last_Name, Phone_Number, Home_StreetName, Car_Reg, Home_Number, Car_Make, Car_Colour, Driver_Password;
    EditText passwordET, firstNameET, lastNameET, phoneET, homePhoneET, addressET, carRegET, carMakeET, carColourET;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);
    }
    public void getSignUpDetails(final View v){
        firstNameET = (EditText)findViewById(R.id.firstNameE);
        First_Name = firstNameET.getText().toString();
        lastNameET = (EditText)findViewById(R.id.lastNameE);
        Last_Name = lastNameET.getText().toString();
        phoneET = (EditText)findViewById(R.id.phoneE);
        Phone_Number = phoneET.getText().toString();
        homePhoneET = (EditText)findViewById(R.id.homePhoneE);
        Home_Number = homePhoneET.getText().toString();
        addressET = (EditText)findViewById(R.id.addressE);
        Home_StreetName = addressET.getText().toString();
        carRegET = (EditText)findViewById(R.id.carRegE);
        Car_Reg = carRegET.getText().toString();
        carMakeET = (EditText)findViewById(R.id.carMakeE);
        Car_Make = carMakeET.getText().toString();
        carColourET = (EditText)findViewById(R.id.carColourE);
        Car_Colour = carColourET.getText().toString();
        Home_Area = "Sandton";
        //passwordET = (EditText)findViewById(R.id.passwordE);
       // Driver_Password = passwordET.getText().toString();
        Driver_Password = "password";
       /* RadioGroup radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        if(radioGroup.getCheckedRadioButtonId()!=-1){
            int id=radioGroup.getCheckedRadioButtonId();
            View radioButton=radioGroup.findViewById(id);
            int radioId=radioGroup.indexOfChild(radioButton);
            RadioButton radioButton1=(RadioButton) radioGroup.getChildAt(radioId);
            area=(String) radioButton1.
        }*/

        String[] varNames = {"First_Name", "Last_Name", "Phone_Number", "Car_Reg", "Car_Make", "Car_Colour", "Home_Number", "Home_StreetName", "Home_Area", "Driver_Password"};
        String[] varTypes = {"String","String","String","String","String","String","int", "String", "String", "String"};
        String[] varActualVariables = {First_Name.toString(), Last_Name.toString(), Phone_Number.toString(), Car_Reg.toString(), Car_Make.toString(), Car_Colour.toString(), Home_Number.toString(), Home_StreetName.toString(), Home_Area.toString(), Driver_Password.toString()};

        String json = JSON_Handler.constructJSONString(varNames, varTypes, varActualVariables);
        AsyncClassSignUp asyncClassSignUp=new AsyncClassSignUp(json);
        asyncClassSignUp.execute(json);

        startActivity(new Intent(getApplicationContext(),loginPage.class));

//        String boy=asyncClassSignUp.feedback;
   /*     if(boy.equals("OK"))
            Toast.makeText(getApplicationContext(),"Signup work",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),loginPage.class));
        else
            Toast.makeText(getApplicationContext(),"Signup failed",Toast.LENGTH_SHORT).show();
*/
/*        AsyncHandler asyncHandler= new AsyncHandler() {
            @Override
            public void handleResponse(String response) {
                if(response.equals("OK"))
                    startActivity(new Intent(getApplicationContext(),loginPage.class));
                else
                    Toast.makeText(getApplicationContext(),"Signup failed",Toast.LENGTH_SHORT).show();
            }
        };
        asyncHandler.handleResponse(asyncClassSignUp.feedback);*/

  //      System.out.println(json);

        /*    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("firstName", name));
            nameValuePairs.add(new BasicNameValuePair("lastName", lastName));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("homeNumber", homeNumber));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("carReg", carReg))99999999999999999999999999999999;
            nameValuePairs.add(new BasicNameValuePair("carMake", carMake));
            nameValuePairs.add(new BasicNameValuePair("carColour", carColour));
            asyncHttpPost.execute("http://10.30.40.41");

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