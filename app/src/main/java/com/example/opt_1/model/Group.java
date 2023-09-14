package com.example.opt_1.model;

import androidx.annotation.NonNull;

import java.util.HashSet;

public class Group implements IGroup{
    private String groupName;
    private String groupOwner;
    //private HashSet<User> groupOfUsers = new HashSet<>();

    public Group(){
    }
    public Group(String groupName, String groupOwner){
        this.groupName = groupName;
        this.groupOwner = groupOwner;
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

    @NonNull
    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupOwner='" + groupOwner + '\'' +
                '}';
    }
}
