package samples.ui.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** The product catalog shown after login. */
public class InventoryPage {

    private static final By TITLE = By.cssSelector("[data-test='title']");
    private static final By PRODUCTS = By.cssSelector("[data-test='inventory-item']");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    private final WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String title() {
        return driver.findElement(TITLE).getText();
    }

    public List<WebElement> products() {
        return driver.findElements(PRODUCTS);
    }

    public String priceOf(String productName) {
        return product(productName).findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
    }

    public WebElement cartButtonOf(String productName) {
        return product(productName).findElement(By.tagName("button"));
    }

    public void addToCart(String productName) {
        cartButtonOf(productName).click();
    }

    public boolean isCartBadgeShown() {
        return !driver.findElements(CART_BADGE).isEmpty();
    }

    public String cartBadgeText() {
        return driver.findElement(CART_BADGE).getText();
    }

    public void openCart() {
        driver.findElement(CART_LINK).click();
    }

    private WebElement product(String productName) {
        return driver.findElements(PRODUCTS).stream()
                .filter(p -> p.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product not found in the catalog: " + productName));
    }
}
