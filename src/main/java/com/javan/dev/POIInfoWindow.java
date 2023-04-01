package com.javan.dev;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * UI class that allows users to view details about POIs.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca], Deep Ashishkumar Shah [dshah228@uwo.ca], Brad McGLynn [bmcglyn4@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class POIInfoWindow extends JFrame implements ActionListener, MouseListener {
    private JPanel panel;
    private JFrame frame;
    private ArrayList<JLabel> labels;
    private PointOfInterest poi;
    private BuildingPointOfInterest buildingPOI;
    private JButton favourite;
    private ImageIcon favouriteIcon = new ImageIcon("data/images/favourited_poi.png");
    private ImageIcon unfavouriteIcon = new ImageIcon("data/images/unfavourited_poi.png");
    

    /**
     * Private variables to hold the instance of the data processor, user, mapComponent and poiComponent objects
     */
    private DataProcessor processor = DataProcessor.getInstance();
    private User userInstance = User.getInstance();
    private POIComponent poiComponent = POIComponent.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();

    /**
     * Constructor that creates the POI information window given the POIs ID
     * @param poiID - the ID of the POI
     */
    public POIInfoWindow(int poiID) {
        /*
         * determines what type of poi to proceed with
         */
        if (mapComponent.getIsCampusMap()) {
            this.buildingPOI = processor.getBuildingPOI(poiID);
        } else {
            this.poi = processor.getPOI(poiID);
        }

        /**
         * Update icons
         */
        Image img = unfavouriteIcon.getImage();
        Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        unfavouriteIcon = new ImageIcon(newimg);

        img = favouriteIcon.getImage();
        newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        favouriteIcon = new ImageIcon(newimg);


        /**
         * Create JFrame Window
         */
        if (mapComponent.getIsCampusMap()) {
            frame = new JFrame(this.buildingPOI.getName());
        } else {
            frame = new JFrame(this.poi.getName());
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setMinimumSize(new Dimension(200, 200));

        /**
         * Add icon to the UI frame (Flag Icon)
         */
        frame.setIconImage(new ImageIcon("data/images/flag.png").getImage());

        /**
         * Create JPanel to hold the POI information
         */
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        /**
         * Initialize array of 5 JLabels to hold the POI information
         */
        labels = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            labels.add(new JLabel());
        }


        /**
         * Create JLabels to hold the POI information
         */
        if (!mapComponent.getIsCampusMap()){
            labels.get(0).setText(poi.getName());
            labels.get(1).setText("POI ID: " + poi.getID());
            labels.get(2).setText("Room Number: " + poi.getRoomNumber());
            labels.get(3).setText("Description: " + poi.getDescription());
            labels.get(4).setText("Layer Type: " + poi.getPOItype());
            labels.get(5).setText("X-Value: " + poi.getCoordinates()[0]);
            labels.get(6).setText("Y-Value: " + poi.getCoordinates()[1]);
            if (userInstance.getIsAdmin() == true) {
                labels.get(7).setText("User ID: " + poi.getUserID());
            }
        } else {
            labels.get(0).setText(buildingPOI.getName());
            labels.get(1).setText("BuildingPOI ID: " + buildingPOI.getID());
            labels.get(2).setText("Building ID: " + buildingPOI.getBuildingID());
            labels.get(3).setText("Description: " + buildingPOI.getDescription());
            labels.get(4).setText("Layer Type: " + buildingPOI.getPOItype());
            labels.get(5).setText("X-Value: " + buildingPOI.getCoordinates()[0]);
            labels.get(6).setText("Y-Value: " + buildingPOI.getCoordinates()[1]);
            if (userInstance.getIsAdmin() == true) {
                labels.get(7).setText("User ID: " + buildingPOI.getUserID());
            }
        }

        /**
         * Add the favourite button to the panel
         */
        if (!mapComponent.getIsCampusMap() && !poi.getIsFavourited(userInstance.getUserID()) ) {
            /**
             * Make JButton with icon image
             */
            favourite = new JButton();
            favourite.setIcon(unfavouriteIcon);


            /**
             * Add icon to center of button
             */
            favourite.setVerticalAlignment(SwingConstants.CENTER);
            favourite.setHorizontalAlignment(SwingConstants.CENTER);
        }
        else {
            /**
             * Make JButton with icon image
             */ 
            favourite = new JButton();
            favourite.setIcon(favouriteIcon);

            /**
             * Add icon to center of button
             */
            favourite.setVerticalAlignment(SwingConstants.CENTER);
            favourite.setHorizontalAlignment(SwingConstants.CENTER);
        }

        /**
         * Style the JButton
         */
        favourite.setBackground(Color.WHITE);
        favourite.setForeground(Color.BLACK);
        favourite.setFocusPainted(false);
        favourite.setPreferredSize(new Dimension(40, 40));
        favourite.setMaximumSize(new Dimension(40, 40));
        favourite.setMinimumSize(new Dimension(40, 40));
        favourite.setBorder(BorderFactory.createEmptyBorder());
        favourite.addActionListener(this);
        favourite.addMouseListener(this);

        /**
         * Move button to the far right
         */
        favourite.setAlignmentX(Component.LEFT_ALIGNMENT);

        /**
         * Style labels
         */
        style(labels.get(0), 20);
        for (int i = 1; i < 8; i++) {
            style(labels.get(i), 15);
        }

        /**
         * Style the panel and space apart the JLabels
         */
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /**
         * Let favourite button take all horizontal space possible
         */
        JPanel favouritePanel = new JPanel();
        favouritePanel.setLayout(new BoxLayout(favouritePanel, BoxLayout.X_AXIS));
        favouritePanel.setBackground(Color.WHITE);
        favouritePanel.add(Box.createHorizontalGlue());
        favouritePanel.add(favourite);

        panel.add(favouritePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));


        /**
         * Add the JLabels to the JPanel
         */
        for (int i = 0; i < 8; i++) {
            panel.add(labels.get(i));
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        /**
         * Add the JPanel to the JFrame
         */
        frame.add(panel);
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
     * Styling for the JLabel text and font
     * @param label - the label to style
     * @param int n - the font size
     * @retun None
     */
    private void style(JLabel label, int n) {
        label.setFont(new Font("Georgia", Font.PLAIN, n));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Getter for the labels ArrayList
     * @return labels
     */
    public ArrayList<JLabel> getLabels() {
        return labels;
    }

    /**
     * Method to change the favourite button appearance and update the POI object
     * @param event of the action listener
     */
    
    @Override
    public void actionPerformed(ActionEvent event)  {
        /**
         * If favourited, change to unfavourited
         */
        if (poi.getIsFavourited(userInstance.getUserID())) {
            favourite.setIcon(unfavouriteIcon);
            poi.setIsFavourited(userInstance.getUserID());
            
        }
        /**
         * If unfavourited, change to favourited
         */
        else {
            favourite.setIcon(favouriteIcon);
            poi.setIsFavourited(userInstance.getUserID());
        }
        /**
         * Refresh window to display new icon
         */
        frame.revalidate();
        frame.repaint();
        try {
            processor.editPointOfInterestInJsonFile(poi);
        } catch (IOException err) {
            err.printStackTrace();
        }
        poiComponent.changeDisplayIfCampusMap();
        /**
         * Update the sidebar component to display the new POI
         */
        poiComponent.updatePOIComponent();
    }

    /**
     * Change mouse cursor when hovering over button
     * @param event of the mouse listener
     */
    @Override
    public void mouseEntered(MouseEvent event) {
        if (event.getSource() == favourite) {
            frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Change mouse cursor when no longer hovering over button
     * @param event of the mouse listener
     */
    @Override
    public void mouseExited(MouseEvent event) {
        if (event.getSource() == favourite) {
            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        // Do nothing
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mousePressed(MouseEvent event) {
        // Do nothing
    }

    /**
     * Unused method from mouse listener
     * @param event of the mouse listener
     */
    @Override
    public void mouseReleased(MouseEvent event) {
        // Do nothing
    }
    
}