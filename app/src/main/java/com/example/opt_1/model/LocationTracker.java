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

import java.util.ArrayList;

public class LocationTracker extends Thread implements ILocationTracker {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Location currentLocation;
    private LocationManager mLocationManager;
    private Controller controller;
    private LocationListener mLocationListener;
    private ActivityFragment fragmentfor;
    private double travelledDistance;
    private ArrayList<Location> locations = new ArrayList<>();

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                locations.add(location);
            }

            if (locations.size() >= 2){
                calculateDistance(locations.get(locations.size()-1).getLatitude(), locations.get(locations.size()-1).getLongitude(), locations.get(locations.size()-2).getLatitude(), locations.get(locations.size()-2).getLongitude());
            }

        }
    };
    public LocationTracker(ActivityFragment activityFragment, Controller controller) {
        this.controller = controller;
        this.fragmentfor = activityFragment;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activityFragment.requireContext());
        initLocationRequest();
        getLastLocation();
        checkSettingsAndStartGps(activityFragment);
    }
    private void initLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

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

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(fragmentfor.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentfor.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        for (int i = 0; i < locations.size(); i++){
            System.out.println("Locations list: " + locations.get(i));
        }

    }

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
    @Override
    public void setActive(boolean stopActivity) {
        stopLocationUpdates();
    }

    public double getTravelledDistance() {
        return travelledDistance;
    }

    @Override
    public void setLocation(ActivityFragment fragment, Controller controller) {
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    public double calculateDistance(double X1, double Y1, double X2, double Y2){
        double radius = 6371000; // metrit

        double latitude1 = X1 * Math.PI/180;
        double latitude2 = X2 * Math.PI/180;
        double longitude1 = Y1;
        double longitude2 = Y2;

        double changeOfLatitude = (latitude2 - latitude1) * Math.PI/180;
        double changeOfLongitude = (longitude2 - longitude1) * Math.PI/180;

        double a = Math.sin(changeOfLatitude/2) * Math.sin(changeOfLatitude/2)
                + Math.cos(latitude1) * Math.cos(latitude2)
                * Math.sin(changeOfLongitude/2) * Math.sin(changeOfLongitude/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

//        if (radius * c >= 3){
//
//        }
        travelledDistance += radius * c;

        controller.getTravelledDistanceModel();

        System.out.println("lopputulos: " + travelledDistance);

        return travelledDistance;
    }
}