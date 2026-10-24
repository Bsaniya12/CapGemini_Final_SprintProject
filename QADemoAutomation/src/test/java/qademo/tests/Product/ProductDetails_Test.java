
package qademo.tests.Product;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;

public class ProductDetails_Test extends BaseClass {

    @Test(groups = {"FT"})
    public void verifyProductDetails() {

        System.out.println("Driver before test: " + driver);

        // Step 1: Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Step 2: Open product catalog
        openPage("/catalog");

        // Step 3: Select a product
        WebElement product = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-testid^='product-link-']")));

        String productUrl = product.getAttribute("href");

        Assert.assertNotNull(
                productUrl, "Product link is not available.");

        // Step 4: Open product details
        driver.get(productUrl);

        // Step 5: Verify product name
        WebElement productName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.tagName("h1")));

        Assert.assertTrue(
                productName.isDisplayed(),
                "Product name is not displayed.");

        Assert.assertFalse(
                productName.getText().trim().isEmpty(),
                "Product name is empty.");

        // Step 6: Verify product price
        String pageText = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue(
                pageText.contains("$"),
                "Product price is not displayed.");

        // Step 7: Verify stock information
        String lowerPageText = pageText.toLowerCase();

        boolean stockDisplayed =
                lowerPageText.contains("in stock")
                || lowerPageText.contains("out of stock")
                || lowerPageText.contains("low stock")
                || lowerPageText.contains("stock");

        System.out.println("Product: " + productName.getText());
        System.out.println("Product URL: " + productUrl);
        System.out.println("Stock information displayed: " + stockDisplayed);

        Assert.assertTrue(
                stockDisplayed,
                "Stock information is not displayed.");

        // Step 8: Print results
        Reporter.log("Product Name: " + productName.getText(), true);
        Reporter.log("Product URL: " + productUrl, true);
        Reporter.log(
                "PASS: Product details are displayed successfully.",
                true);
    }
}
