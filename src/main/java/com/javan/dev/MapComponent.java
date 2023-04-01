package com.javan.dev;

// Import Necessary Libraries
import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;

/**
 * Map UI class that is used for the display of the map itself and POIs on it, and interactivity of the map itself.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class MapComponent extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    /**
     * Initialize private variables for the map UI
     */
    private JPanel mapPanel;
    private ImageIcon mapImg;
    private JLabel map;
    private JPanel imagePanel;
    private JScrollPane scrollPane;

    /**
     * Buttons to go on top of the map
     */
    private JButton floorAbove;
    private JButton floorBelow;
    private JButton campusMap;
    private JButton mapMode;
    private JPanel buttonPanel;

    /**
     * ArrayList to hold the points of interest
     */
    private ArrayList<PointOfInterest> pois;
    private ArrayList<PointOfInterest> userPOIs;
    private ArrayList<PointOfInterest> favouritePOIs;

    /**
     * ArrayList to hold the building points of interest
     */
    private ArrayList<BuildingPointOfInterest> buildingPOIs;
    /**
     * ImageIcon for the flags
     */
    private ImageIcon flag = new ImageIcon("data/images/flag.png");

    /**
     * Singleton instance variable
     */
    private static MapComponent INSTANCE;

    /**
     * Variables to hold the current map and whether or not it is the campus map. Also holding map mode
     */
    private boolean isCampusMap;
    private int currentMapID;
    private int currentBuildingID;
    private FloorMap floorMap = null;
    private Map campus = MapFactory.createMap("CAMPUS", 0, 0);
    private String mapType = "CAMPUS";
    private Map mapObject;
    private boolean isNavigationMode;

    /**
     * DataProcessor instance
     */
    private DataProcessor dataProcessor = DataProcessor.getInstance();
    private POIComponent poiComponent;

    /**
     * User instance
    */
    private User user = User.getInstance();

    /**
     * Coordinates of mouse
     */
    private int mouseStartX;
    private int mouseStartY;


    /**
     * Constructor to initialize the map component
     */
    private MapComponent() {
        /**
         * Create a new JPanel for the map
         */
        mapPanel = new JPanel();
        mapPanel.addMouseListener(this);
        mapPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        /**
         * Create a JPanel that stores 3 buttons to go on top of the map (Floor Up, Floor Down, Back to Campus Map)
         */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        campusMap = createMapButton("Campus Map");
        floorBelow = createMapButton("Floor Down");
        floorAbove = createMapButton("Floor Up");

        buttonPanel.add(campusMap);
        buttonPanel.add(floorBelow);
        buttonPanel.add(floorAbove);

        /**
         * Add scroll bars for the map panel as required
         */
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        /**
         * Add the default campus map image to the image panel
         */
        imagePanel = new JPanel();
        imagePanel.setLayout(null);

        /**
         * Campus map image and ID
         */
        mapObject = campus;
        mapImg = new ImageIcon(campus.getFilePath());
        isCampusMap = true;
        currentMapID = 0;


        map = new JLabel(mapImg);
        map.addMouseListener(this);
        map.addMouseMotionListener(this);
        map.setLayout(null);
        map.setBounds(0, 0, mapImg.getIconWidth(), mapImg.getIconHeight());
        imagePanel.setPreferredSize(new Dimension(mapImg.getIconWidth(), mapImg.getIconHeight()));
        imagePanel.add(map);

        /**
         * Display POIs and update buttons
         */
        displayPOIs();
        updateFloorButtons();

        /**
         * Add the scroll pane to the map panel
         */
        scrollPane.setViewportView(imagePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(12);

        /**
         * Make it so that the scroll pane displays the upper third of the map image when the UI is opened (use ImageIcon dimensions)
         */
        scrollPane.getViewport().setViewPosition(new Point(mapImg.getIconWidth() / 3, mapImg.getIconHeight() / 3));

        /**
         * Check for floor above/below
         */
        isFloorAbove(); 
        isFloorBelow(); 

        /**
         * Create panel to hold map mode button
         */
        JPanel mapModePanel = new JPanel();
        mapModePanel.setLayout(new GridLayout(1, 1));
        mapMode = createMapButton("Navigation Mode");
        isNavigationMode = true;
        mapModePanel.add(mapMode);

        /**
         * Fill the entire display with the campus map
         */
        mapPanel.setLayout(new GridBagLayout());
        /**
         * Add constraints to have buttonPanel at the top taking up as little space as possible, and scrollPane takes the remainder
         * Both should fill all horizontal space
         */
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.weightx = 0;
        gridConstraints.weighty = 0;
        gridConstraints.fill = GridBagConstraints.BOTH;
        mapPanel.add(mapModePanel, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.weightx = 0;
        gridConstraints.weighty = 0;
        gridConstraints.fill = GridBagConstraints.BOTH;
        mapPanel.add(buttonPanel, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;
        gridConstraints.fill = GridBagConstraints.BOTH;
        mapPanel.add(scrollPane, gridConstraints);
    }
    
    /**
     * Getter for the instance of the MapComponent
     * @return MapComponent Instance
     */
    public static MapComponent getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapComponent();
        }
        return INSTANCE;
    }

    /**
     * Method to get current Building ID
     * @return int currentBuildingID
     */
    public int getCurrentBuildingID() {
        return currentBuildingID;
    }

    /**
     * Getter for the user of the MapComponent
     * @return User user
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for map panel
     * @return mapPanel
     */
    public JPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * Getter for current map ID
     * @return currentMapID
     */
    public int getCurrentMapID() {
        return currentMapID;
    }

    /**
     * Getter for isCampusMap boolean
     * @return isCampusMap
     */
    public boolean getIsCampusMap() {
        return isCampusMap;
    }

    /**
     * Setter for isCampusMap boolean
     * @param setMap - boolean value for setting if it is campus map
     */
    public void setIsCampusMap(boolean setMap) {
        this.isCampusMap = setMap;
    }

    /**
     * Getter for mapType
     * @return String mapType
     */
    public String getMapType() {
        return this.mapType;
    }

    /**
     * Setter for mapType
     * @param newMapType - string of the new map type
     */
    public void setMapType(String newMapType) {
        this.mapType = newMapType;
    }

    /**
     * Getter for the current Map object
     * @return Map mapObject
     */
    public Map getMapObject() {
        return mapObject;
    }

    /**
     * Getter for the current floor object
     * @return FloorMap floorMap
     */
    public FloorMap getFloorMapObject() {
        return floorMap;
    }

    /**
     * Setter for the Map object and Map ID, and isCampusMap status
     * @param newMap - Map of the new map
     */
    public void setMapDetails(Map newMap) {

        /**
         * Set Campus Map Boolean and Map Type
         */
        if ("FLOOR".equals(newMap.getMapType())) {
            setIsCampusMap(false);
            setMapType("FLOOR");
            this.floorMap = (FloorMap) MapFactory.createMap(getMapType(), newMap.getBuildingID(), newMap.getMapID());
        }
        else if ("CAMPUS".equals(newMap.getMapType())) {
            setIsCampusMap(true);
            setMapType("CAMPUS");
        }

        /**
         * Set Map object and Map ID
         */
        this.mapObject = newMap;
        this.currentMapID = newMap.getMapID();
        if (getMapType().contains("FLOOR")) {
            this.currentBuildingID = this.floorMap.getBuildingID();
        }
    }
    
    /**
     * Method to update floor up/down buttons depending on if on campus map or a floor map
     */
    public void updateFloorButtons() {
        if (getMapType().contains("CAMPUS")) {
            floorBelow.setVisible(false);
            floorAbove.setVisible(false);
            buttonPanel.remove(floorBelow);
            buttonPanel.remove(floorAbove);
            buttonPanel.setLayout(new GridLayout(1, 3));
        }
        else {
            buttonPanel.add(floorBelow);
            buttonPanel.add(floorAbove);
            floorBelow.setVisible(true);
            floorAbove.setVisible(true);
            buttonPanel.setLayout(new GridLayout(1, 3));
        }
    }

    /**
     * Method to create a new button and style it
     * @param text - the text of the button
     * @return button - the new button
     */
    public JButton createMapButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setFont(new Font("Georgia", Font.PLAIN, 17));
        button.addActionListener(this);
        button.addMouseListener(this);

        return button;
    }
    /**
     * Method to change the floorMap object being displayed in the map panel based on FloorMap object provided
     * @param floorMap - the floor map object
     */
    public void changeFloorMap(FloorMap floorMap) {
        this.floorMap = floorMap;
    }
    /**
     * Method to change the map being displayed in the map panel based on Map object provided
     * @param newMap - the new map object
     */
    public void changeMap(Map newMap) {
        poiComponent = POIComponent.getInstance();
        /**
         * Set map details
         */
        setMapDetails(newMap);
        poiComponent.changeDisplayIfCampusMap();

        /**
         * Updates the map image and map object
         */
        mapImg = new ImageIcon(newMap.getFilePath());
        map.setIcon(mapImg);

        /**
         * Update boundaries of display to current image
         */
        map.setLayout(null);
        map.setBounds(0, 0, mapImg.getIconWidth(), mapImg.getIconHeight());
        imagePanel.setPreferredSize(new Dimension(mapImg.getIconWidth(), mapImg.getIconHeight()));

        /**
         * Update the scroll pane
         */
        scrollPane.setViewportView(imagePanel);

        /**
         * Make it so that the scroll pane displays the upper third of the map image when the UI is opened (use ImageIcon dimensions)
         */
        scrollPane.getViewport().setViewPosition(new Point((int) (mapImg.getIconWidth() / 3), (int) (mapImg.getIconHeight() / 3)));

        /**
         * Check for floor above/below (enable/disable buttons)
         */
        isFloorAbove(); 
        isFloorBelow(); 

        /**
         * Display the POIs for the map and update buttons
         */
        displayPOIs();
        updateFloorButtons();

        /**
         * Set visible
         */
        mapPanel.setVisible(true);
    }

    /**
     * Method to navigate to a POI's coordinates on the map when given the POI name by using DataProcessor to retrieve the POI's coordinates
     * @param poiID - the POI ID
     */
    public void navigateToPOI(int poiID) {
        /**
         * Get the POI's coordinates
         */
        int[] coordinates = dataProcessor.getPOIPosition(poiID);

        /**
         * Navigate to the POI's coordinates
         */
        int width = scrollPane.getViewport().getWidth();
        int height = scrollPane.getViewport().getHeight();
        scrollPane.getViewport().setViewPosition(new Point(coordinates[0] - width/2, coordinates[1] - height/2));
    }

    /**
     * Method that checks via DataProcessor if there is a floor above the current one. 
     * If there is not, it grays out the button.
     * If there is, it enables the button.
     */
    private void isFloorAbove() {
        if (getMapType().contains("FLOOR")) {
            if (floorMap.checkFloorAbove()) {
                /**
                 * Enable the button "Floor Up"
                 */
                floorAbove.setEnabled(true);
            } 
            else {
                floorAbove.setEnabled(false);
            }
        }
    }

    /**
     * Method that checks via DataProcessor if there is a floor below the current one. 
     * If there is not, it grays out the button.
     * If there is, it enables the button.
     */
    private void isFloorBelow() {
        if (getMapType().contains("FLOOR")) {
            if (floorMap.checkFloorBelow()) {
                /**
                 * Enable the button "Floor Down"
                 */
                floorBelow.setEnabled(true);

            } 
            else {
                floorBelow.setEnabled(false);
            }
        } 
    }

    /**
     * Method that changes the map to the floor above if there is a floor above the current one. Uses DataProcessor to get the Map of the floor above.
     * @throws IOException
     */
    private void navigateToFloorAbove() throws IOException {
        if (floorMap.checkFloorAbove()) {
            /**
             * Get the map of the floor above
             */
            mapObject = floorMap.getFloorAbove();
            floorMap = floorMap.getFloorAbove();

            /**
             * Enable all POI Layers
             */
            enablePOILayer("Accessibility");
            enablePOILayer("Restaurants");
            enablePOILayer("Classrooms");
            enablePOILayer("Labs");
            enablePOILayer("User POI");
            poiComponent.enableAllToggleButtons();

            /**
             * Change the map
             */
            changeMap(mapObject);
            displayPOIs();
            poiComponent.updatePOIComponent();
        }
    }

    /**
     * Method that changes the map to the floor below if there is a floor below the current one. Uses DataProcessor to get the Map of the floor below.
     * @throws IOException
     */
    private void navigateToFloorBelow() throws IOException {
        if (floorMap.checkFloorBelow()) {
            /**
             * Get the map of the floor below
             */
            mapObject = floorMap.getFloorBelow();
            floorMap = floorMap.getFloorBelow();
            displayPOIs();

            /**
             * Enable all POI Layers
             */
            enablePOILayer("Accessibility");
            enablePOILayer("Restaurants");
            enablePOILayer("Classrooms");
            enablePOILayer("Labs");
            enablePOILayer("User POI");
            poiComponent.enableAllToggleButtons();

            /**
             * Change the map
             */
            changeMap(mapObject);
            poiComponent.updatePOIComponent();
        }
    }

    /**
     * Method that enables a POI layer on the map. This makes the POIs of that layer visible on the map.
     * @param text of the layer name
     */
    public void enablePOILayer(String text) {
        for (PointOfInterest poi : userPOIs) {
            if (poi.getPOItype().contains(text)) {
                poi.setisVisible(true);
                try {
                    dataProcessor.editPointOfInterestInJsonFile(poi);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } 

        for (PointOfInterest poi : favouritePOIs) {
            if (poi.getPOItype().contains(text)) {
                poi.setisVisible(true);
                try {
                    dataProcessor.editPointOfInterestInJsonFile(poi);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } 

        for (PointOfInterest poi : pois) {
            if (poi.getPOItype().contains(text)) {
                poi.setisVisible(true);
                try {
                    dataProcessor.editPointOfInterestInJsonFile(poi);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
        /**
         * Update the sidebar component to display the new POI
         */
        poiComponent.updatePOIComponent();
        displayPOIs();
    }

    /**
     * Method that disables a POI layer on the map. This makes the POIs of that layer invisible on the map.
     * @param text of the layer name
     */
    public void disablePOILayer(String text) {
        if (this.userPOIs != null) {
            for (PointOfInterest poi : userPOIs) {
                if (poi.getPOItype().contains(text)) {
                    poi.setisVisible(false);
                    try {
                        dataProcessor.editPointOfInterestInJsonFile(poi);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } 
        }
        if (this.favouritePOIs != null) {
            for (PointOfInterest poi : favouritePOIs) {
                if (poi.getPOItype().contains(text)) {
                    poi.setisVisible(false);
                    try {
                        dataProcessor.editPointOfInterestInJsonFile(poi);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } 
        }

        if (this.pois != null) {
            for (PointOfInterest poi : pois) {
                if (poi.getPOItype().contains(text)) {
                    poi.setisVisible(false);
                    try {
                        dataProcessor.editPointOfInterestInJsonFile(poi);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        displayPOIs();
        if (this.poiComponent == null) {
            this.poiComponent = POIComponent.getInstance();
        }
        poiComponent.changeDisplayIfCampusMap();
            /**
             * Update the sidebar component to display the new POI
             */
        poiComponent.updatePOIComponent();
    }

    /**
     * Method to change mouse cursor when button hovered over and when a POI flag is hovered over
     * @param event of the mouse listener
     */
    @Override
    public void mouseEntered(MouseEvent event) {
        /**
         * Button hover
         */
        if (event.getSource() instanceof JButton button) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        /**
         * POI Flag hovered over
         */
        else if (event.getSource() instanceof JLabel jLabel) {
            if (event.getSource() == map) {
                return;
            }
            JLabel label = jLabel;
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Method to change mouse cursor back to default when button not hovered over
     * Also changes back to default when POI flag not hovered over
     * @param event of the mouse listener
     */
    @Override
    public void mouseExited(MouseEvent event) {
        /**
         * Button hover off
         */
        if (event.getSource() instanceof JButton button) {
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        /**
         * POI Flag hover off
         */
        else if (event.getSource() instanceof JLabel label) {
            label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Method to clear Floor POIs from the map
     */
    public void clearPois() {
        pois.clear();
        favouritePOIs.clear();
        userPOIs.clear();
        imagePanel.remove(map);
        mapImg = new ImageIcon(mapObject.getFilePath());
        map = new JLabel(mapImg);
        map.addMouseListener(this);
        map.addMouseMotionListener(this);
        map.setLayout(null);
        map.setBounds(0, 0, mapImg.getIconWidth(), mapImg.getIconHeight());
        imagePanel.setPreferredSize(new Dimension(mapImg.getIconWidth(), mapImg.getIconHeight()));
        imagePanel.add(map);
    }

    /**
     * Method to clear Building POIs from the map
     */
    public void clearbuildingPOIs() {
        buildingPOIs.clear();
        imagePanel.remove(map);
        mapImg = new ImageIcon(mapObject.getFilePath());
        map = new JLabel(mapImg);
        map.addMouseListener(this);
        map.addMouseMotionListener(this);
        map.setLayout(null);
        map.setBounds(0, 0, mapImg.getIconWidth(), mapImg.getIconHeight());
        imagePanel.setPreferredSize(new Dimension(mapImg.getIconWidth(), mapImg.getIconHeight()));
        imagePanel.add(map);
    }

    /**
     * Method to display Floor POIs for the map currently being displayed on the map with a flag icon representing its location
     */
    public void displayPOIs() {
        /**
         * Get the Universal POIs for the map (not user based)
         */
        if (this.pois != null) {
            clearPois();
        }
        if (this.buildingPOIs != null){
            clearbuildingPOIs();
        }
        /*
         * gets building POIS if on campus map
         */
        if (isCampusMap) {

            buildingPOIs = dataProcessor.getBuildingUniversalPOIs();

            addBuildingPOIList(buildingPOIs);

        }
        /*
         * gets  POIS if on not on campus map
         */
        else {
            pois = dataProcessor.getUniversalPOIs(false, user.getUserID());
        /**
         * Get the User and Favourite POIs for the map (based on userID)
         */

        favouritePOIs = dataProcessor.getFavouritePOIs(user.getUserID());
        userPOIs = dataProcessor.getUserPOIs(user.getUserID());
       
        /**
         * Add each POI list to the map
         */
        addPOIList(pois);
        addPOIList(userPOIs);
        addPOIList(favouritePOIs);

        mapPanel.setVisible(true);
        mapPanel.setFocusable(true);
        mapPanel.requestFocusInWindow();
        mapPanel.repaint();
        mapPanel.revalidate();
        }
    }

    /**
     * Method to loop through POI ArrayList and add to map
     * @param pois - the list of Building POIs
     */
    public void addBuildingPOIList(ArrayList<BuildingPointOfInterest> pois) {
        /**
         * Loop through the POIs and add a flag icon to the map at the POI's coordinates
         */
        for (BuildingPointOfInterest poi : pois) {
            if (isCampusMap) {
                /**
                 * Get the POI's coordinates
                 */
                int[] coordinates = poi.getCoordinates();

                /**
                 * Add the flag icon to the map at the POI's coordinates (x and y position)
                 */
                ImageIcon flagIcon = flag;
                /**
                 * Get scaled version of 40x40 pixels as ImageIcon
                 */
                Image scaledFlag = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                /**
                 * Create a JLabel with the scaled flag icon
                 */
                JLabel flag = new JLabel(new ImageIcon(scaledFlag));

                /**
                 * Add the ID of the POI to the flag icon as metadata
                 */
                flag.setText(Integer.toString(poi.getID()));


                flag.setBounds(coordinates[0], coordinates[1], 40, 40);
                
                /**
                 * Add a mouse listener to the flag icon to navigate to the POI when clicked
                 */
                flag.addMouseListener(this);

                /**
                 * Add the flag icon to the map
                 */
                map.add(flag);

                /**
                 * Repaint the map panel
                 */
                mapPanel.repaint();
                flag.setVisible(true);
                imagePanel.setVisible(true);
            }
        }
    }

    /**
     * Method to loop through POI ArrayList and add to map
     * @param pois - the list of POIs
     */
    public void addPOIList(ArrayList<PointOfInterest> pois) {
        /**
         * Loop through the POIs and add a flag icon to the map at the POI's coordinates
         */
        for (PointOfInterest poi : pois) {
            if (this.floorMap != null && poi.getBuildingID() == this.floorMap.getBuildingID() 
            && poi.getFloorID() == this.floorMap.getMapID() && poi.getIsVisible()){
                /**
                 * Get the POI's coordinates
                 */
                int[] coordinates = poi.getCoordinates();

                /**
                 * Add the flag icon to the map at the POI's coordinates (x and y position)
                 */
                ImageIcon flagIcon = flag;
                /**
                 * Get scaled version of 40x40 pixels as ImageIcon
                 */
                Image scaledFlag = flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                /**
                 * Create a JLabel with the scaled flag icon
                 */
                JLabel flag = new JLabel(new ImageIcon(scaledFlag));

                /**
                 * Add the ID of the POI to the flag icon as metadata
                 */
                flag.setText(Integer.toString(poi.getID()));


                flag.setBounds(coordinates[0], coordinates[1], 40, 40);
                
                /**
                 * Add a mouse listener to the flag icon to navigate to the POI when clicked
                 */
                flag.addMouseListener(this);

                /**
                 * Add the flag icon to the map
                 */
                map.add(flag);

                /**
                 * Repaint the map panel
                 */
                mapPanel.repaint();
                flag.setVisible(true);
                imagePanel.setVisible(true);
                }
        }
    }

    /**
     * Method to handle button clicks by delegating to other methods that will make the appropriate changes to the map and use other classes
     * @param event of the action listener
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        /**
         * Get button text
         */
        String buttonText = ((JButton) event.getSource()).getText();

        /**
         * If the button text is "Campus Map", change the map to the campus map
         */
        if (buttonText.equals("Campus Map")) {
            changeMap(campus);
        }
        /**
         * If the button text is "Floor Down", change the map to the floor below the current map
         */
        else if (buttonText.equals("Floor Down")) {
            try {
                navigateToFloorBelow();
            } catch (IOException error) {
                error.printStackTrace();
              }
        }
        /**
         * If the button text is "Floor Up", change the map to the floor above the current map
         */
        else if (buttonText.equals("Floor Up")) {
            try {
                navigateToFloorAbove();
            } catch (IOException error) {
                error.printStackTrace();
              }
        }
        /**
         * If the button text is "Navigation Mode", change that button to "Editing Mode"
         */
        else if (buttonText.equals("Navigation Mode")) {
            if (isCampusMap && !user.getIsAdmin()) {
                JPanel errorPanel = new JPanel();
                JLabel errorMessage = new JLabel("Error: Users cannot create or edit points of interest on the campus map");
                errorPanel.add(errorMessage);
                JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);

            }
            else {
                /**
                 * If admin, change to 'Developer Editing Mode'
                 */
                if (user.getIsAdmin()) {
                    ((JButton) event.getSource()).setText("Developer Editing Mode");
                }
                /**
                 * If user, change to 'User Editing Mode'
                 */
                else {
                    ((JButton) event.getSource()).setText("User Editing Mode");
                }
                isNavigationMode = false;
            }
        }
        /**
         * If the button text is "Editing Mode", change that button to "Navigation Mode"
         */
        else if (buttonText.equals("User Editing Mode") || buttonText.equals("Developer Editing Mode")) {
            ((JButton) event.getSource()).setText("Navigation Mode");
            isNavigationMode = true;
        }
    }

    /**
     * Method to handle mouse clicks on a POI on the map. When a POI is clicked, opens up a small pop-up window with the POI's information
     * @param event of the mouse listener
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        /**
         * Navigation Mode -> Can View POIs
         */
        if (isNavigationMode) {
            if (event.getSource() == map) {
                return;
            }
            if (isCampusMap) {
                if (event.getSource() instanceof JLabel label) {
                
                    /**
                     * Get the POI Id of the label
                     */
                    String id = label.getText();

                    /**
                     * Get the POI object from the POI Id
                     */
                    BuildingPointOfInterest poi = dataProcessor.getBuildingPOI(Integer.parseInt(id));

                    /**
                     * Get the building ID of the POI
                     */
                    int buildingID = poi.getBuildingID();

                    /**
                     * Get the file path of the first floor of the POI given the buildingID by searching BuildignsPOIMetadata.json
                     */
                    Map newFloorMap = dataProcessor.getFloorMapFromMapID(buildingID, 1);

                    /**
                     * Change the map to floorMap
                     */
                    if (newFloorMap != null) {
                        changeMap(newFloorMap);
                    }
                }
            }
            else {
                /**
                 * Get the label that was clicked
                 */
                if (event.getSource() instanceof JLabel label) {

                    /**
                     * Get the POI Id of the label
                     */
                    String id = label.getText();

                    /**
                     * Create a new POIInfoWindow object and pass the POI object to it
                     */
                    POIInfoWindow poiInfoWindow = new POIInfoWindow(Integer.parseInt(id));

                    /**
                     * Display the POIInfoWindow right on top of the map
                     */
                    poiInfoWindow.getFrame().setLocationRelativeTo(mapPanel);
                    poiInfoWindow.setVisibleFrame();
                }
            }
        }
        /**
         * Editing Mode -> Create POIs wherever clicked on the map image ()
         */
        else if (!isNavigationMode) {
            if (event.getSource() instanceof JLabel jLabel) {
                /**
                 * If on the map
                 */
                if (event.getSource() == map) {
                    if (isCampusMap) {
                        /*
                         * Only allowing developers to make building POIs
                         */
                        if (user.getIsAdmin()) {
                            /**
                             * Get the coordinates of the mouse click
                             */
                            int x = event.getX();
                            int y = event.getY();

                            /**
                             * Create a new Point of Interest with EditingTool by opening POICreationWindow with the coordinates
                             * This window will work with EditingTool to create a new POI
                             */
                            POICreationWindow poiCreationWindow = new POICreationWindow(x, y);
                            poiCreationWindow.getFrame().setLocationRelativeTo(mapPanel);
                            poiCreationWindow.setVisibleFrame();
                        }
                        else {
                            JPanel errorPanel = new JPanel();
                            JLabel errorMessage = new JLabel("Error: Users cannot create points of interest on the campus map");
                            errorPanel.add(errorMessage);
                        
                            JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        /**
                         * Get the coordinates of the mouse click
                         */
                        int x = event.getX();
                        int y = event.getY();

                        /**
                         * Create a new Point of Interest with EditingTool by opening POICreationWindow with the coordinates
                         * This window will work with EditingTool to create a new POI
                         */
                        POICreationWindow poiCreationWindow = new POICreationWindow(x, y);
                        poiCreationWindow.getFrame().setLocationRelativeTo(mapPanel);
                        poiCreationWindow.setVisibleFrame();
                    }
                }
                /**
                 * Open up an edit window for the POI object
                 */
                else {                    
                    /**
                     * Get the label that was clicked
                     */
                    JLabel label = jLabel;

                    /**
                     * Get the POI Id of the label
                     */
                    String id = label.getText();

                    /**
                     * Get the POI object from the POI Id
                     */
                    
                    if (getIsCampusMap()) {
                        /*
                         * Only allow developers to edit buildingPOIs
                         */
                        if (user.getIsAdmin()) {
                            BuildingPointOfInterest poi = dataProcessor.getBuildingPOI(Integer.parseInt(id));

                            /**
                             * Create a new POI Editing Window object and pass the POI object to it
                             */
                            POIEditWindow poiEditWindow = new POIEditWindow(poi.getID());

                            /**
                             * Display the POIInfoWindow right on top of the map
                             */
                            poiEditWindow.getFrame().setLocationRelativeTo(mapPanel);
                            poiEditWindow.setVisibleFrame();
                        }
                        else {
                            JPanel errorPanel = new JPanel();
                            JLabel errorMessage = new JLabel("Error: Users cannot edit points of interest on the campus map");
                            errorPanel.add(errorMessage);
                        
                            JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        PointOfInterest poi = dataProcessor.getPOI(Integer.parseInt(id));
                        /**
                         * Check if user is an admin and POI type is not user
                         */
                        if (user.getIsAdmin() == false && !(poi.getPOItype().contains("User POI"))) {
                            /**
                             * Open up a pop-up window saying they can't edit this POI
                             */
                            JOptionPane.showMessageDialog(null, "You can't edit this POI", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        /**
                         * Create a new POI Editing Window object and pass the POI object to it
                         */
                        POIEditWindow poiEditWindow = new POIEditWindow(poi.getID());

                        /**
                         * Display the POIInfoWindow right on top of the map
                         */
                        poiEditWindow.getFrame().setLocationRelativeTo(mapPanel);
                        poiEditWindow.setVisibleFrame();
                        }

                    
                }
            }
        }
    }

    /**
     * Method to change map to campus map
     */
    public void changeToCampusMap() {
        /**
         * Change the map to the campus map
         */
        changeMap(campus);
    }

    /**
     * Method to detect if mouse has been pressed, moving map appropriately if so
     * @param event of the mouse listener
     */
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getSource() == map) {
            mouseStartX = event.getX();
            mouseStartY = event.getY();
        }
    }
    /**
     * Method to detect if mouse is being pressed and dragged, moving map appropriately if so
     * @param event of the mouse listener
     */
    @Override
    public void mouseDragged(MouseEvent event) {
        if (event.getSource() == map) {
            JViewport viewPort = scrollPane.getViewport();
            Point vpp = viewPort.getViewPosition();
            vpp.translate(mouseStartX-event.getX(), mouseStartY-event.getY());
            map.scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));
        }
    }

    /**
     * Set navigation mode to true
     */
    public void setNavigationMode() {
        isNavigationMode = true;
        mapMode.setText("Navigation Mode");
    }

    /**
     * Method to update POI coordinates on map once mouse button is released
     * @param event of the mouse listener
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getSource() != map) {
            if (isNavigationMode == false) {
                if (event.getSource() instanceof JLabel && event.isAltDown()) {
                    JLabel label = (JLabel) event.getSource();
                    /**
                     * Get the POI Id of the label
                     */
                    String id = label.getText();
                    /**
                     * Get the POI object from the POI Id
                     */
                    BuildingPointOfInterest buildingPOI = dataProcessor.getBuildingPOI(Integer.parseInt(id));
                    PointOfInterest poi = dataProcessor.getPOI(Integer.parseInt(id));
                    /**
                     * Check if user is an admin and POI type is not user
                     */
                    if (user.getIsAdmin() == false && !(poi.getPOItype().contains("User POI"))) {
                        /**
                         * Open up a pop-up window saying they can't edit this POI
                         */
                        JOptionPane.showMessageDialog(null, "You can't move this POI", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    /**
                     * Get the coordinates of the mouse relative to the map's dimensions
                     */
                    int xCoord, yCoord;
                    if (!getIsCampusMap()) {
                        xCoord = poi.getCoordinates()[0] + event.getX();
                        yCoord = poi.getCoordinates()[1] + event.getY();
                    }
                    else {
                        xCoord = buildingPOI.getCoordinates()[0] + event.getX();
                        yCoord = buildingPOI.getCoordinates()[1] + event.getY();
                    }


                    /**
                     * Update the POI coordinates
                     */
                    if (!getIsCampusMap()) {
                        poi.setCoordinates(xCoord, yCoord);
                    }
                    else {
                        buildingPOI.setCoordinates(xCoord, yCoord);
                    }
                    try {
                        if (!getIsCampusMap()) {
                            dataProcessor.editPointOfInterestInJsonFile(poi);
                        }
                        else {
                            dataProcessor.editBuildingPointOfInterestInJsonFile(buildingPOI);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    /**
                     * Refresh the map
                     */
                    displayPOIs();
                }
            }
        }
}
    /**
     * Unneeded method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mouseMoved(MouseEvent event) {
    }
}