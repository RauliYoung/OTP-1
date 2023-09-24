package com.example.opt_1.model;

//import static androidx.core.app.AppOpsManagerCompat.Api23Impl.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;
import com.example.opt_1.view.ActivityFragment;

import java.util.ArrayList;

public class LocationTracker extends Thread implements ILocationTracker {


    private Location currentLocation;
    private LocationManager mLocationManager;
    private Controller controller;
    private LocationListener mLocationListener;
    private double lastKnownLocationX1;
    private double lastKnownLocationX2;
    private double lastKnownLocationY1;
    private double lastKnowLocationY2;
    private ActivityFragment fragmentfor;
    private double travelledDistance;
    private Location startLoc;
    private Location endLoc;
    private Criteria criteria;
    ArrayList<Location> locations = new ArrayList<>();

    private boolean isActive;

    @Override
    public void setActive(boolean stopActivity) {
        isActive = stopActivity;
    }

    public double getTravelledDistance() {
        return travelledDistance;
    }


    @Override
    public void setLocation(ActivityFragment fragment, Controller controller) {
        this.controller = controller;
        this.fragmentfor = fragment;
        criteria = new Criteria();
        isActive = true;

        mLocationManager = (LocationManager) fragment.getActivity().getSystemService(fragment.getContext().LOCATION_SERVICE);
        mLocationListener = location -> System.out.println("Current location = " + location.getLatitude() + " LATITUDE " + location.getLongitude() + " LONGITUDE" + " Tässä vielä currentlocation " + currentLocation);
        if (ActivityCompat.checkSelfPermission(fragmentfor.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    @SuppressLint("MissingPermission")
    public Location fetchLocation(){
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0,mLocationListener);
        Location locman = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return locman;
    }
    @Override
    public synchronized void run() {
        Looper.prepare();
        while(isActive){
            try {
                startLoc = fetchLocation();
                Thread.sleep(1000);
                double x1 = startLoc.getLatitude();
                double y1 = startLoc.getLongitude();
                endLoc = fetchLocation();
                calculateDistance(x1,y1,endLoc.getLatitude(),endLoc.getLongitude());
                System.out.println(x1+"XLOCATIO " + y1 + " YLOCATIO");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        for (int j = 0; j < locations.size(); j++){
//            System.out.println(j + ": " + locations.get(j));
//        }

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

        travelledDistance = radius * c;

        System.out.println("lopputulos: " + travelledDistance);

        return travelledDistance;
    }

}
