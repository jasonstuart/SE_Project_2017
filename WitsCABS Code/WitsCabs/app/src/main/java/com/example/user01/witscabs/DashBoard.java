package com.example.user01.witscabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by User01 on 9/14/2017.
 */
public class DashBoard extends Activity {

    //initiates and calls up the dash board activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardlayout);
    }

    //defines what has to be done when the goto maps button is clicked
    public void goToMaps(View v){
        Button button = (Button)v; //abstractly declares and references the goto maps button based on View v
        startActivity(new Intent(getApplicationContext(), MapsActivity.class)); //starts maps activity once goto maps button is clicked
    }
}