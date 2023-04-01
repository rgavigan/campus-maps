package com.javan.dev;

/**
 * Include necessary libraries
 */
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
class TestSidebarComponent {
    /**
     * SidebarComponent instance to be used for testing
     */
    private SidebarComponent sidebar;

    /**
     * Before each test, get a new SidebarComponent instance
     * @throws MalformedURLException
     * @throws IOException
     */
    @BeforeEach
    void setUp() throws MalformedURLException, IOException {
        sidebar = SidebarComponent.getInstance();
    }

    /**
     * Test that the sidebar panel is not null
     */
    @Test
    @DisplayName("SidebarComponent has a non-null sidebar panel.")
    void testSidebarPanelExists() {
        assertNotNull(sidebar.getSidebarPanel());
    }

    /**
     * Test that the search bar exists and is correctly formatted
     */
    @Test
    @DisplayName("SidebarComponent creates a search bar with text field and search button.")
    void testSearchBarExists() {
        JPanel searchBar = sidebar.createSearchBar();
        assertEquals(2, searchBar.getComponents().length);
        assertTrue(searchBar.getComponents()[0] instanceof JTextField);
        assertTrue(searchBar.getComponents()[1] instanceof JButton);
    }

    /**
     * Test that the Points of Interest List panel exists
     */
    @Test
    @DisplayName("SidebarComponent creates a Points of Interest List panel.")
    void testPOIListPanelExists() {
        sidebar.createPOIListPanel();
        assertNotNull(sidebar.getPOIListContentPanel());
    }

    /**
     * Test that the Weather Info panel exists
     */
    @Test
    @DisplayName("SidebarComponent creates a Weather Info panel.")
    void testWeatherInfoPanelExists() throws MalformedURLException, IOException {
        sidebar.createWeatherInfoPanel();
        assertNotNull(sidebar.getWeatherInfoContentPanel());
    }

    /**
     * Test that the POI list button exists
     */
    @Test
    @DisplayName("SidebarComponent has a toggle button for Points of Interest.")
    void testPOIToggleButtonExists() {
        assertNotNull(sidebar.getPOIListBtn());
        assertEquals("Points of Interest", sidebar.getPOIListBtn().getText());
    }

    /**
     * Test that the weather info button exists
     */
    @Test
    @DisplayName("SidebarComponent has a toggle button for Weather Info.")
    void testWeatherInfoToggleButtonExists() {
        assertNotNull(sidebar.getWeatherInfoBtn());
        assertEquals("Weather Information", sidebar.getWeatherInfoBtn().getText());
    }
}
