package com.example.cameraapp;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.example.cameraapp.models.Comm;
import com.example.cameraapp.models.DeviceStatistics;
import com.example.cameraapp.models.InfoPayload;

import okhttp3.internal.Util;

public class DeviceStatisticsPub {

    private Context context;
    private String endpointId;
    private int intervalInMillis;

    private Handler handler;
    private Runnable runnable;

    public DeviceStatisticsPub(Context context, String endpointId, int intervalInMillis) {
        this.context = context;
        this.endpointId = endpointId;
        this.intervalInMillis = intervalInMillis;

        handler = new Handler();
        runnable = () -> {
            publish();
            handler.postDelayed(runnable, intervalInMillis);
        };
    }

    private void publish() {
        DeviceStatisticsPub.publish(this.context, this.endpointId);
    }

    public void start() {
        handler.postDelayed(runnable, intervalInMillis);
        LocationMon.getInstance(context).start();
    }

    public void stop() {
        handler.removeCallbacks(runnable);
        LocationMon.getInstance(context).stop();
    }

    public static void publish(Context context, String endpointId) {
        Location location = LocationMon.getInstance(context).getLastAvailableLocation();

        DeviceStatistics deviceStatistics = new DeviceStatistics();
        if (location != null) {
            deviceStatistics.setLatitude(location.getLatitude());
            deviceStatistics.setLongitude(location.getLongitude());
            deviceStatistics.setLocationValid(true);
        }

        InfoPayload infoPayload = new InfoPayload();
        infoPayload.setTag(UtilConstants.PayloadTags.DEVICE_STATS);
        infoPayload.setData(deviceStatistics);

        Comm.sendToDevice(context, endpointId, infoPayload);
    }
}
