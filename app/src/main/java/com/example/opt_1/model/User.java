package com.example.opt_1.model;
import java.util.ArrayList;
import java.util.Map;

/**
 * The User class is implemented as Singleton
 * It represents the logged-in user
 */
public class User {
    /**
     * A variable for the user instance
     */
    private static User instance = null;
    /**
     * A variable for the user's first name
     */
    private String firstName;
    /**
     * A variable for the user's last name
     */
    private String lastName;
    /**
     * A variable for the user's username
     */
    private String username;
    /**
     * A variable for the user's email
     */
    private String email;
    /**
     * A boolean variable which tells if the user belongs to a group
     */
    private boolean userInGroup;
    /**
     * A variable for the name of the group which user involved
     */
    private String group;
    private ArrayList<Map<String, Object>> exercises = new ArrayList<>();

    private User(){};

    public static User getInstance() {
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Map<String,Object>> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Map<String,Object>> exercises) {
        this.exercises = exercises;
    }

    public boolean isUserInGroup() {
        return userInGroup;
    }

    public void setUserInGroup(boolean userInGroup) {
        this.userInGroup = userInGroup;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
