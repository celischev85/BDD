package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        // Centralize test credentials in one place
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo info) {

        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String number;
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }

    public static String getCardNumberWithoutSpaces(CardInfo card) {
        if (card == null || card.getNumber() == null) {
            return null;
        }
        return card.getNumber().replaceAll("\\s+", "");
    }

    public static String getMaskedNumber(CardInfo card) {
        String n = getCardNumberWithoutSpaces(card);
        if (n == null) {
            return null;
        }
        if (n.length() != 16) {

            return card.getNumber();
        }
        return "**** **** **** " + n.substring(12);
    }
}
