package com.example.opt_1.control;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;
import com.example.opt_1.model.DAO;
import com.example.opt_1.model.Exercise;
import com.example.opt_1.model.IDAO;
import com.example.opt_1.model.CRUDCallbacks;
import com.example.opt_1.model.IExercise;
import com.example.opt_1.model.ILocationTracker;
import com.example.opt_1.model.LocationTracker;
import com.example.opt_1.model.User;
import com.example.opt_1.model.User2;
import com.example.opt_1.view.ActivityFragment;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Controller implements IModeltoView,IViewtoModel {

    private IDAO database = new DAO();
    private IExercise exercise;
    private Map<String,ArrayList<Double>> groupExercises = new HashMap<>();

    private LocationTracker locationTracker;
    private Chronometer activityTimer;
    private String loginInfoUsername;
    private String loginInfoPassword;
    private int activityLength;
    private TextView textViewData;
    private TextView timer;

    @Override
    public void userLogin(String email, String password, CRUDCallbacks callbacks) {
        database.loginUser(email,password, callbacks);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        database.changePassword(oldPassword,newPassword);
    }


    @Override
    public void removeUser() {
        database.removeUser();
    }

    @Override
    public synchronized void startActivity(ActivityFragment fragment,TextView data, TextView timer) {
        this.textViewData = data;
        this.timer = timer;
        activityTimer = (Chronometer) timer;
        activityTimer.setBase(SystemClock.elapsedRealtime());
        activityTimer.start();
        locationTracker = new LocationTracker(fragment, this);
    }

    @Override
    public void changeUsername(String newUsername, CRUDCallbacks callbacks) {
        database.checkIfUsernameExist(newUsername, callbacks);
    }

    @Override
    public synchronized void stopActivity() {
        locationTracker.setActive(false);
        database.addNewExerciseToDatabase(new Exercise(160,150,5.4));
        if(activityTimer != null) {
            activityTimer.stop();
            long elapsedMillis = SystemClock.elapsedRealtime() - activityTimer.getBase();
            int seconds = (int) elapsedMillis / 1000;
            this.activityLength = seconds;
            activityTimer.setBase(SystemClock.elapsedRealtime());
            double distance = locationTracker.getTravelledDistance();
            textViewData.setText("Your activity lasted \n"+ seconds + " seconds." + " and the speed was " + caclulatePace(this.activityLength) + "km/h \n" + "Length of your exercise was " + locationTracker.getTravelledDistance() + " meters");
        }
        System.out.println("Activity Stopping!");
    }

    @Override
    public void getLoginInfo() {

    }

    @Override
    public Boolean getRegisterInfo() {
        return database.getRegisterErrorCheck();
    }

    @Override
    public void getTravelledDistanceModel() {
        textViewData.setText(String.valueOf(locationTracker.getTravelledDistance()));
        //timer.setText(String.valueOf());
    }



    @Override
    public void setLoginInformation(String usernameInput, String passwordInput) {
        loginInfoPassword = passwordInput;
        loginInfoUsername = usernameInput;
    }

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback) {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("username", username);
        user.put("email", email);
        user.put("userInGroup","false");
        user.put("group","null");
        database.createUser2(user, password, callback);
    }

    @Override
    public void createNewGroup(String groupName) {
        database.addNewGroupToDatabase(groupName);
    }

    @Override
    public void joinToGroup(String groupOwnerEmail) {

        database.addUserToTheGroup(groupOwnerEmail, new CRUDCallbacks() {
            @Override
            public void onSucceed() {
                System.out.println("Added user into group");

                database.fetchGroupFromDatabase(groupOwnerEmail, new CRUDCallbacks() {
                    @Override
                    public void onSucceed() {
                        groupExercises = database.getGroupResults();
                        System.out.println("Groups exercises: " + groupExercises);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void onFailure() {
                System.out.println("User not added");
            }
        });
    }

    @Override
    public void leaveFromGroup(String groupOwnerEmail) {
        database.removeUserFromTheGroup(groupOwnerEmail);
    }
    /*
    * calculatePace takes the length(time) and time as parameter
    * then calculates the pace of the activity and returns the value by km/h.
    * */
    @Override
    public double caclulatePace(double activityLength) {
        //Calculate pace, also refactor the method to take the distance of the activity as a parameter.
        double speed = (locationTracker.getTravelledDistance()/activityLength) * 3.6;

        return BigDecimal.valueOf(speed).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public void fecthGroupResults(String groupOwnerEmail) {

    }
}