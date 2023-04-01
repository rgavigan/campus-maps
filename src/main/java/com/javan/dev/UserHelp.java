package com.javan.dev;

// Import Swing Components
import javax.swing.*;
import java.awt.*;


/**
 * UI class that shows users a help menu to aid in using the application.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class UserHelp extends JFrame {
    /**
     * Initialize private variables for the JFrame and its contents
     */
    private final JFrame frame;
    private final JTabbedPane tabbedPane;
    private final JPanel general;
    private final JPanel login;
    private final JPanel navigate;
    private final JPanel search;
    private final JPanel favourite;
    private final JPanel browse;
    private final JPanel create;
    private final JPanel editing;
    private JLabel generalTitle;
    private JLabel loginTitle;
    private JLabel navigateTitle;
    private JLabel searchTitle;
    private JLabel favouriteTitle;
    private JLabel browseTitle;
    private JLabel createTitle;
    private JLabel editTitle;

    /**
     * Initialize singleton instance of UserHelp
     */
    private static UserHelp INSTANCE;

    /**
     * Constructor to create UserHelp JFrame Window with JPanels / tabs for each section of the help menu for the user to browse
     */
     private UserHelp() {
        /**
         * Create JFrame Window
         */
        frame = new JFrame("Help Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 563);
        frame.setMinimumSize(new Dimension(800, 450));

        /**
         * Add icon to the UI frame (UWO Logo)
         */
        frame.setIconImage(new ImageIcon("data/images/icon.png").getImage());

        /**
         * JTabbedPane to hold JPanels for each menu section
         */
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);

        /**
         * JPanels for different menu sections (General, Logging In, Navigating, Searching, Favouriting POIs, Browsing Maps, Creating POIs, Editing POIs)
         */
        general = new JPanel();
        login = new JPanel();
        navigate = new JPanel();
        search = new JPanel();
        favourite = new JPanel();
        browse = new JPanel();
        create = new JPanel();
        editing = new JPanel();

        /**
         * Creating the tabs
         */
        createGeneralTab();
        createLoginTab();
        createNavigateTab();
        createSearchTab();
        createFavouritesTab();
        createBrowseTab();
        createCreatingPOIsTab();
        createEditingPOIsTab();

        /**
         * Add JPanels to the JTabbedPane as well as the JTabbedPane to the JFrame
         */
        tabbedPane.addTab("General", general);
        tabbedPane.addTab("Logging In", login);
        tabbedPane.addTab("Navigating", navigate);
        tabbedPane.addTab("Searching", search);
        tabbedPane.addTab("Favouriting POIs", favourite);
        tabbedPane.addTab("Browsing Maps", browse);
        tabbedPane.addTab("Creating POIs", create);
        tabbedPane.addTab("Editing POIs", editing);
        frame.add(tabbedPane);
     }

    /**
     * Getter for the Singleton Instance of UserHelp
     * @return UserHelp instance
    */
    public static UserHelp getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserHelp();
        }
        return INSTANCE;
    }

    /**
     * Getter for the JFrame created
     * @return frame that was created
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Method to open the user help menu
     */
    public void openHelpMenu() {
        frame.setVisible(true);
    }

    /**
     * Method to set background
     * @param panel - the panel's to set the background for
     */
    public void setBackground(JPanel panel) {
        panel.setBackground(Color.WHITE);
    }

    /**
     * Method to create the general tab
     */
    private void createGeneralTab() {
        /**
         * Setting layout of general tab and title
         */
        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
        setBackground(general);
        generalTitle = new JLabel("Welcome to the Enhanced Campus Navigation System!");
        generalTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        general.add(generalTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        general.add(new JLabel("This application was created by Riley Gavigan (rgavigan@uwo.ca), Jake Choi (jchoi492@uwo.ca), Bradley McGlynn (bmcglyn4@uwo.ca), Deep Shah (dshah228@uwo.ca), and Dylan Sta Ana (dstaana@uwo.ca)"));
        general.add(new JLabel("Version 1.0"));
        general.add(new JLabel("Release Date: April 6th, 2023"));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("This application is designed to help you navigate the Western University campus."));
        general.add(new JLabel("To begin, you must log in to the application with your username and password."));
        general.add(new JLabel("For information regarding logging in, creating an account, and retrieving your password: Navigate to the \"Logging In\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("Once you have logged in, you will be able to navigate the campus using the map."));
        general.add(new JLabel("For information regarding navigating the campus: Navigate to the \"Navigating\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("You can also create new POIs exclusive to you on the floor maps."));
        general.add(new JLabel("For information regarding creating POIs: Navigate to the \"Creating POIs\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("You can also edit your POIs you created on the floor maps."));
        general.add(new JLabel("For information regarding creating POIs: Navigate to the \"Editing POIs\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("You are also able to search for different POIs within the Campus Map and Floor maps."));
        general.add(new JLabel("For information regarding searching for POIs: Navigate to the \"Searching\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("You can also favourite POIs to easily access them later."));
        general.add(new JLabel("For information regarding favouriting POIs: Navigate to the \"Favouriting POIs\" tab."));
        general.add(Box.createVerticalStrut(25));
        general.add(new JLabel("You can also browse the campus map and floor maps."));
        general.add(new JLabel("For information regarding browsing the campus map and floor maps: Navigate to the \"Browsing Maps\" tab."));
    }

    /**
     * Method to create the login tab
     */
    private void createLoginTab() {
        /**
         * Setting layout of login tab and title
         */
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
        setBackground(login);
        loginTitle = new JLabel("Logging In");
        loginTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        login.add(loginTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        login.add(new JLabel("To log in to the application, you must enter your username and password."));
        login.add(new JLabel("If you do not have an account, you can create one by clicking the \"Create Account\" button."));
        login.add(new JLabel("If you have forgotten your password, you can retrieve it by clicking the \"Forgot Password\" button. Simply enter your username and hit Forgot Password to retrieve it."));
        login.add(Box.createVerticalStrut(25));
        login.add(new JLabel("To create an account, you must enter a username and password that will be used to authenticate your login."));
        login.add(new JLabel("You must also enter password once more to confirm your password is correct."));
        login.add(Box.createVerticalStrut(25));
    }

    /**
     * Method to create the navigate tab
     */
    private void createNavigateTab() {
        /**
         * Setting layout of navigate tab and title
         */
        navigate.setLayout(new BoxLayout(navigate, BoxLayout.Y_AXIS));
        setBackground(navigate);
        navigateTitle = new JLabel("Navigating");
        navigateTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        navigate.add(navigateTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        navigate.add(new JLabel("To navigate the campus, you can either use the search feature or the navigation feature."));
        navigate.add(new JLabel("To use the search feature, you can either click on the \"Search for Point of Interest\" box on the top right hand side of the screen, "));
        navigate.add(new JLabel("You can then enter the name of the building you wish to navigate to."));
        navigate.add(Box.createVerticalStrut(25));
        navigate.add(new JLabel("To use the navigation feature, you can check to see if you are in \"Navigation Mode\" or \"User Editing Mode\" at the top of the screen."));
        navigate.add(new JLabel("If you are in \"User Editing Mode\" press the box once to switch to \"Navigation Mode\"."));
        navigate.add(new JLabel("If you are on the campus map, you can use your mouse to navigate around campus or look through the avaliable buildings on the right hand side of the screen."));
        navigate.add(new JLabel("You can also use the navigation feature to navigate and explore floor maps."));
        navigate.add(Box.createVerticalStrut(25));
        navigate.add(new JLabel("To navigate to a floor map, navigate to the building you would like to see the floor map in."));
        navigate.add(new JLabel("You can then use the \"Floor Down\" or \"Floor Up\" buttons to navigate the floor maps of the building."));
        navigate.add(Box.createVerticalStrut(25));
        navigate.add(new JLabel("To navigate to a different building within a floor map, click on the \"Campus Map\" button to return to see the avaliable buildings."));
    }

    /**
     * Method to create the search tab
     */
    private void createSearchTab() {
        /**
         * Setting layout of search tab and title
         */
        search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
        setBackground(search);
        searchTitle = new JLabel("Searching");
        searchTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        search.add(searchTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        search.add(new JLabel("To search for a POI, you can click on the \"Search for Point of Interest\" on the right side of the screen."));
        search.add(new JLabel("You can then enter the name of the POI you wish to search for."));
        search.add(Box.createVerticalStrut(25));
        search.add(new JLabel("To search for a building within a floor map, you must return to the campus map and use the \"Search for Point of Interest\" on the right hand side of the screen"));
        search.add(new JLabel("You can then enter the name of the building you wish to search for."));
    }

    /**
     * Method to create the favourites tab
     */
    private void createFavouritesTab() {
        /**
         * Setting layout of favourites tab and title
         */
        favourite.setLayout(new BoxLayout(favourite, BoxLayout.Y_AXIS));
        setBackground(favourite);
        favouriteTitle = new JLabel("Favourites");
        favouriteTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        favourite.add(favouriteTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        favourite.add(new JLabel("To add a POI to your favourites, you must be on a floor map with POIs."));
        favourite.add(new JLabel("Navigate to the POI you would like to favourite and click on it while in Navigation Mode."));
        favourite.add(new JLabel("A window will pop up with a star at the top-right and information about the POI."));
        favourite.add(new JLabel("Click on the star to turn it into a filled star, which will add it to your favourites."));
        favourite.add(Box.createVerticalStrut(25));;
        favourite.add(new JLabel("To remove a POI from your favourites, you must be on a floor map with POIs."));
        favourite.add(new JLabel("Navigate to the POI you would like to unfavourite and click on it."));
        favourite.add(new JLabel("A window should pop up with a star at the top-right and information about the POI."));
        favourite.add(new JLabel("Click on the star to turn it into a blank star, which will remove it from your favourites."));
     
    }

    /**
     * Method to create the browse tab
     */
    private void createBrowseTab() {
        /**
         * Setting layout of browse tab and title
         */
        browse.setLayout(new BoxLayout(browse, BoxLayout.Y_AXIS));
        setBackground(browse);
        browseTitle = new JLabel("Browsing Maps");
        browseTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        browse.add(browseTitle);

        /**
         * Simply adding content via JLabels to the general tab
         */
        browse.add(new JLabel("You can browse the campus map and the POIs of floor maps."));
        browse.add(new JLabel("To browse the campus map, you can either look at it as you open the application and log in, or go back from a floor map to the campus map by clicking the \"Campus Map\" button"));
        browse.add(new JLabel("The options available for maps you are free to browse are displayed on the right side of your screen on the Campus Map tab, showing the available buildings."));
        browse.add(new JLabel("You may also click on a flag at a building on the map itself to open that building's first floor map."));
        browse.add(new JLabel("To browse the floor POIs navigate to a building and browse through the avaliable floor POIs on the right hand side of the screen."));
    }
    
    /**
     * Method to create the create POI tab
     */
    private void createCreatingPOIsTab() {
        /**
         * Setting layout of creating POI tab and title
         */
        create.setLayout(new BoxLayout(create, BoxLayout.Y_AXIS));
        setBackground(create);
        createTitle = new JLabel("Creating POIs");
        createTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        create.add(createTitle);

        /**
         * Simply adding content via JLabels to the create POI tab
         */
        create.add(new JLabel("You can create new POIs exclusive to you on the Floor map."));
        create.add(new JLabel("To create a new POI navigate to the building and floor you would like to add a POI to."));
        create.add(new JLabel("Once on the floor map, check the top of the screen to see if you are in \"Navigation Mode\" or \"User Editing Mode\". Developers will be in \"Developer Editing Mode\"."));
        create.add(new JLabel("If you are in \"Navigation Mode\" press the button once to enter \"User Editing Mode\". "));
        create.add(new JLabel("Once in \"User Editing Mode\", navigate to the POI location you would like to add and click on it."));
        create.add(new JLabel("A window should pop up with information about the newly created POI. Fill out the information and press the \"Create POI\" button."));
    }
    
    /**
     * Method to create the editing POIs tab
     */
    private void createEditingPOIsTab() {
        /**
         * Setting layout of edit POI tab and title
         */
        editing.setLayout(new BoxLayout(editing, BoxLayout.Y_AXIS));
        setBackground(editing);
        editTitle = new JLabel("Editing POIs");
        editTitle.setFont(new Font("Georgia", Font.BOLD, 25));
        editing.add(editTitle);

        /**
         * Simply adding content via JLabels to the editing POI tab
         */
        editing.add(new JLabel("You can edit POIs that you have made on the floor maps."));
        editing.add(new JLabel("To edit the POI, check the top of the screen to see if you are in \"Navigation Mode\" or if you are in \"User Editing Mode\". Developers will be in \"Developer Editing Mode\"."));
        editing.add(new JLabel("If you are in \"Navigation Mode\" press the button once to enter \"User Editing Mode\". "));
        editing.add(new JLabel("Once in \"User Editing Mode\", navigate to the POI you would like to edit and click on it."));
        editing.add(new JLabel("A window should pop up with information about the POI you selected, edit the information and press the \"Edit POI\" button."));
        editing.add(Box.createVerticalStrut(25));
        editing.add(new JLabel("If you would like to move the location of the POI or drag the POI to a different location without changing the information."));
        editing.add(new JLabel("Check that you are in \"Editing User Mode\" and navigate to the POI you would like to change."));
        editing.add(new JLabel("Hold down the left or right click on your mouse and hold the ALT key while dragging the POI to the new location.."));
        editing.add(Box.createVerticalStrut(25));
        editing.add(new JLabel("To delete a POI instead press the \"User Editing Mode\" button at the top of the window."));
        editing.add(new JLabel("If you are in \"Navigation Mode\" press the button once to enter \"User Editing Mode\". "));
        editing.add(new JLabel("Once in \"User Editing Mode\", navigate to the POI you would like to edit and click on it."));
        editing.add(new JLabel("A window should pop up with information about the POI you selected, press the \"Delete POI\" button."));
    }
}   
