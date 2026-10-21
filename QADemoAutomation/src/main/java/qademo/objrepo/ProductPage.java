
package qademo.objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    By productGrid =
            By.cssSelector("[data-testid='product-grid']");

    By productCards =
            By.cssSelector("[data-testid^='product-card-']");

    By addButtons =
            By.cssSelector("button[data-testid^='product-add-to-cart-']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickBrowseProducts() {
        driver.get("https://qademo.com/catalog");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));
    }

    public boolean isProductPageDisplayed() {
        return driver.getCurrentUrl().contains("/catalog")
                && !driver.findElements(productGrid).isEmpty();
    }

    // Add the first available product
    public void clickAddToCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));

        List<WebElement> buttons = driver.findElements(addButtons);

        for (WebElement button : buttons) {
            if (button.isEnabled()
                    && button.getAttribute("disabled") == null) {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        button);

                try {
                    wait.until(ExpectedConditions.elementToBeClickable(
                            button)).click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", button);
                }

                System.out.println("Product added to cart.");
                return;
            }
        }

        throw new RuntimeException("No available product found.");
    }

    // Add a specific product
    public void addProductToCart(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));

        List<WebElement> cards = driver.findElements(productCards);

        for (WebElement card : cards) {
            List<WebElement> headings =
                    card.findElements(By.cssSelector("h2, h3, h4"));

            for (WebElement heading : headings) {
                if (heading.getText().trim()
                        .equalsIgnoreCase(productName.trim())) {

                    List<WebElement> buttons =
                            card.findElements(addButtons);

                    if (!buttons.isEmpty()) {
                        WebElement button = buttons.get(0);

                        if (button.isEnabled()
                                && button.getAttribute("disabled") == null) {

                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({block:'center'});",
                                    button);

                            try {
                                wait.until(
                                        ExpectedConditions.elementToBeClickable(
                                                button)).click();
                            } catch (ElementClickInterceptedException e) {
                                ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].click();", button);
                            }

                            System.out.println(
                                    "Added product: " + productName);
                            return;
                        }
                    }

                    throw new RuntimeException(
                            "Product is unavailable: " + productName);
                }
            }
        }

        throw new RuntimeException(
                "Product not found: " + productName);
    }

    public String getCartCount() {
        By cartCount = By.cssSelector(
                "[data-testid='cart-count'], "
                + "[data-testid='cart-badge']");

        List<WebElement> elements =
                driver.findElements(cartCount);

        if (elements.isEmpty()) {
            return "";
        }

        return elements.get(0).getText().trim();
    }

    public boolean areProductsDisplayed() {
        return !driver.findElements(productCards).isEmpty();
    }
}
