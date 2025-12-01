package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.BuyingTripPage;

import static data.DataHelper.*;

public class UITest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:8080");
    }

    @Test
    @DisplayName("Успешная покупка тура при валидных данных карты")
    public void shouldCompleteThePaymentSuccessfully() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();
        buyingTripPage.getMsg("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Отказ в покупке тура при валидных данных карты")
    public void shouldSeeAMessageAboutTheFailureOfTheOperationWithValidCardData() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuyOnCredit();
        buyingTripPage.cardData(getNumberCardDeclined(), getMonth(-1), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();
        buyingTripPage.getMsg("Ошибка! Банк отказал в проведении операции");
    }

    @Test
    @DisplayName("Заполненная форма \"Купить\" данными карты с дальнейшим перехом на форму \"Купить в кредит\": форма открывается пустая")
    public void shouldCompletedFormRemainEmptyWithFurtherTransitionBetweenForms() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(3), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonBuyOnCredit();

        buyingTripPage.emptyForm();
    }

    @Test
    @DisplayName("Покупка тура с пустыми полями данных карты")
    public void shouldAppearErrorMsgUnderTheEmptyFields() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData("", "", "", "", "");
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgNumberCard("Поле обязательно для заполнения");
        buyingTripPage.getErrorMsgMonth("Поле обязательно для заполнения");
        buyingTripPage.getErrorMsgYear("Поле обязательно для заполнения");
        buyingTripPage.getErrorMsgHolder("Поле обязательно для заполнения");
        buyingTripPage.getErrorMsgCvc("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка тура с пустым полем \"Номер карты\"")
    public void shouldAppearErrorMsgUnderTheEmptyCardNumberField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(null, getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgNumberCard("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка тура с пустым полем \"Месяц\"")
    public void shouldAppearErrorMsgUnderTheEmptyMonthField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), null, getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgMonth("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка тура с пустым полем \"Год\"")
    public void shouldAppearErrorMsgUnderTheEmptyYearField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(-4), null, getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgYear("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка тура с пустым полем \"Владелец\"")
    public void shouldAppearErrorMsgUnderTheEmptyHolderField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(1), getYear(3), getHolderLength(0), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgHolder("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Покупка тура с пустым полем \"CVC/CVV\"")
    public void shouldAppearErrorMsgUnderTheEmptyCvcField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(0), getYear(3), getHolder(), null);
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgCvc("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Заполнить поле \"Номер карты\", номером из 13 символов")
    public void shouldAppearErrorMsgWhenEnteringCardNumberWith13Characters() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getShortNumberCard(), getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgNumberCard("Неверный формат");
    }

    @Test
    @DisplayName("Заполнить поле \"Номер карты\", буквенными значениями")
    public void shouldAppearErrorMsgWhenEnteringCardNumberLetterValues() {
        var buyingTripPage = new BuyingTripPage();
        String card = "11112222hjilbvft";

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(card, getMonth(0), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgNumberCard("Неверный формат");
    }

    @Test
    @DisplayName("Заполнить поле \"Номер карты\", номером из 17 символов")
    public void shouldFormSentWithEntered17CharacterCardNumber() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getBigNumberCard(), getMonth(-1), getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getMsg("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Ввод несуществующего месяца в поле \"Месяц\"")
    public void shouldAppearErrorMsgWhenEnteringMonthThatDoesNotExist() {
        var buyingTripPage = new BuyingTripPage();
        String month = "13";

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), month, getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Ввод нулевого месяца в поле \"Месяц\"")
    public void shouldAppearErrorMsgWhenEnteringZeroMonth() {
        var buyingTripPage = new BuyingTripPage();
        String month = "00";

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), month, getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Ввод месяца без ведущего нуля в поле \"Месяц\"")
    public void shouldAppearErrorMsgWhenEnteringMonthWithoutLeadingZero() {
        var buyingTripPage = new BuyingTripPage();
        String month = "5";

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), month, getYear(3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgMonth("Неверный формат");
    }

    @Test
    @DisplayName("Ввод в поле \"Месяц\" предыдущий месяц и ввод в поле \"Год\" текущий год")
    public void shouldAppearErrorMsgWhenEnteringCurrentYearAndLastMonth() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(-1), getYear(0), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgMonth("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Ввод прошедшего года в поле \"Год\"")
    public void shouldAppearErrorMsgWhenEnteringPastYear() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(1), getYear(-3), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgYear("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Ввод года на 10 лет больше текущего в поле \"Год\"")
    public void shouldFormSentWith10YearsOlderThanTheCurrentOne() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(6), getYear(10), getHolder(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getMsg("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Ввод одного символа в поле \"Владелец\"")
    public void shouldAppearErrorMsgWhenEnteringSingleCharacterInTheOwnerField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(6), getYear(2), getHolderLength(1), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgHolder("Поле должно содержать не менее 2 символов");
    }

    @Test
    @DisplayName("Ввод 30 символов в поле \"Владелец\"")
    public void shouldBeFormSentWhenEnter30CharactersInTheOwnerField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(5), getYear(3), getHolderLength(30), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getMsg("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("Ввод больше 30 символов в поле \"Владелец\"")
    public void shouldAppearErrorMsgIfEnterMoreThan30CharactersInTheOwnerField() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(-1), getYear(3), getHolderLength(31), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgHolder("Поле должно содержать не более 30 символов");
    }

    @Test
    @DisplayName("Заполнение поля \"Владелец\" в нижнем регистре")
    public void shouldAppearErrorMsgWhenEnteringOwnerInLowercase() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(0), getYear(3), getHolderLowerCase(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgHolder("Неверный формат");
    }

    @Test
    @DisplayName("Заполнение поля \"Владелец\" кириллицей")
    public void shouldAppearErrorMsgWhenEnteringOwnerInCyrillic() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(0), getYear(3), getHolderRus(), getCVC());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgHolder("Неверный формат");
    }

    @Test
    @DisplayName("Ввод в поле \"CVC/CVV\" 1 символа")
    public void ShouldAppearErrorMessageWhenYouEnter1CvcCharacter() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(3), getYear(3), getHolder(), getCVC1Characters());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgCvc("Неверный формат");
    }

    @Test
    @DisplayName("Ввод в поле \"CVC/CVV\" 2 символов")
    public void ShouldAppearErrorMessageWhenYouEnter2CvcCharacter() {
        var buyingTripPage = new BuyingTripPage();

        buyingTripPage.clickButtonBuy();
        buyingTripPage.cardData(getNumberCardApproved(), getMonth(3), getYear(3), getHolder(), getCVC2Characters());
        buyingTripPage.clickButtonContinue();

        buyingTripPage.getErrorMsgCvc("Неверный формат");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
}