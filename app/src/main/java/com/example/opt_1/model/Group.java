package com.example.opt_1.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Group implements IGroup{
    private String groupName;
    private String groupOwner;
    private ArrayList<User> group = new ArrayList<>();

    public ArrayList<User> getGroup() {
        return group;
    }
    public void setGroup(ArrayList<User> group) {
        this.group = group;
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

    @NonNull
    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupOwner='" + groupOwner + '\'' +
                '}';
    }
}
