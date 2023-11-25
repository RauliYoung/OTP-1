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
    public void calculateDistanceWithValidCoordinates() {
        double lat1 = 60.21597801314619;
        double lon1 = 24.905964043814574;
        double lat2 = 60.2177206661023;
        double lon2 = 24.90265956238168;

        double calculatedDistance = locationTracker.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals("Should calculate the distance between two locations", 266.2,calculatedDistance,DELTA);
    }
    @Test
    public void calculateDistanceWithInvalidCoordinates() {
        double invalidLat = 59.21597801314619;
        double lon1 = 24.905964043814574;
        double lat2 = 60.2177206661023;
        double lon2 = 24.90265956238168;

        double calculatedDistance = locationTracker.calculateDistance(invalidLat, lon1, lat2, lon2);

        assertNotEquals("Should not calculate the distance with invalid coordinates", 266.2, calculatedDistance, DELTA);
    }

    @Test
    public void calculateDistanceWithSameCoordinates(){
        double lat1 = 60.21597801314619;
        double lon1 = 24.905964043814574;
        double lat2 = 60.21597801314619;
        double lon2 = 24.905964043814574;

        double calculatedDistance = locationTracker.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals("Should calculate zero distance for the same coordinates", 0, calculatedDistance, DELTA);
    }

    @Test
    public void calculateDistanceWithNegativeCoordinates(){
        double lat1 = -60.2;
        double lon1 = -24.9;
        double lat2 = -60.2;
        double lon2 = -25;

        double calculatedDistance = locationTracker.calculateDistance(lat1, lon1, lat2, lon2);

        assertEquals("Should calculate the distance between two locations with negative values", 5526.09, calculatedDistance, DELTA);
    }
}