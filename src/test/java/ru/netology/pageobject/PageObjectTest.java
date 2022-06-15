package ru.netology.pageobject;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.pageobject.data.DataHelper;
import ru.netology.pageobject.page.LoginPage;
import ru.netology.pageobject.page.PersonalAccount;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.pageobject.data.DataHelper.getFirstCardNumber;
import static ru.netology.pageobject.data.DataHelper.getSecondCardNumber;
import static ru.netology.pageobject.page.PersonalAccount.clickFirstCardButton;
import static ru.netology.pageobject.page.PersonalAccount.clickSecondCardButton;

public class PageObjectTest {
    @BeforeAll
    static void openPage() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromCardToCard() {
        val dashboardPage = new PersonalAccount();
        int amount = 150;
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();


        val moneyTransfer = clickSecondCardButton();
        moneyTransfer.moneyTransfer(amount, getFirstCardNumber());
        val secondCardBalanceResult = secondCardBalanceStart + amount;
        val firstCardBalanceResult = firstCardBalanceStart - amount;


        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        val personalAccount = new PersonalAccount();
        int amount = 20;
        val firstCardBalanceStart = personalAccount.getFirstCardBalance();
        val secondCardBalanceStart = personalAccount.getSecondCardBalance();

        val moneyTransfer = clickFirstCardButton();
        moneyTransfer.moneyTransfer(amount, getSecondCardNumber());
        val firstCardBalanceResult = firstCardBalanceStart + amount;
        val secondCardBalanceResult = secondCardBalanceStart - amount;

        assertEquals(firstCardBalanceResult, personalAccount.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, personalAccount.getSecondCardBalance());
    }

//    @Test
//    void shouldBeErrorWhenTransferMoneyMoreThanBalance() {
//        int amount = 15300;
//        val moneyTransfer = clickSecondCardButton();
//        moneyTransfer.moneyTransfer(amount, getFirstCardNumber());
//        moneyTransfer.getErrorLimit();
//
//    }
}
