
package qademo.tests.Login;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;

public class ValidLogin_Test extends BaseClass {

    @Test(groups = {"SM"})
    public void verifyValidLogin() {

        WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(15));      
        // Step 1: Open login page
        openPage("/login");

        // Step 2: Enter login details
        LoginPage login = new LoginPage(driver);

        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        // Step 3: Wait for catalog page
        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Step 4: Verify login
        Assert.assertTrue( driver.getCurrentUrl().contains("/catalog"),"Login failed.");
        Reporter.log("PASS: Login successful.",true);
    }
}
