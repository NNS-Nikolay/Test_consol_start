package pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import component.InventoryItemComponent;
import helpers.TestHelper;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class InventoryPage {

    private final ElementsCollection inventoryElements = $(".inventory_list").$$(".pricebar");
    private final SelenideElement cartConter = $(".shopping_cart_link");
    public static TestHelper testHelper;

    @Step("Получить список товаров")
    public List<InventoryItemComponent> getInventoryItemComponents(){

        List<InventoryItemComponent> inventoryItemComponents = new ArrayList<>();
        inventoryElements.forEach(inventoryElement -> inventoryItemComponents.add(new InventoryItemComponent(inventoryElement)));
        return inventoryItemComponents;
    }

    @Step("Помещение списка товаров в корзину")
    public double listItemToCart(List<Integer> itemsToCart){

        testHelper = new TestHelper();

        double totalSum = 0;
        List<InventoryItemComponent> inventoryComponents = getInventoryItemComponents();

        for (Integer itemIndex : itemsToCart) {
            InventoryItemComponent inventoryComponent = inventoryComponents.get(itemIndex);
            double itemPrice = testHelper.getPrice(inventoryComponent.getPrice().getText());
            inventoryComponent.findButton().click();
            totalSum = totalSum + itemPrice;
        }
        return totalSum;
    }

    @Step("Получение счетчика корзины")
    public SelenideElement getCartCounter(){
        return cartConter;
    }

    @Step("Открытие страницы корзины")
    public void openCart(){
        cartConter.click();
    }









}
