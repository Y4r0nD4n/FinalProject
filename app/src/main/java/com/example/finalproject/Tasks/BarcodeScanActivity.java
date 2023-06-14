package com.example.finalproject.Tasks;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalproject.R;
import com.example.finalproject.TurnOffAlarm;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class BarcodeScanActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private PendingIntent pendingIntent;

    private ProgressBar progressBar;
    private TextView countdownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);

        surfaceView = findViewById(R.id.surfaceView);

        if (hasCameraPermission()) {
            setupBarcodeScanner();
        } else {
            requestCameraPermission();
        }


        progressBar = findViewById(R.id.progressBar);
        countdownTextView = findViewById(R.id.countdownTextView);

        progressBar.setMax(30);

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                updateCountdown(secondsRemaining);
                progressBar.setProgress(30 - secondsRemaining);
            }

            @Override
            public void onFinish() {
                Toast.makeText(BarcodeScanActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                if (intent != null) {
                    pendingIntent = intent.getParcelableExtra("pendingIntent");
                }

                Intent newActivityIntent = new Intent(BarcodeScanActivity.this, Rewriting.class);
                newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                newActivityIntent.putExtra("pendingIntent", pendingIntent);
                startActivity(newActivityIntent);
            }
        }.start();
    }

    private void updateCountdown(int secondsRemaining) {
        TextView countdownTextView = findViewById(R.id.countdownTextView);
        countdownTextView.setText(String.valueOf(secondsRemaining));
    }


    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
    }

    private void setupBarcodeScanner() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        if (!barcodeDetector.isOperational()) {
            Toast.makeText(this, "Could not set up the barcode detector", Toast.LENGTH_SHORT).show();
            return;
        }

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (hasCameraPermission()) {
                        if (ActivityCompat.checkSelfPermission(BarcodeScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    final String barcodeValue = barcodes.valueAt(0).displayValue;

                    // Perform any additional processing or validation if needed
                    // For simplicity, we are directly displaying a toast message
                    runOnUiThread( () ->{ Toast.makeText(BarcodeScanActivity.this, "Good job! Barcode: " + barcodeValue, Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        if (intent != null) {
                            pendingIntent = intent.getParcelableExtra("pendingIntent");
                        }

                        Intent newActivityIntent = new Intent(BarcodeScanActivity.this, TurnOffAlarm.class);
                        newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        newActivityIntent.putExtra("pendingIntent", pendingIntent);
                        startActivity(newActivityIntent);}
                        );

            }}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasCameraPermission()) {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
                setupBarcodeScanner();
            } else {
                Toast.makeText(this, "Google Play Services not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.stop();
    }
}
