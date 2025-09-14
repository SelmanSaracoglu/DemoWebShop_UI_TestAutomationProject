package tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.demo.framework.pages.pages.HomePage;
import com.demo.framework.pages.pages.LoginPage;
import com.demo.framework.core.ConfigReader;
import com.demo.framework.core.Driver;
import com.demo.framework.core.ReusableMethods;

import java.util.Set;

public class T02_HomePage_FooterComponentTests {
    HomePage homePage;
    LoginPage loginPage;


    @BeforeMethod
    public void setup(){
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }


    @Test(priority = 1)
    public void testFooterIsVisible(){
        Assert.assertTrue(homePage.footerBlock.isDisplayed());
    }

    @Test(priority = 2)
    public void testFooterMenuSectionsVisable(){

        int menuSize = homePage.footerMenuSections.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.footerMenuSections.get(i).isDisplayed());
            System.out.println("Check: " + homePage.footerMenuSections.get(i).getAttribute("class"));
        }
    }

    @Test(priority = 3)
    public void testInformationLinksVisible() {
        int menuSize = homePage.informationLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.informationLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.informationLinks.get(i).getText() + " is checked.");
        }
    }

    @Test(priority = 4)
    public void testCustomerServiceLinksVisible() {
        int menuSize = homePage.customerServiceLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.customerServiceLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.customerServiceLinks.get(i).getText() + " is checked.");
        }
    }

    @Test(priority = 5)
    public void testAccountLinksVisible() {
        int menuSize = homePage.myAccountLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.myAccountLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.myAccountLinks.get(i).getText() + " is checked.");
        }
    }

    @Test(priority = 6)
    public void testFollowUsLinksVisible() {
        int menuSize = homePage.followUsLinks.size();
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.followUsLinks.get(i).isDisplayed());
            System.out.println("Checked: " + homePage.informationLinks.get(i).getText());
        }
    }

    @Test(priority = 7)
    public void testInformationLinksNavigation() {
        SoftAssert softAssert = new SoftAssert();

        for (int i = 0; i < homePage.informationLinks.size(); i++) {
            WebElement link = homePage.informationLinks.get(i);
            String linkText = link.getText();

            System.out.println("Clicking on: " + linkText);

            // Tıklama
            link.click();
            ReusableMethods.waitForSeconds(1);

            // Başlık al ve karşılaştır
            String actualTitle = Driver.getDriver().getTitle();

            // Soft assert ile kaydet
            softAssert.assertTrue(actualTitle.toLowerCase().contains(linkText.toLowerCase()),
                    "🔴 '" + linkText + "' linkine tıklandı ama başlık uyuşmuyor! Sayfa başlığı: " + actualTitle);

            // Sayfaya geri dön
            Driver.getDriver().navigate().back();
            homePage = new HomePage(Driver.getDriver());
        }

        // Hataları fırlat
        softAssert.assertAll();
    }

    @Test(priority = 8)
    public void testCustomerServiceLinksNavigation() {
        SoftAssert softAssert = new SoftAssert();

        for (int i = 0; i < homePage.customerServiceLinks.size(); i++) {
            WebElement link = homePage.customerServiceLinks.get(i);
            String linkText = link.getText();

            System.out.println("Clicking on: " + linkText);

            // Tıklama
            link.click();
            ReusableMethods.waitForSeconds(1);

            // Başlık al ve karşılaştır
            String actualTitle = Driver.getDriver().getTitle();

            // Soft assert ile kaydet
            softAssert.assertTrue(actualTitle.toLowerCase().contains(linkText.toLowerCase()),
                    "🔴 '" + linkText + "' linkine tıklandı ama başlık uyuşmuyor! Sayfa başlığı: " + actualTitle);

            // Sayfaya geri dön
            Driver.getDriver().navigate().back();
            homePage = new HomePage(Driver.getDriver());
        }

        // Hataları fırlat
        softAssert.assertAll();
    }

    @Test(priority = 9)
    public void testMyAccountLinksNavigation() {
        SoftAssert softAssert = new SoftAssert();

        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        for (int i = 0; i < homePage.myAccountLinks.size(); i++) {
            WebElement link = homePage.myAccountLinks.get(i);
            String linkText = link.getText();

            System.out.println("Clicking on: " + linkText);

            // Tıklama
            link.click();
            ReusableMethods.waitForSeconds(1);

            // Başlık al ve karşılaştır
            String actualTitle = Driver.getDriver().getTitle();

            // Soft assert ile kaydet
            softAssert.assertTrue(actualTitle.toLowerCase().contains(linkText.toLowerCase()),
                    "🔴 '" + linkText + "' linkine tıklandı ama başlık uyuşmuyor! Sayfa başlığı: " + actualTitle);

            // Sayfaya geri dön
            Driver.getDriver().navigate().back();
            homePage = new HomePage(Driver.getDriver());
        }

        // Hataları fırlat
        softAssert.assertAll();
    }

    @Test(priority = 10)
    public void testFollowUsLinksNavigation() {

        SoftAssert softAssert = new SoftAssert();

        String originalWindow = Driver.getDriver().getWindowHandle();

        for (int i = 0; i < homePage.followUsLinks.size(); i++) {
            WebElement link = homePage.followUsLinks.get(i);
            String linkText = link.getText();

            System.out.println("Clicking on: " + linkText);

            new Actions(Driver.getDriver()).moveToElement(link).perform();
            link.click();
            ReusableMethods.waitForSeconds(2);

            // Tüm açık pencereleri al
            Set<String> windowHandles = Driver.getDriver().getWindowHandles();
            // Yeni pencereye geç
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    Driver.getDriver().switchTo().window(handle);
                    break;
                }
            }

            // Yeni sekmede URL kontrolü
            String newUrl = Driver.getDriver().getCurrentUrl();
            System.out.println("New tab URL: " + newUrl);

            softAssert.assertTrue(
                    newUrl.toLowerCase().contains(linkText.toLowerCase().replace(" ", "")),
                    "'" + linkText + "' linki yanlış sayfaya yönlendi. URL: " + newUrl
            );

            // Yeni sekmeyi kapat
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindow)) {
                    Driver.getDriver().close();
                }else {
                    Driver.getDriver().navigate().back();
                }
            }

            // Ana pencereye geri dön
            Driver.getDriver().switchTo().window(originalWindow);

        }
    }


    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
