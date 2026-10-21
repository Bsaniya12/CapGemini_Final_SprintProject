
package qademo.tests.Order;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;

public class OrderHistory_Test extends BaseClass {

    @Test(groups = {"SM"})
    public void verifyOrderHistory() {

        // Open login page
        openPage("/login");

        // Login using config.properties
        LoginPage login = new LoginPage(driver);

        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        // Wait for successful login
        WebDriverWait wait = new WebDriverWait( driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Open Order page
        openPage("/orders");

        // Wait for Order page
        wait.until(ExpectedConditions.urlContains("/orders"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'My Orders')]")));

        // Get page content
        String pageText = driver.findElement( By.tagName("body")).getText();

               
        // Verify order history
        Assert.assertTrue( pageText.contains("My Orders"),"My Orders page is not displayed.");
        Reporter.log("PASS: Order history page displayed successfully.",true);
        Reporter.log("Page URL: " + driver.getCurrentUrl(),true);
    }
}
