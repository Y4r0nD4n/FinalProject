package com.example.finalproject.Tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.TurnOffAlarm;

public class Rewriting extends AppCompatActivity {

    TextView text;
    EditText input;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewriting);

            text = findViewById(R.id.rewrite_text);
            crateRandomCharString();

            input = findViewById(R.id.input);
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if( !isInputRight(editable.toString())){
                        text.setTextColor(Color.RED);
                    }
                    else{
                        if(text.getCurrentTextColor() == Color.RED)
                            text.setTextColor(Color.BLACK);
                    }

                    if(editable.toString().equals(text.getText().toString())){
                        Intent intent = getIntent();
                        if (intent != null) {
                            pendingIntent = intent.getParcelableExtra("pendingIntent");
                        }

                        Intent newActivityIntent = new Intent(Rewriting.this, BarcodeScanActivity.class);
                        newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        newActivityIntent.putExtra("pendingIntent", pendingIntent);
                        startActivity(newActivityIntent);
                    }

                }
            });

    }

//    public static void setContrastTextColor(TextView textView) {
//        Drawable background = textView.getBackground(); // Get background drawable
//
//        if (background != null) {
//            int backgroundColor = Color.TRANSPARENT;
//
//            if (background instanceof ColorDrawable) {
//                backgroundColor = ((ColorDrawable) background).getColor(); // Get background color
//            }
//
//            if (isColorDark(backgroundColor)) {
//                textView.setTextColor(Color.WHITE); // Set white text color for dark background
//            } else {
//                textView.setTextColor(Color.BLACK); // Set black text color for light background
//            }
//        }
//    }
//
//    private static boolean isColorDark(int color) {
//        // Calculate the perceived brightness of the color
//        double perceivedBrightness = (Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114);
//
//        return perceivedBrightness < 128; // Consider colors with perceived brightness less than 128 as dark
//    }
    public void crateRandomCharString(){
        String AllCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder(10);
        int length = AllCharacters.length();
        for (int i = 0; i < 10; i++) {
            sb.append(AllCharacters.charAt((int)(length * Math.random())));
        }
        text.setText(sb.toString());
    }

    public boolean isInputRight (String s){
        if(text.getText().toString().startsWith(s))
            return true;
        else
            return false;
    }
}