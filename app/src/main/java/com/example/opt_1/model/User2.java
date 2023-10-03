package com.example.opt_1.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User2 {
    private static User2 instance = null;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private ArrayList<Map> exercises = new ArrayList<>();

    private User2(){};

    public static User2 getInstance() {
        if(instance == null){
            instance = new User2();
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

    public ArrayList<Map> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Map> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "User2{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
