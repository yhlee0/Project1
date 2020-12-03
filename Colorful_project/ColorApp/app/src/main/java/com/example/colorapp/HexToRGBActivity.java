package com.example.colorapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class HexToRGBActivity extends AppCompatActivity {
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_to_rgb);
        setTitle("Hex To RGB");


        final EditText hexText = (EditText) findViewById(R.id.hexNumField);
        final Button convert = (Button) findViewById(R.id.hexToRGB_btn);
        final TextView response = (TextView) findViewById(R.id.finalRGBTextView);
        final Button coloredBtn = (Button) findViewById(R.id.hexToRGBFinalColor);
        convert.setEnabled(false);
        Button button=findViewById(R.id.button);

        hexText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {

                if((hexText.getText().toString().length() == 7) && (Character.toString(hexText.getText().toString().charAt(0)).equals("#")) && (hexText.getText().toString().matches("^#[0-9a-fA-F]+$"))) {
                    convert.setEnabled(true);
                } else if((hexText.getText().toString().length() == 6) && (hexText.getText().toString().matches("^[0-9a-fA-F]+$"))){
                    convert.setEnabled(true);
                } else {
                    convert.setEnabled(false);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                Intent intent = new Intent(HexToRGBActivity.this, RgbMain.class);
                finish();
                startActivity(intent);
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                hideKeyboard(HexToRGBActivity.this);
                String redHex, greenHex, blueHex, finalRGB;
                int red, green, blue;

                //Get the string and remove the "#" at the start.
                String input = hexText.getText().toString();
                input = input.replace("#", "");

                //Get the Hex Characters for Red, Green and Blue
                redHex = (Character.toString(input.charAt(0))) + Character.toString(input.charAt(1));
                greenHex = (Character.toString(input.charAt(2))) + Character.toString(input.charAt(3));
                blueHex = (Character.toString(input.charAt(4))) + Character.toString(input.charAt(5));

                //Convert Hex characters to Base 10 for RGB values
                red = Integer.parseInt(redHex, 16);
                green = Integer.parseInt(greenHex, 16);
                blue = Integer.parseInt(blueHex, 16);

                finalRGB = red + ", " + green + ", " + blue;
                response.setText(finalRGB);
                coloredBtn.setBackgroundColor(Color.parseColor("#" + input));
                response.setVisibility(View.VISIBLE);
                coloredBtn.setVisibility(View.VISIBLE);
            }
        });

        coloredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                String hexCode = hexText.getText().toString();
                hexCode = hexCode.replace("#", "");
                String url = "http://www.color-hex.com/color/" + hexCode;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}