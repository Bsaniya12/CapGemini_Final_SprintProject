
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

        // Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        // Wait for elements
        WebDriverWait wait = new WebDriverWait( driver, Duration.ofSeconds(15));
               
        // Open product catalog
        openPage("/catalog");

        // Select a product
        WebElement product = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid^='product-link-']")));
                      
        String productUrl = product.getAttribute("href");
        Assert.assertNotNull(productUrl,"Product link is not available.");
              
        // Open product details
        driver.get(productUrl);

        // Wait for product name
        WebElement productName = wait.until( ExpectedConditions.visibilityOfElementLocated( By.tagName("h1")));
 
        // Get page information
        String pageText = driver.findElement( By.tagName("body")).getText();
               

        // Verify product name
        Assert.assertTrue(productName.isDisplayed(), "Product name is not displayed.");
              
        Assert.assertFalse(productName.getText().trim().isEmpty(), "Product name is empty.");
               
        // Verify product price
        Assert.assertTrue(pageText.contains("$"), "Product price is not displayed.");
 
        // Verify stock information
        Assert.assertTrue( pageText.toLowerCase().contains("in stock"), "Stock information is not displayed.");
               
        // Print results
        Reporter.log("Product Name: " + productName.getText(),true);
        Reporter.log("Product URL: " + productUrl,true);
        Reporter.log( "PASS: Product details are displayed successfully.",true);            
    }
}
