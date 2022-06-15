package ru.netology.pageobject.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.pageobject.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class MoneyTransfer {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public PersonalAccount moneyTransfer(int amount, DataHelper.CardInfo from) {
        amountField.setValue(valueOf(amount));
        fromField.setValue(valueOf(from));
        transferButton.click();
        return new PersonalAccount();
    }

    public void getErrorLimit() {
        $(byText("Ошибка! Сумма превышает допустимый лимит!")).shouldBe(visible);
    }
}
