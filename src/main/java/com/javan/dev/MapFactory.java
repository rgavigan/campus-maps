package com.javan.dev;

/**
 * Factory class used in map creation.
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class MapFactory {
    /**
     * Function to create map metadata based on images in directory
     * @param mapType type of map to be created
     * @param buildingID building ID of relevant floor or building map
     * @param mapID id of the map
     * @return Map object
     */
    public static Map createMap(String mapType, int buildingID, int mapID)
    {
        if (mapType == null || mapType.isEmpty())
            return null;        
        else if (mapType.equals("FLOOR")) {
            return createFloorMap(buildingID, mapID);
        }
        else if (mapType.equals("BUILDING")) {
            return createBuildingMap(mapID);
        }
        else if (mapType.equals("CAMPUS")) {
            return CampusMap.getInstance(mapID);
        }
        else {
            throw new IllegalArgumentException("Unknown map type " + mapType);
        }
    }

    public static FloorMap createFloorMap(int buildingID, int mapID) {
        return new FloorMap(buildingID, mapID);
    }
    
    public static BuildingMap createBuildingMap(int mapID) {
        return new BuildingMap(mapID);
    }

    
}
