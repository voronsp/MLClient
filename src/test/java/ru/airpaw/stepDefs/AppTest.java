package ru.airpaw.stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.awt.AWTException;
import java.awt.Robot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    public static Robot robot;
    public static WebDriver driver;
    
    @Before
    public void setUp () throws AWTException{
        robot = new Robot();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
    }
    
    @After
    public void tearDown() {
        driver.close();
    }
}
