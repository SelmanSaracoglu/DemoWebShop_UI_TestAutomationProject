package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.RegisterPage;
import utilities.ConfigReader;
import utilities.Driver;

public class AddToCartTest {

    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        productPage = new ProductPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));


    }

    @Test
    public void testLoginfromProperties() {

        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");
    }

    @Test
    public void testAddBookToCartAfterLogin(){
        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");


        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.booksCategory.click();
        productPage.computingAndInternetBook.click();
        productPage.AddtoCart.click();

        Assert.assertTrue(productPage.AddtoCartSuccessMessage.isDisplayed());
        Assert.assertTrue(productPage.AddtoCartSuccessMessage.getText().contains("added to your shopping cart"));
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
