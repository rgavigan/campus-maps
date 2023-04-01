package com.javan.dev;

/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author : Jake Choi [jchoi492@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */

public class TestDataProcessor {
    
    private static DataProcessor dataProcessor = DataProcessor.getInstance();
    private static MapComponent map = MapComponent.getInstance();

    @BeforeEach
    public void setUp(){
        Map currMap = MapFactory.createMap("FLOOR", 38, 1);
        map.setMapDetails(currMap);
    }

    @Test
    @DisplayName("Should confirm that the correct map file path returns based on the input.")
    public void testLoadMapFilePath() {
        /**
         * Test for floor map.
         */
        String currMapFile = dataProcessor.loadMapFilePath(1, 1, "floor");
        String testFilePathString = filePathFixer(currMapFile);
        assertEquals("data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-1.png", testFilePathString);

        /**
         * Test for building map.
         */
        currMapFile = dataProcessor.loadMapFilePath(1, 1, "bUiLdiNG");
        testFilePathString = filePathFixer(currMapFile);
        assertEquals("data/images/maps/floorPlans/3M, Thames, and Somerville", testFilePathString);

        /**
         * Test for null.
         */
        assertNull(dataProcessor.loadMapFilePath(1, 1, ""));
    }

    @Test
    @DisplayName("Should confirm that the correct ArrayList is returned based on the input to the method.")
    public void testParseWeather() {
        /**
         * Test if the method catches a null StringBuffer correctly.
         */
        ArrayList<String> parsedData = dataProcessor.parseWeather(null);
        assertNull(parsedData);

        /**
         * Test if the method is correctly parsing.
         * NOTE: I don't even know if this actually fully tests, since parseWeather method in Weather uses the parseWeather in DataProcessor...
         */
        Weather weather = Weather.getInstance();
        weather.parseWeather();
        parsedData = dataProcessor.parseWeather(weather.getJSON());
        assertNotNull(parsedData);
        assertFalse(parsedData.isEmpty());
        assertTrue(parsedData.get(0) instanceof String);
    }

    /**
     * Tested in TestJsonReader.java
     * This method in DataProcessor returns the value returned by a method in the JsonReader class.
     */
    public void testGetFavouritePOIs() {
    }

    /**
     * Tested in TestJsonReader.java
     * This method in DataProcessor returns the value returned by a method in the JsonReader class.
     */
    public void testGetUserPOIs() {
    }

    /**
     * Floor map test (when isCampusMap is passed as false) for getUniversalPOIs will be done in TestJsonReader.java, as this method returns the value returned by a method in the JsonReader class.
     */
    @Test
    @DisplayName("Should confirm if the correct list of POIs is returned for when it is the campus map.")
    public void testGetUniversalPOIs() {
        ArrayList<PointOfInterest> universalPOIS;
        /**
         * Test for for campus map.
         */
        universalPOIS = dataProcessor.getUniversalPOIs(true, 7);
        assertNotNull(universalPOIS);
        assertFalse(universalPOIS.isEmpty());
        assertTrue(universalPOIS.get(0) instanceof PointOfInterest);
        assertEquals("3M, Thames, and Somerville", universalPOIS.get(0).getName());
    }

    /**
     * Tested in TestJsonReader.java
     * This method in DataProcessor returns the value returned by a method in the JsonReader class.
     */
    public void testGetBuildingUniversalPOIs() {
    }

    @Test
    @DisplayName("Should confirm if the correct POI coordinates are returned based on a POI ID.")
    public void testGetPOIPosition() {
        /**
         * Check actual existing POI.
         */
        int[] expectedPOIPos = {1452, 1794};
        assertEquals(expectedPOIPos[0], dataProcessor.getPOIPosition(0)[0]);
        assertEquals(expectedPOIPos[1], dataProcessor.getPOIPosition(0)[1]);
        
        /**
         * Check if a non-existent POI ID input returns null.
         */
        assertNull(dataProcessor.getPOIPosition(1000));
    }

    @Test
    @DisplayName("Should confirm that a POI was added, edited, and deleted successfully or unsuccessfully to the Json File.")
    public void testAddEditDeletePointOfInterestToJsonFile() {

        //////////////////////////////////////
        //////// ADD POI TO JSON TEST ////////
        //////////////////////////////////////

        /**
         * Check if the method will return false by passing in a POI that already exists.
         */
        ArrayList<PointOfInterest> existingPOIs;
        try {
            existingPOIs = dataProcessor.getUniversalPOIs(false, 1);
            assertFalse(dataProcessor.addPointOfInterestToJsonFile(existingPOIs.get(0)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Add new POI and assert that the method returns true.
         */
        ArrayList<Integer> favourites = new ArrayList<Integer>(); 
        PointOfInterest newPOI = new PointOfInterest("DataProcessor JUnit Tester", 1, false, "Classroom", 1750, 1856, 1, 38, favourites, "This is a JUnit Testing POI.", "1138B", false);
        try {
            assertTrue(dataProcessor.addPointOfInterestToJsonFile(newPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ///////////////////////////////////////
        //////// EDIT POI IN JSON TEST ////////
        ///////////////////////////////////////

        /**
         * Edit existing POI and check if method returns true.
         */
        try {
            existingPOIs = dataProcessor.getUniversalPOIs(false, 7);
            PointOfInterest existingPOI = new PointOfInterest(null, 0, false, null, 0, 0, 0, 0, null, null, null, false);
            for (PointOfInterest poi : existingPOIs) {
                if (poi.getName().equals("DataProcessor JUnit Tester")) {
                    existingPOI = poi;
                    break;
                }
            }
            existingPOI.setName("Edited DataProcessor JUnit Testing POI");
            assertTrue(dataProcessor.editPointOfInterestInJsonFile(existingPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Attempt to edit non-existent POI, and 
         */
        try {
            PointOfInterest nonExistPOI = new PointOfInterest(null, 0, false, null, 0, 0, 0, 0, null, null, null, false);
            assertFalse(dataProcessor.editPointOfInterestInJsonFile(nonExistPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /////////////////////////////////////////
        //////// DELETE POI IN JSON TEST ////////
        /////////////////////////////////////////

        /**
         * Delete existing POI and check if method returns true.
         */
        try {
            existingPOIs = dataProcessor.getUniversalPOIs(false, 7);
            PointOfInterest existingPOI = new PointOfInterest(null, 0, false, null, 0, 0, 0, 0, null, null, null, false);
            for (PointOfInterest poi : existingPOIs) {
                if (poi.getName().equals("Edited DataProcessor JUnit Testing POI")) {
                    existingPOI = poi;
                    break;
                }
            }
            assertTrue(dataProcessor.deletePointOfInterestFromJsonFile(existingPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Attempt to delete non-existent POI from Json and check if method returns false.
         */
        try {
            PointOfInterest nonExistPOI = new PointOfInterest(null, 0, false, null, 0, 0, 0, 0, null, null, null, false);
            assertFalse(dataProcessor.deletePointOfInterestFromJsonFile(nonExistPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should confirm that a Building POI was added, edited, and deleted successfully or unsuccessfully to the Json File.")
    public void testAddEditDeleteBuildingPointOfInterestToJsonFile() {

        ///////////////////////////////////////////////
        //////// ADD BUILDING POI TO JSON TEST ////////
        ///////////////////////////////////////////////

        /**
         * Check if the method will return false by passing in a POI that already exists.
         */
        ArrayList<BuildingPointOfInterest> existingPOIs;
        try {
            existingPOIs = dataProcessor.getBuildingUniversalPOIs();
            assertFalse(dataProcessor.addBuildingPointOfInterestToJsonFile(existingPOIs.get(0)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Add new POI and assert that the method returns true.
         */
        try {
            ArrayList<Integer> favourites = new ArrayList<Integer>(); 
            BuildingPointOfInterest newPOI = new BuildingPointOfInterest("JUnit Tester", 1, false, "Building", 2600, 1900, 1000, favourites, "This is a JUnit Tester Created Building POI.", false);    
            assertTrue(dataProcessor.addBuildingPointOfInterestToJsonFile(newPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ////////////////////////////////////////////////
        //////// EDIT BUILDING POI IN JSON TEST ////////
        ////////////////////////////////////////////////

        /**
         * Edit existing POI and check if method returns true.
         */
        try {
            existingPOIs = dataProcessor.getBuildingUniversalPOIs();
            BuildingPointOfInterest existingPOI = new BuildingPointOfInterest(null, 0, false, null, 0, 0, 0, null, null, false);
            for (BuildingPointOfInterest poi : existingPOIs) {
                if (poi.getBuildingID() == 1000) {
                    existingPOI = poi;
                    break;
                }
            }
            existingPOI.setName("Edited JUnit Testing POI");
            assertTrue(dataProcessor.editBuildingPointOfInterestInJsonFile(existingPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Attempt to edit non-existent POI, and 
         */
        try {
            BuildingPointOfInterest nonExistPOI = new BuildingPointOfInterest(null, 0, false, null, 0, 0, 0, null, null, false);
            assertFalse(dataProcessor.editBuildingPointOfInterestInJsonFile(nonExistPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        //////////////////////////////////////////////////
        //////// DELETE BUILDING POI IN JSON TEST ////////
        //////////////////////////////////////////////////

        /**
         * Delete existing POI and check if method returns true.
         */
        try {
            existingPOIs = dataProcessor.getBuildingUniversalPOIs();
            BuildingPointOfInterest existingPOI = new BuildingPointOfInterest(null, 0, false, null, 0, 0, 0, null, null, false);
            for (BuildingPointOfInterest poi : existingPOIs) {
                if (poi.getBuildingID() == 1000) {
                    existingPOI = poi;
                    break;
                }
            }
            assertTrue(dataProcessor.deleteBuildingPointOfInterestFromJsonFile(existingPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Attempt to delete non-existent POI from Json and check if method returns false.
         */
        try {
            BuildingPointOfInterest nonExistPOI = new BuildingPointOfInterest(null, 0, false, null, 0, 0, 0, null, null, false);
            assertFalse(dataProcessor.deleteBuildingPointOfInterestFromJsonFile(nonExistPOI));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should confirm that the method can correctly identify if there is a floor above or below the current floor.")
    public void testCheckFloorsAboveAndBelow() {
        /**
         * Test methods with a floor that we know has a floor above, but not a floor below.
         */
        assertTrue(dataProcessor.checkFloorAbove(1, 38));
        assertFalse(dataProcessor.checkFloorBelow(1, 38));

        /**
         * Test methods with a floor that we know does not have a floor above, but has a floor below..
         */
        assertFalse(dataProcessor.checkFloorAbove(4, 38));
        assertTrue(dataProcessor.checkFloorBelow(4, 38));
    }

    @Test
    @DisplayName("Should confirm that the method can correctly return the correct floor map that is above or below the current floor.")
    public void testGetFloorAboveAndBelow() {
        /**
         * Test that methods return null for floors we know have no floor above or below.
         */
        try {
            assertNull(dataProcessor.getFloorBelow(1, 38));
            assertNull(dataProcessor.getFloorAbove(4, 38));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        /**
         * Test that getFloorBelow returns correct FloorMap.
         */
        try {
            FloorMap floorBelow = dataProcessor.getFloorBelow(2, 38);
            assertNotNull(floorBelow);
            assertEquals("data/images/maps/floorPlans/University College/University College Floor Plans-1.png", filePathFixer(floorBelow.getFilePath()));
            assertEquals(1, floorBelow.getMapID());
            assertEquals(38, floorBelow.getBuildingID());
            assertEquals("FLOOR", floorBelow.getMapType());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Test that getFloorAbove returns correct FloorMap.
         */
        try {
            FloorMap floorAbove = dataProcessor.getFloorAbove(2, 38);
            assertNotNull(floorAbove);
            assertEquals("data/images/maps/floorPlans/University College/University College Floor Plans-3.png", filePathFixer(floorAbove.getFilePath()));
            assertEquals(3, floorAbove.getMapID());
            assertEquals(38, floorAbove.getBuildingID());
            assertEquals("FLOOR", floorAbove.getMapType());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }  

    @Test
    @DisplayName("Should confirm that this method does not return 0, since there are existing POIs built-in already.")
    public void testMakeNewPOIID() {
        /**
         * Test that making a new POI ID works properly and does not return 0.
         */
        assertNotEquals(0, dataProcessor.makeNewPOIID());
    }

    @Test
    @DisplayName("Should confirm that this method does not return 0, since there are existing Building POIs built-in already.")
    public void testMakeNewBuildingPOIID() {
        /**
         * Test that making a new Building POI ID works properly and does not return 0.
         */
        assertNotEquals(0, dataProcessor.makeNewBuildingPOIID());
    }

    @Test
    @DisplayName("Should confirm that this method returns the correct POI from an input POI ID.")
    public void testGetPOI() {
        /**
         * Test method to see if it will return null for a POI ID that does not exist.
         */
        assertNull(dataProcessor.getPOI(dataProcessor.makeNewPOIID()));

        /**
         * Test method with existing POI ID.
         */
        PointOfInterest poi = dataProcessor.getPOI(0);
        assertEquals(1, poi.getFloorID());
        assertEquals("1131", poi.getRoomNumber());
        assertEquals(1452, poi.getCoordinates()[0]);
        assertEquals(1794, poi.getCoordinates()[1]);
        assertEquals(38, poi.getBuildingID());
    }

    @Test
    @DisplayName("Should confirm that this method returns the correct Building POI from an input POI ID.")
    public void testGetBuildingPOI() {
        /**
         * Test method to see if it will return null for a Building POI ID that does not exist.
         */
        assertNull(dataProcessor.getBuildingPOI(dataProcessor.makeNewBuildingPOIID()));

        /**
         * Test method with existing Building POI ID.
         */
        BuildingPointOfInterest poi = dataProcessor.getBuildingPOI(1);
        assertEquals(4, poi.getBuildingID());
        assertEquals("Arts and Humanities", poi.getName());
        assertEquals(2308, poi.getCoordinates()[0]);
        assertEquals(3074, poi.getCoordinates()[1]);
    }

    @Test
    @DisplayName("Should confirm that this method returns a correctly encrypted password based on the encryption algorithm.")
    public void testEncrypt() {
        /**
         * Check if an inputted password will encrypt correctly.
         */
        String passwordToEncrypt = "dA2j!";
        assertEquals("jG2p!", DataProcessor.encrypt(passwordToEncrypt));
    }

    @Test
    @DisplayName("Should confirm that this method returns a correctly decrypted password based on the encryption algorithm.")
    public void testDecrypt() {
        /**
         * Check if an inputted password will be decrypted correctly.
         */
        String passwordToDecrypt = "jG2p!";
        assertEquals("dA2j!", DataProcessor.decrypt(passwordToDecrypt));
    }

    @Test
    @DisplayName("Should confirm that this method correctly checks for a matching username and password in the usersMetaData.json.")
    public void testAuthenticateLogin() {
        /**
         * Check if an existing login username and password is found using this method correctly.
         */
        assertEquals(1, dataProcessor.authenticateLogin("admin", "admin"));

        /**
         * Check if a non-existing login is dealt with by the method correctly.
         */
        assertEquals(-1, dataProcessor.authenticateLogin("IAmAUnitTest-Data", "DataProcessor"));
    }

    @Test
    @DisplayName("Should confirm that this method returns a correctly decrypted password matching the username inputted.")
    public void testGetPasswordFromUsername() {
        /**
         * Check if the method will return the correct corresponding decrypted password to the inputted username.
         */
        assertEquals("admin", dataProcessor.getPasswordFromUsername("admin"));

        /**
         * Check if the method will return null for a non-existent username.
         */
        assertNull(dataProcessor.getPasswordFromUsername("IAmAUnitTest-Data"));
    }

    @Test
    @DisplayName("Should confirm that this method creates an account properly if a matching username does not already exist.")
    public void testCreateAccount() {
        /**
         * Check if the method correctly returns false when creating an account with an already existing username.
         */
        assertFalse(dataProcessor.createAccount("a", "12345566"));

        /**
         * Check if the method correctly adds a new account with a new username.
         */
        assertTrue(dataProcessor.createAccount("ILovePizza", "HawaiianIsGreat"));
    }

    @Test
    @DisplayName("Should confirm that this method returns the correct FloorMap object.")
    public void testGetFloorMapFromMapID() {
        /**
         * Check if method returns correct FloorMap object based on the building and map ID.
         */
        FloorMap currMap = dataProcessor.getFloorMapFromMapID(1, 1);
        assertEquals("data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-1.png", filePathFixer(currMap.getFilePath()));
        assertEquals("FLOOR", currMap.getMapType());
    }

    @AfterEach
    public void cleanUp() {
        /**
         * Reset map to campus map for other tests.
         * Clean up usersMetaData.json, since the newly created user was not deleted during testing.
         */

        Map currMap = MapFactory.createMap("CAMPUS", 0, 0);
        map = MapComponent.getInstance();
        map.setMapDetails(currMap);
        dataProcessor = DataProcessor.getInstance();

        /** 
         * JSON file location
         */
        String filePath = "data/users/usersMetadata.json";

        try {
            /** 
             * Read the JSON file
             */
            FileReader fileReader = new FileReader(filePath);
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(jsonTokener);
            JSONArray newJsonArray = new JSONArray();
            String username = "ILovePizza";
            
            /** 
             * Delete the most recently created account.
             */
            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject user = (JSONObject) iterator.next();
                /** 
                 * Exclude the matching username to delete from the json file.
                 */
                if (!username.equals(user.getString("username"))) {
                    newJsonArray.put(user);
                }
            }
            
            FileWriter fileWriter = new FileWriter(filePath);
            newJsonArray.write(fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * Helper method to fix file path format into a uniform format across all devices.
     * Ex. you/are/amazing
     * @param original  Input file path string
     * @return          Formatted file path string
     */
    private String filePathFixer(String original) {
        String fixedFilePath = "";
        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == '\\') {
                fixedFilePath += '/';
            }
            else {
                fixedFilePath += original.charAt(i);
            }
        }
        return fixedFilePath;
    }

}
