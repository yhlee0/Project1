package com.example.colorapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;




public class SelectPhoto extends Activity implements View.OnClickListener {
    Button select_camera, select_gallery;

    SoundPool soundPool;
    SoundManager soundManager;
    boolean play;
    int playSoundId;
    Button back_btn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        select_camera=(Button) findViewById(R.id.select_camera);
        select_camera.setOnClickListener(this);

        select_gallery=(Button) findViewById(R.id.select_gallery);
        select_gallery.setOnClickListener(this);
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent i = new Intent(SelectPhoto.this, TotalActivity.class);
                finish();
                startActivity(i);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            soundPool = new SoundPool.Builder().build();
        }else{
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        soundManager = new SoundManager(this,soundPool);
        soundManager.addSound(0,R.raw.tada);

    }

    private Vibrator vibrator;
    @Override
    public void onClick(View v) {
        Intent i = null;
        Intent ii = null;
        switch (v.getId()) {

            case R.id.select_camera:
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }

                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                i = new Intent(this, ColoringActivity_Camera.class);
                finish();
                startActivity(i);
                break;

            case R.id.select_gallery:
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }

                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                ii = new Intent(this, ColoringActivity.class);
                finish();
                startActivity(ii);
                break;
        }
    }
}
