package com.example.colorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(2000);//초
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,TotalActivity.class));
        finish();
    }
}
