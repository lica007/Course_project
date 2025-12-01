package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    @DisplayName("Успешная отправка заспроса с кодом 200 и статусом APPROVED")
    void shouldSuccessfulSendingAndStatusApproved() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(0),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    @Test
    @DisplayName("Успешная отправка заспроса с кодом 200 и статусом DECLINED")
    void shouldSuccessfulSendingAndStatusDeclined() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardDeclined(),
                DataHelper.getYear(3),
                DataHelper.getMonth(1),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("DECLINED"));
    }

    @Test
    @DisplayName("Отправленная форма с пустым полем \"Номер карты\", код ответа 400")
    void should400StatusWithAnEmptyFieldNumberCard() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                "",
                DataHelper.getYear(3),
                DataHelper.getMonth(0),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Отправленная форма с пустым полем \"Год\", код ответа 400")
    void should400StatusWithAnEmptyFieldYear() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                "",
                DataHelper.getMonth(0),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Отправленная форма с пустым полем \"Месяц\", код ответа 400")
    void should400StatusWithAnEmptyFieldMonth() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                "",
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Отправленная форма с пустым полем \"Владелец\", код ответа 400")
    void should400StatusWithAnEmptyFieldHolder() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(2),
                "",
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Отправленная форма с пустым полем \"CVC/CVV\", код ответа 400")
    void should400StatusWithAnEmptyFieldCvc() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(2),
                DataHelper.getHolder(),
                ""
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Отправленная форма с пустыми полеми, код ответа 400")
    void should400StatusWithEmptyFields() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                "", "", "", "", ""
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод несуществующего месяца в поле \"Месяц\", код ответа 400")
    void should400StatusWithNonExistentMonth() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                "13",
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод нулевого месяца в поле \"Месяц\", код ответа 400")
    void should400StatusWithZeroMonth() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                "00",
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод месяца без ведущего нуля в поле \"Месяц\", код ответа 400")
    void should400StatusWithMonthWithoutALeadingZero() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                "5",
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод 1 символа в поле \"CVC/CVV\", код ответа 400")
    void should400StatusWith1CharacterInTheCvcField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(3),
                DataHelper.getHolder(),
                DataHelper.getCVC1Characters()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод 2 символов в поле \"CVC/CVV\", код ответа 400")
    void should400StatusWith2CharacterInTheCvcField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(3),
                DataHelper.getHolder(),
                DataHelper.getCVC2Characters()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод прошедшего года в поле \"Год\", код ответа 400")
    void should400StatusWithTheInputOfTheLastYear() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(-2),
                DataHelper.getMonth(3),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод текущего года в поле \"Год\" и прошлого месяца в поле \"Месяц\", код ответа 400")
    void should400StatusWithTheInputOfTheCurrentYearAndLastMonth() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(0),
                DataHelper.getMonth(-1),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод максимального года (10 лет от текущего года) в поле \"Год\", код ответа 200 и статус APPROVED")
    void should200StatusWithAMaximumYearInput() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(10),
                DataHelper.getMonth(-1),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    @Test
    @DisplayName("Ввод больше 10 лет от текущего года (11 лет) в поле \"Год\", код ответа 400")
    void should400StatusWithAnEntryMoreThan10YearsOldFromTheCurrentYear() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(11),
                DataHelper.getMonth(-1),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод номера больше 16 символов (17 символов) в поле \"Номера карты\", код ответа 400")
    void should400StatusWithACardNumberOfMoreThan16Characters() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getBigNumberCard(),
                DataHelper.getYear(3),
                DataHelper.getMonth(5),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод номера меньше 16 символов (13 символов) в поле \"Номера карты\", код ответа 400")
    void should400StatusWithACardNumberOfLessThan16Characters() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getShortNumberCard(),
                DataHelper.getYear(3),
                DataHelper.getMonth(5),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод буквенных значений в поле \"Номера карты\", код ответа 400")
    void should400StatusWithEnteredLetterValuesInTheCardNumberField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                "aaaa aaaa aaaa aaaa",
                DataHelper.getYear(3),
                DataHelper.getMonth(5),
                DataHelper.getHolder(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод 1 буквы в поле \"Владелец\", код ответа 400")
    void should400StatusWith1LetterInputInTheHolderField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(5),
                DataHelper.getHolderLength(1),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод 30 символов в поле \"Владелец\", код ответа 200 и статус APPROVED")
    void should200StatusWith30CharactersEnteredInTheHolderField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(5),
                DataHelper.getHolderLength(30),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    @Test
    @DisplayName("Ввод больше 30 символов в поле \"Владелец\", код ответа 400")
    void should400StatusWithMoreThat30CharactersEnteredInTheHolderField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(6),
                DataHelper.getHolderLength(31),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод в поле \"Владелец\" имя в нижнем регистре, код ответа 400")
    void should400StatusWithTheNameEnteredInTheHolderFieldInLowercase() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(6),
                DataHelper.getHolderLowerCase(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("Ввод имени кириллицей в поле \"Владелец\", код ответа 400")
    void should400StatusWithTheNameEnteredInCyrillicInTheHolderField() {
        String body = String.format(
                "{\"number\":\"%s\",\"year\":\"%s\",\"month\":\"%s\",\"holder\":\"%s\",\"cvc\":\"%s\"}",
                DataHelper.getNumberCardApproved(),
                DataHelper.getYear(3),
                DataHelper.getMonth(6),
                DataHelper.getHolderRus(),
                DataHelper.getCVC()
        );

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/pay")
                .then()
                .log().all()
                .statusCode(400);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
