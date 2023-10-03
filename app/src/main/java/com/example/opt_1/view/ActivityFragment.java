package com.example.opt_1.view;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.control.CurrentUserInstance;
import com.example.opt_1.control.IViewtoModel;
import com.example.opt_1.model.User2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private TextView dataText;
    private ArrayList<Button> activityHistoryButtonList = new ArrayList<>();

    public ActivityFragment() {
        controller = new Controller();
    }

    private TextView timer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityHistory = User2.getInstance().getExercises();
        v = inflater.inflate(R.layout.fragment_activity, container, false);
        activityButton = (Button) v.findViewById(R.id.activity_StartActivityButton);
        stopActivityButton = (Button) v.findViewById(R.id.activity_StopActivityButton);
        dataText = v.findViewById(R.id.activity_datatext);
        activityDataLayout = v.findViewById(R.id.ActivityDataFrame);
        activityDataHistoryScrollView = v.findViewById(R.id.ActivityListForData);
        timer = v.findViewById(R.id.activity_timer);

        fragment = this;
        System.out.println("Activity fragment avautuu");

        for (int i = 0; i < activityHistory.size(); i++) {
            View clonedUserSection = inflater.inflate(R.layout.activityobject, container, false);
            TextView dateText = clonedUserSection.findViewById(R.id.ActivityDate);
            Button activityHistoryButton = clonedUserSection.findViewById(R.id.ActivityReadDataButton);
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
            activityDataHistoryScrollView.addView(clonedUserSection);
        }


        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                try {
                    if (fragment != null) {
                        controller.startActivity(fragment, dataText, timer);

                    }
                } catch (Exception e) {
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
