package com.example.user01.witscabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by User01 on 5/20/2016.
 */
public class StartUpActivity extends Activity {
    private static int TIMEOUT=7000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startupactivity);
     /*   ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageResource(R.drawable.centralblock);*/

//        final View myView=findViewById(R.id.startup);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(), loginPage.class));
                finish();
            }
        }, TIMEOUT);
    }
}