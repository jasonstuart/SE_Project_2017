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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardlayout);
    }

    public void goToMaps(View v){
        Button button = (Button)v;
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }
}