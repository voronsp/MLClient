package ru.airpaw.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
/**
 *
 * @author sbt-voronova-id
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome=true,
        plugin = {"pretty"},
        glue = "ru.airpaw.stepDefs",
        features = "src/test/java/ru/airpaw/feautures")
public class CucumberTest {
    
}
