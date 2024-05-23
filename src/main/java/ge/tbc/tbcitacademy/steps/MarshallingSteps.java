//package ge.tbc.tbcitacademy.steps;
//
//import com.example.springboot.soap.interfaces.*;
//import ge.tbc.tbcitacademy.constants.MARSHALLINGCONSTANTS;
//import ge.tbc.tbcitacademy.util.SerializeXml;
//import io.restassured.response.Response;
//
//import java.math.BigDecimal;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//public class MarshallingSteps {
//    ObjectFactory objectFactory = new ObjectFactory();
//    public MarshallingSteps createEmployee(){
//        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();
//        EmployeeInfo employeeInfo = objectFactory.createEmployeeInfo();
//        employeeInfo.setEmployeeId(MARSHALLINGCONSTANTS.EMPLOYEEID);
//        employeeInfo.setAddress(MARSHALLINGCONSTANTS.EMPLOYEEADDRESS);
//        employeeInfo.setDepartment(MARSHALLINGCONSTANTS.EMPLOYEEDEPARTMENT);
//        employeeInfo.setEmail(MARSHALLINGCONSTANTS.EMPLOYEEEMAIL);
//        employeeInfo.setSalary(new BigDecimal(MARSHALLINGCONSTANTS.EMPLOYEESALARY));
//        employeeInfo.setPhone(MARSHALLINGCONSTANTS.EMPLOYEEPHONE);
//        addEmployeeRequest.setEmployeeInfo(employeeInfo);
//
//        String body = SerializeXml.marshallXml(addEmployeeRequest);
//
//        Response response = given()
//                .contentType("text/xml")
//                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest")
//                .body(body)
//                .when()
//                .post("http://localhost:8087/ws/employees.wsdl")
//                .then()
//                .extract().response();
//
////        xpath აქ პრობლემა ან არვიცი რატო ვერგადის ასერშენს, დანარჩენი ტასკები ვერგავიგე როგორ უნდა დამეწერა
////        response.then()
////                .body(hasXPath("//Envelope.Body.addEmployeeResponse.message", containsString("Content Added Successfully")));
//
//        System.out.println(response);
//        return this;
//    }
//}
