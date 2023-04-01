package com.javan.dev;

/**
 * Include necessary imports
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;


/**
 * Weather class that handles retrieving API data from HTTP requests.
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public final class Weather {
    /**
     * API Key for Weather API [Too lazy to hide it + we won't be using it regularly so won't use a .gitignore]
     */
    private String API_key = "04decf0c05e44c028f211659231302";
    /**
     * URL for Weather API GET request for our purposes (simple current weather request)
     * Gets it for current IP of the user
     */
    private String GET_URL = "http://api.weatherapi.com/v1/current.json?key=" + API_key + "&q=auto:ip";
    /**
     * String to hold JSON object retrieved from constructor
     */
    private StringBuffer json = null;
    /**
     * Variables to hold weather data
     */
    private String tempInCelcius = null;
    private String condition;
    private String conditionIcon = null;
    /**
     * Weather panel
     */
    private JPanel weatherInfoPanel;

    /**
     * Private variable to hold the Weather singleton instance
     */
    private static Weather INSTANCE;

    /**
     * DataProcessor instance to be used
     */
    private DataProcessor processor = DataProcessor.getInstance();

    /**
     * Constructor for Weather object
     * @throws IOException
     * @throws MalformedURLException
     */
    private Weather() throws IOException, MalformedURLException {
        /**
         * Turn GET request URL into a URL object
         */
        URL url = new URL(GET_URL);

        /**
         * Open HTTP connection and set to GET request
         */
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        /**
         * Check if the connection was successful. If it wasn't, disconnect and return
         */
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            connection.disconnect();
            return;
        }

        /**
         * Open a BufferedReader for reading API response for the current weather at Western University
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        /**
         * Initialize json stringbuffer
         */
        json = new StringBuffer();

        /**
         * Add to the stringbuffer
         */
        while ((input = reader.readLine()) != null) {
            json.append(input);
        }

        /**
         * Close the connection to the API [Data has been received]
         */
        reader.close();
        connection.disconnect();
    }

    /**
     * Getter for the singleton instance
     * @return Weather instance
     */
    public static Weather getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new Weather();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    /**
     * Getter for the JSON object retrieved from the constructor
     * @return String json, the unprocessed JSON object
     */
    public StringBuffer getJSON() {
        return this.json;
    }

    /**
     * method to use DataProcessor to parse the weather JSON object
     */
    public void parseWeather() {
        /**
         * Do nothing / no parsing if null JSON object
         */
        if (this.json == null) {
            return;
        }
        
        /**
         * Get the parsed data and store it in the current Weather objet
         */
        ArrayList<String> parsedData = processor.parseWeather(this.json);
        this.tempInCelcius = parsedData.get(0);
        this.condition = parsedData.get(1);
        this.conditionIcon = parsedData.get(2);
    }

    /**
     * Getter for the temperature in celsius
     * @return String tempInCelcius, the temperature in celsius
     */
    public String getTempC() {
        return this.tempInCelcius;
    }

    /**
     * Getter for the textual representation of current weather condition
     * @return String condition
    */
    public String getCondition() {
        return this.condition;
    }

    /**
     * Getter for the link to the icon representing the current weather condition
     * @return String conditionIcon, link to icon
     */
    public String getConditionIcon() {
        return this.conditionIcon;
    }

    /**
     * Getter for the weather info panel
     * @return JPanel weather info panel for UI
     */
    public JPanel getWeatherInfoPanel() {
        return this.weatherInfoPanel;
    }

    /**
     * Method to add information to the Weather Information panel using the Weather class getTempC and getConditionIcon
     * Reads getConditionIcon URL and convert to BufferedImage for use in UI.
     * @return None
     * @throws IOException
     * @throws MalformedURLException
     */
    public JPanel addWeatherInfo() throws MalformedURLException, IOException {
        weatherInfoPanel = new JPanel();
        Weather weather = Weather.getInstance();
        weather.parseWeather();

        /**
         * If the temperature or icon are null, simply return null to not display weather information
         */
        if (weather.getTempC() == null || weather.getConditionIcon() == null) {
            return null;
        }
        /**
         * Get the temperature from the Weather class.
         */
        String temp = weather.getTempC() + "°C";

        /**
         * Process iconLink from Weather class to allow it to be read by ImageIO
         */
        String iconLink = "http:" + weather.getConditionIcon();

        /**
         * Read the URL and convert to BufferedImage
         */
        URL url = new URL(iconLink);
        BufferedImage image = ImageIO.read(url);
        Image newImage = image.getScaledInstance(35, 35, Image.SCALE_SMOOTH);

        /**
         * Add the image and temperature to the panel
         */
        JPanel tempFrame = new JPanel();
        JLabel tempLabel = new JLabel(temp);
        tempLabel.setFont(new Font("Georgia", Font.PLAIN, 25));
        tempLabel.setBackground(Color.WHITE);
        tempFrame.setBackground(Color.WHITE);
        tempFrame.add(tempLabel);

        JPanel imageFrame = new JPanel();
        JLabel label = new JLabel(new ImageIcon(newImage));
        label.setBackground(Color.WHITE);
        imageFrame.setBackground(Color.WHITE);

        /**
         * Remove spacing around the image
         */
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        imageFrame.add(label);


        /**
         * Add it all to the weatherInfoPanel
         */
        weatherInfoPanel.add(imageFrame);
        weatherInfoPanel.add(tempFrame);
        weatherInfoPanel.setBackground(Color.WHITE);
        weatherInfoPanel.setVisible(true);
        return weatherInfoPanel;
    }
}