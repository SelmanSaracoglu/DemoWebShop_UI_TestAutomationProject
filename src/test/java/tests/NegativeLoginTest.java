package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.demo.framework.pages.HomePage;
import com.demo.framework.pages.LoginPage;
import com.demo.framework.core.ConfigReader;
import com.demo.framework.core.Driver;

public class NegativeLoginTest {

    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @Test
    public void testLoginWithWrongEmailWrongPAssword() {
        homePage.loginLink.click();

        String wrongEmail = ConfigReader.getProperty("wrongEmail");
        String wrongpassword = ConfigReader.getProperty("wrongPassword");

        loginPage.emailInput.sendKeys(wrongEmail);
        loginPage.passwordInput.sendKeys(wrongpassword);
        loginPage.loginButton.click();

        Assert.assertTrue(loginPage.loginErrorMessage
                .isDisplayed(), "Hata mesajı görünmedi!");
        Assert.assertTrue(loginPage.loginErrorMessage
                .getText()
                .contains("Login was unsuccessful"), "Beklenen hata mesajı alınmadı.");
    }
    @Test
    public void testLoginWithInvalidEmail() {
        homePage.loginLink.click();

        String invalidemail = ConfigReader.getProperty("invalidemail");
        String wrongpassword = ConfigReader.getProperty("wrongPassword");

        loginPage.emailInput.sendKeys(invalidemail);
        loginPage.passwordInput.sendKeys(wrongpassword);


        Assert.assertTrue(loginPage.invalidMailErrorMessage
                .isDisplayed(), "Hata mesajı görünmedi!");
        Assert.assertTrue(loginPage.invalidMailErrorMessage
                .getText()
                .contains("Please enter a valid email address."), "Beklenen hata mesajı alınmadı.");
    }
    @Test
    public void testLoginWithNonExistingAccount() {
        homePage.loginLink.click();

        String nonExistingAccount = ConfigReader.getProperty("nonExistingAccount");
        String nonExistingPassword = ConfigReader.getProperty("nonExistingPassword");

        loginPage.emailInput.sendKeys(nonExistingAccount);
        loginPage.passwordInput.sendKeys(nonExistingPassword);
        loginPage.loginButton.click();

        Assert.assertTrue(loginPage.nonExistingAccountErrorMessage
                .isDisplayed(), "Hata mesajı görünmedi!");
        Assert.assertTrue(loginPage.nonExistingAccountErrorMessage
                .getText()
                .contains("No customer account found"), "Beklenen hata mesajı alınmadı.");
    }
    @Test
    public void testLoginWithCorrectEmailWrongPassword() {
        homePage.loginLink.click();

        String Email = ConfigReader.getProperty("Email");
        String nonExistingPassword = ConfigReader.getProperty("nonExistingPassword");

        loginPage.emailInput.sendKeys(Email);
        loginPage.passwordInput.sendKeys(nonExistingPassword);
        loginPage.loginButton.click();

        Assert.assertTrue(loginPage.incorrectCredentialsErrorMessage
                .isDisplayed(), "Hata mesajı görünmedi!");
        Assert.assertTrue(loginPage.incorrectCredentialsErrorMessage
                .getText()
                .contains("The credentials provided are incorrect"), "Beklenen hata mesajı alınmadı.");
    }



    @AfterMethod
    public void tearDownClass() {
        Driver.closeDriver();
    }
}
