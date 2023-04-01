package com.javan.dev;

import org.json.JSONObject;
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




public class TestBuildingPointOfInterest {
    DataProcessor processor = DataProcessor.getInstance();
    private BuildingPointOfInterest poi;
    @BeforeEach
    public void setup() {
        poi = new BuildingPointOfInterest("POI 1", 1, true, "type", 10, 20, 1, new ArrayList<Integer>(), "description", true);
    }
    
    /**
     * Method to test that the POI was constructed properly
     */
    @Test
    @DisplayName("Should confirm that all poi variables were constructed properly, and return the appropriate values for all getter methods")
    public void testGetters() {
        assertEquals("POI 1", poi.getName());
        assertEquals(1, poi.getUserID());
        assertEquals(true, poi.getIsUserMade());
        assertEquals("type", poi.getPOItype());
        assertEquals(10, poi.getCoordinates()[0]);
        assertEquals(20, poi.getCoordinates()[1]);
        assertEquals(1, poi.getBuildingID());
        assertEquals(1, poi.getUserID());
        assertEquals("description", poi.getDescription());
        assertFalse(poi.getIsFavourited(poi.getUserID()));
        assertEquals(true, poi.getIsVisible());
        assertEquals(processor.makeNewBuildingPOIID(), poi.getID());
        

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

    /**
     * Method to test that the toJSON method creates an accurately made JSON object
     */
    @Test
    @DisplayName("Should confirm a valid and correct JSON object is constructed")
    public void testToJSON() {

        JSONObject json = poi.toJSON();
        String expectedName = "POI 1";
        int expectedID = poi.getID();
        int expectedUserID = 1;
        boolean expectedIsUserMade = true;
        String expectedPoiType = "type";
        int expectedCoordinatesX = 10;
        int expectedCoordinatesY = 20;
        int expectedBuildingID = 1;
        ArrayList<Integer> expectedUserFavouritesList = new ArrayList<Integer>();
        String expectedDescription = "description";
        boolean expectedIsVisible = true;

        assertEquals(expectedName, json.getString("name"));
        assertEquals(expectedID, json.getInt("ID"));
        assertEquals(expectedUserID, json.getInt("userID"));
        assertEquals(expectedIsUserMade, json.getBoolean("isUserMade"));
        assertEquals(expectedPoiType, json.getString("poiType"));
        ArrayList<Integer> jsonCoordinates = new ArrayList<Integer>();
        for (Object i : json.getJSONArray("coordinates")) {
            jsonCoordinates.add((Integer) i);
        }
        assertEquals(expectedCoordinatesX, jsonCoordinates.get(0));
        assertEquals(expectedCoordinatesY, jsonCoordinates.get(1));
        assertEquals(expectedBuildingID, json.getInt("buildingID"));
        ArrayList<Integer> jsonUserFavouritesList = new ArrayList<Integer>();
        for (Object i : json.getJSONArray("userFavouritesList")) {
            jsonUserFavouritesList.add((Integer) i);
        }
        assertEquals(expectedUserFavouritesList, jsonUserFavouritesList);

        assertEquals(expectedDescription, json.getString("description"));
        assertEquals(expectedIsVisible, json.getBoolean("isVisible"));
    }
    
}
