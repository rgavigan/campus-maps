package com.javan.dev;
import java.io.IOException;

/**
 * Main class for the floor map objects in the application.
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class FloorMap implements Map{
    /**
     * Declaring the mapID, buildingID, filePath and map type of the Floor.
     */
    private String filePath;
    private int mapID;
    private int buildingID;
    private String mapType;
    
    /**
     * Private variable to hold the instance of the data processor
     */
    private DataProcessor processor = DataProcessor.getInstance();

    /**
     * Constructor for the FloorMap class to initialize the ID, type and filePath
     * @param buildingID
     * @param mapID
     */
    public FloorMap(int buildingID, int mapID) {
        this.filePath = processor.loadMapFilePath(buildingID, mapID, "FLOOR");
        this.mapID = mapID;
        this.buildingID = buildingID;
        this.mapType = "FLOOR";
    }

    /**
     * Getter for filePath
     * @return String
     */
    @Override
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Getter for the mapID
     * @return mapID
     */
    @Override
    public int getMapID() {
        return this.mapID;
    }



    /**
     * Getter for the buildingID
     * @return mapID
     */
    @Override
    public int getBuildingID() {
        return this.buildingID;
    }


    /**
     * Getter for mapType
     * @return String
     */
    @Override
    public String getMapType() {
        return this.mapType;
    }


    /**
     * Method to check if there is a floorMap above this one
     * @return boolean that determines if the above floor exists
     */
    public boolean checkFloorAbove() {
        return this.processor.checkFloorAbove(this.mapID, this.buildingID);
    }

    /**
     * Method to check if there is a floorMap below this one
     * @return boolean that determines if the below floor exists
     */
    public boolean checkFloorBelow() {
        return this.processor.checkFloorBelow(this.mapID, this.buildingID);
    }

    /**
     * Method to check if get the floorMap above this one
     * @return FloorMap object above the current floorMap
     * @throws IOException
     */
    public FloorMap getFloorAbove() throws IOException {
        return this.processor.getFloorAbove(this.mapID, this.buildingID);
    }

    /**
     * Method to check if get the floorMap below this one
     * @return FloorMap object below the current floorMap
     * @throws IOException
     */
    public FloorMap getFloorBelow() throws IOException {
        return this.processor.getFloorBelow(this.mapID, this.buildingID);
    }

}