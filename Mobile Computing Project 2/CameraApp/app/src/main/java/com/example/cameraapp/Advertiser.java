package com.example.cameraapp;

import android.content.Context;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.Strategy;

public class Advertiser {

    private Context context;
    private AdvertisingOptions advertisingOptions;

    public Advertiser(Context context) {
        this.context = context;
        this.advertisingOptions = new AdvertisingOptions.Builder()
                .setStrategy(Strategy.P2P_CLUSTER)
                .build();
    }

    public void beginAdvertising(String clientId) {
        NearbyConnectionsWrapper.getInstance(context).advertise(clientId, advertisingOptions);
    }

    public void endAdvertising() {
        Nearby.getConnectionsClient(context).stopAdvertising();
    }

}
