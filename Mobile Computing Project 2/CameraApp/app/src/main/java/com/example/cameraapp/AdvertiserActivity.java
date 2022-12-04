package com.example.cameraapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdvertiserActivity extends AppCompatActivity {

    private ClientListener clientListener;
    private InfoPayloadListener infoPayloadListener;
    private TextView workStatusTextView, finishedTextView;
    private Button closeButton, disconnectButton;
    private DeviceStatisticsPub deviceStatisticsPub;

    private String masterEndpointId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiser_activity);

//        unpackBundle();
//        bindViews();
//        setEventListeners();
//
//        startDeviceStatsPublisher();
//        connectToMaster();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NearbyConnectionsWrapper.getInstance(getApplicationContext()).registerPayloadListener(infoPayloadListener);
        NearbyConnectionsWrapper.getInstance(getApplicationContext()).registerClientConnectionListener(clientListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        NearbyConnectionsWrapper.getInstance(getApplicationContext()).unregisterPayloadListener(infoPayloadListener);
        NearbyConnectionsWrapper.getInstance(getApplicationContext()).unregisterClientConnectionListener(clientListener);
    }

    private void connectToMaster() {
        NearbyConnectionsWrapper.getInstance(getApplicationContext()).acceptConnection(masterEndpointId);
    }

}
