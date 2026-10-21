
package qademo.objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "[data-testid='signup-form'] input[type='email']")
    WebElement email;

    @FindBy(css = "[data-testid='signup-form'] input[type='tel']")
    WebElement phone;

    @FindBy(css = "[data-testid='username-input']")
    WebElement username;

    @FindBy(css = "[data-testid='signup-form'] input[type='password']")
    List<WebElement> passwordFields;

    @FindBy(css = "[data-testid='signup-submit-button']")
    WebElement createAccount;

    @FindBy(css = "[role='alert']")
    WebElement errorMessage;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String value) {
        wait.until(ExpectedConditions.visibilityOf(email)).sendKeys(value);
    }

    public void enterPhone(String value) {
        wait.until(ExpectedConditions.visibilityOf(phone)).sendKeys(value);
    }

    public void enterUsername(String value) {
        wait.until(ExpectedConditions.visibilityOf(username)).sendKeys(value);
    }

    public void enterPassword(String value) {
        wait.until(ExpectedConditions.visibilityOfAllElements(passwordFields));
        passwordFields.get(0).sendKeys(value);
    }

    public void enterConfirmPassword(String value) {
        wait.until(ExpectedConditions.visibilityOfAllElements(passwordFields));
        passwordFields.get(1).sendKeys(value);
    }

    public void clickCreateAccount() {
        wait.until(ExpectedConditions.visibilityOf(createAccount));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                createAccount);

        if (createAccount.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", createAccount);

            System.out.println("Clicked Create Account");
        } else {
            throw new RuntimeException(
                    "Create Account button is disabled. Check all signup fields.");
        }
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage))
                .getText();
    }
}
