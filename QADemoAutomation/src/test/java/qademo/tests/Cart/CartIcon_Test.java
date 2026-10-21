
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;

public class CartIcon_Test extends BaseClass {

    @Test(groups = {"SM"})
    public void verifyCartOpens() {

        // Step 1: Open cart page
        openPage("/cart");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));       

        // Step 2: Wait for cart page to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector("[data-testid='cart-page']")));
               
        // Step 3: Verify cart URL
        Assert.assertTrue( driver.getCurrentUrl().contains("/cart"), "Cart URL is incorrect.");
                            
        // Step 4: Verify cart page is displayed
        Assert.assertTrue(driver.findElement( By.cssSelector("[data-testid='cart-page']") ).isDisplayed(),
        		 "Shopping Cart page was not displayed.");
        Reporter.log("PASS: Shopping Cart page opened successfully.",true);             
    }
}
