package com.example.opt_1.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;

public class GroupFragment extends Fragment {

     FrameLayout usersection;
    Button adduserButton;
    View v;
    LinearLayout groupUserList;
    LinearLayout.LayoutParams layoutParams;
    public GroupFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_groups,container,false);
        groupUserList  = (LinearLayout) v.findViewById(R.id.groupUsers);
        adduserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View f) {
                View clonedUserSection = inflater.inflate(R.layout.usernameframe, container, false);
                groupUserList.addView(clonedUserSection);
                System.out.println("CLICKY CLICK");
            }
        });
        return v;
    }

}
