package com.example.opt_1.model;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.example.opt_1.view.ActivityFragment;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Observable;

public class LocationTracker extends Observable implements ILocationTracker {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private ActivityFragment fragmentfor;
    private double travelledDistance;
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
            System.out.println("Distance: " + getTravelledDistance());
            setChanged();
            notifyObservers(getTravelledDistance());
        }
    };

    public LocationTracker(ActivityFragment activityFragment) {
        addObserver(activityFragment);
        this.fragmentfor = activityFragment;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activityFragment.requireContext());
        initLocationRequest();
        getLastLocation();
        checkSettingsAndStartGps(activityFragment);
    }
    public LocationTracker(){};

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
    public void setTravelledDistance() {
        travelledDistance = 0f;
    }

    /*
    * calculateDistance-method calculates distance between two locations using Haversine-formula.
    * It takes in latitude and longitude values as parameters from the two locations it calculates the distance between,
    * and returns the difference as double.
    * */

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2)
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