
package qademo.tests.Order;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;

public class Order_Refresh_Test extends BaseClass {

    @Test(groups = {"FT"})
    public void verifyOrderRemainsAfterRefresh() {

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        // Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Open My Orders page
        openPage("/orders");

        wait.until(ExpectedConditions.urlContains("/orders"));

        By orderNumber = By.xpath("//*[contains(normalize-space(text()),'Order #')]");
                

        // Find the first order displayed
        WebElement order = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumber));               
        String actualOrder = order.getText().trim();

        Assert.assertFalse(actualOrder.isEmpty(),"No order number was found.");
        Reporter.log("Order found: " + actualOrder,true);

        // Refresh the page
        driver.navigate().refresh();

        wait.until(ExpectedConditions.urlContains("/orders"));

        // Verify the same order is displayed after refresh
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumber));

        String pageText = driver.findElement( By.tagName("body")).getText();

               
        Assert.assertTrue( pageText.contains(actualOrder),"The order was not displayed after refresh.");

        Reporter.log( "PASS: " + actualOrder + " is still displayed after refresh.",true);
               
    }
}