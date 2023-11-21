package com.example.opt_1.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocationTrackerTest {
    double DELTA = 0.1;

    @Test
    public void calculateDistance() {
        assertEquals("Should calculate the distance between two locations", 266.2, LocationTracker.calculateDistance(60.21597801314619, 24.905964043814574, 60.2177206661023, 24.90265956238168), DELTA);
    }
}