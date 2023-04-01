package com.javan.dev; 

/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */


public class TestBuildingMap {
  
    
    BuildingMap buildingMap;

    @BeforeEach
    public void setUp() throws Exception {
        buildingMap = new BuildingMap(1);
    }

    /**
     * Method to test building file path is not null
     */
    @Test
    @DisplayName("Should get a non-null filePath")
    public void testGetFilePath() {
        assertNotNull(buildingMap.getFilePath());
    }

    /**
     * Method to test that mapID is set properly in constructor
     */
    @Test
    @DisplayName("Should get a mapID with a value of 1 since that is the value initially passed into buildingMap")
    public void testGetMapID() {
        assertEquals(1, buildingMap.getMapID());
    }

    /**
     * Method to test that buildingID is set properly in constructor
     */
    @Test
    @DisplayName("Should get a buildingID with a value of 1 since that is the value initially passed into buildingMap")
    public void testGetBuildingID() {
        assertEquals(1, buildingMap.getBuildingID());
    }

    /**
     * Method to test that mapType is set properly in constructor
     */
    @Test
    @DisplayName("Should confirm that the building type == BUILDING")
    public void testGetMapType() {
        assertEquals("BUILDING", buildingMap.getMapType());
    }

    /**
     * Method to test that the building objects name is set properly
     */
    @Test
    @DisplayName("Should confirm the building names are the same")
    public void testSetName() {
        buildingMap.setName("Test Building");
        assertEquals("Test Building", buildingMap.getMapName());
    }


    

}

