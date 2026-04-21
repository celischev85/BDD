package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper; // или ru.netology.data

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    public TopUpPage selectCard(DataHelper.CardInfo card) {
        SelenideElement cardElement =
                $$("li")
                        .find(text(card.getId()));

        cardElement.$("button").click();
        return new TopUpPage();
    }

    public int getCardBalance(String maskedCardNumber) {
        ElementsCollection cards = $$("li");
        SelenideElement card = cards.findBy(text(maskedCardNumber));
        String value = card.getText();
        return extractBalance(value);
    }

    public int getCardBalance(DataHelper.CardInfo card) {
        ElementsCollection cards = $$("li");
        SelenideElement cardElement = cards.findBy(text(card.getId()));
        String value = cardElement.getText();
        return extractBalance(value);
    }

    private int extractBalance(String text) {
        int index = text.indexOf("баланс:");
        if (index == -1) {
            throw new RuntimeException("Баланс not found in text");
        }
        String balancePart = text.substring(index + 7); // 7 — длина слова "баланс:"
        String digitsOnly = balancePart.replaceAll("\\D+", "");
        return Integer.parseInt(digitsOnly);
    }
}