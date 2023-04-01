package com.javan.dev;

/**
 * Import necessary libraries
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.swing.*;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
@DisplayName("POIInfoWindow Tests")
class TestPOIInfoWindow {
    /**
     * Private variables for POIInfoWindow tests
     */
    private POIInfoWindow poiInfoWindow;
    private MapComponent map = MapComponent.getInstance();

    /**
     * Create new POIInfoWindow object before each test with POI name "Test"
     */
    @BeforeEach
    @Test
    void setUp() {
        map.changeToCampusMap();
        poiInfoWindow = new POIInfoWindow(1);
    }

    /**
     * This method tests that the POIInfoWindow frame title is correct
     */
    @Test
    @DisplayName("Test POIInfoWindow Frame Title")
    void testFrameTitle() throws InterruptedException {
        assertEquals("Arts and Humanities", poiInfoWindow.getFrame().getTitle(), "Frame title is incorrect");
    }

    /**
     * This method tests that the POIInfoWindow labels are correct
     */
    @Test
    @DisplayName("Test POIInfoWindow Labels")
    void testLabels() {
        map.changeToCampusMap();
        ArrayList<JLabel> labels = poiInfoWindow.getLabels();
        

        assertEquals("Arts and Humanities", labels.get(0).getText(), "POI name label is incorrect");
        assertEquals("BuildingPOI ID: 1", labels.get(1).getText(), "POI ID label is incorrect");
        assertEquals("Description: Arts and Humanities Building", labels.get(3).getText(), "POI description label is incorrect");
    }

    /**
     * This method tests that the POIInfoWindow frame is visible
     */
    @Test
    @DisplayName("Test POIInfoWindow Frame Visibility")
    void testFrameVisibility() {
        // Set to visible frame
        poiInfoWindow.getFrame().setVisible(true);
        assertTrue(poiInfoWindow.getFrame().isVisible(), "Frame visibility is incorrect");
    }

}
