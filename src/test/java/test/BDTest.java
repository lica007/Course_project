package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import page.BuyingTripPage;

import static data.DataHelper.*;
import static data.DataHelper.getCVC;
import static data.DataHelper.getHolder;
import static data.SQLHelper.getStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BDTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:8080");
    }

    @Test
    @DisplayName("Запись в базе со статусом \"Одобренно\" (APPROVED)")
    public void shouldAppearInTheDatabaseAnEntryWithTheApprovedStatus() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();
        buyingTripPage.getMessage();

        assertEquals("APPROVED", getStatus());
    }

    @Test
    @DisplayName("Запись в базе со статусом \"Одобренно\" (DECLINED)")
    public void shouldAppearInTheDatabaseAnEntryWithTheDeclinedStatus() {
        var buyingTripPage = new BuyingTripPage();


        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardDeclined(), getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();
        buyingTripPage.getMessage();

        assertEquals("DECLINED", getStatus());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}
