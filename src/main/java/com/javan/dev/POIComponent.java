package com.javan.dev;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Main POI handling UI class used within the SidebarComponent to handle specifics related to POIs.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class POIComponent extends JPanel implements ActionListener, MouseListener, FocusListener {
    /**
     * Initialize private variables for the POIComponent
     */
    private JPanel POIPanel;
    /**
     * {POILayerPanel, FavouritePOIPanel, UserPOIPanel, OtherPOIPanel}
     */
    private ArrayList<JPanel> POIPanels = new ArrayList<>();
    /**
     * {POILayerScrollPane, FavouritePOIScrollPane, UserPOIScrollPane, OtherPOIScrollPane}
     */
    private ArrayList<JScrollPane> POIScrollPanes = new ArrayList<>();
    private ArrayList<PointOfInterest> favouritePOIList;
    private ArrayList<PointOfInterest> userPOIList;
    private ArrayList<PointOfInterest> otherPOIList;
    private JButton otherPOIButton;

    /**
     * Private instance of DataProcessor, User, MapComponent, and POIComponent
     */
    private DataProcessor dataProcessor = DataProcessor.getInstance();
    private User user = User.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();
    private static POIComponent INSTANCE;

    private JToggleButton accessibilityButton;
    private JToggleButton restaurantsButton;
    private JToggleButton classroomsButton;
    private JToggleButton labsButton;
    private JToggleButton userButton;
    private JToggleButton washroomsButton;
    private JToggleButton navigationButton;




    /**
     * Constructor to create POIPanel that holds the four other panels vertically, allowing them to display their information
     */
    private POIComponent() {
        /**
         * Initialize list of POI panels and scroll panes
         */
        for (int i = 0; i < 4; i++) {
            POIPanels.add(new JPanel());
            POIScrollPanes.add(new JScrollPane());
            POIScrollPanes.get(i).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            POIScrollPanes.get(i).setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

        /**
         * Initialize POIPanel
         */
        POIPanel = new JPanel();
        POIPanel.setLayout(new BoxLayout(POIPanel, BoxLayout.Y_AXIS));
        POIPanel.setBackground(Color.WHITE);
        POIPanel.setVisible(true);

        /**
         * Initialize the 4 panels contained in a loop with max dimensions to allow for scrolling and resizing
         */
        for (int i = 0; i < 4; i++) {
            POIScrollPanes.get(i).setOpaque(true);
            POIScrollPanes.get(i).setBackground(Color.WHITE);
            POIScrollPanes.get(i).setMaximumSize(new Dimension(100000, 1000));
            POIScrollPanes.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            POIPanels.get(i).setMaximumSize(new Dimension(100000, 1000));
            POIPanels.get(i).setBackground(Color.WHITE);
            POIPanel.add(POIScrollPanes.get(i));
            POIScrollPanes.get(i).setViewportView(POIPanels.get(i));
        }


        /**
         * Use functions to add content to each of the four POI Panels
         */
        addPOILayerPanel();
        addFavouritePOIPanel();
        addUserPOIPanel();
        addOtherPOIPanel();

        /**
         * change display if campus map
         */
        changeDisplayIfCampusMap();

        /**
         * Make POI Panel visible
         */
        POIPanel.setVisible(true);
    }

    /**
     * Getter for the instance of the POIComponent
     * @return None
     */
    public static POIComponent getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new POIComponent();
        }
        return INSTANCE;
    }

    /**
     * Method to create a button for the POI Panel
     * @param string - The text to be displayed on the button
     * @return JButton The button to be added to the POI Panel to toggle the visibility of the POI Panels
     */
    private JButton createPOIButton(String string) {
        JButton button = new JButton(string);
        button.setBackground(Color.WHITE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(JLabel.CENTER);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setMaximumSize(new Dimension(100000, 50));
        button.setFont(new Font("Georgia", Font.BOLD, 20));
        button.addActionListener(this);
        button.addMouseListener(this);
        return button;
    }

    /**
     * Method to add the POI Layer Panel to the POI Panel
     * This panel contains the toggle buttons for the POI layers
     * The toggle buttons are styled and have action listeners for toggling
     */
    private void addPOILayerPanel() {
        /**
         * Create a centered title label 'POI Layers'
         */
        JPanel titleLayer = new JPanel();
        JButton POILayerButton = createPOIButton("POI Layers");
        titleLayer.setBackground(Color.WHITE);
        titleLayer.add(POILayerButton);
        /**
         * Create 5 toggle buttons for the different POI layers
         */
        accessibilityButton = createPOILayerToggleButton("Accessibility");
        washroomsButton = createPOILayerToggleButton("Washrooms");
        navigationButton = createPOILayerToggleButton("Navigation");
        restaurantsButton = createPOILayerToggleButton("Restaurants");
        classroomsButton = createPOILayerToggleButton("Classrooms");
        labsButton = createPOILayerToggleButton("Labs");
        userButton = createPOILayerToggleButton("User POI");

        /**
         * Put the buttons into POIPanels.get(0), the POI Layer Panel
         */
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gridConstraints = new GridBagConstraints();

        POIPanels.get(0).setLayout(grid);
        /**
         * Have the title take up 2 columns 1 row
         * Have the first four buttons take up 2 rows, 2 in each row, and the last button take up 2 columns 1 row
         */
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 2;
        gridConstraints.gridheight = 1;
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 0.25;
        POIPanels.get(0).add(titleLayer, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 1;
        gridConstraints.gridheight = 1;
        gridConstraints.weightx = 0.5;
        gridConstraints.weighty = 0.25;
        POIPanels.get(0).add(accessibilityButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        POIPanels.get(0).add(washroomsButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        POIPanels.get(0).add(navigationButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        POIPanels.get(0).add(restaurantsButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        POIPanels.get(0).add(classroomsButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        POIPanels.get(0).add(labsButton, gridConstraints);

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 2;
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 0.25;
        POIPanels.get(0).add(userButton, gridConstraints);

        /**
         * Set the POI Layer Panel to be visible
         */
        POIPanels.get(0).setVisible(true);
    }

    /**
     * Method to add user favourites to the Favourite POI Panel.
     * Has a title 'Favourite POIs' and a list of favourite POIs that can be scrolled through
     */
    public void addFavouritePOIPanel() {
        /**
         * Clear the POI Panel first
         */
        POIPanels.get(1).removeAll();

        /**
         * JPanel that separates the title from the list of favourite POIs
         */
        JPanel gridDisplay = new JPanel();
        POIPanels.get(1).setLayout(new BoxLayout(POIPanels.get(1), BoxLayout.Y_AXIS));

        /**
         * Create a centered title label 'Favourite POIs'
         */
        JPanel titleFavourite = new JPanel();
        JButton favouritePOIButton = createPOIButton("Favourite POIs");
        titleFavourite.add(favouritePOIButton);
        titleFavourite.setBackground(Color.WHITE);
        POIPanels.get(1).add(titleFavourite);

        /**
         * Update the list of Strings representing the favourite POIs 
         */
        favouritePOIList = dataProcessor.getFavouritePOIs(user.getUserID());
        
        /**
         * Add the POIs to the panel
         */
        int numRows = addPOIsToPanel(gridDisplay, favouritePOIList);


        GridLayout grid = new GridLayout(numRows, 2);
        gridDisplay.setLayout(grid);
        POIPanels.get(1).add(gridDisplay);


        /**
         * Set the Favourite POI Panel to be visible
         */
        POIPanels.get(1).setVisible(true);
    }

    /**
     * Method to add POIs that were created by users to the user POI panel, the same way Favourites POIs are added
     */
    public void addUserPOIPanel() {
        /**
         * Clear the POI Panel first
         */
        POIPanels.get(2).removeAll();

        /**
         * JPanel that separates the title from the list of user-created POIs
         */
        JPanel gridDisplay = new JPanel();
        POIPanels.get(2).setLayout(new BoxLayout(POIPanels.get(2), BoxLayout.Y_AXIS));

        /**
         * Create a centered title label 'User POIs'
         */
        JPanel titleUser = new JPanel();
        JButton userPOIButton = createPOIButton("User POIs");
        titleUser.setBackground(Color.WHITE);
        titleUser.add(userPOIButton);
        POIPanels.get(2).add(titleUser);

        /**
         * Get list of strings of user-created POIs from DataProcessor
         */
        userPOIList = dataProcessor.getUserPOIs(user.getUserID());

        /**
         * Add the POIs to the panel
         */
        int numRows = addPOIsToPanel(gridDisplay, userPOIList);


        GridLayout grid = new GridLayout(numRows, 2);
        gridDisplay.setLayout(grid);
        POIPanels.get(2).add(gridDisplay);

        /**
         * Set the User POI Panel to be visible
         */
        POIPanels.get(2).setVisible(true);
    }

    /**
     * Method to add all other POIs to the other POI panel, the same way Favourites POIs and User POIs are added
     */
    public void addOtherPOIPanel() {
        /**
         * Clear the POI Panel first
         */
        POIPanels.get(3).removeAll();
        /**
         * JPanel that separates the title from the list of other POIs
         */
        JPanel gridDisplay = new JPanel();
        POIPanels.get(3).setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();


        /**
         * Create a centered title label 'Other POIs'
         */
        JPanel titleOther = new JPanel();
        titleOther.setBackground(Color.WHITE);
        otherPOIButton = createPOIButton("Floor POIs");
        titleOther.add(otherPOIButton);

        /**
         * Add the title button to the panel and have it weigh 0 in y axis
         */
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 0;
        gridConstraints.fill = GridBagConstraints.BOTH;
        POIPanels.get(3).add(titleOther, gridConstraints);

        /**
         * Get list of strings of other POIs from DataProcessor
         */
        if (mapComponent.getIsCampusMap()) {
            otherPOIList = dataProcessor.getUniversalPOIs(true, user.getUserID());
        }
        else {
            otherPOIList = dataProcessor.getUniversalPOIs(false, user.getUserID());
        }

        /**
         * Add the POIs to the panel
         */
        int numRows = addPOIsToPanel(gridDisplay, otherPOIList);

        GridLayout grid = new GridLayout(numRows, 1);
        gridDisplay.setLayout(grid);

        /**
         * Add the gridDisplay to the panel and have it weigh 1 in y axis
         */
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 1.0;
        gridConstraints.fill = GridBagConstraints.BOTH;
        POIPanels.get(3).add(gridDisplay, gridConstraints);

        /**
         * Set the Other POI Panel to be visible
         */
        POIPanels.get(3).setVisible(true);
    }

    /**
     * Function to loop through list of POIs and add them to the POI panel (showing their string that is visible and POI id invisible)
     * @param gridDisplay - the grid display JPanel
     * @param POIList - the list of POIs to add
     * @return int numRows to add
     */
    public int addPOIsToPanel(JPanel gridDisplay, ArrayList<PointOfInterest> POIList) {
        /**
         * Loop through Floor POIs in the list and add JPanels to the POI Panel on top of one another
         */
        for (PointOfInterest poi : POIList) {
            /**
             * Create JPanel for each POI and a panel for the name to display
             */
            JPanel POIDisplayPanel = new JPanel();
            JLabel POIPanelName = new JLabel(poi.getName());
            POIPanelName.setHorizontalAlignment(JLabel.CENTER);
            POIPanelName.setFont(new Font("Georgia", Font.PLAIN, 15));
            POIPanelName.setAlignmentX(Component.CENTER_ALIGNMENT);
            POIDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            POIDisplayPanel.setBackground(Color.WHITE);
            POIDisplayPanel.add(POIPanelName);

            /**
             * If campus map, get building ID and map ID and make that the hidden label
             */
            if (mapComponent.getIsCampusMap()) {
                JLabel POIPanelID = new JLabel(poi.getBuildingFloorID());
                POIPanelID.setVisible(false);
                POIDisplayPanel.add(POIPanelID);
            }
            /**
             * If floor map, get POI ID and make that the hidden label
             */
            else {
                JLabel POIPanelID = new JLabel(String.valueOf((poi.getID())));
                POIPanelID.setVisible(false);
                POIDisplayPanel.add(POIPanelID);
            }
            
            /**
             * Add listeners to each name to allow for clicking on the name to open the POI
             */
            POIDisplayPanel.addMouseListener(this);
            gridDisplay.add(POIDisplayPanel);
        }

        /**
         * Return the number of rows to add to the grid layout
         */
        return POIList.size();
    }

    /**
     * Getter to return the POIPanel
     * @return POIPanel
     */
    public JPanel getPOIPanel() {
        return POIPanel;
    }

    /**
     * Method to create toggle buttons for the POI layers panel that are styled and have action listeners for toggling
     * @param text - the text of the button
     * @return JToggleButton button of POI layer toggling
     */
    public JToggleButton createPOILayerToggleButton(String text) {
        JToggleButton POILayerToggleButton = new JToggleButton(text);
        POILayerToggleButton.setBackground(Color.WHITE);
        POILayerToggleButton.setForeground(Color.BLACK);
        POILayerToggleButton.setOpaque(true);
        POILayerToggleButton.setPreferredSize(new Dimension(50, 30));
        POILayerToggleButton.setMaximumSize(new Dimension(75, 40));
        POILayerToggleButton.setMinimumSize(new Dimension(75, 40));
        POILayerToggleButton.setFont(new Font("Georgia", Font.PLAIN, 15));
        POILayerToggleButton.setFocusPainted(false);
        POILayerToggleButton.addActionListener(this);
        POILayerToggleButton.addMouseListener(this);
        POILayerToggleButton.setSelected(true);

        return POILayerToggleButton;
    }

    /**
     * Method to hide the POI layers panel, favourite POIs, and User POIs if the campus map is selected
     */
    public void changeDisplayIfCampusMap() {
        if (mapComponent.getIsCampusMap()) {
            /**
             * Make the first 3 scroll panes not show on UI
             */
            for (int i = 0; i < 3; i++) {
                POIScrollPanes.get(i).setVisible(false);
            }
            addFavouritePOIPanel();
            addOtherPOIPanel();
            addUserPOIPanel();
            /**
             * Change the title of Floor POIs to Building Directory
             */
            otherPOIButton.setText("Building Directory");
        }
        else {
            /**
             * Make the first 3 scroll panes show on UI
             */
            for (int i = 0; i < 3; i++) {
                POIScrollPanes.get(i).setVisible(true);
            }
            addFavouritePOIPanel();
            addOtherPOIPanel();
            addUserPOIPanel();

            /**
             * Change the title of Floor POIs to Other POIs
             */
            otherPOIButton.setText("Floor POIs");
        }
    }

    /**
     * Method to determine what cursor looks like depending on what type of object it is over
     * @param event of the mouse listener
     */
    @Override
    public void mouseEntered(MouseEvent event) {
        /**
         * When the mouse enters a POI Layer Toggle Button, change the colour of the button to light gray and make the cursor a hover cursor
         */
        if (event.getSource() instanceof JToggleButton button) {
            button.setBackground(Color.LIGHT_GRAY);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        /**
         * When the mouse enters the section of a POI name, change the colour of the POI to light gray and make the cursor a hover cursor
         */
        else if (event.getSource() instanceof JPanel panel) {
            panel.setBackground(Color.LIGHT_GRAY);
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        /**
         * If hovering on one of the POI Layer Toggle Buttons, change the cursor to a hand cursor
         */
        else if (event.getSource() instanceof JButton button) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
    }

    /**
     * Method to determine what cursor looks like depending on what type of object it is over
     * @param event of the mouse listener
     */
    @Override
    public void mouseExited(MouseEvent event) {
        /**
         * When the mouse exits a POI Layer Toggle Button, change the colour of the button to white and make the cursor a default cursor
         */
        if (event.getSource() instanceof JToggleButton button) {
            button.setBackground(Color.WHITE);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        /**
         * When the mouse exits the section of a POI name, change the colour of the POI to white and make the cursor a default cursor
         */
        else if (event.getSource() instanceof JPanel panel) {
            panel.setBackground(Color.WHITE);
            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        /**
         * If hovering on one of the POI Layer Toggle Buttons, change the cursor to a default cursor
         */
        else if (event.getSource() instanceof JButton button) {
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    
    }

    /**
     * Method to determine what methods to call based on where / what the mouse is pressing
     * @param event of the action listener
     */
    public void actionPerformed(ActionEvent event) {
        /**
         * When a POI Layer Toggle Button is pressed, toggle the layer on or off by sending the button's text to the MapPanel
         */
        if (event.getSource() instanceof JToggleButton button) {
            if (button.isSelected()) {
                mapComponent.enablePOILayer(button.getText());
            }
            else {
                mapComponent.disablePOILayer(button.getText());
            }
        }
        /**
         * When a POI button from createPOIButton is clicked, hide the gridDisplay panel associated with it
         */
        else if (event.getSource() instanceof JButton button) {
            switch (button.getText()) {
                case "POI Layers":
                    if (POIPanels.get(0).getComponent(1).isVisible()) {
                        /**
                         * Make everything but the button invisible and also resize the panel to be the size of the button
                         */
                        for (int i = 1; i < POIPanels.get(0).getComponentCount(); i++) {
                            POIPanels.get(0).getComponent(i).setVisible(false);
                        }
                        POIPanels.get(0).setPreferredSize(new Dimension(POIPanels.get(0).getPreferredSize().width, POIPanels.get(0).getComponent(0).getPreferredSize().height));
                        
                        
                    }
                    else {
                        /**
                         * Restore to original state
                         */
                        for (int i = 1; i < POIPanels.get(0).getComponentCount(); i++) {
                            POIPanels.get(0).getComponent(i).setVisible(true);
                        }
                        POIPanels.get(0).setPreferredSize(new Dimension(POIPanels.get(0).getPreferredSize().width, POIPanels.get(0).getComponent(0).getPreferredSize().height + POIPanels.get(0).getComponent(1).getPreferredSize().height + POIPanels.get(0).getComponent(2).getPreferredSize().height + POIPanels.get(0).getComponent(3).getPreferredSize().height));
                    }   break;
                case "Favourite POIs":
                    if (POIPanels.get(1).getComponent(1).isVisible()) {
                        for (int i = 1; i < POIPanels.get(1).getComponentCount(); i++) {
                            POIPanels.get(1).getComponent(i).setVisible(false);
                        }
                    }
                    else {
                        for (int i = 1; i < POIPanels.get(1).getComponentCount(); i++) {
                            POIPanels.get(1).getComponent(i).setVisible(true);
                        }
                    }   break;
                case "User POIs":
                    if (POIPanels.get(2).getComponent(1).isVisible()) {
                        for (int i = 1; i < POIPanels.get(2).getComponentCount(); i++) {
                            POIPanels.get(2).getComponent(i).setVisible(false);
                        }
                    }
                    else {
                        for (int i = 1; i < POIPanels.get(2).getComponentCount(); i++) {
                            POIPanels.get(2).getComponent(i).setVisible(true);
                        }
                    }   break;
                case "Floor POIs":
                case "Building Directory":
                    if (POIPanels.get(3).getComponent(1).isVisible()) {
                        for (int i = 1; i < POIPanels.get(3).getComponentCount(); i++) {
                            POIPanels.get(3).getComponent(i).setVisible(false);
                        }
                    }
                    else {
                        for (int i = 1; i < POIPanels.get(3).getComponentCount(); i++) {
                            POIPanels.get(3).getComponent(i).setVisible(true);
                        }
                    }   break;
                default:
                    break;
            }
            /**
             * Refresh the POI Panel to show the changes
             */
            POIPanel.revalidate();
            POIPanel.repaint();
            
        }
    }

    /**
     * Method to handle mouse clicks on different POI names, sending the name of the POI to the MapPanel to be highlighted
     * @param event of the mouse listener
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        /**
         * If the mouse is clicked on a POI name, send the name of the POI to the MapPanel to be highlighted if not on campus map
         * If on campus map, change the map to that POI's map
         */
        if (event.getSource() instanceof JPanel jPanel) {
            /**
             * Campus map case
             */
            if (mapComponent.getIsCampusMap()) {
                JPanel panel = jPanel;
                JLabel label = (JLabel) panel.getComponent(1);

                /**
                 * Get the text from label, which is two integers separated by a space, and put both integers into an integer array
                 */
                String[] mapIDAndPOIID = label.getText().split(" ");
                int buildingID = Integer.parseInt(mapIDAndPOIID[0]);
                int mapID = Integer.parseInt(mapIDAndPOIID[1]);


                /**
                 * Get the Map object associated with the Map ID and Building ID
                 */
                Map map = dataProcessor.getFloorMapFromMapID(buildingID, mapID);
                FloorMap floorMap = dataProcessor.getFloorMapFromMapID(buildingID, mapID);

                /*
                 * Change Map
                 */
                mapComponent.changeFloorMap(floorMap);
                mapComponent.changeMap(map);

            }
            /**
             * POI From Floor Map Case
             */
            else {
                JPanel panel = jPanel;
                JLabel label = (JLabel) panel.getComponent(1);
                String poiName = label.getText();
                int poiID = Integer.parseInt(poiName);
                /**
                 * Get the PointOfInterest object from the POI ID
                 */
                PointOfInterest currSelected = dataProcessor.getPOI(poiID);
                /**
                 * Pass POI name to the MapPanel to be highlighted on the map
                 */
                mapComponent.changeMap(dataProcessor.getFloorMapFromMapID(currSelected.getBuildingID(), currSelected.getFloorID()));
                mapComponent.enablePOILayer(currSelected.getPOItype());
                enableSelectToggleButtons(currSelected.getPOItype());

                mapComponent.navigateToPOI(poiID);
                
                POIInfoWindow poiWindow = new POIInfoWindow(poiID);
                poiWindow.getFrame().setLocationRelativeTo(mapComponent.getMapPanel());
                poiWindow.setVisibleFrame();
            }
        }
    }

    /**
     * Method to update the POI Panel components
     */
    public void updatePOIComponent() {
        this.addOtherPOIPanel();
        this.addFavouritePOIPanel();
        this.addUserPOIPanel();
        for (int i = 0; i < POIPanels.size(); i++) {
            POIPanels.get(i).revalidate();
            POIPanels.get(i).repaint();
        }
        /**
         * Refresh POIComponent display
         */
        this.revalidate();
        this.repaint();
        POIPanel.setVisible(true);
    }

    /**
     * Getter for POI Panels
     * @return POIPanels
     */
    public ArrayList<JPanel> getPOIPanels() {
        return POIPanels;
    }

    /**
     * Unused method from FocusListener
     * @param event of the focus listener
     */
    @Override
    public void focusGained(FocusEvent event) {
    }

    /**
     * Unused method from FocusListener
     * @param event of the focus listener
     */
    @Override
    public void focusLost(FocusEvent event) {
    }

    /**
     * Unused method from MouseListener
     * @param event of the mouse listener
     */
    @Override
    public void mousePressed(MouseEvent event) {
    }

    /**
     * Unused method from MouseListener
     * @param event of the mouse listener
     */
    @Override
    public void mouseReleased(MouseEvent event) { 
    }

    /**
     * Method to enable all toggle buttons in the POI Layers Panel
     */
    public void enableAllToggleButtons() {
        accessibilityButton.setSelected(true);
        navigationButton.setSelected(true);
        washroomsButton.setSelected(true);
        restaurantsButton.setSelected(true);
        classroomsButton.setSelected(true);
        labsButton.setSelected(true);
        userButton.setSelected(true);
        
        POIPanels.get(0).revalidate();
        POIPanels.get(0).repaint();
    }

    /**
     * Method to enable select toggle buttons in the POI Layers Panel
     * @param layerType - the type of layer
     */
    public void enableSelectToggleButtons(String layerType) {
        switch (layerType) {
            case "Accessibility" -> accessibilityButton.setSelected(true);
            case "Restaurants" -> restaurantsButton.setSelected(true);
            case "Classrooms" -> classroomsButton.setSelected(true);
            case "Labs" -> labsButton.setSelected(true);
            case "User POI" -> userButton.setSelected(true);
            case "Navigation" -> navigationButton.setSelected(true);
            case "Washrooms" -> washroomsButton.setSelected(true);
            default -> {
            }
        }
        
        POIPanels.get(0).revalidate();
        POIPanels.get(0).repaint();
    }
}