package ge.tbc.tbcitacademy.tests.unmarshallingtests;

import ge.tbc.tbcitacademy.steps.SomeServiceSteps;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

public class SomeServiceTest {
    SomeServiceSteps serviceSteps = new SomeServiceSteps();
    @Epic("Some service test")
    @Test
    public void serviceTest(){
        serviceSteps
                .findPerson()
                .validatePresenceOfPerson()
                .validateName()
                .validateSSN()
                .validateSSNLength()
                .validateSSNPattern()
//                .validateDOB()
                .validatePresenceOfColors()
                .validateLastname()
//                .checkIfRedIsPresent()
                .validateIndividualZipcodes()
        ;
    }
}
