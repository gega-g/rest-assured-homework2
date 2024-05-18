package ge.tbc.tbcitacademy.tests.unmarshallingtests;

import ge.tbc.tbcitacademy.steps.UnmarshallingSteps;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;

public class UnmarshallingTest {
    UnmarshallingSteps unmarshallingSteps = new UnmarshallingSteps();

    @Epic("Employees service tests")
    @Test
    public void serviceTest(){
        unmarshallingSteps
                .addEmployee()
                .getEmployeeById()
                .updateEmployee()
                .deleteEmployee()
                .validateEmployeeDeletion()
        ;
    }
}
