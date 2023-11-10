package com.example.opt_1.model;

import android.location.Location;

import com.example.opt_1.control.Controller;
import com.example.opt_1.view.ActivityFragment;

public interface ILocationTracker {

    double getTravelledDistance();
    void setTravelledDistance();
    void setActive(boolean stopActivity);

}
