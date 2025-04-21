package pageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import component.User;
import helpers.PropertiesHelper;
import io.qameta.allure.Attachment;
import io.qameta.allure.Link;
import io.qameta.allure.Step;

import java.io.IOException;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public final SelenideElement usernameField = $("#user-name");
    public final SelenideElement passwordField = $("#password");
    public final SelenideElement loginButton = $("#login-button");
    public final SelenideElement errorHeader = $("h3[data-test='error']");

    public static void open() throws IOException {
        Selenide.open(PropertiesHelper.getProperty("url"));
    }

    @Step("Успешная авторизация")
    @Link("https://www.saucedemo.com/")
    public void login_standard(User usercreds) throws IOException {

        login(usercreds);
        errorHeader.shouldNotBe(Condition.visible, Duration.ofSeconds(3));
    }

    @Step("Неуспешная авторизация")
    @Link("https://www.saucedemo.com/")
    public void login_locked_out(User usercreds) throws IOException {

        login(usercreds);
        errorHeader.shouldBe(Condition.visible, Duration.ofSeconds(3));
    }

    private void login(User usercreds) throws IOException {
        open();
        attachData("username: " + usercreds.username() + "\n" + " password: " + usercreds.password());
        usernameField.setValue(usercreds.username() );
        passwordField.setValue(usercreds.password());
        loginButton.click();
    }


    @Attachment(value = "data", type = "text/plain", fileExtension = ".txt")
    public String attachData(String text){
        return text;
    }



}













