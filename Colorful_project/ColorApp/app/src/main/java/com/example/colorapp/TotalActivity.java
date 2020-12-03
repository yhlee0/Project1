package com.example.colorapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TotalActivity extends Activity implements OnClickListener{
    Button draw_btn;
    Button coloring_btn;
    Button end_btn;
    Button changergb_btn;
    SoundPool soundPool;
    SoundManager soundManager;
    boolean play;
    int playSoundId;

    private static final int REQUEST_ENABLE_BT = 3;
    private BluetoothAdapter mBluetoothAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        draw_btn = (Button) findViewById(R.id.draw_btn);
        coloring_btn=(Button) findViewById(R.id.coloring_btn);
        end_btn = (Button) findViewById(R.id.end_btn);
        draw_btn.setOnClickListener(this);
        changergb_btn=(Button) findViewById(R.id.changergb_btn);
        coloring_btn.setOnClickListener(this);
        end_btn.setOnClickListener(this);
//        changergb_btn.setOnClickListener(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        }else{
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        soundManager = new SoundManager(this,soundPool);
        soundManager.addSound(0,R.raw.tada);
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "블루투스를 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
            finish();
            return;//
        }


    }
    public void onStart(){
        super.onStart();

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode==Activity.RESULT_OK){
                    Toast.makeText(this,"블루투스를 활성화하였습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "원활한 활동을 위해\n" +"블루투스를 연결해주세요.",Toast.LENGTH_LONG).show();
                    finish(); //
                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private Vibrator vibrator;
    public void onClick(View v) {
        Intent i = null;
        Intent ii = null;
        Intent iii= null;
        switch (v.getId()) {
            case R.id.draw_btn:
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }

                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                i = new Intent(this, MainActivity.class);
                finish();
                startActivity(i);
                break;
            case R.id.coloring_btn:
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }

                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                ii = new Intent(this,SelectPhoto.class);
                finish();
                startActivity(ii);
                break;
            case R.id.changergb_btn:
//                if (!play) {
//                    playSoundId = soundManager.playSound(0);
//                    play = true;
//                } else {
//                    soundManager.pauseSound(0);
//                    play = false;
//                }

                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                iii = new Intent(this, HappyActivity.class);
                finish();
                startActivity(iii);
                break;

            case R.id.end_btn:
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                finish();
                break;
        }
    }
}
