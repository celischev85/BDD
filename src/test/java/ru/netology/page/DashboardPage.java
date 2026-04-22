package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    public TopUpPage selectCard(DataHelper.CardInfo card) {
        SelenideElement cardElement = $$("li").find(text(DataHelper.getMaskedNumber(card)));
        cardElement.$("button").click();
        return new TopUpPage();
    }

    public int getCardBalance(String maskedCardNumber) {
        SelenideElement card = findCardByMaskedNumber(maskedCardNumber);
        return extractBalance(card.getText());
    }

    public int getCardBalance(DataHelper.CardInfo card) {
        SelenideElement cardElement = findCardByMaskedNumber(DataHelper.getMaskedNumber(card));
        return extractBalance(cardElement.getText());
    }

    private SelenideElement findCardByMaskedNumber(String maskedNumber) {
        ElementsCollection cards = $$("li");
        SelenideElement card = cards.findBy(text(maskedNumber));
        if (card == null) {
            throw new RuntimeException("Не найдена карта с маской: " + maskedNumber);
        }
        return card;
    }

    private int extractBalance(String text) {
        int colonIndex = text.indexOf(":");
        if (colonIndex == -1) {
            throw new RuntimeException("Не найден символ ':' в тексте: " + text);
        }
        String afterColon = text.substring(colonIndex + 1);
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("-?\\d+").matcher(afterColon);
        if (m.find()) {
            try {
                return Integer.parseInt(m.group());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Не удалось распарсить баланс: " + m.group(), e);
            }
        }
        throw new RuntimeException("Не найдены цифры баланса в тексте: " + text);
    }
}