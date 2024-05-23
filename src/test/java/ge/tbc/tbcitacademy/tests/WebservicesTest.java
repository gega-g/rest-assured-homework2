package ge.tbc.tbcitacademy.tests;

import ge.tbc.tbcitacademy.steps.WebservicesSteps;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.XmlConfig.xmlConfig;

public class WebservicesTest {
    WebservicesSteps webservicesSteps = new WebservicesSteps();

    @Epic("Using XmlPath and hamcrest matchers on continents data")
    @Test
    public void continentsTest(){
        webservicesSteps
                .countValidation()
                .nodesValueValidation()
                .equalsToAN()
                .lastContinentValue()
                .validateUnique()
                .validatePresence()
                .validatePattern()
                .validateOrder()
                .validatePresenceOfAllContinents()
                .validateNoNumbersInsCode()
                .findContainingO()
                .startsWithAendsWithCA()
                .validateNoNumbersInsName();
    }
}
