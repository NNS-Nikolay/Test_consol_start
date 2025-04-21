package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.BrowserPerTestStrategyExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import ext.*;
import helpers.PropertiesHelper;
import helpers.TestHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pageObjects.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;

@ExtendWith(LoginPageResolver.class)
@ExtendWith(InventoryPageResolver.class)
@ExtendWith(CartPageResolver.class)
@ExtendWith(CheckoutStepOnePageResolver.class)
@ExtendWith(CheckoutStepTwoPageResolver.class)

@ExtendWith(BrowserPerTestStrategyExtension.class)
public class saucedemoTest {

    public static PropertiesHelper propertiesHelper;
    public static TestHelper testHelper;

    @BeforeAll
    public static void setUp() throws IOException {
        propertiesHelper = new PropertiesHelper();
        testHelper = new TestHelper();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        Configuration.browserPosition = "10x10";
        Configuration.timeout = 20000;
        Configuration.headless = false;
    }

    @Test
    @DisplayName("Авторизация под пользователем standart")
    @Severity(SeverityLevel.BLOCKER)
    public void standartAutorizationTest(LoginPage loginPage) throws IOException {
        loginPage.login_standard(testHelper.getStandartUser());
    }

    @Test
    @DisplayName("Авторизация под пользователем locked_out")
    @Severity(SeverityLevel.BLOCKER)
    public void lokedAutorizationTest(LoginPage loginPage) throws IOException {
        loginPage.login_locked_out(testHelper.getLockedUser());
    }


    @Test
    @DisplayName("Заказ товаров. Стандартный пользователь")
    @Description("Добавление в корзину трех товаров, проверка суммы заказа под стандартным пользователем")
    @Severity(SeverityLevel.BLOCKER)
    public void standartUserOrderTest(LoginPage loginPage, InventoryPage inventoryPage, CartPage cartPage,
                      CheckoutStepOnePage checkoutStepOnePage, CheckoutStepTwoPage checkoutStepTwoPage) throws IOException {

        loginPage.login_standard(testHelper.getStandartUser());
        buyItemsCheckSum(inventoryPage, cartPage, checkoutStepOnePage, checkoutStepTwoPage);

    }


    @Test
    @DisplayName("Заказ товаров. Заглюченный пользователь")
    @Description("Добавление в корзину трех товаров, проверка суммы заказа под заглюченным пользователем")
    public void glitchUserOrderTest(LoginPage loginPage, InventoryPage inventoryPage, CartPage cartPage,
                                      CheckoutStepOnePage checkoutStepOnePage, CheckoutStepTwoPage checkoutStepTwoPage) throws IOException {

        loginPage.login_standard(testHelper.getGlitchedUser());
        buyItemsCheckSum(inventoryPage, cartPage, checkoutStepOnePage, checkoutStepTwoPage);

    }


    private static void buyItemsCheckSum(InventoryPage inventoryPage, CartPage cartPage,
                                         CheckoutStepOnePage checkoutStepOnePage, CheckoutStepTwoPage checkoutStepTwoPage) throws IOException {


        List<Integer> listItemIndex = Arrays.asList(0, 2, 4);
        double orderSum = inventoryPage.listItemToCart(listItemIndex);

        String orderCount = Integer.toString(listItemIndex.size());
        SelenideElement cartCounter = inventoryPage.getCartCounter();
        cartCounter.shouldHave(text(orderCount));

        inventoryPage.openCart();
        cartPage.getOrderListSize().shouldHave(size(listItemIndex.size()));
        cartPage.goToCheckoutForm();

        checkoutStepOnePage.inputBuyerData();
        checkoutStepTwoPage.checkSumOrder(orderSum);

    }









}


