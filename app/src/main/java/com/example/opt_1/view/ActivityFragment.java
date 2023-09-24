package com.example.opt_1.view;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;

public class ActivityFragment extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private Controller controller;
    View v;
    //private int click = 0;
    private Button activityButton;
    private Button stopActivityButton;
    public ActivityFragment(){
        controller = new Controller();
    }
    ActivityFragment fragment;

    private TextView dataText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_activity,container,false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        stopActivityButton = (Button) v.findViewById(R.id.activity_StopActivityButton);
        dataText = v.findViewById(R.id.activity_datatext);


        fragment = this;

        System.out.println("Activity fragment avautuu");

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                try {
                    if (fragment != null) {
                        controller.startActivity(fragment,dataText);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        stopActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.stopActivity();
            }
        });

        return v;
    }
}
