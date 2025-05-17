package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class RemoveToCartTest {

    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        productPage = new ProductPage(Driver.getDriver());
        cartPage =new CartPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));

    }

    @Test
    public void RemoveFromCart(){
        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        // 1. Login
        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        // 2. Ürünü sepete ekle ve sepete git
        homePage.categories.get(1).click();
        productPage.productList.get(1).click();
        productPage.AddtoCart.click();

        Assert.assertTrue(productPage.addtoCartSuccessMessage.isDisplayed());
        homePage.shoppingCartLink.click();

        // 3. Sepette ürün var mı?
        Assert.assertTrue(ReusableMethods.isListNotEmpty(cartPage.cartItems));

        // 4. Ürünü sil + sepeti güncelle
        cartPage.removeItems.click();
        cartPage.updateShoppingCardButton.click();

        // 5. Sepetin boş olduğunu doğrula
        Assert.assertTrue(cartPage.cartIsEmptyMessage.isDisplayed(), "Sepet boş değil!");

    }

    @AfterMethod
    public void tearDownClass() {
        Driver.closeDriver();
    }

}
