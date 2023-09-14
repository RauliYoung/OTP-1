package com.example.opt_1.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.IViewtoModel;

public class ActivityFragment extends Fragment {

    private IViewtoModel controller;
    View v;
    private int click = 0;
    private Button activityButton;
    public ActivityFragment(){
        controller = new Controller();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_activity,container,false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click == 0){
                    controller.startActivity();
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