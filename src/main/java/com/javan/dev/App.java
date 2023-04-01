package com.javan.dev;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Main class that is used to run the application.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class App
{
    /**
     * Main function that runs the application.
     * Function that waits for user to interact with the application, delegating any tasks to the appropriate classes. 
     * This function will be called when the application is ran and will run until the application is closed or exited.
     * @param args, none of which will be necessary to run the application.
     * @throws IOException if there is IO exception
     * @throws MalformedURLException for malformed URL
     */
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
        /**
         * Load Map Metadata
        */
        String directoryPath = "data/images/maps/floorPlans";
        String jsonFilePath = "data/images/maps/metadata/mapMetadata.json";
        JsonReader.addMapInfoToJSON(directoryPath, jsonFilePath);

        /**
         * Create UserInterface object to create the UI -> this will remain for the session
        */
        UserInterface.getInstance();
    }
}