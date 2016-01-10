package com.example.ize.jhongqadmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity{

    private Handler myHandler;

    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.splashscreen);



        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent goMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goMain);
            }
        },3000);
    }
}
