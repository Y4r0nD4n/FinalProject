package com.example.finalproject.Tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

public class Shake extends AppCompatActivity  {}
//    private SensorManager sensorManager;
//    private Sensor accelerometer;
//    private boolean shakingStarted = false;
//    private long shakeStartTime;
//    private static final int SHAKE_DURATION = 10000; // 10 seconds
//    private static final double SHAKE_THRESHOLD = 2.5;
//    private CountDownTimer countDownTimer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shake);
//
//        // Initialize sensor manager and accelerometer sensor
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Register the accelerometer sensor listener
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Unregister the accelerometer sensor listener
//        sensorManager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//
//            // Calculate acceleration magnitude
//            double acceleration = Math.sqrt(x * x + y * y + z * z);
//
//            // Check if shaking started
//            if (!shakingStarted && acceleration > SHAKE_THRESHOLD) {
//                shakingStarted = true;
//                shakeStartTime = System.currentTimeMillis();
//                startCountdown();
//            }
//
//            // Check if shaking is ongoing
//            if (shakingStarted && acceleration <= SHAKE_THRESHOLD) {
//                shakingStarted = false;
//                cancelCountdown();
//                resetProgressBar();
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Not used in this example
//    }
//
//    private void startCountdown() {
//        // Cancel any existing countdown timer
//        cancelCountdown();
//
//        // Start a new countdown timer
//        countDownTimer = new CountDownTimer(SHAKE_DURATION, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // Calculate remaining time
//                int seconds = (int) (millisUntilFinished / 1000);
//
//                // Update the countdown UI
//                TextView countdownTextView = findViewById(R.id.countdownTextView);
//                countdownTextView.setText(String.valueOf(seconds));
//
//                // Calculate progress percentage
//                int progress = (int) (((SHAKE_DURATION - millisUntilFinished) / (double) SHAKE_DURATION) * 100);
//
//                // Update the progress bar UI
//                ProgressBar progressBar = findViewById(R.id.progressBar);
//                progressBar.setProgress(progress);
//            }
//
//            @Override
//            public void onFinish() {
//                // Countdown finished, show success toast
//                Toast.makeText(Shake.this, "Success!", Toast.LENGTH_SHORT).show();
//                resetProgressBar();
//            }
//        };
//
//        // Start the countdown timer
//        countDownTimer.start();
//    }
//
//    private void cancelCountdown() {
//        // Cancel the countdown timer if it's running
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//    }
//
//    private void resetProgressBar() {
//        // Reset the progress bar
//        ProgressBar progressBar = findViewById(R.id.progressBar);
//        progressBar.setProgress(0);
//    }
//}
