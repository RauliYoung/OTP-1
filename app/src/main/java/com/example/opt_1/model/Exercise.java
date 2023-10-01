package com.example.opt_1.model;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class Exercise implements IExercise {
    private double exerciseTime;
    private double exerciseInMeters;
    private double avgSpeed;

    public Exercise(double exerciseTime, double exerciseInMeters, double avgSpeed){
        this.exerciseTime = exerciseTime;
        this.exerciseInMeters = exerciseInMeters;
        this.avgSpeed = avgSpeed;
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
