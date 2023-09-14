package com.example.opt_1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.opt_1.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    View v;
    Button logOutButton;
    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home,container,false);
        logOutButton = (Button) v.findViewById(R.id.logOut_Button);
        System.out.println(v + "OLEN VIEW");
        System.out.println(logOutButton + "OLEN BUTTON");
        System.out.println("OLEN FRAGMENTTI?");

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return v;

    }
}
