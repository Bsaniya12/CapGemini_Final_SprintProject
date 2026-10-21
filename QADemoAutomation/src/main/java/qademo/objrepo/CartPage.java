
package qademo.objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "[data-testid='cart-page']")
    WebElement cartPage;

    @FindBy(css = "[data-testid='cart-items-list']")
    WebElement cartItemsList;

    @FindBy(css = "[data-testid^='cart-item-remove-']")
    WebElement removeButton;

    @FindBy(css = "[data-testid^='cart-item-increase-']")
    WebElement increaseButton;

    @FindBy(css = "[data-testid^='cart-item-decrease-']")
    WebElement decreaseButton;

    @FindBy(css = "[data-testid^='cart-item-quantity-']")
    WebElement quantity;

    @FindBy(css = "[data-testid='clear-cart-button']")
    WebElement clearCartButton;

    @FindBy(css = "[data-testid='continue-shopping-link-button']")
    WebElement continueShoppingButton;

    @FindBy(css = "[data-testid='proceed-to-checkout-button']")
    WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public boolean isCartPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(cartPage)).isDisplayed();
    }

    public int getCartItemCount() {
        wait.until(ExpectedConditions.visibilityOf(cartPage));

        return driver.findElements(
                By.cssSelector("[data-testid^='cart-item-'][role='listitem']")
        ).size();
    }

    public void removeFirstProduct() {
        clickElement(removeButton);
    }

    public boolean isCartEmpty() {
        wait.until(ExpectedConditions.visibilityOf(cartPage));
        return getCartItemCount() == 0;
    }


public int getFirstProductQuantity() {
    By quantity = By.cssSelector(
            "[data-testid^='cart-item-quantity-']");

    return Integer.parseInt(
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(quantity))
                    .getText().trim());
}


    public void increaseFirstProductQuantity() {
        clickElement(increaseButton);
    }

    public void decreaseFirstProductQuantity() {
        clickElement(decreaseButton);
    }

    public void clearCart() {
        clickElement(clearCartButton);
    }

    public void clickContinueShopping() {
        By continueShoppingButton = By.cssSelector(
                "[data-testid='continue-shopping-link-button']");

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueShoppingButton)).click();
    }

    public void clickCheckout() {
        clickElement(checkoutButton);
    }

    private void clickElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
