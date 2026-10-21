
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
import qademo.utility.ExcelUtility;

public class CartRefresh_Test extends BaseClass {

    @Test(groups = {"ST"})
    public void verifyCartItemsAfterRefresh() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      
        // Step 1: Read product name from Excel
        String productName = ExcelUtility.getDataByHeader("TC-04", "ProductName");
        productName = productName.split(";")[0].trim();

        // Step 2: Open catalog
        openPage("/catalog");

        // Step 3: Add product to cart
        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName);

        // Step 4: Open cart
        openPage("/cart");

        // Step 5: Wait for cart page
        wait.until(ExpectedConditions.visibilityOfElementLocated(  By.cssSelector("[data-testid='cart-page']")));
              

        // Step 6: Wait for the product to appear
        By productInCart = By.xpath( "//*[@data-testid='cart-items-list']//*[contains(normalize-space(), '" + productName + "')]");
       
        wait.until(ExpectedConditions.visibilityOfElementLocated(productInCart));

        // Step 7: Record cart item count
        By cartItems = By.cssSelector( "[data-testid='cart-items-list'] > *");
    
        int itemCountBeforeRefresh = driver.findElements(cartItems).size();
        
        Assert.assertTrue(itemCountBeforeRefresh > 0, "Cart should contain a product before refresh.");
               
        // Step 8: Refresh the page
        driver.navigate().refresh();
        
        // Step 9: Wait for cart page to load again
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cart-page']")));  
        
        // Step 10: Verify product remains in cart
        wait.until(ExpectedConditions.visibilityOfElementLocated( productInCart));
        Assert.assertEquals( driver.findElements(cartItems).size(),itemCountBeforeRefresh, "Cart items were not retained after refreshing.");
        Reporter.log( "PASS: Cart item remained after refresh: " + productName,true);
               
    }
}
