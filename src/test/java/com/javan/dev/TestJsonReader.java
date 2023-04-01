package com.javan.dev; 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


public class TestJsonReader {
    private DataProcessor processor = DataProcessor.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();

    @Test
    @DisplayName("Should confirm that a file path was accurately generated based on the json file path entered.")
    public void testRead() throws IOException {
        String expected = "{\"filePath\":\"data/images/maps/floorPlans/3M, Thames, and Somerville\",\"floorMaps\":[{\"filePath\":\"data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-1.png\",\"mapType\":\"FLOOR\",\"mapID\":1,\"mapName\":\"3M, Thames and Somerville Floor Plans-1.png\"},{\"filePath\":\"data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-2.png\",\"mapType\":\"FLOOR\",\"mapID\":2,\"mapName\":\"3M, Thames and Somerville Floor Plans-2.png\"},{\"filePath\":\"data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-3.png\",\"mapType\":\"FLOOR\",\"mapID\":3,\"mapName\":\"3M, Thames and Somerville Floor Plans-3.png\"},{\"filePath\":\"data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-4.png\",\"mapType\":\"FLOOR\",\"mapID\":4,\"mapName\":\"3M, Thames and Somerville Floor Plans-4.png\"}],\"mapType\":\"BUILDING\",\"mapName\":\"3M, Thames, and Somerville\",\"buildingID\":1}";
        expected = filePathFixer(expected);
        String actual = JsonReader.read("data/images/maps/metadata/mapMetadata.json");

        JSONArray jsonArray = new JSONArray(actual);
        actual = filePathFixer(jsonArray.get(0).toString());
        actual = filePathFixer(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should confirm that an ArrayList of floorMaps is properly populated.")
    public void testGetFloorMaps() throws IOException {
        String expectedName = "Arts and Humanities";
        int expectedFloor = 1;
        ArrayList<FloorMap> floorMaps = JsonReader.getFloorMaps("data/images/maps/metadata/mapMetadata.json");
        BuildingPointOfInterest building = processor.getBuildingPOI(floorMaps.get(0).getBuildingID());
        assertEquals(expectedName, building.getName());
        assertEquals(expectedFloor, floorMaps.get(0).getMapID());
    }

    @Test
    @DisplayName("Should confirm that an ArrayList of buildingMaps is properly populated.")
    public void testGetBuildingMaps() throws IOException {
        String expected = "Arts and Humanities";
        ArrayList<BuildingMap> buildingMaps = JsonReader.getBuildingMaps("data/images/maps/metadata/mapMetadata.json");
        BuildingPointOfInterest building = processor.getBuildingPOI(buildingMaps.get(0).getBuildingID());
        assertEquals(expected, building.getName());
    }

    @Test
    @DisplayName("Should confirm that a file path was accurately generated and added to a Json array.")
    public void testAddMapInfoToJSON() throws IOException {
        String directoryPath = "data/images/maps/floorPlans";
        String jsonFilePath = "data/images/maps/metadata/mapMetadata.json";
        JsonReader.addMapInfoToJSON(directoryPath, jsonFilePath);
        String expected = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        assertNotNull(expected);
    }

    
    @Test
    @DisplayName("Should retrieve the correct floorMap file path based on the map ID.")
    public void testGetFloorMapPathFromID() {
        String filePath = "data/images/maps/floorPlans/3M, Thames, and Somerville/3M, Thames and Somerville Floor Plans-1.png";
        filePath = filePathFixer(filePath);
        String newFilePath = JsonReader.getFloorMapPathFromID(1, 1, "data/images/maps/metadata/mapMetadata.json");
        newFilePath = filePathFixer(newFilePath);
        assertEquals(filePath, newFilePath);
    }

    @Test
    @DisplayName("Should retrieve the correct buildingMap file path based on the map ID.")
    public void testGetBuildingMapPathFromID() {      
        String filePath = "data/images/maps/floorPlans/3M, Thames, and Somerville";
        filePath = filePathFixer(filePath);
        String newFilePath = JsonReader.getBuildingMapPathFromID(1, "data/images/maps/metadata/mapMetadata.json");
        newFilePath = filePathFixer(newFilePath);
        assertEquals(filePath, newFilePath);
    }

    @Test
    @DisplayName("Should retrieve the user POI list, confirming that the POIs are accurately generated and retreived.")
    public void testUserPOIList() {
        /*
         * tests the user POIs available
         */

        ArrayList<PointOfInterest> userPOIs = JsonReader.userPOIList(7);
        assertNotNull(userPOIs);

        /*
         * testing first user POI for demo user
         */
        assertEquals(6, userPOIs.size());
        assertEquals("Group Meeting Room", userPOIs.get(0).getName());
        assertEquals(7, userPOIs.get(0).getUserID());
        assertTrue(userPOIs.get(0).getIsUserMade());
        assertEquals("User POI", userPOIs.get(0).getPOItype());
        assertArrayEquals(new int[]{683,1429}, userPOIs.get(0).getCoordinates());
        assertEquals(3, userPOIs.get(0).getFloorID());
        assertEquals(21, userPOIs.get(0).getBuildingID());
        assertFalse(userPOIs.get(0).getIsFavourited(1));
        assertEquals("The room we book for our group meetings in CS 2212", userPOIs.get(0).getDescription());
        assertEquals("220", userPOIs.get(0).getRoomNumber());
        assertTrue(userPOIs.get(0).getIsVisible());

        /*
         * testing last user POI
         */
        assertEquals("UC Study Spot", userPOIs.get(userPOIs.size() - 1).getName());
        assertEquals(7, userPOIs.get(userPOIs.size() - 1).getUserID());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsUserMade());
        assertEquals("User POI", userPOIs.get(userPOIs.size() - 1).getPOItype());
        assertArrayEquals(new int[]{1784,1726}, userPOIs.get(userPOIs.size() - 1).getCoordinates());
        assertEquals(2, userPOIs.get(userPOIs.size() - 1).getFloorID());
        assertEquals(38, userPOIs.get(userPOIs.size() - 1).getBuildingID());
        assertFalse(userPOIs.get(1).getIsFavourited(1));
        assertEquals("Riley's favourite study spot in UC - it has good couches", userPOIs.get(userPOIs.size() - 1).getDescription());
        assertEquals("2133", userPOIs.get(userPOIs.size() - 1).getRoomNumber());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsVisible());
    }

