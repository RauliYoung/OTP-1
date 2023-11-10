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
import com.example.opt_1.model.User;

import java.util.ArrayList;
import java.util.Map;

public class ActivityFragment extends Fragment {

    private static final int REQUEST_LOCATION = 1;
    private Controller controller;
    private ArrayList<Map<String,Object>> activityHistory;
    View v;
    //private int click = 0;
    private Button activityButton;
    private Button stopActivityButton;
    ActivityFragment fragment;
    private FrameLayout activityDataLayout;
    private LinearLayout activityDataHistoryScrollView;
    private TextView dataText_duration, dataText_speed, dataText_length;
    private ArrayList<Button> activityHistoryButtonList = new ArrayList<>();

    public ActivityFragment() {
        controller = new Controller();
    }

    private TextView timer;
    private String durationText, speedText, lengthText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityHistory = User.getInstance().getExercises();
        v = inflater.inflate(R.layout.fragment_activity, container, false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        stopActivityButton = (Button) v.findViewById(R.id.activity_StopActivityButton);
        dataText_duration = v.findViewById(R.id.activity_data_duration);
        dataText_speed = v.findViewById(R.id.activity_data_speed);
        dataText_length = v.findViewById(R.id.activity_data_length);
        dataText_duration.setVisibility(View.INVISIBLE);
        dataText_speed.setVisibility(View.INVISIBLE);
        dataText_length.setVisibility(View.INVISIBLE);
        activityDataLayout = v.findViewById(R.id.ActivityDataFrame);
        activityDataHistoryScrollView = v.findViewById(R.id.ActivityListForData);
        timer = v.findViewById(R.id.activity_timer);
        //Initializing for result strings
        durationText = dataText_duration.getText().toString();
        speedText = dataText_speed.getText().toString();
        lengthText = dataText_length.getText().toString();


        fragment = this;
        System.out.println("Activity fragment avautuu");

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
                        dataText_duration.setVisibility(View.INVISIBLE);
                        dataText_speed.setVisibility(View.INVISIBLE);
                        dataText_length.setVisibility(View.INVISIBLE);
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
                Map<String,Double> results = controller.stopActivity();
                System.out.println("Dur results: " + results.get("duration"));

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
}
