package com.example.opt_1.model;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.example.opt_1.control.Controller;
import com.example.opt_1.view.ActivityFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class LocationTracker extends Thread implements ILocationTracker {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private LocationManager mLocationManager;
    private Controller controller;
    private LocationListener mLocationListener;
    private ActivityFragment fragmentfor;
    private static double travelledDistance;
    private ArrayList<Location> locations = new ArrayList<>();


    /*
    * Adds the requested locations into list and their latitudes and longitudes.
    * */
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                locations.add(location);
            }

            if (locations.size() >= 2){
                calculateDistance(locations.get(locations.size()-1).getLatitude(), locations.get(locations.size()-1).getLongitude(), locations.get(locations.size()-2).getLatitude(), locations.get(locations.size()-2).getLongitude());
            }
            controller.getTravelledDistanceModel();
        }
    };

    public LocationTracker(){};
    public LocationTracker(ActivityFragment activityFragment, Controller controller) {
        this.controller = controller;
        this.fragmentfor = activityFragment;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activityFragment.requireContext());
        initLocationRequest();
        getLastLocation();
        checkSettingsAndStartGps(activityFragment);
    }

    /*
    * Is used to to adjust to the intervals in which the location is requested
    * and sets a radius for the smallest displacement.
    * */
    private void initLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setSmallestDisplacement(3);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /*
    * Checks the settings for location requests and calls for locations updates to start.
    * */
    private void checkSettingsAndStartGps(ActivityFragment fragment) {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(fragment.getContext());
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /*
    * Starts the location updates if permissions are granted.
    * */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(fragmentfor.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentfor.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    /*
    * Used for stopping the location updates.
    * */
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        for (int i = 0; i < locations.size(); i++){
            System.out.println("Locations list: " + locations.get(i));
        }

    }

    /*
    * Checks the permission for location use and returns the last known location.
    * */
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(fragmentfor.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    System.out.println("Location from getLastLocation() : " + location);
                } else {
                    System.out.println("Location from getLastLocation() was null : " + location);
                }
            }
        });
    }

    /*
    * Is used to stop the activity running.
    * */
    @Override
    public void setActive(boolean stopActivity) {
        stopLocationUpdates();
    }

    /*
    * Returns the rounded value of travelled distance.
    * */
    public double getTravelledDistance() {
        return BigDecimal.valueOf(travelledDistance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public void setLocation(ActivityFragment fragment, Controller controller) {
    }

    /*
    * Returns the current location of users mobile device.
    * */
    @Override
    public Location getLocation() {
        return currentLocation;
    }

    /*
    * calculateDistance-method calculates distance between two locations using Haversine-formula.
    * It takes in latitude and longitude values as parameters from the two locations it calculates the distance between,
    * and returns the difference as double.
    * */
    static double calculateDistance(double lat1, double lon1,
                            double lat2, double lon2)
    {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371000;
        double c = 2 * Math.asin(Math.sqrt(a));

        travelledDistance += rad * c;
        return travelledDistance;
    }
}