package com.javan.dev;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;


public class TestPointOfInterest {
    private PointOfInterest poi;
    private DataProcessor processor = DataProcessor.getInstance();

    
    /**
     * Method to test that the POI was constructed properly
     */
    @BeforeEach
    @Test
    @DisplayName("Should confirm that all poi variables were constructed properly, and return the appropriate values for all getter methods")
    public void testConstructor() {
        String name = "testPOI";
        int userID = 1;
        boolean isUsermade = true;
        String poiType = "testType";
        int coordinatesX = 5;
        int coordinatesY = 5;
        int floorID = 1;
        int buildingID = 1;
        boolean isFavourited = false;
        String description = "test description";
        String roomNumber = "test room number";
        boolean isVisible = true;

        poi = new PointOfInterest(name, userID, isUsermade, poiType, coordinatesX, coordinatesY, floorID, buildingID, new ArrayList<Integer>(), description, roomNumber, isVisible);

        assertEquals(name, poi.getName());
        assertEquals(userID, poi.getUserID());
        assertEquals(isUsermade, poi.getIsUserMade());
        assertEquals(poiType, poi.getPOItype());
        assertEquals(coordinatesX, poi.getCoordinates()[0]);
        assertEquals(coordinatesY, poi.getCoordinates()[1]);
        assertEquals(floorID, poi.getFloorID());
        assertEquals(buildingID, poi.getBuildingID());
        assertEquals(isFavourited, poi.getIsFavourited(poi.getUserID()));
        assertEquals(description, poi.getDescription());
        assertEquals(roomNumber, poi.getRoomNumber());
        assertEquals(isVisible, poi.getIsVisible());
        assertEquals(processor.makeNewPOIID(), poi.getID());
        String expectedBuildingFloorID = "1 1";
        assertEquals(expectedBuildingFloorID, poi.getBuildingFloorID());
    }


    /**
     * Method to test that the toJSON method creates an accurately made JSON object
     */
    @Test
    @DisplayName("Should confirm a valid and correct JSON object is constructed")
    public void testToJSON() {

        JSONObject json = poi.toJSON();
        String expectedName = "testPOI";
        int expectedID = poi.getID();
        int expectedUserID = 1;
        boolean expectedIsUserMade = true;
        String expectedPoiType = "testType";
        int expectedCoordinatesX = 5;
        int expectedCoordinatesY = 5;
        int expectedFloorID = 1;
        int expectedBuildingID = 1;
        ArrayList<Integer> expectedUserFavouritesList = new ArrayList<>();
        String expectedDescription = "test description";
        String expectedRoomNumber = "test room number";
        boolean expectedIsVisible = true;

        assertEquals(expectedName, json.getString("name"));
        assertEquals(expectedID, json.getInt("ID"));
        assertEquals(expectedUserID, json.getInt("userID"));
        assertEquals(expectedIsUserMade, json.getBoolean("isUserMade"));
        assertEquals(expectedPoiType, json.getString("poiType"));
        ArrayList<Integer> jsonCoordinates = new ArrayList<>();
        for (Object i : json.getJSONArray("coordinates")) {
            jsonCoordinates.add((Integer) i);
        }
        assertEquals(expectedCoordinatesX, jsonCoordinates.get(0));
        assertEquals(expectedCoordinatesY, jsonCoordinates.get(1));
        assertEquals(expectedFloorID, json.getInt("floorID"));
        assertEquals(expectedBuildingID, json.getInt("buildingID"));
        ArrayList<Integer> jsonUserFavouritesList = new ArrayList<>();
        for (Object i : json.getJSONArray("userFavouritesList")) {
            jsonUserFavouritesList.add((Integer) i);
        }
        assertEquals(expectedUserFavouritesList, jsonUserFavouritesList);

        assertEquals(expectedDescription, json.getString("description"));
        assertEquals(expectedRoomNumber, json.getString("roomNumber"));
        assertEquals(expectedIsVisible, json.getBoolean("isVisible"));
    }

    /**
     * Method to test that setter methods work properly
     */
    @Test
    @DisplayName("Should confirm that setter functions work as intended")
    public void testSetters() {
        poi.setName("POI 2");
        poi.setPOItype("new type");
        poi.setCoordinates(15, 25);
        poi.setDescription("new description");
        assertEquals("POI 2", poi.getName());
        assertEquals("new type", poi.getPOItype());
        assertArrayEquals(new int[]{15, 25}, poi.getCoordinates());
        assertEquals("new description", poi.getDescription());
    }

    /**
     * Method to test that getIsFavourited returns the appropriate values based on the provided userIDs
     */
    @Test
    @DisplayName("Should confirm that appropriate user ID is added to the favourites list / that the right boolean value shows up depending on userID")
    public void testGetIsFavourited() {
        ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();
        userFavouritesList.add(1);
        userFavouritesList.add(2);
        for (Integer i : userFavouritesList){
            poi.setIsFavourited(i);
            assertTrue(poi.getIsFavourited(i));
        }
        assertFalse(poi.getIsFavourited(3));
    }
}
