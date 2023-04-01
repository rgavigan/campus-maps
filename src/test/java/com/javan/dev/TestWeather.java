package com.javan.dev;

/**
 * Include necessary libraries
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * @author : Riley Emma Gavigan [rgavigan@uwo.ca]
 * @version : 1.0
 * @since : 1.0
 */
public class TestWeather {
    static Weather weather;

    /**
     * This method is called before testing - sets up a new weather object
     * @throws IOException
     * @throws MalformedURLException
     */
    @BeforeAll
    static void setUp() throws MalformedURLException, IOException {
        weather = Weather.getInstance();
        weather.parseWeather();
    }

    /**
     * This method is called to check that the Weather singleton instance is not null and that it returns the same instance
     */
    @Test
    void testGetInstanceReturnsSameInstance() {
        Weather instance1 = Weather.getInstance();
        Weather instance2 = Weather.getInstance();
        assertEquals(instance1, instance2);
    }

    /**
     * This method is called to check that the temperature is not null
     */
    @Test
    @DisplayName("Test that the temperature is not null")
    void testTemperature() {
        assertNotNull(weather.getTempC());
    }

    /**
     * This method is called to check that the condition is not null
     */
    @Test
    @DisplayName("Test that the condition is not null")
    void testCondition() {
        assertNotNull(weather.getCondition());
    }

    /**
     * This method is called to check that the condition icon is not null
     */
    @Test
    @DisplayName("Test that the condition icon is not null")
    void testConditionIcon() {
        assertNotNull(weather.getConditionIcon());
    }

    /**
     * This method is called to check that the API successfully ran and returned a JSON that is not null
     */
    @Test
    @DisplayName("Test that the WeatherAPI was successfully called")
    void testWeatherAPI() {
        assertNotNull(weather.getJSON());
    }
}