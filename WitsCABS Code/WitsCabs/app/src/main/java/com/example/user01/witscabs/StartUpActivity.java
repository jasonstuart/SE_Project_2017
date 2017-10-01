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
    private static int TIMEOUT=7000; //variable to store activity timeout in milliseconds

    //initiates and calls up the start up activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startupactivity);

        //handles what to do when the activity times out
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(getApplicationContext(), loginPage.class));
                finish(); //indiatess to the handler that its purpose and use is now complete
            }
        }, TIMEOUT); //starts login page activity once the start up activity times out
    }
}