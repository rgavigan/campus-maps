package com.javan.dev;

/**
 * Class that is used to implement the objects for Building Maps in the application
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class BuildingMap implements Map {
    /**
     * Declaring the name, ID, filePath and FloorArray of the Building.
     */
    private final String filePath;
    private final int mapID;
    private final String mapType;
    private String mapName;

    /**
     * Private variable to hold the instance of the data processor
     */
    private DataProcessor processor = DataProcessor.getInstance();

    /**
     * Constructor for the BuildingMap class to initialize the ID, type and filePath
     * @param mapID - the ID of the map
     */
    public BuildingMap(int mapID) {
        this.filePath = processor.loadMapFilePath(mapID, 0, "BUILDING");
        this.mapID = mapID;
        this.mapType = "BUILDING";
    }

    /**
     * Getter for filePath
     * @return String - the file path
     */
    @Override
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Getter for mapID
     * @return int - the map ID
     */
    @Override
    public int getMapID() {
        return this.mapID;
    }

    /**
     * Getter for buildingID id
     * @return int - map ID of the building
     */
    @Override
    public int getBuildingID() {
        return this.mapID;
    }


    /**
     * Getter for mapType
     * @return String - the map type
     */
    @Override
    public String getMapType() {
        return this.mapType;
    }

    /**
     * Getter for map name
     * @return mapName - the map name
     */
    public String getMapName() {
        return this.mapName;
    }

    /**
     * Setter for building name
     * @param buildingName - the name of the building
     */
    public void setName(String buildingName) {
        this.mapName = buildingName;
    }
}