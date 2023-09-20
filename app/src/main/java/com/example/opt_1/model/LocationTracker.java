package com.example.opt_1.model;

//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;
import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.opt_1.view.ActivityFragment;

public class LocationTracker implements ILocationTracker {


    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    @Override
    public Location getLocation(ActivityFragment fragment) {

        //currentLocation = mLocationManager.getAllProviders();

        mLocationManager = (LocationManager) fragment.getActivity().getSystemService(fragment.getContext().LOCATION_SERVICE);
        mLocationListener = location -> System.out.println("Current location = " + location.getLatitude() + " LATITUDE " + location.getLongitude() + " LONGITUDE" + " Tässä vielä currentlocation " + currentLocation);

        if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Tekstii");
        }

        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);

        System.out.println("Vastauksia kiitos " + mLocationManager.getAllProviders());

        try {
            String provider = mLocationManager.getBestProvider(new Criteria(), true);
            currentLocation = mLocationManager.getLastKnownLocation(provider);
            System.out.println("Currentlocation täsä näi " + currentLocation);
        } catch (Exception e){
            e.printStackTrace();
        }

        return currentLocation;
    }
}

