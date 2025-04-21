package pageObjects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutStepTwoPage {

    private final SelenideElement itemTotal = $("[data-test=subtotal-label]");
    private final SelenideElement finishButton = $("#finish");
    private final SelenideElement completeHeader = $(".complete-header");


    @Step("Проверка стоимости заказа без учета налогов")
    public void checkSumOrder(double orderSum){
        String checkSum = "Item total: $" + orderSum;
        itemTotal.shouldHave(text(checkSum), Duration.ofSeconds(3));
        attachImage(itemTotal);

        finishButton.click();
        completeHeader.shouldHave(text("Thank you for your order!"), Duration.ofSeconds(3));
        attachImage(completeHeader);
    }

    @Attachment(value = "data", type = "image/png", fileExtension = ".png")
    public byte[] attachImage(SelenideElement element){
        return element.getScreenshotAs(OutputType.BYTES);
    }

}
