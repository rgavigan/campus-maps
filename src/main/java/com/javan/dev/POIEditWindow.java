package com.javan.dev;

// Import Swing Components
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.IOException;



/**
 * UI class that allows users to edit POI objects.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class POIEditWindow extends JFrame implements ActionListener, MouseListener {
    /**
     * Private instance variables
     */
    private JFrame frame;
    private JPanel panel;
    private JButton edit;
    private JButton cancel;
    private JPanel buttonPanel;
    private JButton delete;
    private JPanel deletePanel;

    /**
     * Private variables to hold the instance of the data processor, user, mapCOmponent and poiComponent objects
     */
    private DataProcessor processor = DataProcessor.getInstance();
    private User user = User.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();
    private POIComponent poiComponent = POIComponent.getInstance();

    private PointOfInterest poi;
    private BuildingPointOfInterest buildingPOI;

    /**
     * Constructor for POICreationWindow given a POIs ID
     * @param poiID - the POI ID
     */
    public POIEditWindow (int poiID) {
        /*
         * Fills buildigPOI if on campus map
         */
        if (mapComponent.getIsCampusMap()) {
            this.buildingPOI = processor.getBuildingPOI(poiID);
        }
        else {
            this.poi = processor.getPOI(poiID);
        }

        /**
         * Create Frame and Panel
         */
        frame = new JFrame("Edit POI");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        frame.setSize(450, 250);
        frame.setMinimumSize(new Dimension(200, 200));
        /**
         * Add icon to the UI frame (Flag Icon)
         */
        frame.setIconImage(new ImageIcon("data/images/flag.png").getImage());

        /**
         * Create Button to delete POI and add to panel
         */
        deletePanel = new JPanel();
        deletePanel.setLayout(new GridBagLayout());
        /**
         * Make button take up entire horizontal space
         */
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        deletePanel.setBackground(Color.WHITE);
        delete = createMapButton("Delete POI");
        deletePanel.add(delete, gridBagConstraints);

        /**
         * Add delete panel to the main panel
         */
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        panel.add(deletePanel, gridBagConstraints);


        
        /**
         * Create ArrayList of different metadata String titles
         */
        ArrayList<String> metadata = new ArrayList<>();
        metadata.add("Name");
        if (!mapComponent.getIsCampusMap()) {
            metadata.add("Room Number");
        }
        metadata.add("Description");
        if (user.getIsAdmin()) {
            metadata.add("Layer Type");
        }

        ArrayList<String> currentPOIData = new ArrayList<>();
        if (!mapComponent.getIsCampusMap()) {
            currentPOIData.add(this.poi.getName());
            currentPOIData.add(String.valueOf(this.poi.getRoomNumber()));
            currentPOIData.add(this.poi.getDescription());
            if (user.getIsAdmin()) {
                currentPOIData.add(this.poi.getPOItype());
            }
        }
        else {
            currentPOIData.add(this.buildingPOI.getName());
            currentPOIData.add(this.buildingPOI.getDescription());
            if (user.getIsAdmin()) {
                currentPOIData.add(this.buildingPOI.getPOItype());
            }
        }
        

        /**
         * Loop 10 times, creating a JPanel that holds a JLabel and a JTextField
         */
        for (int i = 0; i < metadata.size(); i++) {
            gridBagConstraints.gridy += 1;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weighty = 1;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            JPanel tempPanel = new JPanel();
            tempPanel.setBackground(Color.WHITE);
            tempPanel.setLayout(new GridLayout());
            JPanel labelHolder = new JPanel();
            labelHolder.setBackground(Color.WHITE);
            JLabel tempLabel = new JLabel(metadata.get(i));
            tempLabel.setFont(new Font("Georgia", 1, 17));
            labelHolder.add(tempLabel);
            JTextField tempField = createTextField(currentPOIData.get(i));
            tempPanel.add(labelHolder);
            tempPanel.add(tempField);
            panel.add(tempPanel, gridBagConstraints);
        }

        /**
         * Create buttons for submit and cancel and add to panel
         */
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        edit = createMapButton("Edit POI");
        cancel = createMapButton("Cancel");
        buttonPanel.add(cancel);
        buttonPanel.add(edit);
        gridBagConstraints.gridy += 1;

        panel.add(buttonPanel, gridBagConstraints);

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
     * @param text - the text of the field
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
     * Function to create a new button and style it
     * @param text - the text of the button
     * @return JButton button
     */
    public JButton createMapButton(String text) {
        JButton button = new JButton(text);
        if (text.contains("Delete POI")) {
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
        }
        else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
        button.setFocusPainted(false);
        button.setFont(new Font("Georgia", Font.PLAIN, 19));
        button.addActionListener(this);
        button.addMouseListener(this);
        button.setOpaque(true);
        return button;
    }


    /**
     * Method to determine what methods to call based on where / what the mouse is pressing
     * @param event of the action listener
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        /**
         * When the edit button is clicked, edit a new POI
         */
        if (event.getSource() == edit) {
            Component[] children = panel.getComponents();
            /*
             * iterate over all subPanels
             */ 
            ArrayList<String> newPOIData = new ArrayList<String>();
            for (Component sp : children) {
                if (sp instanceof JPanel) {
                    Component[] spChildren = ((JPanel)sp).getComponents();
                    /*
                     * iterate over all JTextFields
                     */ 
                    for (Component spChild : spChildren) {
                        if (spChild instanceof JTextField) {
                            String text = ((JTextField)spChild).getText();
                            if (text.equals("")) {
                                JPanel errorPanel = new JPanel();
                                JLabel errorMessage = new JLabel("Error: A text field is blank in the editing window. POI cannot be edited.");
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
            if (user.getIsAdmin()) {
                layerType = newPOIData.get(3);
            }
            else {
                layerType = "User POI";
            }

            /*
             * Determines what to do based on what type of map the user is on
             */
            if (!mapComponent.getIsCampusMap()) {
                /**
                 * Edit POI object with the new POI data with setters
                 */
                poi.setName(newPOIData.get(0));
                poi.setRoomNumber(newPOIData.get(1));
                poi.setDescription(newPOIData.get(2));
                poi.setPOItype(layerType);

                try {
                    boolean editedSuccessfully = processor.editPointOfInterestInJsonFile(poi);
                    /*
                    * Gives an error message if the POI already exists for the user
                    */
                    if (!editedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI editing failed");
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
            
            else {
                buildingPOI.setName(newPOIData.get(0));
                buildingPOI.setDescription(newPOIData.get(1));
                buildingPOI.setPOItype(layerType);

                try {
                    boolean editedSuccessfully = processor.editBuildingPointOfInterestInJsonFile(buildingPOI);
                    /*
                    * Gives an error message if the POI already exists for the user
                    */
                    if (!editedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI editing failed");
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
        /**
         * When the delete button is clicked, delete the POI from the map and JSON file
         */
        if (event.getSource() == delete) {
            if (!mapComponent.getIsCampusMap()) {
                try {
                    boolean deletedSuccessfully = processor.deletePointOfInterestFromJsonFile(poi);
                    /*
                     * Gives an error message if the POI already exists for the user
                     */
                    if (!deletedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI deletion failed");
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
            else {
                try {
                    boolean deletedSuccessfully = processor.deleteBuildingPointOfInterestFromJsonFile(buildingPOI);
                    /*
                     * Gives an error message if the POI already exists for the user
                     */
                    if (!deletedSuccessfully) {
                        JPanel errorPanel = new JPanel();
                        JLabel errorMessage = new JLabel("Error: POI deletion failed");
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
        if (event.getSource() instanceof JButton button) {
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