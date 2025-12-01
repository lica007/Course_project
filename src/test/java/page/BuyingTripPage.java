package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyingTripPage {
    private final SelenideElement numberCardField = $$("span.input__top").findBy(text("Номер карты")).closest("div").$("input");
    private final SelenideElement monthField = $$("span.input__top").findBy(text("Месяц")).closest(".input").$(".input__control");
    private final SelenideElement yearField = $$("span.input__top").findBy(text("Год")).closest(".input").$(".input__control");
    private final SelenideElement holderField = $$("span.input__top").findBy(text("Владелец")).closest(".input").$(".input__control");
    private final SelenideElement cvcField = $$("span.input__top").findBy(text("CVC/CVV")).closest(".input").$(".input__control");
    private final SelenideElement buttonBuy = $$(".button__text").findBy(text("Купить"));
    private final SelenideElement buttonBuyOnCredit = $$(".button__text").findBy(text("Купить в кредит"));
    private final SelenideElement buttonContinue = $$(".button__text").findBy(text("Продолжить"));
    private final SelenideElement notification = $(".notification__content");
    private final SelenideElement errorMsgNumberCard = $$("span.input__top").findBy(text("Номер карты")).closest(".input").$(".input__sub");
    private final SelenideElement errorMsgMonth = $$("span.input__top").findBy(text("Месяц")).closest(".input").$(".input__sub");
    private final SelenideElement errorMsgYear = $$("span.input__top").findBy(text("Год")).closest(".input").$(".input__sub");
    private final SelenideElement errorMsgHolder = $$("span.input__top").findBy(text("Владелец")).closest(".input").$(".input__sub");
    private final SelenideElement errorMsgCvc = $$("span.input__top").findBy(text("CVC/CVV")).closest(".input").$(".input__sub");
    private final SelenideElement notificationMsg = $(".notification");

    public void clickButtonBuy() {
        buttonBuy.click();
    }

    public void clickButtonBuyOnCredit() {
        buttonBuyOnCredit.click();
    }

    public void clickButtonContinue() {
        buttonContinue.click();
    }

    public void cardData(String numberCard, String month, String year, String holder, String cvc) {
        numberCardField.setValue(numberCard);
        monthField.setValue(month);
        yearField.setValue(year);
        holderField.setValue(holder);
        cvcField.setValue(cvc);
    }

    public void emptyForm() {
        numberCardField.getText().isEmpty();
        monthField.getText().isEmpty();
        yearField.getText().isEmpty();
        holderField.getText().isEmpty();
        cvcField.getText().isEmpty();
    }

    public void getMsg(String msg) {
        notification.shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldHave(text(msg));
    }

    public void getErrorMsgNumberCard(String msg) {
        errorMsgNumberCard.shouldHave(exactText(msg))
                .should(Condition.visible);
    }

    public void getErrorMsgMonth(String msg) {
        errorMsgMonth.shouldHave(exactText(msg))
                .should(Condition.visible);
    }

    public void getErrorMsgYear(String msg) {
        errorMsgYear.shouldHave(exactText(msg))
                .should(Condition.visible);
    }

    public void getErrorMsgHolder(String msg) {
        errorMsgHolder.shouldHave(exactText(msg))
                .should(Condition.visible);
    }

    public void getErrorMsgCvc(String msg) {
        errorMsgCvc.shouldHave(exactText(msg))
                .should(Condition.visible);
    }

    public void getMessage() {
        notificationMsg.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}