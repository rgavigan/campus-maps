package com.javan.dev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class TestMap {

    /**
     * Method to test that filePath is not null when valid parameters are added
     */
    @Test
    @DisplayName("Should confirm file path was set properly")
    public void testGetFilePath() {
        Map map = new FloorMap(1, 1);
        assertNotNull(map.getFilePath());
    }

    /**
     * Method to test that mapID is set properly when a new map is map
     */
    @Test
    @DisplayName("Should confirm that mapID was set properly")
    public void testGetMapID() {
        Map map = new FloorMap(1, 1);
        assertEquals(1, map.getMapID());
    }

    /**
     * Method to test that mapType is set properly when a new map is map
     */
    @Test
    @DisplayName("Should assert that a building type has been properly set")
    public void testGetMapType() {
        Map map = new FloorMap(1, 1);
        assertNotNull(map.getMapType());
    }

    /**
     * Method to test that buildingID is set properly when a new map is map
     */
    @Test
    @DisplayName("Should confirm that buildingID was set properly")
    public void testGetBuildingID() {
        Map map = new FloorMap(1, 1);
        assertEquals(1, map.getBuildingID());
    }
}
