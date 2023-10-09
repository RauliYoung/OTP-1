package com.example.opt_1.control;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;
import com.example.opt_1.model.*;
import com.example.opt_1.view.ActivityFragment;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Controller class represents acts as the coordinator that handles the overall behavior and interaction of the application
 */

public class Controller implements IModeltoView, IViewToModel {

    /**
     * A variable for the Data access object
     * used for the database interactions
     */
    private IDAO database = new DAO();
    /**
     * A list variable for the exercise group results
     */
    private Map<String,ArrayList<Double>> groupExercises = new HashMap<>();
    /**
     * A variable for the Location Tracker
     */
    private LocationTracker locationTracker;
    /**
     * A variable for the Timer
     */
    private Chronometer activityTimer;
    /**
     * A variable for exercise time
     */
    private int activityLength;
    /**
     * A variable for UI ongoing exercise displayed in Activity page
     */
    private TextView textViewData;
    /**
     * A variable for UI ongoing exercise time displayed in Activity page
     */
    private TextView timer;

    /**
     * Method is called login button in login page
     * @param email The email provided in the login form
     * @param password The password provided in the login form
     * @param callbacks Used for verifying log in
     */
    @Override
    public void userLogin(String email, String password, CRUDCallbacks callbacks) {
        database.loginUser(email,password, callbacks);
    }

    /**
     * Method is called change password button in home fragment page
     * @param oldPassword An old password provided in home fragment page form
     * @param newPassword A new password provided in home fragment page form
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        database.changePassword(oldPassword,newPassword);
    }


    /**
     * Method for to remove the user from app
     */
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

    /**
     * Method is called change username button in home fragment page
     * @param newUsername A new username provided in home fragment page form
     * @param callbacks Used for verifying that username has been changed
     */
    @Override
    public void changeUsername(String newUsername, CRUDCallbacks callbacks) {
        database.checksIfUsernameExist(newUsername, callbacks);
    }

    @Override
    public synchronized void stopActivity() {
        locationTracker.setActive(false);
        if(activityTimer != null) {
            activityTimer.stop();
            long elapsedMillis = SystemClock.elapsedRealtime() - activityTimer.getBase();
            int seconds = (int) elapsedMillis / 1000;
            this.activityLength = seconds;
            activityTimer.setBase(SystemClock.elapsedRealtime());
            double distance = locationTracker.getTravelledDistance();
            textViewData.setText("Your activity lasted \n"+ seconds + " seconds." + " and the speed was " + caclulatePace(this.activityLength) + "km/h \n" + "Length of your exercise was " + locationTracker.getTravelledDistance() + " meters");
        }
        database.addNewExerciseToDatabase(new Exercise(activityLength, locationTracker.getTravelledDistance(), caclulatePace(activityLength)));
        System.out.println("Activity Stopping!");
    }

    @Override
    public void getTravelledDistanceModel() {
        textViewData.setText(String.valueOf(locationTracker.getTravelledDistance()));
        //timer.setText(String.valueOf());
    }

    @Override
    public Map<String, ArrayList<Double>> getGroupExericesforView() {
        System.out.println(groupExercises +" TÄSSÄ OLLAAAN LISTA");
        return groupExercises;
    }

    /**
     * Method creating a new hashmap for saving using to the database
     * @param firstName The first name provided in register page form
     * @param lastName The last name provided in register page form
     * @param username The username provided in register page form
     * @param password The password provided in register page form
     * @param email The email provided in register page form
     * @param callback Used for verifying that a new user has been created
     */

    @Override
    public void setRegisterInformation(String firstName, String lastName, String username, String password, String email, CRUDCallbacks callback) {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("username", username);
        user.put("email", email);
        user.put("userInGroup","false");
        user.put("group","null");
        database.createUser(user, password, callback);
    }

    /**
     * Method is called in the group activity page
     * @param groupName The group name provided in the group activity page form
     */
    @Override
    public void createNewGroup(String groupName) {
        database.addNewGroupIntoDatabase(groupName);
    }

    /**
     * Method is called in the group activity page
     * @param groupOwnerEmail The group owner email provided in the group activity page form
     */
    @Override
    public void joinToGroup(String groupOwnerEmail) {

        database.addUserToTheGroup(groupOwnerEmail, new CRUDCallbacks() {
            @Override
            public void onSucceed() {
                System.out.println("Added a new user into group");

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

                System.out.println("Error while adding user into the group");
            }
        });
    }

    /**
     * Method is called in the group activity page
     * @param groupOwnerEmail The group owner email provided in the group activity page form
     */
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

    /**
     * Method is called in the group activity page
     * @param groupOwnerEmail The group owner email provided in the user instance variable
     */
    @Override
    public void fecthGroupResults(String groupOwnerEmail,CRUDCallbacks callbacks) {
        database.fetchGroupFromDatabase(groupOwnerEmail, new CRUDCallbacks() {
            @Override
            public void onSucceed() {
                groupExercises = database.getGroupResults();
                System.out.println("Groups exercises: " + groupExercises);
                callbacks.onSucceed();
            }

            @Override
            public void onFailure() {

            }
        });
    }
}