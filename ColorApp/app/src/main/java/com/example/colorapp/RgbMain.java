package com.example.colorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class RgbMain extends AppCompatActivity {
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgbmain);
        setTitle("Color Change");

        Button button=findViewById(R.id.button);
        Button rgb_to_hex = (Button) findViewById(R.id.rgbToHex_btn);
        Button hex_to_rgb = (Button) findViewById(R.id.hexToRGB_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent intent = new Intent(RgbMain.this, TotalActivity.class);
                finish();
                startActivity(intent);
            }
        });

        rgb_to_hex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent intent = new Intent(RgbMain.this, RGBToHexActivity.class);
                finish();
                startActivity(intent);
            }
        });

        hex_to_rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent intent = new Intent(RgbMain.this, HexToRGBActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}