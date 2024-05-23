package ge.tbc.tbcitacademy.tests;

import ge.tbc.tbcitacademy.steps.MarshallingSteps;
import org.testng.annotations.Test;

public class MarshallingTest {
    MarshallingSteps marshallingSteps = new MarshallingSteps();

    @Test
    public void createPerson(){
        marshallingSteps
                .createEmployee();
    }
}
