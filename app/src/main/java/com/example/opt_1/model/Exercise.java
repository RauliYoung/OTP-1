package com.example.opt_1.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exercise class represents a POJO object
 * The object is for saving data to database
 */

public class Exercise {
    /**
     * exerciseTime is a variable for the exercise times
     */
    private double exerciseTime;
    /**
     * exerciseInMeters is a variable for the exercise meters
     */
    private double exerciseInMeters;
    /**
     * avgSpeed is a variable for the exercise average speeds
     */
    private double avgSpeed;
    /**
     * exerciseDate is a variable for the exercise dates
     */
    private String exerciseDate;

    /**
     * Constructor for a exercise object
     *
     * @param exerciseTime expecting a value from controller timer
     * @param exerciseInMeters expecting a value from Location Tracker via controller
     * @param avgSpeed expecting a value from Location Tracker via controller
     */
    public Exercise(double exerciseTime, double exerciseInMeters, double avgSpeed){
        this.exerciseTime = exerciseTime;
        this.exerciseInMeters = exerciseInMeters;
        this.avgSpeed = avgSpeed;
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.exerciseDate = date.format(format);
    }

    public String getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(String exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public double getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(double exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public double getExerciseInMeters() {
        return exerciseInMeters;
    }

    public void setExerciseInMeters(double exerciseInMeters) {
        this.exerciseInMeters = exerciseInMeters;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }
}
