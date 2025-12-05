package data;

import io.restassured.http.ContentType;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class APIHelper {

    public static String getCreateRequestBody(String number, String year, String month, String holder, String cvc) {
        return String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                number, year, month, holder, cvc
        );
    }

    public static Response getSendPaymentRequest(String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .extract()
                .response();
    }
}
