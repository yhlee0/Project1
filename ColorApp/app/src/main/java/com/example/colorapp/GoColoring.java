package com.example.colorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GoColoring extends Activity implements OnClickListener{
    Button button2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

    }


    public void onClick(View v) {
        Intent a = null;

        switch (v.getId()) {
            case R.id.button2:
                a = new Intent(this, ColoringActivity2.class);
                finish();
                startActivity(a);
                break;

        }
    }
}
