package com.example.opt_1.model;

import java.util.ArrayList;

/**
 * Represents a group object for saving data to database
 */
public class Group {
    /**
     * A variable for group name
     */
    private String groupName;
    /**
     * A variable for group owner
     */
    private String groupOwner;
    /**
     * A list variable for members of the group
     */
    private ArrayList<String> groupOfUserEmails = new ArrayList<>();

    public ArrayList<String> getGroupOfUserEmails() {
        return groupOfUserEmails;
    }
    public void setGroupOfUserEmails(ArrayList<String> groupOfUserEmails) {
        this.groupOfUserEmails = groupOfUserEmails;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Group(){
    }
    public Group(String groupName){
        this.groupName = groupName;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupOwner='" + groupOwner + '\'' +
                ", group=" + groupOfUserEmails +
                '}';
    }
}
