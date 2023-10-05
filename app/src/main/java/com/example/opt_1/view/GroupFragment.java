package com.example.opt_1.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;

public class GroupFragment extends AppCompatActivity {

     FrameLayout usersection;
    LinearLayout groupUserList;
    LinearLayout.LayoutParams layoutParams;

    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();

    }

}
