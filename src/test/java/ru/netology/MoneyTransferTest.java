package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoney() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();


        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);


        val firstCardInfo = DataHelper.getFirstCard();
        val secondCardInfo = DataHelper.getSecondCard();


        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(firstCardInfo));
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(secondCardInfo));


        int amount = secondCardBalance / 2;


        val transferPage = dashboardPage.selectCard(firstCardInfo);
        int expectedBalanceFirstCard = firstCardBalance + amount;
        int expectedBalanceSecondCard = secondCardBalance - amount;

        val dashboardPageAfter = transferPage.validTransfer(amount, secondCardInfo);


        int actualBalanceFirstCard = dashboardPageAfter.getCardBalance(DataHelper.getMaskedNumber(firstCardInfo));
        int actualBalanceSecondCard = dashboardPageAfter.getCardBalance(DataHelper.getMaskedNumber(secondCardInfo));

        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard, "First card balance should reflect the transfer amount added");
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard, "Second card balance should reflect the transfer amount deducted");
    }
}
