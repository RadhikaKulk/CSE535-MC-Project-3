package com.example.cameraapp;

import android.content.Context;
import android.location.Location;

import com.example.cameraapp.FLocListener;

public class LocationMon {

    private Context context;
    private int intervalInMillis;

    private LocationService locationService;

    private Location lastAvailableLocation;

    private static LocationMon locationMonitor;

    public LocationMon(Context context) {
        this.context = context;
        this.intervalInMillis = 4 * 1000;

        this.locationService = new LocationService(this.context);
    }

    public static LocationMon getInstance(Context context) {
        if (locationMonitor == null) {
            locationMonitor = new LocationMon(context);
        }

        return locationMonitor;
    }

    public void start() {
        locationService.requestLocationUpdates(new FLocListener() {
            @Override
            public void onLocationAvailable(Location location) {
                lastAvailableLocation = location;
            }
        });
    }

    public void stop() {
        locationService.removeLocationUpdates();
    }

    public Location getLastAvailableLocation() {
        return this.lastAvailableLocation;
    }
}
