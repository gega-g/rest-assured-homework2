package ge.tbc.tbcitacademy.util;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SoapServiceSender {

    public static Response send(String url, String action, String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SoapAction", action)
                .body(body)
                .post(url)
                .then().log().all().extract().response();
    }
}
