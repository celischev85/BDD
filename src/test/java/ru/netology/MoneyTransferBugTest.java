package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferBugTest {

    private LoginPage loginPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Нельзя перевести сумму больше остатка")
    void shouldNotAllowTransferMoreThanBalance() {
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardInfo = DataHelper.getFirstCard();
        val secondCardInfo = DataHelper.getSecondCard();

        if (firstCardInfo == null || secondCardInfo == null) {
            throw new IllegalStateException("Card information should not be null");
        }
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(firstCardInfo));
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(secondCardInfo));
        int amountToTransfer = secondCardBalance + 1;
        val transferPage = dashboardPage.selectCard(firstCardInfo);
        int preTransferFirstBalance = firstCardBalance;
        int preTransferSecondBalance = secondCardBalance;

        val dashboardPageAfter = transferPage != null ? transferPage.validTransfer(amountToTransfer, secondCardInfo) : null;

        if (dashboardPageAfter == null) {
            throw new IllegalStateException("Dashboard page after transfer is null; UI state may be broken.");
        }

        int actualFirstBalance = dashboardPageAfter.getCardBalance(DataHelper.getMaskedNumber(firstCardInfo));
        int actualSecondBalance = dashboardPageAfter.getCardBalance(DataHelper.getMaskedNumber(secondCardInfo));

        assertEquals(preTransferFirstBalance, actualFirstBalance, "Balance of the source card should not change on failed transfer");
        assertEquals(preTransferSecondBalance, actualSecondBalance, "Balance of the destination card should not change on failed transfer");
    }
}