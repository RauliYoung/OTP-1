package com.example.opt_1.view;

import android.Manifest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.model.LocationTracker;
import com.example.opt_1.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ActivityFragment extends Fragment implements Observer {

    private static final DecimalFormat decfor = new DecimalFormat("0.0");
    private static final int REQUEST_LOCATION = 1;
    private Controller controller;
    private ArrayList<Map<String,Object>> activityHistory;
    View v;
    private Button activityButton;
    private Button stopActivityButton;
    ActivityFragment fragment;
    private FrameLayout activityDataLayout;
    private LinearLayout activityDataHistoryScrollView;
    private TextView dataText_duration, dataText_speed, dataText_length, distance_travelled;
    private ArrayList<Button> activityHistoryButtonList = new ArrayList<>();

    public ActivityFragment() {
        controller = new Controller();
    }

    private TextView timer;
    private String durationText, speedText, lengthText, travelledDistanceText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityHistory = User.getInstance().getExercises();
        v = inflater.inflate(R.layout.fragment_activity, container, false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        stopActivityButton = (Button) v.findViewById(R.id.activity_StopActivityButton);
        dataText_duration = v.findViewById(R.id.activity_data_duration);
        dataText_speed = v.findViewById(R.id.activity_data_speed);
        dataText_length = v.findViewById(R.id.activity_data_length);
        distance_travelled = v.findViewById(R.id.distance_travelled);
        distance_travelled.setVisibility(View.INVISIBLE);
        dataText_duration.setVisibility(View.INVISIBLE);
        dataText_speed.setVisibility(View.INVISIBLE);
        dataText_length.setVisibility(View.INVISIBLE);
        activityDataLayout = v.findViewById(R.id.ActivityDataFrame);
        activityDataHistoryScrollView = v.findViewById(R.id.ActivityListForData);
        timer = v.findViewById(R.id.activity_timer);

        //Initializing result strings
        durationText = dataText_duration.getText().toString();
        speedText = dataText_speed.getText().toString();
        lengthText = dataText_length.getText().toString();
        travelledDistanceText = distance_travelled.getText().toString();

        String distance = String.format(travelledDistanceText, 0.0f);
        distance_travelled.setText(distance);

        fragment = this;

        for (int i = 0; i < activityHistory.size(); i++) {
            View clonedActivitySection = inflater.inflate(R.layout.activityobject, container, false);
            TextView dateText = clonedActivitySection.findViewById(R.id.ActivityDate);
            Button activityHistoryButton = clonedActivitySection.findViewById(R.id.ActivityReadDataButton);
            activityHistoryButton.setId(i);
            activityHistoryButtonList.add(activityHistoryButton);
            View popupView = inflater.inflate(R.layout.popup_windowactivity, null);
            TextView popUpViewDataOne = popupView.findViewById(R.id.dataOne);

            popUpViewDataOne.setText(activityHistory.get(i).values().toString()
                    .replace("{","")
                    .replace("}","")
                    .replace("]", "")
                    .replace("[","")
                    .replace(",",""));

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            activityHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            });

            dateText.setText(activityHistory.get(i).keySet().toString().replace("]", "").replace("[", ""));
            activityDataHistoryScrollView.addView(clonedActivitySection);
        }


        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                try {
                    if (fragment != null) {
                        distance_travelled.setVisibility(View.VISIBLE);
                        dataText_duration.setVisibility(View.INVISIBLE);
                        dataText_speed.setVisibility(View.INVISIBLE);
                        dataText_length.setVisibility(View.INVISIBLE);
                        String distance = String.format(travelledDistanceText, 0.0f);
                        distance_travelled.setText(distance);
                        controller.startActivity(fragment, timer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance_travelled.setVisibility(View.INVISIBLE);
                Map<String,Double> results = controller.stopActivity();

                String duration = String.format(durationText,
                        results.get("duration"));
                dataText_duration.setText(duration);

                String speed = String.format(speedText,
                        results.get("speed"));
                dataText_speed.setText(speed);

                String length = String.format(lengthText,
                        results.get("length"));
                dataText_length.setText(length);

                dataText_duration.setVisibility(View.VISIBLE);
                dataText_speed.setVisibility(View.VISIBLE);
                dataText_length.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof LocationTracker) {
            String distance = String.format(travelledDistanceText, arg);
            distance_travelled.setText(distance);
        }
    }
}
