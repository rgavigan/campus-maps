package com.javan.dev;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.io.FileWriter;

/**
 * Main back-end class used with the reading of JSON data and any other JSON-related applications.
 * @author : Brad McGlynn [bmcglyn4@uwo.ca], Dylan Sta Ana [dstaana@uwo.ca], Deep Ashishkumar Shah [dshah228@uwo.ca]
 * @version : 1.1
 * @since : 1.0
 */
public class JsonReader {

    /**
     * Method to read the contents found at fileName and returns them as a string
     * @param fileName, file to be read
     * @return String content, file content as a string
     * @throws IOException If there is an error reading the map metadata file.
     */
    public static String read(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }

    /**
     * method to create map metadata based on images in directory
     * @param directoryPath, directory path of the directory of directories containing map PNG's
     * @param jsonFilePath, the target path for the json file of metadata to be created at
     */
    public static void addMapInfoToJSON(String directoryPath, String jsonFilePath) {
        /**
         * Create a JSON array to hold the maps
         */
        JSONArray buildingsJson = new JSONArray();
    
        /**
         * Get a list of sub-directories in the specified directory
         */
        File directory = new File(directoryPath);
        File[] subdirectories = directory.listFiles();

        /**
         * Sort the subdirectories alphabetically
         */
        java.util.Arrays.sort(subdirectories);

        int mapID = 1;
        int buildingId = 1;
        /**
         * Loop through each subdirectory inside floorPlans (each Building's directory of maps)
         */
        for (File subdirectory : subdirectories) {
            /**
             * Set floor ID to 1 for each building
             */
            mapID = 1;

            /**
             * Create a new JSON object for this building
             */
            JSONObject buildingJson = new JSONObject();

            /**
             * Get the building name subdirectory
             */
            String buildingName = subdirectory.getName();
    
            /**
             * Set the building name, type and ID in the JSON object
             */
            buildingJson.put("mapName", buildingName);
            buildingJson.put("mapType", "BUILDING");
            buildingJson.put("buildingID", buildingId);
    
            /**
             * Get a list of all PNG files in this subdirectory (these are the FloorMap imaages)
             */
            File[] imageFiles = subdirectory.listFiles();
            java.util.Arrays.sort(imageFiles);
    
            /**
             * Create a JSON array to hold the floor maps for the building
             */
            JSONArray mapsJson = new JSONArray();

            /**
             * Loop through each image file and add it to the JSON array
             */
            for (File imageFile : imageFiles) {
                /**
                 * Create a new JSON object for this floor
                 */
                JSONObject mapJson = new JSONObject();

                /**
                 * Get the map name from the subdirectory 
                 */
                String mapName = imageFile.getName();
        
                /**
                 * Set the map name, map type and ID in the JSON object
                 */
                mapJson.put("mapName", mapName);
                mapJson.put("mapType", "FLOOR");
                mapJson.put("mapID", mapID);
                
                /**
                 * Loop through each image file and add it to the JSON array
                 */
                String imagePath = imageFile.getPath();
                mapJson.put("filePath", imagePath);
                mapsJson.put(mapJson);
                
                /**
                 * Increment mapID by 1 for next floor within the building
                 */
                mapID++;
            }
    
            /**
             * Set the floor maps in the JSON object
             */
            buildingJson.put("floorMaps", mapsJson);

            /**
             * Set the building path in the JSON object
             */
            String buildingPath = subdirectory.getPath();
            buildingJson.put("filePath", buildingPath);
    
            /**
             * Add the map JSON object to the buildings JSON array
             */
            buildingsJson.put(buildingJson);

            /**
             * Increment the building ID for next building
             */
            buildingId++;
        }

        /**
         * Write the maps to the JSON file
         */
        FileWriter file = null;
        try {
            file = new FileWriter(jsonFilePath);
            file.write(buildingsJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns the floor maps associated with this map metadata file.
     * @param mapJsonFilePath
     * @return An ArrayList of FloorMap objects representing the floor maps.
     * @throws IOException If there is an error reading the map metadata file.
     */
    public static ArrayList<FloorMap> getFloorMaps(String mapJsonFilePath) throws IOException {
        /*
         * Initialize an ArrayList to store the floor maps.
         */
        ArrayList<FloorMap> floorMaps = new ArrayList<>();
        /*
         * Use the JSONReader to read the map metadata file.
         */
        JSONArray buildings = new JSONArray(JsonReader.read(mapJsonFilePath));
        /*
         * Loop through each building in the JSON data.
         */
        for (int i = 0; i < buildings.length(); i++) {
            JSONObject buildingObj = (JSONObject) buildings.get(i);
            /*
             * Get the building ID
             */
            int buildingID = ((Integer) buildingObj.get("buildingID"));
            /*
             * Get the floor maps for this building.
             */
            JSONArray floors = (JSONArray) buildingObj.get("floorMaps");
            /*
             * Loop through each floor in the JSON data.
             */
            for (int j = 0; j < floors.length(); j++) {
                JSONObject floorObj = (JSONObject) floors.get(j);
                /*
                 * Get the floor map ID and name.
                 */
                int floorID = ((Integer) floorObj.get("mapID"));

                /*
                 * Create a new FloorMap object and add it to the ArrayList.
                 */
                FloorMap floorMap = new FloorMap(buildingID, floorID);
                floorMaps.add(floorMap);
            }
        }
        /*
         * Return the ArrayList of floor maps.
         */
        return floorMaps;
    }

    /**
     * Method to get all of the building maps from the JSON file and create BuildingMaps, adding them to CampusMap
     * @param mapJsonFilePath
     * @return buildingMaps
     * @throws java.io.IOException
     */
    public static ArrayList<BuildingMap> getBuildingMaps(String mapJsonFilePath) throws IOException {
        /*
         * Initialize an ArrayList to store the building maps.
         */
        ArrayList<BuildingMap> buildingMaps = new ArrayList<BuildingMap>();
        /*
         * Use the JSONReader to read the map metadata file.
         */
        JSONArray buildings = new JSONArray(JsonReader.read(mapJsonFilePath));
        /*
         * Loop through each building in the JSON data.
         */
        for (int i = 0; i < buildings.length(); i++) {
            JSONObject buildingObj = (JSONObject) buildings.get(i);
            /*
             * Get the building ID
             */
            int buildingID = ((Integer) buildingObj.get("buildingID"));

            /**
             * Get the building name
             */
            String buildingName = (String) buildingObj.get("mapName");
            /*
             * Create a new BuildingMap object and add it to the ArrayList.
             */
            BuildingMap buildingMap = new BuildingMap(buildingID);
            buildingMap.setName(buildingName);
            buildingMaps.add(buildingMap);
        }
        /*
         * Return the ArrayList of building maps.
         */
        return buildingMaps;
    }


    /**
     * method to find filePath of FloorMap using Gson
     * @param buildingID
     * @param mapID
     * @param jsonFilePath
     * @return file path of floor map
     */
    public static String getFloorMapPathFromID(int buildingID, int mapID, String jsonFilePath) {
        FileReader reader;
        try {
            /*
             * Reads from jsonFilePath in order to get the appropriate building maps info,
             * then loops through the building maps the to find the floormap paths
             */
            reader = new FileReader(jsonFilePath);
            JsonArray buildingMaps = JsonParser.parseReader(reader).getAsJsonArray();
            String mapPath;
            JsonArray floorMaps;
            int counter = 0;
            int floorCounter = 0;

            while (counter < buildingMaps.size()) {
                if (counter + 1 == buildingID){ 
                    /* 
                     * counter + 1 because buildingIDs start at 1 rather than 0, but 
                     * the JsonArray is 0 indexed
                     */
                    floorMaps = buildingMaps.get(counter).getAsJsonObject().get("floorMaps").getAsJsonArray();
                    while (floorCounter < floorMaps.size()) {
                        mapPath = floorMaps.get(floorCounter).getAsJsonObject().get("filePath").getAsString();
                        int id = floorMaps.get(floorCounter).getAsJsonObject().get("mapID").getAsInt();
                        if (id == mapID) {
                            return mapPath;
                        }
                        floorCounter++;
                    }
                }
                counter++;
                floorCounter = 0;
            }
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * method to find filePath of BuildingMap using Gson
     * @param buildingID
     * @param jsonFilePath
     * @return file path of building map
     */
    public static String getBuildingMapPathFromID(int buildingID, String jsonFilePath) {
        FileReader reader;
        try {
            reader = new FileReader(jsonFilePath);
            JsonArray buildingMaps = JsonParser.parseReader(reader).getAsJsonArray();
            String mapPath;
            int counter = 0;

            while (counter < buildingMaps.size()) {
                mapPath = buildingMaps.get(counter).getAsJsonObject().get("filePath").getAsString();
                int id = buildingMaps.get(counter).getAsJsonObject().get("buildingID").getAsInt();
                if (id == buildingID) {
                    return mapPath;
                }
                counter++;
            }
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * method to get userFavouites POI Data
     * @param userID
     * @return sorted POI array of user favourites if exists
     */
    public static ArrayList<PointOfInterest> favouritesList(int userID) {

        FileReader reader;

        /*
         * attempts to read file 
         */
        try {
            
            reader = new FileReader("data/PointOfInterests/PointOfInterestMetadata.json");
            JsonArray POIDataArray = JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<PointOfInterest> arrayList = new ArrayList<>();

            /*
             * loops through json file to find user made POIs
             */ 
            for (JsonElement POI : POIDataArray) {

                JsonObject poiObject = POI.getAsJsonObject();
                JsonArray userFavouritesArray = poiObject.getAsJsonArray("userFavouritesList");
                ArrayList<Integer> userFavouritesData = new ArrayList<>();

                if (userFavouritesArray.size() > 0) {
                    for (int i = 0; i < userFavouritesArray.size(); i++) {
                        /**
                         * Get current value from the userFavouritesArray and store as int, then add to array
                         */
                        int userFavourite = userFavouritesArray.get(i).getAsInt();
                        userFavouritesData.add(userFavourite);
                    }
                }

                if (userFavouritesData.contains(userID)) {

                    /*
                    * declares all data from json file
                    * then creates a POI object
                    * which is then added to arraylist of type POI
                    */   

                    int poiID = poiObject.get("ID").getAsInt();  
                    String name  = poiObject.get("name").getAsString();
                    boolean isUserMade = poiObject.get("isUserMade").getAsBoolean();
                    String poiType = poiObject.get("poiType").getAsString();
                    JsonArray jsoncoordinateArray = poiObject.get("coordinates").getAsJsonArray();
                    int[] coordinateArray = new int[2];

                    for (int i=0; i< coordinateArray.length; i++){
                        coordinateArray[i] = jsoncoordinateArray.get(i).getAsInt();
                    }

                    int floorID = poiObject.get("floorID").getAsInt();
                    int buildingID = poiObject.get("buildingID").getAsInt();
                    ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();

                    for (int i = 0; i < userFavouritesData.size(); i++) {
                        userFavouritesList.add(i, userFavouritesData.get(i));
                    }

                    String description = poiObject.get("description").getAsString();
                    String roomNumber = poiObject.get("roomNumber").getAsString();
                    Boolean isVisible = poiObject.get("isVisible").getAsBoolean();
                    PointOfInterest POIdata = new PointOfInterest(name, userID, isUserMade, poiType, coordinateArray[0], coordinateArray[1], floorID, buildingID, userFavouritesList, description, roomNumber, isVisible);
                    POIdata.setID(poiID);
                    arrayList.add(POIdata);
                }   
            }
            /**
             * Sort ArrayList by the POI name alphabetically (Each POI object's name attribute)
             * Put it into a new ArrayList that will be returned
             */
            return sortPOIArray(arrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

     /**
     * method to get user made POI Data
     * @param userID
     * @return user POI list
     */
    public static ArrayList<PointOfInterest> userPOIList(int userID) {
        
        FileReader reader;

        /*
         * attempts to read file 
         */
        try {

            reader = new FileReader("data/PointOfInterests/PointOfInterestMetadata.json");
            JsonArray POIDataArray= JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<PointOfInterest> arrayList = new ArrayList<PointOfInterest>();

            /*
             * loops through json file to find user made POIs
             */ 
            for (JsonElement POI : POIDataArray) {

                JsonObject poiObject = POI.getAsJsonObject();
                if (poiObject == null) {
                    return null;
                }
                JsonArray userFavouritesArray = poiObject.getAsJsonArray("userFavouritesList");
                ArrayList<Integer> userFavouritesData = new ArrayList<Integer>();

                if (userFavouritesArray != null) {
                    for (int i =0; i < userFavouritesArray.size(); i++) {
                        /**
                         * Get current value from the userFavouritesArray and store as int, then add to array
                         */
                        int userFavourite = userFavouritesArray.get(i).getAsInt();
                        userFavouritesData.add(userFavourite);
                    }
                }

                if (poiObject.get("isUserMade").getAsBoolean() && poiObject.get("userID").getAsInt() == userID){

                    /*
                    * declares all data from json file
                    * then creates a POI object
                    * which is then added to arraylist of type POI
                    */

                    int poiID = poiObject.get("ID").getAsInt();
                    String name  = poiObject.get("name").getAsString();
                    boolean isUserMade = poiObject.get("isUserMade").getAsBoolean();
                    String poiType = poiObject.get("poiType").getAsString();
                    JsonArray jsoncoordinateArray = poiObject.get("coordinates").getAsJsonArray();
                    int[] coordinateArray = new int[2];
                    
                    for (int i=0; i< coordinateArray.length; i++){
                        coordinateArray[i] = jsoncoordinateArray.get(i).getAsInt();
                    }

                    int floorID = poiObject.get("floorID").getAsInt();
                    int buildingID = poiObject.get("buildingID").getAsInt();
                    ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();

                    for (int i = 0; i < userFavouritesData.size(); i++) {
                        userFavouritesList.add(i, userFavouritesData.get(i));
                    }

                    String description = poiObject.get("description").getAsString();
                    String roomNumber = poiObject.get("roomNumber").getAsString();
                    Boolean isVisible = poiObject.get("isVisible").getAsBoolean();
                    PointOfInterest POIdata = new PointOfInterest(name, userID, isUserMade, poiType, coordinateArray[0], coordinateArray[1], floorID, buildingID, userFavouritesList, description, roomNumber, isVisible);
                    POIdata.setID(poiID);
                    arrayList.add(POIdata);
                }             
            }
            /**
             * Sort ArrayList by the POI name alphabetically (Each POI object's name attribute)
             * Put it into a new ArrayList that will be returned
             */
            return sortPOIArray(arrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * method to get Floor POIs currently available to the user
     * @param userID
     * @return floor POIs
     */
    public static ArrayList<PointOfInterest> universalPOIs(int userID) {

        FileReader reader;

        /**
         * Get the current map ID
         */
        MapComponent mapComponent = MapComponent.getInstance();
        int mapID = mapComponent.getCurrentMapID();

        /**
         * Get building ID
         */
        int curbuildingID = mapComponent.getCurrentBuildingID();

        /*
         * attempts to read file 
         */
        try {

            reader = new FileReader("data/PointOfInterests/PointOfInterestMetadata.json");
            JsonArray POIDataArray= JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<PointOfInterest> arrayList = new ArrayList<>();

            /*
             * loops through json file to find POIs available to this user
             */ 
            for (JsonElement POI : POIDataArray) {

                JsonObject poiObject = POI.getAsJsonObject();
                JsonArray userFavouritesArray = poiObject.getAsJsonArray("userFavouritesList");
                ArrayList<Integer> userFavouritesData = new ArrayList<>();

                if (userFavouritesArray != null) {
                    for (int i =0; i < userFavouritesArray.size(); i++) {
                        /**
                         * Get current value from the userFavouritesArray and store as int, then add to array
                         */
                        int userFavourite = userFavouritesArray.get(i).getAsInt();
                        userFavouritesData.add(userFavourite);
                    }
                }
                
                // either developer made or user made POIs
                if (poiObject.get("isUserMade").getAsBoolean() == false || poiObject.get("userID").getAsInt() == userID){

                    /*
                    * declares all data from json file
                    * then creates a POI object
                    * which is then added to arraylist of type POI
                    */

                    int poiID = poiObject.get("ID").getAsInt();
                    userID = poiObject.get("userID").getAsInt();
                    String name  = poiObject.get("name").getAsString();
                    boolean isUserMade = poiObject.get("isUserMade").getAsBoolean();
                    String poiType = poiObject.get("poiType").getAsString();
                    JsonArray jsoncoordinateArray = poiObject.get("coordinates").getAsJsonArray();
                    int[] coordinateArray = new int[2];
                    for (int i=0; i< coordinateArray.length; i++){
                        coordinateArray[i] = jsoncoordinateArray.get(i).getAsInt();
                    }
                    int floorID = poiObject.get("floorID").getAsInt();
                    int buildingID = poiObject.get("buildingID").getAsInt();
                    ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();

                    for (int i = 0; i < userFavouritesData.size(); i++) {
                        userFavouritesList.add(i, userFavouritesData.get(i));
                    }

                    String description = poiObject.get("description").getAsString();
                    String roomNumber = poiObject.get("roomNumber").getAsString();
                    boolean isVisible = poiObject.get("isVisible").getAsBoolean();
                    if (mapID == floorID && curbuildingID == buildingID) {
                        PointOfInterest POIdata = new PointOfInterest(name, userID, isUserMade, poiType, coordinateArray[0], coordinateArray[1], floorID, buildingID, userFavouritesList, description, roomNumber, isVisible);
                        POIdata.setID(poiID);
                        arrayList.add(POIdata);
                    }
                }       
            }
            /**
             * Sort ArrayList by the POI name alphabetically (Each POI object's name attribute)
             * Put it into a new ArrayList that will be returned
             */
            return sortPOIArray(arrayList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method to get Building POIs currently available to the user
     * @return building POIs
     */
    public static ArrayList<BuildingPointOfInterest> universalBuildingPOIs() {

        FileReader reader;

        /*
         * attempts to read file 
         */
        try {

            reader = new FileReader("data/PointOfInterests/BuildingPOIMetadata.json");
            JsonArray POIDataArray= JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<BuildingPointOfInterest> arrayList = new ArrayList<BuildingPointOfInterest>();

            /*
             * loops through json file to find POIs available to this user
             */ 
            for (JsonElement POI : POIDataArray) {

                JsonObject poiObject = POI.getAsJsonObject();
                JsonArray userFavouritesArray = poiObject.getAsJsonArray("userFavouritesList");
                ArrayList<Integer> userFavouritesData = new ArrayList<Integer>();

                if (userFavouritesArray != null) {
                    for (int i =0; i < userFavouritesArray.size(); i++) {
                        /**
                         * Get current value from the userFavouritesArray and store as int, then add to array
                         */
                        int userFavourite = userFavouritesArray.get(i).getAsInt();
                        userFavouritesData.add(userFavourite);
                    }
                }
                

                /* 
                 * developer made POIs
                 */
                if (poiObject.get("userID").getAsInt() == 1){

                    /*
                    * declares all data from json file
                    * then creates a POI object
                    * which is then added to arraylist of type POI
                    */

                    int poiID = poiObject.get("ID").getAsInt();
                    String name  = poiObject.get("name").getAsString();
                    boolean isUserMade = poiObject.get("isUserMade").getAsBoolean();
                    String poiType = poiObject.get("poiType").getAsString();
                    JsonArray jsoncoordinateArray = poiObject.get("coordinates").getAsJsonArray();
                    int[] coordinateArray = new int[2];
                    for (int i=0; i< coordinateArray.length; i++){
                        coordinateArray[i] = jsoncoordinateArray.get(i).getAsInt();
                    }
                    int buildingID = poiObject.get("buildingID").getAsInt();
                    ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();

                    for (int i = 0; i < userFavouritesData.size(); i++) {
                        userFavouritesList.add(i, userFavouritesData.get(i));
                    }

                    String description = poiObject.get("description").getAsString();
                    boolean isVisible = poiObject.get("isVisible").getAsBoolean();
                    {
                        BuildingPointOfInterest POIdata = new BuildingPointOfInterest(name, 1, isUserMade, poiType, coordinateArray[0], coordinateArray[1], buildingID, userFavouritesList, description, isVisible);
                        POIdata.setID(poiID);
                        arrayList.add(POIdata);
                    }
                }       
            }
            /**
             * Sort arraylist by the POI name alphabetically (Each POI object's name attribute)
             * Put it into a new arraylist that will be returned
             */
            return sortBuildingPOIArray(arrayList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method to get POIs currently available to the user inside of the current building
     * @param userID
     * @param currBuilding
     * @return available POIs
     */
    public static ArrayList<PointOfInterest> currentBuildingPOIS(int userID, int currBuilding) {

        FileReader reader;

        /**
         * Get the current map ID
         */
        MapComponent mapComponent = MapComponent.getInstance();
        int mapID = mapComponent.getCurrentMapID();

        /*
         * attempts to read file 
         */
        try {

            reader = new FileReader("data/PointOfInterests/PointOfInterestMetadata.json");
            JsonArray POIDataArray= JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<PointOfInterest> arrayList = new ArrayList<PointOfInterest>();

            /*
             * loops through json file to find POIs available to this user
             */ 
            for (JsonElement POI : POIDataArray) {

                JsonObject poiObject = POI.getAsJsonObject();
                JsonArray userFavouritesArray = poiObject.getAsJsonArray("userFavouritesList");
                ArrayList<Integer> userFavouritesData = new ArrayList<Integer>();

                if (userFavouritesArray != null) {
                    for (int i =0; i < userFavouritesArray.size(); i++) {
                        /**
                         * Get current value from the userFavouritesArray and store as int, then add to array
                         */
                        int userFavourite = userFavouritesArray.get(i).getAsInt();
                        userFavouritesData.add(userFavourite);
                    }
                }
                
                /* 
                 * either developer made or user made POIs
                 */ 
                if (poiObject.get("userID").getAsInt() == 1 || poiObject.get("userID").getAsInt() == userID){

                    /*
                    * declares all data from json file
                    * then creates a POI object
                    * which is then added to arraylist of type POI
                    */

                    int poiID = poiObject.get("ID").getAsInt();
                    userID = poiObject.get("userID").getAsInt();
                    String name  = poiObject.get("name").getAsString();
                    boolean isUserMade = poiObject.get("isUserMade").getAsBoolean();
                    String poiType = poiObject.get("poiType").getAsString();
                    JsonArray jsoncoordinateArray = poiObject.get("coordinates").getAsJsonArray();
                    int[] coordinateArray = new int[2];
                    for (int i=0; i< coordinateArray.length; i++){
                        coordinateArray[i] = jsoncoordinateArray.get(i).getAsInt();
                    }
                    int floorID = poiObject.get("floorID").getAsInt();
                    int buildingID = poiObject.get("buildingID").getAsInt();
                    ArrayList<Integer> userFavouritesList = new ArrayList<Integer>();

                    for (int i = 0; i < userFavouritesData.size(); i++) {
                        userFavouritesList.add(i, userFavouritesData.get(i));
                    }

                    String description = poiObject.get("description").getAsString();
                    String roomNumber = poiObject.get("roomNumber").getAsString();
                    boolean isVisible = poiObject.get("isVisible").getAsBoolean();
                    if (mapID != floorID && currBuilding == buildingID) {
                        PointOfInterest POIdata = new PointOfInterest(name, userID, isUserMade, poiType, coordinateArray[0], coordinateArray[1], floorID, buildingID, userFavouritesList, description, roomNumber, isVisible);
                        POIdata.setID(poiID);
                        arrayList.add(POIdata);
                    }
                }       
            }
            /**
             * Sort ArrayList by the POI name alphabetically (Each POI object's name attribute)
             * Put it into a new ArrayList that will be returned
             */
            return arrayList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that sorts an ArrayList of POIs by their name alphabetically
     * @param ArrayList<PointOfInterest> arrayList
     * @return ArrayList<PointOfInterest>
     */
    private static ArrayList<PointOfInterest> sortPOIArray(ArrayList<PointOfInterest> arrayList) {
        ArrayList<PointOfInterest> sortedArrayList = new ArrayList<>();
        ArrayList<String> nameArray = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            nameArray.add(arrayList.get(i).getName());
        }
        Collections.sort(nameArray);
        for (int i = 0; i < nameArray.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (nameArray.get(i).equals(arrayList.get(j).getName())) {
                    sortedArrayList.add(arrayList.get(j));
                }
            }
        }
        return sortedArrayList;
    }

    /**
     * Method that sorts an arraylist of Building POIs by their name alphabetically
     * @param ArrayList<BuildingPointOfInterest> arrayList
     * @return ArrayList<BuildingPointOfInterest>
     */
    private static ArrayList<BuildingPointOfInterest> sortBuildingPOIArray(ArrayList<BuildingPointOfInterest> arrayList) {
        ArrayList<BuildingPointOfInterest> sortedArrayList = new ArrayList<>();
        ArrayList<String> nameArray = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            nameArray.add(arrayList.get(i).getName());
        }
        Collections.sort(nameArray);
        for (int i = 0; i < nameArray.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (nameArray.get(i).equals(arrayList.get(j).getName())) {
                    sortedArrayList.add(arrayList.get(j));
                }
            }
        }
        return sortedArrayList;
    }
}
    