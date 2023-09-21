package com.example.opt_1.model;

import android.location.Location;

import com.example.opt_1.view.ActivityFragment;

public interface ILocationTracker {

    Location getLocation(ActivityFragment fragment);
    void setActive(boolean stopActivity);

}
