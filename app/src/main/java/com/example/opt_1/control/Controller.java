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


public class Controller implements IModeltoView,IViewtoModel {

    private IDAO database = new DAO();
    private IExercise exercise;

    private LocationTracker locationTracker;
    private Chronometer activityTimer;
    private String loginInfoUsername;
    private String loginInfoPassword;
    private int activityLength;
    private TextView textViewData;
    private TextView timer;
    @Override
    public void userLogin() {
        database.loginUser(loginInfoUsername, loginInfoPassword);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        database.changePassword(oldPassword,newPassword);
    }

    @Override
    public void changeUsername(String newUsername) {
        database.checkIfUsernameExist(newUsername);
    }

    @Override
    public void removeUser() {
        database.removeUser();
    }

    @Override
    public synchronized void startActivity(ActivityFragment fragment,TextView data, TextView timer) {
//        System.out.println("DATATEXTVIEW" + data);
        this.textViewData = data;
        this.timer = timer;
        activityTimer = (Chronometer) timer;
        activityTimer.setBase(SystemClock.elapsedRealtime());
        activityTimer.start();
//        System.out.println("Activity Starts!");
        locationTracker = new LocationTracker(fragment, this);
      //  locationTracker.setLocation(fragment,this);
//        locationTracker.start();

    }


    @Override
    public synchronized void stopActivity() {
        locationTracker.setActive(false);
        database.addNewExerciseToDatabase(new Exercise(120,150,5.4));
        if(activityTimer != null) {
            activityTimer.stop();
            long elapsedMillis = SystemClock.elapsedRealtime() - activityTimer.getBase();
            int seconds = (int) elapsedMillis / 1000;
            this.activityLength = seconds;
            activityTimer.setBase(SystemClock.elapsedRealtime());
            textViewData.setText("Your activity lasted \n"+ seconds + " seconds.");
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
        //database.createUser(new User(firstName, lastName, username, email, password, callback), callback);
        User2 user = User2.getInstance();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        database.createUser2(user, password);
    }

    @Override
    public void createNewGroup(String groupName) {
        database.addNewGroupToDatabase(groupName);
    }

    @Override
    public void joinToGroup(String groupOwnerEmail) {
        database.addUserToTheGroup(groupOwnerEmail);
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
    public int caclulatePace(int activityLength) {
        //Calculate pace, also refactor the method to take the distance of the activity as a parameter.

        return this.activityLength;
    }

}