package com.javan.dev;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestMapFactory {

    /**
     * Method to test that a floorMap object can be made
     */
    @Test
    @DisplayName("Should create a valid floorMap ojbect")
    public void testCreateFloorMap() {
        int buildingID = 1;
        int mapID = 1;
        Map floorMap = MapFactory.createMap("FLOOR", buildingID, mapID);
        assertTrue(floorMap instanceof FloorMap);
        assertEquals(buildingID, floorMap.getBuildingID());
        assertEquals(mapID, floorMap.getMapID());
        assertEquals("FLOOR", floorMap.getMapType());
    }
    
    /**
     * Method to test that a buildingMap object can be made
     */
    @Test
    @DisplayName("Should create a valid buildingMap ojbect")
    public void testCreateBuildingMap() {
        int mapID = 1;
        Map buildingMap = MapFactory.createMap("BUILDING", 0, mapID);
        assertTrue(buildingMap instanceof BuildingMap);
        assertEquals(mapID, buildingMap.getMapID());
        assertEquals("BUILDING", buildingMap.getMapType());
    }
    
    /**
     * Method to test that a campusMap object can be made
     */
    @Test
    @DisplayName("Should create a valid campusMap ojbect")
    public void testCreateCampusMap() {
        int mapID = 0;
        Map campusMap = MapFactory.createMap("CAMPUS", 0, 0);
        assertTrue(campusMap instanceof CampusMap);
        assertEquals(mapID, campusMap.getMapID());
        assertEquals("CAMPUS", campusMap.getMapType());
    }
    
    /**
     * Method to test that no object is made if an invalid mapt type was provided
     */
    @Test
    @DisplayName("Should return an error code based on th einvalid map type")
    public void testCreateInvalidMap() {
        try {
            MapFactory.createMap("INVALID_MAP_TYPE", 0, 0);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("Unknown map type INVALID_MAP_TYPE", e.getMessage());
        }
    }
}
