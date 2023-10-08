package com.example.opt_1.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opt_1.R;
import com.example.opt_1.control.Controller;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.User2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class GroupFragment extends AppCompatActivity {

    FrameLayout usersection;
    LinearLayout groupUserList;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout userNameListForData;
    Button leaveGroupBtn;

    TextView groupSumResult,groupMeterSumResult;

    Map groupResultsWithMembers;



    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_groups);
        controller = new Controller();
        leaveGroupBtn = findViewById(R.id.leaveGroupButton);
        groupUserList = findViewById(R.id.groupUsers);
        userNameListForData = findViewById(R.id.groupUsers);
        groupSumResult = findViewById(R.id.wholeGroupSumResult);
        groupMeterSumResult = findViewById(R.id.wholeGroupSumResultMeter);

        controller.fecthGroupResults(User2.getInstance().getGroup(), new CRUDCallbacks() {
            @Override
            public void onSucceed() {
                groupResultsWithMembers = controller.getGroupExericesforView();
                activateMethods();
            }

            @Override
            public void onFailure() {

            }
        });

        leaveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.leaveFromGroup(User2.getInstance().getGroup());
            }
        });

    }
    private void activateMethods(){
        int i = 0;
        int sumTime = 0;
        int sumMeter = 0;
        for (Object set : groupResultsWithMembers.keySet()) {
            View clonedUserSection = LayoutInflater.from(this).inflate(R.layout.usernameframe,null);
            TextView userNameInList = clonedUserSection.findViewById(R.id.userNameNameInList);
            TextView userSumResult = clonedUserSection.findViewById(R.id.sumResult);
            TextView userSumResult2 = clonedUserSection.findViewById(R.id.sumResult2);
            ArrayList<Double> resultKeyList = (ArrayList<Double>) groupResultsWithMembers.get(set);

            //Timeresult
            userSumResult.setText(resultKeyList.get(i).toString());
            sumTime += resultKeyList.get(i);
            i++;

            //Metersresult
            userSumResult2.setText(resultKeyList.get(i).toString());

            sumMeter += resultKeyList.get(i);

            userNameInList.setText(set.toString());

            String time = "Group´s Total Time: " + sumTime;
            String meter = "Group´s Total Meter´s: " + sumMeter;

            groupSumResult.setText(time);
            groupMeterSumResult.setText(meter);
            userNameListForData.addView(clonedUserSection);

            if (i == 1){
                i = 0;
            }
        }



    }

}
