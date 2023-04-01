package com.javan.dev;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

/**
 * Class used in association with all building points of interest. Creation, editing, etc.
 * @author : Dylan Sta Ana [dstaana@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class BuildingPointOfInterest {
    
    private int ID;
    private String name; 
    private final int userID;
    private final Boolean isUserMade;
    private String poiType;
    private int[] coordinates = {0,0};
    private int buildingID;
    private ArrayList<Integer> userFavouritesList = new ArrayList<>();
    private final String mapFilePath;
    private boolean isVisible;
    private String description;

    /**
     * Private variable to hold the instance of the data processor
     */
    private final DataProcessor processor = DataProcessor.getInstance();


    /**
     * Constructor for the building POI
     * @param name - the name of the building POI
     * @param userID - the user ID
     * @param isUsermade - if it is user made or not
     * @param poiType - type of POI
     * @param coordinatesX - x coordinate of POI
     * @param isVisible - if the POI is visible
     * @param coordinatesY - y coordinate of POI
     * @param buildingID - the ID of the building 
     * @param userFavouritesList - the list of favourited users
     * @param description - the description
     */
    public BuildingPointOfInterest(String name, int userID, boolean isUsermade, String poiType, int coordinatesX, int coordinatesY, int buildingID, ArrayList<Integer> userFavouritesList, String description, boolean isVisible)  {
        this.name = name;
        this.userID = userID;
        this.isUserMade = isUsermade;
        this.poiType = poiType;
        this.coordinates[0] = coordinatesX;
        this.coordinates[1] = coordinatesY;
        this.buildingID = buildingID;
        this.userFavouritesList = userFavouritesList;
        this.ID = processor.makeNewBuildingPOIID();
        this.description = description;
        this.mapFilePath = processor.loadMapFilePath(this.buildingID, 1, "BUILDING");
        this.isVisible = isVisible;
    }
    

    /**
     * Getter for the description of the POI
     * @return String of the description 
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter for the building POI Id
     * @return int of the ID 
     */
    public int getID() {
        return ID;
    }

    /**
     * Set building ID
     * @param buildingID - the building ID
     * @return buildingID - the new building ID
     */
    public int setBuildingID(int buildingID) {
        return this.buildingID = buildingID;
    }


    /**
     * Getter for the name of the POI
     * @return String of the name
     */
    public String getName() {
        return this.name; 
    }

    /**
     * Getter for the file path of the map that the POI is on
     * @return String of the name
     */
    public String getMapFilePath() {
        return this.mapFilePath; 
    }

    /**
     * Getter for the ID of the user
     * @return int of the ID
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Getter for the user made POI
     * @return true or false of user made
     */
    public boolean getIsUserMade() {
        return this.isUserMade;
    }

    /**
     * Getter for the type of the POI
     * @return String of the type
     */
    public String getPOItype() {
        return poiType;
    }

    /**
     * Getter for the coordinates of the POI 
     * @return int[] of the coordinates
     */
    public int[] getCoordinates() {
        return this.coordinates;
    }

    /**
     * Getter for the building ID of the POI
     * @return int of the building ID
     */
    public int getBuildingID() {
        return this.buildingID;
    }

    /** 
     * Getter for the favourited POI
     * @param userID - the user ID
     * @return int of the favourited 
     */
    public Boolean getIsFavourited(int userID) {
        for (int id : this.userFavouritesList) {
            if(id == userID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a JSON object from the Building POI
     * @return json object
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("ID", this.ID);
        json.put("userID", this.userID);
        json.put("isUserMade", this.isUserMade);
        json.put("poiType", this.poiType);
        json.put("coordinates", new JSONArray(this.coordinates));
        json.put("buildingID", this.buildingID);
        json.put("userFavouritesList", this.userFavouritesList);
        json.put("description", this.description);
        json.put("isVisible", this.isVisible);
        return json;
    }
    
    /**
     * Setter for POI X and Y position
     * @param x - the x coordinate
     * @param y - the y coordinate
     */
    public void setCoordinates(int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    /**
     * Setter for the name of the building POI
     * @param newName 
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Setter for the type of the POI
     * @param newLayer 
     */
    public void setPOItype(String newLayer) {
        this.poiType = newLayer;
    }

    /**
     * Setter for the description of the POI
     * @param newDesc 
     */
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    /** 
     * Getter for the favourited list
     * @return the user favourites list
     */
    public ArrayList<Integer> getIsFavourited() {
        return this.userFavouritesList;
    }

    /**
     * Getter for the isVisible state of POI
     * @return true or false of isVisible state
     */
    public Boolean getIsVisible(){
        return this.isVisible;
    }

    /**
     * Setter for the favourited status of a given user ID for the building POI
     * @param userID 
     */
    public void setIsFavourited(int userID) {
        Boolean in = false;
        for (int i = 0; i < userFavouritesList.size(); i++) {
            if (userFavouritesList.get(i) == userID) {
                userFavouritesList.remove(i);
                in = true;
            }
        }
        if (!in) {
            userFavouritesList.add(userID);
        }
    }

    /**
     * Setter for ID
     * @param ID - the ID of the POI
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    
    /*
     * Setter for isVisible 
     * @param isVisible - if the POI is visible
     */
    public void setisVisible(boolean isVisible) {
        this.isVisible= isVisible;
    }
}