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
    String Home_Area, First_Name, Last_Name, Phone_Number, Home_StreetName, Car_Reg, Home_Number, Car_Make, Car_Colour, Driver_Password; //strings to store the user input from edittests from the GUI
    EditText passwordET, firstNameET, lastNameET, phoneET, homePhoneET, addressET, carRegET, carMakeET, carColourET; //edittexts to store the user's input as an EditText object

    //initiates and calls up the sign up activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);
    }

    //acquires the relevent input from the user and sends it to the server
    public void getSignUpDetails(final View v) {
        /*the following 29 lines obtain the users input from the EditText fields and
        * converts the EditText objects to strings*/
        firstNameET = (EditText) findViewById(R.id.firstNameE);
        First_Name = firstNameET.getText().toString();
        lastNameET = (EditText) findViewById(R.id.lastNameE);
        Last_Name = lastNameET.getText().toString();
        phoneET = (EditText) findViewById(R.id.phoneE);
        Phone_Number = phoneET.getText().toString();
        homePhoneET = (EditText) findViewById(R.id.homePhoneE);
        Home_Number = homePhoneET.getText().toString();
        addressET = (EditText) findViewById(R.id.addressE);
        Home_StreetName = addressET.getText().toString();
        carRegET = (EditText) findViewById(R.id.carRegE);
        Car_Reg = carRegET.getText().toString();
        carMakeET = (EditText) findViewById(R.id.carMakeE);
        Car_Make = carMakeET.getText().toString();
        carColourET = (EditText) findViewById(R.id.carColourE);
        Car_Colour = carColourET.getText().toString();
        //Home_Area = "Sandton";
        passwordET = (EditText) findViewById(R.
                id.passwordE);
        Driver_Password = passwordET.getText().toString();
        //Driver_Password = "password";
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            int id = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(id);
            int radioId = radioGroup.indexOfChild(radioButton);
            RadioButton radioButton1 = (RadioButton) radioGroup.getChildAt(radioId);
            Home_Area = (String) radioButton1.getText();
        }
        if (Home_Area.isEmpty()) Home_Area = "Sandton";

        /*varNames: stores the string variable names
        * varTypes: stores the variable types of the variables
        * varActualVariables: stores the values of the variables to be sent to the serve*/
        String[] varNames = {"First_Name", "Last_Name", "Phone_Number", "Car_Reg", "Car_Make", "Car_Colour", "Home_Number", "Home_StreetName", "Home_Area", "Driver_Password"};
        String[] varTypes = {"String", "String", "String", "String", "String", "String", "int", "String", "String", "String"};
        String[] varActualVariables = {First_Name.toString(), Last_Name.toString(), Phone_Number.toString(), Car_Reg.toString(), Car_Make.toString(), Car_Colour.toString(), Home_Number.toString(), Home_StreetName.toString(), Home_Area.toString(), Driver_Password.toString()};

        String json = JSON_Handler.constructJSONString(varNames, varTypes, varActualVariables); //converts the variables to be sent into one JSON string
        AsyncClassSignUp asyncClassSignUp = new AsyncClassSignUp(json); //specifies the JSON string to be sent to the server
        asyncClassSignUp.execute(json);  //initiates the sending of the JSON string to the server

        startActivity(new Intent(getApplicationContext(), loginPage.class)); //starts the next activity after all of the above code runs successfully

//        String boy=asyncClassSignUp.feedback;
   /*     if(boy.equals("OK"))
            Toast.makeText(getApplicationContext(),"Signup work",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(),loginPage.class));
        else
            Toast.makeText(getApplicationContext(),"Signup failed",Toast.LENGTH_SHORT).show();*/
    }
}