package com.example.opt_1.model;

import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;
import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;


import com.example.opt_1.view.ActivityFragment;

public class LocationTracker implements ILocationTracker {


    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    @Override
    public void getLocation(ActivityFragment fragment) {
        if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            mLocationManager = (LocationManager) getSystemService(fragment.getContext().LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        }
    }
}

