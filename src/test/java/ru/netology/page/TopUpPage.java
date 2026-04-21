package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpPage {

    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement error = $("[data-test-id='error-notification']");

    public DashboardPage validTransfer(int value, String number) {
        amount.setValue(String.valueOf(value));
        from.setValue(number);
        transferButton.click();

        $("[data-test-id]").shouldBe(visible);

        return new DashboardPage();
    }
}


