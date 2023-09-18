package com.example.opt_1.view;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;

public class ActivityFragment extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private IViewtoModel controller;
    View v;
    private int click = 0;
    private Button activityButton;
    public ActivityFragment(){
        controller = new Controller();
    }
    ActivityFragment fragment;

    private TextView locationText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_activity,container,false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        locationText = v.findViewById(R.id.activity_datatext);
        fragment = this;

        System.out.println("Activity fragment avautuu");

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click == 0){
                    requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    if (fragment != null) {
                        controller.startActivity(fragment);
                    }
                    click = 1;

                }else{
                    controller.stopActivity();
                    click = 0;
                }
            }
        });
        return v;
    }
}
