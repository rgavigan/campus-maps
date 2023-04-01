package com.javan.dev;

/**
 * Include necessary libraries
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class TestMapComponent {

    private static MapComponent mapComponent;

    /**
     * Before each test method, get the Map instance
     */
    @BeforeAll
    static void setUp() {
        mapComponent = MapComponent.getInstance();
    }

    /**
     * This method is called to check that the MapComponent singleton instance is not null and that it is the same instance
     */
    @Test
    void testGetInstanceReturnsSameInstance() {
        MapComponent instance1 = MapComponent.getInstance();
        MapComponent instance2 = MapComponent.getInstance();
        assertEquals(instance1, instance2);
    }

    /**
     * This method is called to check that the map panel is not null
     */
    @Test
    void testGetMapPanelReturnsNonNullJPanel() {
        JPanel mapPanel = mapComponent.getMapPanel();
        assertNotNull(mapPanel);
    }

    /**
     * This method is called to check that the current map ID is 0, meaning the campusMap is open on default
     */
    @Test
    void testGetCurrentMapIDReturns0() {
        mapComponent.changeToCampusMap();
        int currentMapID = mapComponent.getCurrentMapID();
        assertEquals(0, currentMapID);
    }

    /**
     * This method is called to check that the current map is the campus map
     */
    @Order(1)
    @Test
    void testGetIsCampusMapReturnsTrue() {
        mapComponent.changeToCampusMap();
        boolean isCampusMap = mapComponent.getIsCampusMap();
        assertTrue(isCampusMap);
    }
}