    @Test
    @DisplayName("Should retrieve the universal POI list, confirming that the POIs are accurately generated and retreived.")
    public void testUniversalPOIs() {
        /*
         * tests the universal POIs available on the first floor of the University College building
         */
        FloorMap newMap = new FloorMap(38, 1);
        mapComponent.changeMap(newMap);
        ArrayList<PointOfInterest> userPOIs = JsonReader.universalPOIs(1);
        assertNotNull(userPOIs);

        /*
         * testing first admin POI
         */
        assertEquals(25, userPOIs.size());
        assertEquals("Accessibility Bathroom", userPOIs.get(0).getName());
        assertEquals(1, userPOIs.get(0).getUserID());
        assertFalse(userPOIs.get(0).getIsUserMade());
        assertEquals("Washrooms", userPOIs.get(0).getPOItype());
        assertArrayEquals(new int[]{1452,1794}, userPOIs.get(0).getCoordinates());
        assertEquals(1, userPOIs.get(0).getFloorID());
        assertEquals(38, userPOIs.get(0).getBuildingID());
        assertFalse(userPOIs.get(0).getIsFavourited(1));
        assertEquals("Accessible bathroom on the 1st floor of UC", userPOIs.get(0).getDescription());
        assertEquals("1131", userPOIs.get(0).getRoomNumber());
        assertTrue(userPOIs.get(0).getIsVisible());

        /*
         * testing last admin POI
         */
        assertEquals("Women's Bathroom", userPOIs.get(userPOIs.size() - 1).getName());
        assertEquals(1, userPOIs.get(userPOIs.size() - 1).getUserID());
        assertFalse(userPOIs.get(userPOIs.size() - 1).getIsUserMade());
        assertEquals("Washrooms", userPOIs.get(userPOIs.size() - 1).getPOItype());
        assertArrayEquals(new int[]{2718,1702}, userPOIs.get(userPOIs.size() - 1).getCoordinates());
        assertEquals(1, userPOIs.get(userPOIs.size() - 1).getFloorID());
        assertEquals(38, userPOIs.get(userPOIs.size() - 1).getBuildingID());
        assertFalse(userPOIs.get(1).getIsFavourited(1));
        assertEquals("Women's bathroom on the first floor of University College", userPOIs.get(userPOIs.size() - 1).getDescription());
        assertEquals("1201", userPOIs.get(userPOIs.size() - 1).getRoomNumber());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsVisible());
    }

    @Test
    @DisplayName("Should retrieve the user favourites POI list, confirming that the POIs are accurately generated and retreived.")
    public void testFavouritePOIs() {
        /*
         * tests the favourited POIs 
         */
        ArrayList<PointOfInterest> userPOIs = JsonReader.favouritesList(7);
        assertNotNull(userPOIs);

        /*
         * testing first favourited POI by userID 7
         */
        assertEquals(4, userPOIs.size());
        assertEquals("Classroom 110", userPOIs.get(0).getName());
        assertEquals(7, userPOIs.get(0).getUserID());
        assertFalse(userPOIs.get(0).getIsUserMade());
        assertEquals("Classrooms", userPOIs.get(0).getPOItype());
        assertArrayEquals(new int[]{1814,798}, userPOIs.get(0).getCoordinates());
        assertEquals(2, userPOIs.get(0).getFloorID());
        assertEquals(21, userPOIs.get(0).getBuildingID());
        assertTrue(userPOIs.get(0).getIsFavourited(7));
        assertEquals("Large classroom on the 1st floor of Middlesex College", userPOIs.get(0).getDescription());
        assertEquals("110", userPOIs.get(0).getRoomNumber());
        assertTrue(userPOIs.get(0).getIsVisible());

        /*
         * testing last favourited POI by userID 7
         */
        assertEquals("Tims Express", userPOIs.get(userPOIs.size() - 1).getName());
        assertEquals(7, userPOIs.get(0).getUserID());
        assertFalse(userPOIs.get(0).getIsUserMade());
        assertEquals("User POI", userPOIs.get(userPOIs.size() - 1).getPOItype());
        assertArrayEquals(new int[]{1913,582}, userPOIs.get(userPOIs.size() - 1).getCoordinates());
        assertEquals(2, userPOIs.get(userPOIs.size() - 1).getFloorID());
        assertEquals(39, userPOIs.get(userPOIs.size() - 1).getBuildingID());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsFavourited(7));
        assertEquals("because why wait in the 30 minute tim's line", userPOIs.get(userPOIs.size() - 1).getDescription());
        assertEquals("196", userPOIs.get(userPOIs.size() - 1).getRoomNumber());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsVisible());

    }

    @Test
    @DisplayName("Should retrieve the universal buildingPOI list on the campus map, confirming that the buildingPOIs are accurately generated and retreived.")
    public void testUniversalBuildingPOIs() {
        /*
         * tests the universal POIs available on the campus map
         */

        ArrayList<BuildingPointOfInterest> userPOIs = JsonReader.universalBuildingPOIs();
        assertNotNull(userPOIs);

        /*
         * testing first admin POI
         */
        assertEquals(45, userPOIs.size());
        assertEquals("3M, Thames, and Somerville", userPOIs.get(0).getName());
        assertEquals(1, userPOIs.get(0).getUserID());
        assertFalse(userPOIs.get(0).getIsUserMade());
        assertEquals("Building", userPOIs.get(0).getPOItype());
        assertArrayEquals(new int[]{2097,3114}, userPOIs.get(0).getCoordinates());
        assertEquals(1, userPOIs.get(0).getBuildingID());
        assertFalse(userPOIs.get(0).getIsFavourited(1));
        assertEquals("3M, Thames, and Somerville Buildings", userPOIs.get(0).getDescription());
        assertTrue(userPOIs.get(0).getIsVisible());

        /*
         * testing last admin POI
         */
        assertEquals("Westminister Hall", userPOIs.get(userPOIs.size() - 1).getName());
        assertEquals(1, userPOIs.get(userPOIs.size() - 1).getUserID());
        assertFalse(userPOIs.get(userPOIs.size() - 1).getIsUserMade());
        assertEquals("Building", userPOIs.get(userPOIs.size() - 1).getPOItype());
        assertArrayEquals(new int[]{2180,989}, userPOIs.get(userPOIs.size() - 1).getCoordinates());
        assertEquals(44, userPOIs.get(userPOIs.size() - 1).getBuildingID());
        assertFalse(userPOIs.get(1).getIsFavourited(1));
        assertEquals("Westminister Hall", userPOIs.get(userPOIs.size() - 1).getDescription());
        assertTrue(userPOIs.get(userPOIs.size() - 1).getIsVisible());
    }


    @Test
    @DisplayName("Should retrieve the user POI list available in the currect building, confirming that the POIs are accurately generated and retreived.")
    public void testCurrentBuildingPOIS() {
        
        Map newMap = MapFactory.createMap("BUILDING", 38, 1);
        mapComponent.changeMap(newMap);
        ArrayList<PointOfInterest> currentBuildingPOIs = JsonReader.currentBuildingPOIS(1, 38);

        /*
         * testing first admin POI
         */
        assertEquals(47, currentBuildingPOIs.size());
        assertEquals("Accessibility Bathroom", currentBuildingPOIs.get(0).getName());
        assertEquals(1, currentBuildingPOIs.get(0).getUserID());
        assertFalse(currentBuildingPOIs.get(0).getIsUserMade());
        assertEquals("Washrooms", currentBuildingPOIs.get(0).getPOItype());
        assertArrayEquals(new int[]{1435,1787}, currentBuildingPOIs.get(0).getCoordinates());
        assertEquals(2, currentBuildingPOIs.get(0).getFloorID());
        assertEquals(38, currentBuildingPOIs.get(0).getBuildingID());
        assertFalse(currentBuildingPOIs.get(0).getIsFavourited(1));
        assertEquals("Accessible, gender neutral bathroom on the 2nd floor of UC", currentBuildingPOIs.get(0).getDescription());
        assertEquals("2131", currentBuildingPOIs.get(0).getRoomNumber());
        assertTrue(currentBuildingPOIs.get(0).getIsVisible());

        /*
         * testing last admin POI
         */
        assertEquals("Classroom 3225", currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getName());
        assertEquals(1, currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getUserID());
        assertFalse(currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getIsUserMade());
        assertEquals("Classrooms", currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getPOItype());
        assertArrayEquals(new int[]{3144,2269}, currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getCoordinates());
        assertEquals(3, currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getFloorID());
        assertEquals(38, currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getBuildingID());
        assertFalse(currentBuildingPOIs.get(1).getIsFavourited(1));
        assertEquals("Classroom on the 3rd floor of UC", currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getDescription());
        assertEquals("3225", currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getRoomNumber());
        assertTrue(currentBuildingPOIs.get(currentBuildingPOIs.size() - 1).getIsVisible());
        

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
            else if  (original.charAt(i) == '/' && i < original.length() - 1 && original.charAt(i + 1) == '/') {
                fixedFilePath += '/';
                i++;
            }
            else {
                fixedFilePath += original.charAt(i);
            }
        }
        return fixedFilePath;
    }

}
