
package qademo.tests.Cart;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.CartPage;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class IncreaseQuantity extends BaseClass {

    @Test(priority = 4, groups = {"FT"})
    public void verifyIncreaseQuantity() throws Exception {

        // Read product name from Excel
        String productName = ExcelUtility.getDataByHeader("TC-06", "ProductName");
                

        // Open catalog and add the selected product
        openPage("/catalog");

        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName);

        // Open cart
        openPage("/cart");
        CartPage cart = new CartPage(driver);

        // Read initial quantity
        int quantityBefore = cart.getFirstProductQuantity();

        // Increase quantity
        cart.increaseFirstProductQuantity();

        // Read updated quantity
        int quantityAfter = cart.getFirstProductQuantity();

        // Verify quantity increased by one
        Assert.assertEquals( quantityAfter,quantityBefore + 1,"Product quantity did not increase correctly.");
    
        Reporter.log("PASS: Quantity increased for " + productName,true);
                
     }
}
