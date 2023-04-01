package com.javan.dev;

/**
 * Import necessary libraries
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
class TestPOIComponent {

    private POIComponent poiComponent;

    @BeforeEach
    void setUp() {
        poiComponent = POIComponent.getInstance();
    }

    /**
     * This method tests that the creating a toggle button does not give a null button
     */
    @Test
    @DisplayName("Test Creating Toggle Button")
    void testCreateToggleButton() {
        JToggleButton button = poiComponent.createPOILayerToggleButton("Test");
        assertNotNull(button, "Button is null");
    }

    /**
     * This method tests that adding POIs to the panel is greater than 0
     */
    @Test
    @DisplayName("Test Adding POIs to Map")
    void testAddPOIsToMap() {
        /**
         * Create arraylist of test PointOfInterest objects
         */
        ArrayList<PointOfInterest> testPOIs = new ArrayList<PointOfInterest>();
        for (int i = 0; i < 5; i++) {
            testPOIs.add(new PointOfInterest(null, i, false, null, i, i, i, i, null, null, null, false));
        }

        /**
         * Create random grid display JPanel
         */
        JPanel gridDisplay = new JPanel();
        gridDisplay.setLayout(new GridLayout(5, 5));

        /**
         * Add POIs to the panel
         */
        int res = poiComponent.addPOIsToPanel(gridDisplay, testPOIs);
        assertTrue(res > 0, "No POIs added to panel");
    }


    /**
     * This method tests that the POIComponent is not null
     */
    @Test
    @DisplayName("Test POI Panel is Not Null")
    void testPOIPanelNotNull() {
        assertNotNull(poiComponent.getPOIPanel(), "POI Panel is null");
    }

    @Test
    @DisplayName("Test POI Layers Toggle Buttons")
    void testPOILayersToggleButtons() {
        JPanel poiLayerPanel = poiComponent.getPOIPanels().get(0);

        // Get the toggle buttons for POI Layers
        JToggleButton accessibilityButton = (JToggleButton) poiLayerPanel.getComponent(1);
        JToggleButton restaurantsButton = (JToggleButton) poiLayerPanel.getComponent(2);
        JToggleButton classroomsButton = (JToggleButton) poiLayerPanel.getComponent(3);
        JToggleButton labsButton = (JToggleButton) poiLayerPanel.getComponent(4);
        JToggleButton userButton = (JToggleButton) poiLayerPanel.getComponent(5);

        // Test that all the toggle buttons are selected initially
        assertTrue(accessibilityButton.isSelected());
        assertTrue(restaurantsButton.isSelected());
        assertTrue(classroomsButton.isSelected());
        assertTrue(labsButton.isSelected());
        assertTrue(userButton.isSelected());
    }
}