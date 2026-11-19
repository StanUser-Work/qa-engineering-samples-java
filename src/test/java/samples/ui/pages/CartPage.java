package samples.ui.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** The shopping cart page. */
public class CartPage {

    private static final By TITLE = By.cssSelector("[data-test='title']");
    private static final By ITEMS = By.cssSelector("[data-test='inventory-item']");

    private final WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String title() {
        return driver.findElement(TITLE).getText();
    }

    public List<WebElement> items() {
        return driver.findElements(ITEMS);
    }

    public String nameOf(WebElement item) {
        return item.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText();
    }

    public String priceOf(WebElement item) {
        return item.findElement(By.cssSelector("[data-test='inventory-item-price']")).getText();
    }

    public String quantityOf(WebElement item) {
        return item.findElement(By.cssSelector("[data-test='item-quantity']")).getText();
    }
}
