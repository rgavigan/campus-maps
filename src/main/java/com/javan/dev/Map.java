package com.javan.dev;

/**
 * Map interface that is used by the 3 map types.
 * @author : Brad McGlynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public interface Map {

    /**
     * Getter for file path
     * @return file path string
     */
    public String getFilePath();

    /**
     * Getter for map id
     * @return map id int
     */
    public int getMapID();


    /**
     * Getter for map type
     * @return String map type
     */
    public String getMapType();


    /**
     * Getter for map id
     * @return map id int
     */
    public int getBuildingID();

}