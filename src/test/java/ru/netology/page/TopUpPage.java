package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {

    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement from = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public DashboardPage validTransfer(int value, DataHelper.CardInfo fromCard) {
        amount.setValue(String.valueOf(value));
        from.setValue(fromCard.getNumberWithoutSpaces());
        transferButton.click();
        // Убрали бессмысленную проверку видимости любого data-test-id
        return new DashboardPage();
    }
}