package com.example.opt_1.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class LocationTrackerTest {
    private LocationTracker locationTracker;
    double DELTA = 0.5;
    @Before
    public void init(){
        locationTracker = new LocationTracker();
    }

    @Test
    public void calculateDistance() {
        assertEquals("Should calculate the distance between two locations", 266.2,
                LocationTracker.calculateDistance(60.21597801314619, 24.905964043814574, 60.2177206661023, 24.90265956238168), DELTA);
    }

    @Test
    public void calculateDistance_incorrect_coordinates() {
        assertNotEquals("Should calculate the distance between two locations", 266.2,
                LocationTracker.calculateDistance(59.21597801314619, 24.905964043814574, 60.2177206661023, 24.90265956238168), DELTA);
    }
}