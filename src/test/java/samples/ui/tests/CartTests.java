package samples.ui.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import samples.ui.UiTestBase;
import samples.ui.pages.CartPage;
import samples.ui.pages.InventoryPage;
import samples.ui.pages.LoginPage;
import samples.ui.testdata.Users;

@Tag("ui")
class CartTests extends UiTestBase {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";
    private static final String PRODUCT_PRICE = "$29.99";

    @BeforeEach
    void logIn() {
        new LoginPage(driver).open().logIn(Users.STANDARD_USER, Users.PASSWORD);
        wait.until(ExpectedConditions.urlMatches("/inventory\\.html$"));
    }

    @Test
    @DisplayName("A product added from the catalog appears in the cart with the same name, price and quantity 1")
    void addProductToCart_showsProductInCart_withCorrectNameAndPrice() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        assertFalse(inventoryPage.isCartBadgeShown(), "precondition: the cart is empty");
        assertEquals(PRODUCT_PRICE, inventoryPage.priceOf(PRODUCT_NAME), "catalog price");

        inventoryPage.addToCart(PRODUCT_NAME);

        // Expected: the cart badge shows 1 and the product button switches to "Remove".
        assertAll(
                () -> assertEquals("1", inventoryPage.cartBadgeText(), "cart badge"),
                () -> assertEquals("Remove", inventoryPage.cartButtonOf(PRODUCT_NAME).getText(), "product button"));

        inventoryPage.openCart();

        // Expected: the cart contains exactly this product, with the catalog price and quantity 1.
        wait.until(ExpectedConditions.urlMatches("/cart\\.html$"));
        assertEquals("Your Cart", cartPage.title(), "page title");
        List<WebElement> items = cartPage.items();
        assertEquals(1, items.size(), "number of items in the cart");

        WebElement item = items.get(0);
        assertAll(
                () -> assertEquals(PRODUCT_NAME, cartPage.nameOf(item), "item name"),
                () -> assertEquals(PRODUCT_PRICE, cartPage.priceOf(item), "item price"),
                () -> assertEquals("1", cartPage.quantityOf(item), "item quantity"));
    }
}
