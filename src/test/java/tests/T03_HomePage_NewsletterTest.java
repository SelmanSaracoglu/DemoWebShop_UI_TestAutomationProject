package tests;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.demo.framework.pages.pages.HomePage;
import com.demo.framework.core.ConfigReader;
import com.demo.framework.core.Driver;
import com.demo.framework.core.ReusableMethods;

public class T03_HomePage_NewsletterTest {

    HomePage homePage;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        homePage = new HomePage(Driver.getDriver());
    }

    @Test(priority = 1)
    public void testNewsletterVisible() {

        Assert.assertTrue(homePage.newsLetterSignUpBox.isDisplayed());
        Assert.assertTrue(homePage.subscribeButton.isDisplayed());
    }

    @Test(priority = 2)
    public void testValidEmailSubscription(){

        String email = ConfigReader.getProperty("Email");

        homePage.newsLetterSignUpBox.clear();
        homePage.newsLetterSignUpBox.sendKeys(email + Keys.ENTER);
        homePage.subscribeButton.click();

        ReusableMethods.waitForSeconds(2);

        Assert.assertTrue(homePage.subscriptionMessage.getText().toLowerCase().contains("thank you"),
                "Başarılı abonelik mesajı alınamadı!");
    }

    @Test(priority = 3)
    public void testInvalidEmailSubscription() {
        String invaliEmail = ConfigReader.getProperty("invalidemail");

        homePage.newsLetterSignUpBox.clear();
        homePage.newsLetterSignUpBox.sendKeys(invaliEmail + Keys.ENTER);
        homePage.subscribeButton.click();

        ReusableMethods.waitForSeconds(2);

        Assert.assertTrue(homePage.subscriptionMessage.getText().toLowerCase().contains("enter valid email"),
                "Geçersiz e-posta mesajı alınamadı!");

    }

    @Test(priority = 4)
    public void testEmptyEmailSubscription() {
        homePage.newsLetterSignUpBox.click();
        homePage.subscribeButton.click();

        ReusableMethods.waitForSeconds(1);
        Assert.assertTrue(homePage.subscriptionMessage.getText().toLowerCase().contains("enter valid email"),
                "Geçersiz e-posta mesajı alınamadı!");


    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
