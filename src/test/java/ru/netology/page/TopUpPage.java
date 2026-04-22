package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;


public class TopUpPage {

    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement from = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");


    private TopUpPage clearAndType(SelenideElement element, String value) {
        if (element == null) {
            throw new IllegalStateException("Target input element is not initialized");
        }
        element.clear();
        if (value != null) {
            element.setValue(value);
        }
        return this;
    }


    public DashboardPage validTransfer(int value, DataHelper.CardInfo fromCard) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be non-negative");
        }
        if (fromCard == null) {
            throw new IllegalArgumentException("fromCard must not be null");
        }

        clearAndType(amount, String.valueOf(value));
              String cardNumber = DataHelper.getCardNumberWithoutSpaces(fromCard);
        clearAndType(from, cardNumber);
        transferButton.click();
        return new DashboardPage();
    }
}
