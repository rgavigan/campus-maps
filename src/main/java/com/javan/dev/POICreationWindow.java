package com.javan.dev;

// Import Swing Components
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.IOException;



/**
 * UI class that allows users to create POI objects.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca], Deep Ashishkumar Shah [dshah228@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class POICreationWindow extends JFrame implements ActionListener, MouseListener {
    /**
     * Private instance variables
     */
    private JFrame frame;
    private JPanel panel;
    private JButton create;
    private JButton cancel;
    private JPanel buttonPanel;

    /**
     * Private variables to hold the instance of the data processor, user, mapComponent and poiComponent objects
     */
    private DataProcessor processor = DataProcessor.getInstance();
    private User user = User.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();
    private POIComponent poiComponent = POIComponent.getInstance();


    /**
     * Constructor for POICreationWindow given x and y coordinates
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public POICreationWindow(int x, int y) {
        /**
         * Create Frame and Panel
         */
        frame = new JFrame("Create a new POI");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setSize(450, 300);
        frame.setMinimumSize(new Dimension(200, 200));
        /**
         * Add icon to the UI frame (Flag Icon)
         */
        frame.setIconImage(new ImageIcon("data/images/flag.png").getImage());

        /**
         * Create ArrayList of different metadata String titles
         */
        ArrayList<String> metadata = new ArrayList<>();
        metadata.add("Name");
        if (!mapComponent.getIsCampusMap()){
            metadata.add("Room Number");
        }
        else {
            metadata.add("Building ID");
        }
        metadata.add("Description");
        metadata.add("X-Value");
        metadata.add("Y-value");
        if (user.getIsAdmin() && !mapComponent.getIsCampusMap()) {
            metadata.add("Layer Type");
        }
        

        /**
         * Loop, creating a JPanel that holds a JLabel and a JTextField
         */
        for (int i = 0; i < metadata.size(); i++) {
            JPanel tempPanel = new JPanel();
            tempPanel.setBackground(Color.WHITE);
            tempPanel.setLayout(new GridLayout());
            JPanel labelHolder = new JPanel();
            labelHolder.setBackground(Color.WHITE);
            JLabel tempLabel = new JLabel(metadata.get(i));
            labelHolder.add(tempLabel);

            if (i == 3) {
                JTextField tempField = createTextField(Integer.toString(x));
                tempPanel.add(labelHolder);
                tempPanel.add(tempField);
                panel.add(tempPanel);
                continue;
            }
            if (i == 4) {
                JTextField tempField = createTextField(Integer.toString(y));
                tempPanel.add(labelHolder);
                tempPanel.add(tempField);
                panel.add(tempPanel);
                continue;
            }
            /**
             * If it is Layer Type, create radio buttons
             */
            if (i == 5) {
                JPanel radioHolder = new JPanel();
                radioHolder.setBackground(Color.WHITE);
                radioHolder.setLayout(new GridLayout(4, 2));
                JRadioButton accessibilityRadio = new JRadioButton("Accessibility");
                JRadioButton washroomsRadio = new JRadioButton("Washrooms");
                JRadioButton navigationRadio = new JRadioButton("Navigation");
                JRadioButton restaurantRadio = new JRadioButton("Restaurants");
                JRadioButton classroomRadio = new JRadioButton("Classrooms");
                JRadioButton labsRadio = new JRadioButton("Labs");

                ButtonGroup group = new ButtonGroup();
                accessibilityRadio.setBackground(Color.WHITE);
                washroomsRadio.setBackground(Color.WHITE);
                navigationRadio.setBackground(Color.WHITE);
                restaurantRadio.setBackground(Color.WHITE);
                classroomRadio.setBackground(Color.WHITE);
                labsRadio.setBackground(Color.WHITE);
                group.add(accessibilityRadio);
                group.add(washroomsRadio);
                group.add(navigationRadio);
                group.add(restaurantRadio);
                group.add(classroomRadio);
                group.add(labsRadio);
                radioHolder.add(accessibilityRadio);
                radioHolder.add(washroomsRadio);
                radioHolder.add(navigationRadio);
                radioHolder.add(restaurantRadio);
                radioHolder.add(classroomRadio);
                radioHolder.add(labsRadio);
                tempPanel.add(labelHolder);
                tempPanel.add(radioHolder);
                panel.add(tempPanel);
                continue;
            }
            JTextField tempField = createTextField("");
            tempPanel.add(labelHolder);
            tempPanel.add(tempField);
            panel.add(tempPanel);
        }

        /**
         * Create buttons for submit and cancel and add to panel
         */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        create = createMapButton("Create POI");
        cancel = createMapButton("Cancel");
        buttonPanel.add(cancel);
        buttonPanel.add(create);

        panel.add(buttonPanel);

        /**
         * Add panel to frame
         */
        frame.add(panel);

        /**
         * Set frame properties
         */
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    /**
     * Method to set frame visibility
     */
    public void setVisibleFrame() {
        frame.setVisible(true);
    }

    /**
     * Method to get creation windows JPanel frame
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Method to create JTextField
     * @param text - the text of the text field
     * @return JTextField of the text field
     */
    public JTextField createTextField(String text) {
        /**
         * Create the new text field
         */
        JTextField newTextField = new JTextField(30);
        newTextField.setText(text);
        newTextField.setPreferredSize(new Dimension(150, 40));
        newTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newTextField.setFont(new Font("Georgia", 1, 15));
        return newTextField;
    }

    /**
     * method to create a new button and style it
     * @param text - the text of the button
     * @return button
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
     * Method to determine what methods to call based on where / what the mouse is pressing
     * @param event of the action listener
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        /**
         * When the create button is clicked, create a new POI
         */
        if (event.getSource() == create) {
            Component[] children = panel.getComponents();
            /*
             * iterate over all subPanels
             */
            ArrayList<String> newPOIData = new ArrayList<String>();
            for (Component sp : children) {
                if (sp instanceof JPanel) {
                    Component[] spChildren = ((JPanel)sp).getComponents();
                    /*
                     *  iterate over all JTextFields
                     */ 
                    for (Component spChild : spChildren) {
                        /**
                         * If it is a radio button get the radio button text
                         */
                        if (spChild instanceof JPanel) {
                            Component[] radioChildren = ((JPanel)spChild).getComponents();
                            for (Component radioChild : radioChildren) {
                                if (radioChild instanceof JRadioButton) {
                                    if (((JRadioButton)radioChild).isSelected()) {
                                        newPOIData.add(((JRadioButton)radioChild).getText());
                                    }
                                }
                            }
                        }
                        else if (spChild instanceof JTextField) {
                            String text = ((JTextField)spChild).getText();
                            if (text.equals("")) {
                                JPanel errorPanel = new JPanel();
                                JLabel errorMessage = new JLabel("Error: A text field is blank in the creation window. POI cannot be generated.");
                                errorPanel.add(errorMessage);
                                JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            newPOIData.add(text);
                        } 
                    }
                }
            }
            String layerType; 
            if (user.getIsAdmin() && !mapComponent.getIsCampusMap()) {
                layerType = newPOIData.get(5);
            }
            else if (user.getIsAdmin() && mapComponent.getIsCampusMap()) {
                layerType = "Building";
            }
            else {
                layerType = "User POI";
            }
            /**
             * condition if user on campus map
             */
            if (mapComponent.getIsCampusMap()){
                BuildingPointOfInterest buildingPOI = new BuildingPointOfInterest(
                    newPOIData.get(0), user.getUserID(),
                    !user.getIsAdmin(), layerType,
                    Integer.parseInt(newPOIData.get(3)), 
                    Integer.parseInt(newPOIData.get(4)), 
                    Integer.parseInt(newPOIData.get(1)), 
                    new ArrayList<>(), newPOIData.get(2), true
                    );
                try{
                    boolean addedSuccessfully = processor.addBuildingPointOfInterestToJsonFile(buildingPOI);
                    /*
                    * Gives an error message if the POI already exists for the user
                    */
                    if (!addedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI already exists");
                        errorPanel.add(errorMessage);
                    
                        JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (IOException err) {
                    err.printStackTrace();
                }
                mapComponent.displayPOIs();
                poiComponent.changeDisplayIfCampusMap();
                /**
                * Update the sidebar component to display the new POI
                */
                poiComponent.updatePOIComponent();
                frame.dispose();
            }
            /*
             * condition if not on campus map
             */
            else{
                PointOfInterest poi = new PointOfInterest(
                    newPOIData.get(0), user.getUserID(), 
                    !user.getIsAdmin(), layerType, 
                    Integer.parseInt(newPOIData.get(3)), 
                    Integer.parseInt(newPOIData.get(4)), 
                    mapComponent.getMapObject().getMapID(), 
                    mapComponent.getFloorMapObject().getBuildingID(), 
                    new ArrayList<>(), newPOIData.get(2), 
                    newPOIData.get(1), true
                    );
                try {
                    boolean addedSuccessfully = processor.addPointOfInterestToJsonFile(poi);
                    /*
                    * Gives an error message if the POI already exists for the user
                    */
                    if (!addedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI already exists");
                        errorPanel.add(errorMessage);
                    
                        JOptionPane.showMessageDialog(null, errorPanel, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException err) {
                    err.printStackTrace();
                }
            mapComponent.displayPOIs();
            poiComponent.changeDisplayIfCampusMap();
            /**
             * Update the sidebar component to display the new POI
             */
            poiComponent.updatePOIComponent();
            frame.dispose();
            }
           
        }
        /**
         * When the cancel button is clicked, close the window
         */
        if (event.getSource() == cancel) {
            frame.dispose();
        }
    }

    /**
     * Method to determine what cursor looks like depending on what type of object it is over
     * @param event of the mouse listener
     */
    @Override
    public void mouseEntered(MouseEvent event) {
        /**
         * Button hover
         */
        if (event.getSource() instanceof JButton) {
            JButton button = (JButton) event.getSource();
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
         * Button hover off
         */
        if (event.getSource() instanceof JButton button) {
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mousePressed(MouseEvent event) {
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mouseReleased(MouseEvent event) {
    }
}