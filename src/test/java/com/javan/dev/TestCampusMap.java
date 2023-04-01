package com.javan.dev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

/**
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */

public class TestCampusMap {



    private CampusMap campusMap;

    @BeforeEach
    public void setUp() {
        campusMap = CampusMap.getInstance(0);
    }

    /**
     * Method to test that the campus map instance is not null, and is equal to the campus map instance with mapID of 1
     */
    @Test
    @DisplayName("Should confirm campusMap instance is appropriate")
    public void testGetInstance() {
        assertNotNull(campusMap);
        assertEquals(CampusMap.getInstance(0), campusMap);
    }

    /**
     * Method to confirm that the campusMap file path is set properly
     */
    @Test
    @DisplayName("Should confirm campusMap file path is the intended value")
    public void testGetFilePath() {
        assertEquals("data/images/maps/campusMap.png", campusMap.getFilePath());
    }

    /**
     * Method to test that confirm password text field is created and non-null
     */
    @Test
    @DisplayName("Should create confirm password text field")
    public void testGetMapID() {
        assertEquals(0, campusMap.getMapID());
    }

    /**
     * Method to test that confirm password text field is created and non-null
     */
    @Test
    @DisplayName("Should create confirm password text field")
    public void testGetBuildingID() {
        assertEquals(-1, campusMap.getBuildingID());
    }

    /**
     * Method to test that confirm password text field is created and non-null
     */
    @Test
    @DisplayName("Should create confirm password text field")
    public void testGetMapType() {
        assertEquals("CAMPUS", campusMap.getMapType());
    }

    /**
     * Method to test that confirm password text field is created and non-null
     */
    @Test
    @DisplayName("Should create confirm password text field")
    public void testGetBuildingArray() {
        ArrayList<BuildingMap> buildingArray = campusMap.getBuildingArray();
        assertNotNull(buildingArray);
        assertFalse(buildingArray.isEmpty());
        assertTrue(buildingArray.get(0) instanceof BuildingMap);
    }
}

