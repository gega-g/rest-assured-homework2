package ge.tbc.tbcitacademy.util;

import ge.tbc.tbcitacademy.constants.WEBSERVICECONSTANTS;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class XmlPathFromWebservices {
    public XmlPath getXmlPathFromService() {
        Response response = given()
                .filter(new AllureRestAssured())
                .baseUri(WEBSERVICECONSTANTS.BASEURI)
                .when()
                .get(WEBSERVICECONSTANTS.BASEPATH);

        String responseBody = response.getBody().asString();
        return new XmlPath(responseBody);
    }
}
