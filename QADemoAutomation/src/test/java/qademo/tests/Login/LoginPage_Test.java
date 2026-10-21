
package qademo.tests.Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;

public class LoginPage_Test extends BaseClass {

    @Test(groups = {"SM"})
    public void verifyLoginPageOpens() {

        openPage("/login");

        Assert.assertTrue( driver.getCurrentUrl().contains("/login"), "Login page did not open.");
        Reporter.log("PASS: Login page opened successfully.",true);
    }
}
