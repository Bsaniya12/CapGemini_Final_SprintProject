package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class ClearCart_Test extends BaseClass {

    @Test(groups = {"FT"})
    public void verifyClearCart() throws Exception {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 1: Read product name from Excel
        String productName = ExcelUtility.getDataByHeader(
                "TC-09", "ProductName");

        // Step 2: Select the first product name
        productName = productName.split(";")[0].trim();

        // Step 3: Open catalog
        openPage("/catalog");

        // Step 4: Add product to cart
        ProductPage product = new ProductPage(driver);product.addProductToCart(productName);
        
        // Step 5: Open cart
        openPage("/cart");

        // Step 6: Wait for cart page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector("[data-testid='cart-page']")));
               
        // Step 7: Locate the cart items list
        By cartItemsList =By.cssSelector("[data-testid='cart-items-list']");
                

        wait.until(ExpectedConditions.visibilityOfElementLocated( cartItemsList));

               
        // Step 8: Verify the product is present
        By productInCart = By.xpath("//*[@data-testid='cart-items-list']//*[contains(normalize-space(), '"  + productName + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productInCart));
         
        System.out.println("PASS: Product is present: " + productName);

        // Step 9: Click Clear Cart
        By clearCartButton = By.xpath( "//button[contains(normalize-space(), 'Clear Cart')]");
 
        wait.until(ExpectedConditions.elementToBeClickable(clearCartButton)).click();               

        // Step 10: Verify the cart is empty
        By cartItemCards = By.cssSelector("[data-testid='cart-items-list'] > *");
                
        wait.until(d -> d.findElements(cartItemCards).isEmpty());

        Assert.assertEquals(driver.findElements(cartItemCards).size(), 0,"Cart should be empty after clicking Clear Cart.");
          
        Reporter.log( "PASS: Cart cleared successfully for " + productName,true);
               
    }
}
