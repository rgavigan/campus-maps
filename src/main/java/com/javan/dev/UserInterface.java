package com.javan.dev;

// Import necessary libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Main UI class that handles the other UI classes that are visible and present within it.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class UserInterface extends JFrame implements ActionListener {
    /**
     * Initialize the JFrame for the UI during its entire run-time
     */
    private JFrame frame = new JFrame("Enhanced Campus Navigation - Group 1");

    /**
     * Initialize private variables for the menu bar in the JFrame
     */
    private JMenuBar menuBar;
    private JMenu helpMenu;
    private JMenuItem helpItem;
    private JMenuItem logout;
    private JMenuItem minimize;
    private JMenuItem exit;

    /**
     * Initialize private components contained within the UI
     */
    private LoginComponent loginComponent = LoginComponent.getInstance();
    private MapComponent mapComponent = MapComponent.getInstance();
    private SidebarComponent sidebarComponent = SidebarComponent.getInstance();
    private UserHelp userHelp = UserHelp.getInstance();

    /**
     * Initialize singleton instance of UserInterface
     */
    private static UserInterface INSTANCE;
    
    /**
     * Constructor to create Main Frame of UI. This will be the main frame that will be used throughout the session.
     * @throws IOException
     * @throws MalformedURLException
     */
    private UserInterface() throws MalformedURLException, IOException {
        /**
         * Create JFrame Window
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 675);
        frame.setMinimumSize(new Dimension(800, 450));

        /**
         * Add icon to the UI frame (UWO Logo)
         */
        frame.setIconImage(new ImageIcon("data/images/icon.png").getImage());

        /**
         * Create the menu bar and add it to the frame
         */
        createMenuBar();
        openLoginComponent();

        /**
         * Set the frame as visible after adding everything to it
        */
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        frame.requestFocusInWindow();

        /**
         * Loop throughout program lifespan, keeping UI open with the MapComponent if its open, but if the user hits
         * logout then go back to loginComponent and loop around again
         */
        while(true) {
            /**
             * If LoginComponent has user log in, then open the MapComponent
             */
            while (!loginComponent.getLoginStatus()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException event) {
                    event.printStackTrace();
                }
            }
            /**
             * Open MapComponent once user has logged in
             */
            frame.getContentPane().removeAll();
            openMapComponent();
            frame.revalidate();
            frame.repaint();

            /**
             * Loop until user logs out, then go back to LoginComponent
             */
            while (loginComponent.getLoginStatus()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException event) {
                    event.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to get instance of the singleton
     * @return UserInterface INSTANCE
     */
    public static UserInterface getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new UserInterface();
            } catch (MalformedURLException event) {
                event.printStackTrace();
            } catch (IOException event) {
                event.printStackTrace();
            }
        }
        return INSTANCE;
    }

    /**
     * Method to handle the actions of the menu items and other components
     * @param event of the action
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        /**
         * Go to UserHelp page when help menu is clicked
         */
        if (event.getSource() == helpItem) {
            userHelp.getFrame().setLocationRelativeTo(frame);
            userHelp.openHelpMenu();
        }
        /**
         * Go to LoginComponent page when logout is clicked
         */
        else if (event.getSource() == logout) {
            /**
             * Remove any other frames in the UI, then add login component to UI
             */
            frame.getContentPane().removeAll();
            openLoginComponent();
            loginComponent.setLoginStatus(false);

            frame.revalidate();
            frame.repaint();
        }
        /**
         * Minimize the UI when minimize is clicked
         */
        else if (event.getSource() == minimize) {
            frame.setState(JFrame.ICONIFIED);
        }
        /**
         * Exit the UI when exit is clicked
         */
        else if (event.getSource() == exit) {
            System.exit(0);
        }
    }

    /**
     * Method that opens a LoginComponent to the UI, allowing the user to login
     * This method will open the login component inside the same window as the main UI
     */
    public void openLoginComponent() {
        /**
         * Create instance of LoginComponent and add it to the frame
         */
        frame.add(loginComponent.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Method that opens a MapComponent to the UI, allowing the user to view maps
     * This method will open the map component inside the same window as the main UI
     * @throws IOException
     * @throws MalformedURLException
     */
    public void openMapComponent() throws MalformedURLException, IOException {
        /**
         * Close other components in the UI
         */
        frame.getContentPane().removeAll();

        /**
         * Create a new JPanel to hold the map component, then add the MapComponent to it
         */
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        
        /**
         * Have map component take up the leftmost majority of the display
         */
        gridConstraints.fill = GridBagConstraints.BOTH;
        gridConstraints.weightx = 0.75;
        gridConstraints.weighty = 1.0;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        mapPanel.add(mapComponent.getMapPanel(), gridConstraints);

        /**
         * Have sidebar component take up rightmost remainder of the display
         */
        gridConstraints.fill = GridBagConstraints.BOTH;
        gridConstraints.weightx = 0.10;
        gridConstraints.weighty = 1.0;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        mapPanel.add(sidebarComponent.getSidebarPanel(), gridConstraints);

        /**
         * Add the mapPanel to the UI
         */
        frame.add(mapPanel);
    }

    /**
     * Method to create the menu bar for the UI and add it to the UI
     */
    public void createMenuBar() {
        /**
         * Create a menu bar with a help menu contained in it
         */
        menuBar = new JMenuBar();
        helpMenu = new JMenu("Help");

        /**
         * Create the items for the help menu (helpItem, logout, minimize, exit)
         * Add action listeners to each item, as well as mnemonics for accessibility / shortcuts for faster use
         */
        helpItem = new JMenuItem("Help Menu");
        helpItem.setMnemonic(KeyEvent.VK_H);
        helpItem.addActionListener(this);

        logout = new JMenuItem("Logout");
        logout.setMnemonic(KeyEvent.VK_L);
        logout.addActionListener(this);

        minimize = new JMenuItem("Minimize");
        minimize.setMnemonic(KeyEvent.VK_M);
        minimize.addActionListener(this);

        exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.addActionListener(this);

        /**
         * Add the help menu and its items to the menu bar
         */
        menuBar.add(helpMenu);
        helpMenu.add(helpItem);
        helpMenu.add(logout);
        helpMenu.add(minimize);
        helpMenu.add(exit);
    }
}
