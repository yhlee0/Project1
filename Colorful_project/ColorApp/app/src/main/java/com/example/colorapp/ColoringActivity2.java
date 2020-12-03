package com.example.colorapp;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;



import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ColoringActivity2 extends AppCompatActivity {


    Button back_btn;
    Button color;

    private Vibrator vibrator;

    private PaintView2 paintView;
    private int defaultColor;
    private int defaultColor2;
    private int STORAGE_PERMISSION_CODE = 1;

    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);
        bt = new BluetoothSPP(this); //Initializing
        String redHex, greenHex, blueHex;
        final TextView response = (TextView) findViewById(R.id.finalHexCodeTextView);
        Button button;
        back_btn = (Button) findViewById(R.id.back_btn);
        color=(Button)findViewById(R.id.color);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent i = new Intent(ColoringActivity2.this, TotalActivity.class);
                finish();
                startActivity(i);
            }
        });
        paintView = findViewById(R.id.paintView);
        button = findViewById(R.id.change_color_button);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView textView = findViewById(R.id.current_pen_size);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        paintView.initialise(displayMetrics);

        textView.setText("Pen size: " + seekBar.getProgress());

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);

                openColourPicker();

            }

        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                paintView.setStrokeWidth(seekBar.getProgress());
                textView.setText("Pen size: " + seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    private void requestStoragePermission () {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Needed to save image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(ColoringActivity2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }

                    })
                    .create().show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }

    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Access granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Access denied", Toast.LENGTH_LONG).show();

            }

        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.main,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.clear_button:
                        paintView.clear();
                        break;
                    case R.id.undo_button:
                        paintView.undo();
                        break;
                    case R.id.redo_button:
                        paintView.redo();
                        break;
                    case R.id.save_button:

                        if (ContextCompat.checkSelfPermission(ColoringActivity2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            requestStoragePermission();

                        }
                        paintView.saveImage();
                        return true;


                }
                return true;
            }
        });

        popup.show();

    }


    private void openColourPicker () {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

                Toast.makeText(ColoringActivity2.this, "Unavailable", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultColor = color;

                paintView.setColor(color);

            }

        });

        ambilWarnaDialog.show(); // add








        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {


            public void onDataReceived(byte[] data, String message) {
                String redHex, greenHex, blueHex,finalHex;
                String[] array=message.split(",");

                int dRed=Integer.parseInt(array[0]);
                int dGreen=Integer.parseInt(array[1]);
                int dBlue=Integer.parseInt(array[2]);



                if(dRed <= 15) {
                    redHex = "0" + Integer.toHexString(dRed);
                } else {
                    redHex = Integer.toHexString(dRed);
                }

                if(dGreen <= 15) {
                    greenHex = "0" + Integer.toHexString(dGreen);
                } else {
                    greenHex = Integer.toHexString(dGreen);
                }

                if(dBlue <= 15) {
                    blueHex = "0" + Integer.toHexString(dBlue);
                } else {
                    blueHex = Integer.toHexString(dBlue);
                }
                finalHex = redHex + greenHex + blueHex;
                String a="#"+finalHex;

                //int dHex=Integer.parseInt(a);
                //defaultColor2 =dHex;
                paintView.setColor(Color.parseColor(a));

                /*Intent intent = new Intent(HappyActivity.this, HappyPaintView.class);
                intent.putExtra("String",finalHex);
                startActivity(intent);*/
                //response.setText(finalHex);
                //response.setVisibility(View.VISIBLE);


            }



        });






        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { 
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { 
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                // setup();
            }
        }
    }

    /*public void setup() {
        Button btnSend = findViewById(R.id.btnSend); 
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("Text", true);
            }
        });
    }*/

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                //setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
