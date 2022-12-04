package com.example.cameraapp;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class FirstActivity extends AppCompatActivity {

    Button discoverModeButton, advertiseModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        bindViews();
        eventListener();
    }

    private void bindViews() {
        //mainContainer = findViewById(R.id.main_container);
        discoverModeButton = findViewById(R.id.discoverModeButton);
        advertiseModeButton = findViewById(R.id.advertiseModeButton);
    }

    private void eventListener() {
        discoverModeButton.setOnClickListener((view) -> {
            //startClientDiscoveryActivity();
            openMainActivity();
        });


        advertiseModeButton.setOnClickListener((view) -> {
            //startAdvertisementActivity();
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void startAdvertisementActivity() {
//        Intent intent = new Intent(getApplicationContext(), AdvertisementActivity.class);
//        startActivity(intent);
    }
}
