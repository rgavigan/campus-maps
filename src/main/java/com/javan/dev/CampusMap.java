package com.javan.dev;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used for the Campus Map. This is a singleton for the single campus map.
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class CampusMap implements Map{
    /**
     * Declaring the file path, ID, type and BuildingArray of the Campus.
     */
    private final String filePath;
    private final int mapID;
    private final String mapType;
    private ArrayList<BuildingMap> buildingArray = new ArrayList<>();

    /**
     * Private variable to hold instance of CampusMap
     */
    private static CampusMap INSTANCE;

    /**
     * Constructor for the CampusMap class to initialize the ID, type and filePath
     * @param mapID - the map ID
     * @throws IOException
     */
    private CampusMap(int mapID) throws IOException {
        this.filePath = "data/images/maps/campusMap.png";
        this.mapID = mapID;
        this.mapType = "CAMPUS";

        /**
         * Add BuildingMaps to buildingArray
         */
        buildingArray = JsonReader.getBuildingMaps("data/images/maps/metadata/mapMetadata.json");
    }

    /**
     * Getter for CampusMap Instance
     * @param mapID - the map ID
     * @return the instance of the campus map
     */
    public static CampusMap getInstance(int mapID) {
        if (INSTANCE == null) {
            try {
                INSTANCE = new CampusMap(mapID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
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
     * @return building id int
     */
    @Override
    public int getBuildingID() {
        return -1;
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
     * Getter for the BuildingArray
     * @return buildingArray - the building array
     */
    public ArrayList<BuildingMap> getBuildingArray() {
        return this.buildingArray;
    }

}