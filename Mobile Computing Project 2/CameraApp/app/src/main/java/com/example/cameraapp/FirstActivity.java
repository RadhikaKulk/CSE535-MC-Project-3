package com.example.cameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
            startAdvertisementActivity();
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void startAdvertisementActivity() {
        Intent intent = new Intent(getApplicationContext(), AdvertisementInitActivity.class);
        startActivity(intent);
    }
}
