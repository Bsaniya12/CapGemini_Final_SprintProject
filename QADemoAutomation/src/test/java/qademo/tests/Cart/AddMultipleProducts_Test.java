package qademo.tests.Cart;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class AddMultipleProducts_Test extends BaseClass {

    @Test
    public void verifyMultipleProductsCanBeAdded() throws Exception {

        // Read product names from Excel
        String products = ExcelUtility.getDataByHeader("TC-05", "ProductName");
   
        // Open product catalog
        openPage("/catalog");
        ProductPage productPage = new ProductPage(driver);
        

        // Add each product
        String[] productNames = products.split(";");
        for (String productName : productNames) {
            productPage.addProductToCart(productName.trim());
        } 

        Reporter.log("Products added: " + products,true);
        Reporter.log("Multiple products test completed.",true);
    }
}
