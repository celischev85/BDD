package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    public TopUpPage selectCard(DataHelper.CardInfo card) {
        SelenideElement cardElement = $$("li").find(text(card.getMasked()));
        cardElement.$("button").click();
        return new TopUpPage();
    }

    public int getCardBalance(String maskedCardNumber) {
        ElementsCollection cards = $$("li");
        SelenideElement card = cards.findBy(text(maskedCardNumber));
        return extractBalance(card.getText());
    }

    private int extractBalance(String text) {
        // Мы используем символ двоеточия ':', так как кириллица (слово "баланс")
        // ломалась при компиляции на Windows из-за кодировки-кракозябр.
        int start = text.indexOf(":");
        if (start == -1) {
            throw new RuntimeException("Не найден симовол ':' в тексте: " + text);
        }
        String afterColon = text.substring(start + 1);
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("-?\\d+").matcher(afterColon);
        if (m.find()) {
            return Integer.parseInt(m.group(0));
        }
        throw new RuntimeException("Не найдены цифры баланса в тексте: " + text);
    }
}