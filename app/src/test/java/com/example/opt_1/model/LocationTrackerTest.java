package com.example.opt_1.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
@RunWith(BlockJUnit4ClassRunner.class)
public class LocationTrackerTest {
    private LocationTracker locationTracker;
    double DELTA = 0.5;
    @Before
    public void init(){
        locationTracker = new LocationTracker();
    }
    @Test
    public void calculateDistanceWithCorrectCoordinates() {
        assertEquals("Should calculate the distance between two locations", 266.2,
                locationTracker.calculateDistance(60.21597801314619, 24.905964043814574, 60.2177206661023, 24.90265956238168), DELTA);
    }
    @Test
    public void calculateDistanceWithIncorrectCoordinates() {
        assertNotEquals("Should calculate the distance between two locations", 266.2,
                locationTracker.calculateDistance(59.21597801314619, 24.905964043814574, 60.2177206661023, 24.90265956238168), DELTA);
    }
}