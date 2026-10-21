
package qademo.objrepo;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage {

    WebDriver driver;

    // Order confirmation page
    @FindBy(css = "[data-testid='order-confirmation-heading']")
    WebElement confirmationHeading;

    @FindBy(css = "[data-testid='order-confirmation-number']")
    WebElement orderNumber;

    // Order details
    @FindBy(xpath = "//*[normalize-space()='Pending']")
    WebElement orderStatus;

    @FindBy(css = "[data-testid='order-items-heading']")
    WebElement orderItemsHeading;

    @FindBy(xpath = "//*[contains(text(),'$')]")
    List<WebElement> orderAmounts;

    // My Orders link
    @FindBy(css = "a[href='/orders']")
    WebElement myOrdersLink;

    // Constructor
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Check confirmation page
    public boolean isConfirmationDisplayed() {
        return confirmationHeading.isDisplayed();
    }

    // Get confirmation heading
    public String getConfirmationHeading() {
        return confirmationHeading.getText();
    }

    // Get order number
    public String getOrderNumber() {
        return orderNumber.getText();
    }

    // Check order status
    public String getOrderStatus() {
        return orderStatus.getText();
    }

    // Check order items section
    public boolean isOrderItemsDisplayed() {
        return orderItemsHeading.isDisplayed();
    }

    // Get order amount
    public String getOrderAmount() {
        for (WebElement amount : orderAmounts) {
            if (amount.getText().contains("$")) {
                return amount.getText();
            }
        }

        return "";
    }

    // Open My Orders
    public void clickMyOrders() {
        myOrdersLink.click();
    }
}
