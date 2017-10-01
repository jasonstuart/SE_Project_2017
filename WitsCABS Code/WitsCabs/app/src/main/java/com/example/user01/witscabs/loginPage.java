package com.example.user01.witscabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;


public class loginPage extends Activity {
    EditText usernameE,passwordE; //edittexts to store the user's input as an EditText object
    String username, password; //strings to store the user input from edittests from the GUI

    //initiates and calls up the sign up activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        Button loginB = (Button) findViewById(R.id.loginButton); //initiates and "finds" the login button on the GUI
    }

    //acquires the relevent input from the user and sends it to the server
    public void getLoginDetails(View v){
        /*the following 4 lines obtain the users input from the EditText fields and
        * converts the EditText objects to strings*/
        usernameE = (EditText)findViewById(R.id.usernameTextBox);
        passwordE = (EditText)findViewById(R.id.passwordTextBox);
        username = usernameE.getText().toString();
        password = passwordE.getText().toString();

        /*varNames: stores the string variable names
        * varTypes: stores the variable types of the variables
        * varActualVariables: stores the values of the variables to be sent to the serve*/
        String[] varNames = {"username", "password"};
        String[] varTypes = {"String","String"};
        String[] varActualVariables = {username,password};

        String json = JSON_Handler.constructJSONString(varNames, varTypes, varActualVariables); //converts the variables to be sent into one JSON stringP
        AsyncClassLogin asyncClassLogin=new AsyncClassLogin(json); //specifies the JSON string to be sent to the server
        asyncClassLogin.execute(json); //initiates the sending of the JSON string to the server

        startActivity(new Intent(getApplicationContext(),DashBoard.class)); //starts the next activity after all of the above code runs successfully
    }

    //defines what has to be done when the sign up button is clicked
    public void goToSignUp(View v){
        Button button = (Button)v; //abstractly declares and references the sign up button based on View v
        startActivity(new Intent(getApplicationContext(), signUpPage.class)); //starts sign up page activity once sign up button is clicked
    }
}
