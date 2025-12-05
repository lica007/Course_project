package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker();
    private static final Faker RUSSIAN_FAKER = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getNumberCardApproved() {
        return "1111 2222 3333 4444";
    }

    public static String getNumberCardDeclined() {
        return "5555 6666 7777 8888";
    }

    public static String getShortNumberCard() {
        return "1111 2222 3333 4";
    }

    public static String getBigNumberCard() {
        return "1111 2222 3333 4444 5";
    }

    public static String getLettersNumberCard() {
        return "1111 2222 hjil bvft";
    }

    public static String getMonth(int monthAdd) {
        return LocalDate.now().plusMonths(monthAdd).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getNonExistentMonth() { return "13"; }

    public static String getZeroMonth() { return "00"; }

    public static String getYear(int yearsAdd) {
        return String.valueOf(LocalDate.now().plusYears(yearsAdd).getYear()).substring(2);
    }

    public static String getHolder() {
        return FAKER.name().fullName().toUpperCase();
    }

    public static String getHolderRus() {
        return RUSSIAN_FAKER.name().fullName().toUpperCase();
    }

    public static String getHolderLowerCase() {
        return FAKER.name().fullName().toLowerCase();
    }

    public static String getHolderLength(int length) {
        if (length <= 0) return "";

        String name = FAKER.name().firstName().toUpperCase();

        while (name.length() < length) {
            name += " " + FAKER.name().firstName().toUpperCase();
        }
        return name.substring(0, length);
    }

    public static String getCVC() {
        return FAKER.numerify("###");
    }

    public static String get2Characters() {
        return FAKER.numerify("##");
    }

    public static String get1Characters() { return FAKER.numerify("#"); }

    @Value
    @AllArgsConstructor
    public static class CardInfo {
        String numberCard;
        String month;
        String year;
        String holder;
        String cvc;
    }
}