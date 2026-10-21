
package qademo.tests.Checkout;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;

public class EmptyCartCheckout_Test extends BaseClass {

    @Test(groups = {"NT"})
    public void verifyCheckoutWithEmptyCart() {

        openPage("/cart");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
               

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cart-page']")));
        boolean isCartEmpty = driver.findElements(By.cssSelector("[data-testid='cart-items-list'] > *")).isEmpty();
                
        Assert.assertTrue(isCartEmpty,"Cart should be empty.");
                

        Reporter.log("PASS: Cart is empty.",true);

        boolean checkoutButtonPresent = driver.findElements( By.xpath("//button[contains(normalize-space(), 'Proceed to Checkout')]"))
        		 .size() > 0;
               
        Assert.assertFalse(checkoutButtonPresent,"Checkout button should not be present for an empty cart.");
        Reporter.log("PASS: Checkout button is not displayed for an empty cart.",true);
                

        // Take screenshot after verifying the empty cart
        saveScreenshot("TC18_EmptyCartCheckout");
    }

    public void saveScreenshot(String testName) {

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    

            String folder = System.getProperty("user.dir") + "/screenshots/";
            Files.createDirectories(Paths.get(folder));
            File destination = new File(folder + testName + ".png");
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);                 
            Reporter.log("Screenshot saved: "+ destination.getAbsolutePath(),true);
                    
        } catch (Exception e) {
        	Reporter.log("Screenshot error: " + e.getMessage(),true);
        }
    }
}