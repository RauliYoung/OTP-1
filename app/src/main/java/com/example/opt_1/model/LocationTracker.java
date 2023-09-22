package com.example.opt_1.model;

//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;


import com.example.opt_1.view.ActivityFragment;

import java.util.ArrayList;

public class LocationTracker extends Thread implements ILocationTracker {


    private Location currentLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private double lastKnownLocationX1;
    private double lastKnownLocationX2;
    private double lastKnownLocationY1;
    private double lastKnowLocationY2;

    private double d = 0;
    ArrayList<Location> locations = new ArrayList<>();

    private boolean isActive;

    @Override
    public void setActive(boolean stopActivity) {
        isActive = stopActivity;
    }

    @Override
    public double getTravelledDistance() {
        return d;
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
                lastKnownLocationX1 = currentLocation.getLatitude();
                lastKnownLocationY1 = currentLocation.getLongitude();
                //locations.add(currentLocation);
                Thread.sleep(30000);
                double X1 = lastKnownLocationX1;
                double Y1 = lastKnownLocationY1;
                lastKnownLocationX2 = currentLocation.getLatitude();
                lastKnowLocationY2 = currentLocation.getLongitude();
                System.out.println("Tässä X1: " + X1);
                System.out.println("Tässä Y1: " + Y1);
                System.out.println("Tässä X2: " + lastKnownLocationX2);
                System.out.println("Tässä Y2: " + lastKnowLocationY2);
                calculateDistance(X1, Y1, lastKnownLocationX2, lastKnowLocationY2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        for (int j = 0; j < locations.size(); j++){
//            System.out.println(j + ": " + locations.get(j));
//        }

    }

    public void calculateDistance(){
        double radius = 6371000; // metrit

        double latitude1 = locations.get(0).getLatitude() * Math.PI/180;
        double latitude2 = locations.get(1).getLatitude() * Math.PI/180;
        double longitude1 = locations.get(0).getLongitude();
        double longitude2 = locations.get(1).getLongitude();

        double changeOfLatitude = (latitude2 - latitude1) * Math.PI/180;
        double changeOfLongitude = (longitude2 - longitude1) * Math.PI/180;

        double a = Math.sin(changeOfLatitude/2) * Math.sin(changeOfLatitude/2)
                + Math.cos(latitude1) * Math.cos(latitude2)
                * Math.sin(changeOfLongitude/2) * Math.sin(changeOfLongitude/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = radius * c;

        System.out.println("lopputulos: " + d);
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

        d = radius * c;

        System.out.println("lopputulos: " + d);

        return d;
    }

}
