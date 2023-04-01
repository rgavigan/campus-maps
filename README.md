# **Campus Navigation Application**
## Group 1 - Riley Gavigan, Jake Choi, Bradley McGlynn, Dylan Sta Ana, Deep Shah
--- 
<br/>

### **Description**
---
* This application is a campus navigation application that allows a user to log-in, view the map of the campus to select one of the available
buildings available, and browse the available floorplan maps for the buildings. 

* Specifically, the system handles multiple users as well as 
developer accounts (which are allowed to edit POIs in the application). It allows users to interact with the floor-plan maps, specifically
by creating POIs, disabling / enabling layers of POIs, favouriting POIs, scrolling the map, browsing different floors, and viewing the weather.
User information (passwords) are encrypted for the safety of users of this application.

<br/><br/>

### **Required Software Versions / Libraries / Third-Party Tools**
---
* **Java SE 19**
* **Gson** (Version 2.9.0 and newer)
* **org.json** (Version 20220924)
* **JUnit 5** (Version 5.9.1)

<br/><br/>

### Step-By-Step Software Building Guide
---
To run this program, use the following steps to building and run the application:

1. Ensure that you have Java 19 or later. You can use the following command in your terminal to determine if you have the correct version of java downloaded on your computer. If you don't have the latest version downloaded download Java 19 here: [Download Java 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
```
java -version
```  

2. Download the latest NetBeans IDE or ensure that you have the latest version of NetBeans. You can download it here ([click here](https://netbeans.apache.org/download/nb17/)) This is because the latest NetBeans IDE has Maven bundled with the IDE. Maven is required to run our program, thus downloading NetBeans is required as Maven is built-in.

3. Open NetBeans and open an existing project. Ensure you open up the project named "CS2212-group1".

4. Once you open the project, you'll see 3 tabs on the left: "Projects", "Files", and "Services". Make sure you are under "Projects". Right-click on the project name "CS2212-group1" and click on "Build with Dependencies". If all your Index have been unpacked for the central repo, you will notice the project should build properly. However, if the all the indices haven't unpacked, please wait until NetBeans has done so before continuing.

<br/><br/>

### How to Run Built Application
---
1. If the project has been built, you may simply open 'group1.jar' and it will run the application.

- Alternatively:
1. If the project has been built already, you may run the application by clicking on the green play button in the top bar of icons.

2. A window will pop up asking you to choose a main class to run the program. That will be the App.class, which may show up as "com.javan.dev.App" file in the case of our application. It will most likely be the only option available to you, but if not please select the one with app in the name.

3. A java application window should pop-up with a log-in screen. Now you're ready to use our application.

<br/><br/>

### User Guide
---
#### Log-In / Create an Account
* Users must create an account or log into an existing account. Users can create an account by clicking on "New User? Create New Account". If you forget the password to your account, click on "Forgot Password".


#### Navigating the Map
* You can click and drag to navigate through a map, or scroll through the map as well.

#### Point of Interests
* On the Campus Map, if you click on the Building POIs (red flags), you will be redirected to the bottom-most floor map of the building you just clicked on.
* To interact with the POIs (red flags) on a floor map, click on them. A window should pop up with information about that POI. 

#### Side Panel
* Use the search bar to seearch for POIs on the current floor or in the building (for floor maps). If you are on the campus map, it will search for the available buildings on campus.
    * The search results should show up as a list in a pop up window. Select one item in the list to be redirected to the building (if you're on the campus map) or to the POI (if you're on a floor map).
    * NOTE: The search functionality within buildings will search through the POIs on other floors if a POI matching your search does not exist on your current floor.
* Point Of Interest Side Panel:
    * When on the campus map, a list of the all the buildings will appear on the side panel. If you select a building from that list, you will be redirected to the bottom-most floor map of that building.
    * When on a floor map, there will be a few sections.
        * The POI Layers section will allow you to toggle different layers of POIs on or off. By default, all POI layers will be toggled on. However, if you do not want to see any washroom POIs, then click on "Washrooms" under the "POI Layers" and it will hide any POIs that are a washroom.
        * The "Favourite POIs" section will list all POIs that you have favourited across every building on campus. When you select one in this list, it will redirect you to where that favourited POI is.
        * The "User POIs" section will list all POIs that you have created across every building on campus. When you select one in this list, it will redirect you to where that user created POI is.
        * The "Floor POIs" section lists all the current POIs on the floor. When you select one in this list, it will jump to that POI on the map.
* The Weather section will show the current condition as an icon (picture) and will display the temperature. 
    * NOTE: If you did not have an internet connection when first initially starting the application, the Weather section will not be shown on the side panel.

#### Editing Mode
* Please refer to the section "How To Access Editor Mode" below to learn how to enter and use editor mode.

<br/><br/>

### Accounts You Can Use To Run Software
---
1. User Account

    * **Username**: demo-user

    * **Password**: user

2. Developer Account

    * **Username**: admin

    * **Password**: admin

<br/><br/>

### How To Access Editor Mode
---
At the top of the application screen, you will see "Navigation Mode". If you click on "Navigation Mode", you will see "____ Editor Mode", where the blank is either Deveoper or User.

#### Developer Editor Mode:
* Developer Editor Mode is only available in the admin account.
* Admin accounts can add/edit/delete POIs on both the campus map and any floor maps. They can also add/edit/delete built-in POIs.

#### User Editor Mode:
* This editor mode is available to all other accounts.
* This editor mode is restricted to floors of any building. Users cannot add/edit/delete built-in POIs. They can only add/edit/delete user-made POIs.

#### How To Add/Edit/Delete POIs:
* Add:
    * In editor mode, click on an area where a red flag does not exist. This will make a window pop up to create a new POI.
* Edit:
    * Click on an existing POI. A window will pop up for the user to edit the information on the POI.
    * Hold ALT and left click to move a POI around the display to update it's position.
* Delete: 
    * Click on an existing POI. A window will pop up for the user, and click on the "Delete POI" button to delete the POI.

<br/><br/>

### Additional Information
---
Offline Mode:
* If you start up the application without an internet connection, the side panel will not show the weather. However, the application will still run as normal.