
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;

public class AddToCart_Test extends BaseClass {

    @Test(priority = 1, groups = {"FT", "SM", "IT"})
    public void verifyAddToCart() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
               
        // Step 1: Open product catalog
        openPage("/catalog");

        // Step 2: Wait for products to load
        wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("[data-testid^='product-card-']")));

        ProductPage product = new ProductPage(driver);

        // Step 3: Add the first available product
        product.clickAddToCart();

        // Step 4: Open cart
        openPage("/cart");

        // Step 5: Wait for cart page
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector("[data-testid='cart-page']")));       

        // Step 6: Wait for cart item
        By cartItems =By.cssSelector("[data-testid^='cart-item-']");
        wait.until(ExpectedConditions.presenceOfElementLocated(cartItems));
       
        // Step 7: Verify product was added
        boolean itemExists =!driver.findElements(cartItems).isEmpty();
  
        Assert.assertTrue(itemExists,"Product was not found in the cart.");
       
        Reporter.log( "PASS: Product was added to the cart successfully.",true);                    
    }
}
