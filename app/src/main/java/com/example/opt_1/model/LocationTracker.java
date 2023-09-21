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
import androidx.dynamicanimation.animation.SpringAnimation;


import com.example.opt_1.view.ActivityFragment;

import java.util.ArrayList;

public class LocationTracker extends Thread implements ILocationTracker {


    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    ArrayList<Location> locations = new ArrayList<>();

    private boolean isActive;

    @Override
    public void setActive(boolean stopActivity) {
        isActive = stopActivity;
    }


    @Override
    public Location getLocation(ActivityFragment fragment) {

        isActive = true;

        mLocationManager = (LocationManager) fragment.getActivity().getSystemService(fragment.getContext().LOCATION_SERVICE);
        mLocationListener = location -> System.out.println("Current location = " + location.getLatitude() + " LATITUDE " + location.getLongitude() + " LONGITUDE" + " Tässä vielä currentlocation " + currentLocation);

        if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);

        try {
            String provider = mLocationManager.getBestProvider(new Criteria(), true);
            currentLocation = mLocationManager.getLastKnownLocation(provider);
        } catch (Exception e){
            e.printStackTrace();
        }


        return currentLocation;
    }

    @Override
    public synchronized void run() {

        while(isActive){
            try {
                locations.add(currentLocation);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int j = 0; j < locations.size(); j++){
            System.out.println(j + ": " + locations.get(j));
        }

    }
}
