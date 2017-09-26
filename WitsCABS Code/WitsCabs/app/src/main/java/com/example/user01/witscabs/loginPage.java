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
    EditText usernameE;
    EditText passwordE;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        Button loginB = (Button) findViewById(R.id.loginButton);
    }
    public void getLoginDetails(View v){
        usernameE = (EditText)findViewById(R.id.usernameTextBox);
        passwordE = (EditText)findViewById(R.id.passwordTextBox);
        String username = usernameE.getText().toString();
        String password = passwordE.getText().toString();

        Socket client; String feedback;
        DataOutputStream out;
        BufferedReader read;
        try {
            client=new Socket("192.168.0.9",9987);
            read=new BufferedReader(new InputStreamReader(client.getInputStream()));
            out=new DataOutputStream(client.getOutputStream());
            out.writeBytes(username); out.writeBytes(password);
            feedback=read.readLine();
            if(feedback.equals("SUCCESS")) System.out.println("change to main activity");
            else Toast.makeText(loginPage.this,"Incorrect login details",Toast.LENGTH_LONG).show();
        }catch(IOException e){System.out.println(e);}
    }
    public void goToSignUp(View v){
        Button button = (Button)v;
        startActivity(new Intent(getApplicationContext(), signUpPage.class));
    }
}
