package com.example.cameraapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionResolution;

public class AdvertisementInitActivity extends AppCompatActivity {

    private Button accept, reject;
    private String deviceId;
    private Advertiser advertiser;
    private Dialog connRequest;
    private String masterEndpointId;
    private ClientListener clientListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_initiation_activity);
        deviceId = (Build.MANUFACTURER + " " + Build.MODEL);
        advertiser = new Advertiser(this);

        showConnectionDialog();
        accept = connRequest.findViewById(R.id.accept);
        reject = connRequest.findViewById(R.id.reject);
        initiateListeners();
    }

    private void showConnectionDialog() {
        connRequest = new Dialog(this);
        connRequest.setCancelable(false);
        connRequest.requestWindowFeature(Window.FEATURE_NO_TITLE);
        connRequest.setContentView(R.layout.conn_req);
        try {
            connRequest.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initiateListeners() {

        accept.setOnClickListener(view -> {
            NearbyConnectionsWrapper.getInstance(getApplicationContext())
                    .acceptConnection(masterEndpointId);

            DeviceStatisticsPub.publish(getApplicationContext(), masterEndpointId);
            advertiser.endAdvertising();
            startAdvertiserActivity();
            finish();
        });

        reject.setOnClickListener(view -> {
            NearbyConnectionsWrapper.getInstance(getApplicationContext()).rejectConnection(masterEndpointId);
            connRequest.dismiss();
        });

        clientListener = new ClientListener() {
            @Override
            public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
                masterEndpointId = endpointId;
                connRequest.show();
            }

            @Override
            public void onConnectionResult(String endpointId, ConnectionResolution connectionResolution) {
                Toast.makeText(AdvertisementInitActivity.this, "CONNECTED", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDisconnected(String endpointId) {
                Toast.makeText(AdvertisementInitActivity.this, "DISCONNECTED", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void startAdvertiserActivity() {
        Intent intent = new Intent(getApplicationContext(), AdvertiserActivity.class);
        Bundle b = new Bundle();
        b.putString(UtilConstants.MASTER_ENDPOINT_ID, masterEndpointId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NearbyConnectionsWrapper.getInstance(getApplicationContext()).registerClientConnectionListener(clientListener);
        advertiser.beginAdvertising(deviceId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NearbyConnectionsWrapper.getInstance(getApplicationContext()).unregisterClientConnectionListener(clientListener);
        advertiser.endAdvertising();
    }
}
