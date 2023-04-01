package com.javan.dev;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */

class TestFloorMap {
    FloorMap floorMap;

    @BeforeEach
    public void setUp() {
        floorMap = new FloorMap(1, 1);
    }
    /**
     * Method to test that the floorMap constructor worked
     */
    @Test
    @DisplayName("Should confirm that all values are as expected given the entered constuctor values")
    void testConstructor() {
        assertNotNull(floorMap);
        assertEquals("FLOOR", floorMap.getMapType());
        assertEquals(1, floorMap.getBuildingID());
        assertEquals(1, floorMap.getMapID());
    }

    /**
     * Method to confirm that the floorMap file path is set properly
     */
    @Test
    @DisplayName("Should confirm floorMap file path is the intended value")
    void testGetFilePath() {
        assertNotNull(floorMap.getFilePath());
    }

    /**
     * Method to test if there the floorMap object above is properly received
     */
    @Test
    @DisplayName("Should not be null, as there is a valid floor above the currently constructed floorMap")
    void testGetFloorAbove() throws IOException {
        assertNotNull(floorMap.getFloorAbove());
    }

    /**
     * Method to test if there the floorMap object below is properly received
     */
    @Test
    @DisplayName("Should be null, as there is not a valid floor above the currently constructed floorMap")
    void testGetFloorBelow() throws IOException {
        assertNull(floorMap.getFloorBelow());
    }

    /**
     * Method to test if there is a floor above
     */
    @Test
    @DisplayName("Should not true, as there is a valid floor above the currently constructed floorMap")
    void testCheckFloorAbove() {
        assertTrue(floorMap.checkFloorAbove());
    }

    /**
     * Method to test if there is a floor below
     */
    @Test
    @DisplayName("Should be false, as there is not a valid floor above the currently constructed floorMap")
    void testCheckFloorBelow() {
        assertFalse(floorMap.checkFloorBelow());
    }

    /**
     * Method to test that the floorMap constructor with bad variables will provide a usable map object
     */
    @Test
    @DisplayName("Should confirm that all values are null / false where appropriate")
    void testBadConstructor() throws IOException {
        floorMap = new FloorMap(-1, 1);
        assertNotNull(floorMap);
        assertEquals("FLOOR", floorMap.getMapType());
        assertEquals(-1, floorMap.getBuildingID());
        assertEquals(1, floorMap.getMapID());
        assertNull(floorMap.getFilePath());
        assertNull(floorMap.getFloorBelow());
        assertNull(floorMap.getFloorAbove());
        assertFalse(floorMap.checkFloorBelow());
        assertFalse(floorMap.checkFloorAbove());
    }
}
