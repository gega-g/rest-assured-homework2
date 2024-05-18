package ge.tbc.tbcitacademy.steps;

import com.example.springboot.soap.interfaces.*;
import ge.tbc.tbcitacademy.constants.MARSHALLINGCONSTANTS;
import ge.tbc.tbcitacademy.util.Marshall;
import ge.tbc.tbcitacademy.util.SoapServiceSender;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UnmarshallingSteps {
    ObjectFactory objectFactory = new ObjectFactory();
    EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
    EmployeeInfo updatedInfo = objectFactory.createEmployeeInfo();
    String body = null;

    @Step("Adds employee, Sends the request and ensure message `Content Added Successfully`")
    public UnmarshallingSteps addEmployee() {
        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();

        employeeInfo
                .withEmployeeId(MARSHALLINGCONSTANTS.EMPLOYEEID)
                .withAddress(MARSHALLINGCONSTANTS.EMPLOYEEADDRESS)
                .withDepartment(MARSHALLINGCONSTANTS.EMPLOYEEDEPARTMENT)
                .withEmail(MARSHALLINGCONSTANTS.EMPLOYEEEMAIL)
                .withSalary(new BigDecimal(MARSHALLINGCONSTANTS.EMPLOYEESALARY))
                .withPhone(MARSHALLINGCONSTANTS.EMPLOYEEPHONE);
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        body = Marshall.marshallSoapRequest(addEmployeeRequest);
        Response response = SoapServiceSender.send(MARSHALLINGCONSTANTS.EMPLOYEESURL,
                "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest",
                body);
        AddEmployeeResponse addEmployeeResponse = Marshall.unmarshallResponse(response.asString(), AddEmployeeResponse.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(addEmployeeResponse.getServiceStatus().getMessage(), equalTo("Content Added Successfully"));
        return this;
    }

    @Step("Sends the request and ensure the returned employee details match the expected values.")
    public UnmarshallingSteps getEmployeeById(){
        Response response =  SoapServiceSender.send(MARSHALLINGCONSTANTS.EMPLOYEESURL,
                "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest",
                body);
        String res = response.asString();

        AddEmployeeResponse addEmployeeResponse = Marshall.unmarshallResponse(res, AddEmployeeResponse.class);
        assertThat(addEmployeeResponse.withEmployeeInfo(employeeInfo).getEmployeeInfo().getEmployeeId(),equalTo(MARSHALLINGCONSTANTS.EMPLOYEEID));
        assertThat(addEmployeeResponse.withEmployeeInfo(employeeInfo).getEmployeeInfo().getAddress(),equalTo(MARSHALLINGCONSTANTS.EMPLOYEEADDRESS));
        assertThat(addEmployeeResponse.withEmployeeInfo(employeeInfo).getEmployeeInfo().getDepartment(),equalTo(MARSHALLINGCONSTANTS.EMPLOYEEDEPARTMENT));
        assertThat(addEmployeeResponse.withEmployeeInfo(employeeInfo).getEmployeeInfo().getEmail(),equalTo(MARSHALLINGCONSTANTS.EMPLOYEEEMAIL));
        return this;
    }

    @Step("Sends the request and ensure the operation was successful and than getEmployeeById details are updated correctly.")
    public UnmarshallingSteps updateEmployee(){
        UpdateEmployeeRequest updateEmployeeRequest = objectFactory.createUpdateEmployeeRequest();
        updatedInfo
                .withEmployeeId(MARSHALLINGCONSTANTS.UPDATEDID)
                .withAddress(MARSHALLINGCONSTANTS.UPDATEDADDRESS)
                .withDepartment(MARSHALLINGCONSTANTS.UPDATEDDEPARTMENT)
                .withEmail(MARSHALLINGCONSTANTS.UPDATEDEMAIL)
                .withSalary(new BigDecimal(MARSHALLINGCONSTANTS.UPDATEDSALARY))
                .withPhone(MARSHALLINGCONSTANTS.UPDATEDPHONE);
        updateEmployeeRequest.setEmployeeInfo(updatedInfo);
        body = Marshall.marshallSoapRequest(updateEmployeeRequest);
        Response response = SoapServiceSender.send(MARSHALLINGCONSTANTS.EMPLOYEESURL,
                "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest",
                body);

        assertThat(response.statusCode(), equalTo(200));
        UpdateEmployeeResponse updateEmployeeResponse = Marshall.unmarshallResponse(response.asString(), UpdateEmployeeResponse.class);
        assertThat(updateEmployeeResponse.getServiceStatus().getMessage(), equalTo(MARSHALLINGCONSTANTS.UPDATEMESSAGE));
        assertThat(updateEmployeeRequest.withEmployeeInfo(updatedInfo).getEmployeeInfo().getEmployeeId(),equalTo(MARSHALLINGCONSTANTS.UPDATEDID));
        System.out.println(updateEmployeeRequest.withEmployeeInfo(updatedInfo));
        return this;
    }

    @Step("Sends the request and validates the response with XPath to ensure the operation was successful.")
    public UnmarshallingSteps deleteEmployee() {
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(MARSHALLINGCONSTANTS.EMPLOYEEID);

        String body = Marshall.marshallSoapRequest(deleteEmployeeRequest);
        Response response = SoapServiceSender.send(MARSHALLINGCONSTANTS.EMPLOYEESURL,
                "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest",
                body);

        assertThat(response.statusCode(), equalTo(200));

        DeleteEmployeeResponse deleteEmployeeResponse = Marshall.unmarshallResponse(response.asString(), DeleteEmployeeResponse.class);
        assertThat(deleteEmployeeResponse.getServiceStatus().getStatus(), equalTo(MARSHALLINGCONSTANTS.SUCCESS));
        assertThat(deleteEmployeeResponse.getServiceStatus().getMessage(), equalTo(MARSHALLINGCONSTANTS.SUCCESSMESSAGE));

        return this;
    }

    @Step
    public UnmarshallingSteps validateEmployeeDeletion() {
        GetEmployeeByIdRequest getEmployeeByIdRequest = objectFactory.createGetEmployeeByIdRequest();
        getEmployeeByIdRequest.setEmployeeId(MARSHALLINGCONSTANTS.EMPLOYEEID);

        String body = Marshall.marshallSoapRequest(getEmployeeByIdRequest);
        Response response = SoapServiceSender.send(MARSHALLINGCONSTANTS.EMPLOYEESURL,
                "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest",
                body);


        assertThat(response.statusCode(), equalTo(500));
        return this;
    }
}
