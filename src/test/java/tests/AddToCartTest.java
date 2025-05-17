package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.Random;

public class AddToCartTest {

    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        productPage = new ProductPage(Driver.getDriver());
        cartPage = new CartPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @Ignore
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

    @Ignore
    @Test
    public void testAddBookToCartAfterLogin(){
        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.categories.get(0).click();
        productPage.productList.get(0).click();
        productPage.addtoCart.click();

        Assert.assertTrue(productPage.addtoCartSuccessMessage.isDisplayed());
        Assert.assertTrue(productPage.addtoCartSuccessMessage.getText().contains("added to your shopping cart"));
    }

    @Test
    public void E05_AddRandomProductToCart(){

        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        // 2. Random ana kategori seç
        Random random = new Random();
        int index = random.nextInt(homePage.categories.size());
        WebElement selectedCategory = homePage.categories.get(index);
        selectedCategory.click();
        ReusableMethods.waitForSeconds(1);

        // 3. Eğer alt kategori varsa, random bir alt kategori seç
        if (!homePage.subCategories.isEmpty()){
            int subIndex = random.nextInt(homePage.subCategories.size());
            WebElement selectedSubCategory = homePage.subCategories.get(subIndex);
            selectedSubCategory.click();
            ReusableMethods.waitForSeconds(1);
        }

        // 4. Random ürün seç ve detay sayfasına git
        Assert.assertTrue(ReusableMethods.isListNotEmpty(homePage.categories)
                , "Bu kategoride ürün bulunamadı!");

        Actions actions = new Actions(Driver.getDriver());
        productPage.clickRandomProduct();
        ReusableMethods.waitForSeconds(1);

        // 5. "Add to Cart" butonu varsa ürünü sepete ekle, yoksa log yaz ve testi bitir
        if (ReusableMethods.isListNotEmpty(productPage.addToCartButtons)) {
            productPage.addtoCart.click();
            Assert.assertTrue(productPage.addtoCartSuccessMessage.isDisplayed(), "Başarı mesajı görünmedi!");
            Assert.assertTrue(productPage.addtoCartSuccessMessage.getText().contains("added to your shopping cart"));
        } else {
            System.out.println("Seçilen üründe 'Add to Cart' butonu bulunmuyor. Test başarısız sayılmadan durduruldu.");
            return; // Test fail olmaz, sadece o ürünle devam etmez
        }

        // 6. Başarı mesajını doğrula
        Assert.assertTrue(productPage.addtoCartSuccessMessage.isDisplayed()
                , "Başarı mesajı görünmedi!");
        Assert.assertTrue(productPage.addtoCartSuccessMessage.getText().contains("added to your shopping cart"));
    }

    @Test(dependsOnMethods = "E05_AddRandomProductToCart")
    public void E06_CheckoutAfterCartTest (){

        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.shoppingCartLink.click();

        // Sepette ürün var mı?
        Assert.assertTrue(ReusableMethods.isListNotEmpty(cartPage.cartItems)
                , "Sepet Bos!");

        // Checkout işlemleri
        Assert.assertTrue(cartPage.checkoutButton.isDisplayed()
                , "Checkout butonu görünmüyor!");
        cartPage.termsOfServiceRadioButton.click();
        cartPage.checkoutButton.click();
        ReusableMethods.waitForSeconds(1);

        Assert.assertTrue(cartPage.checkOutTitle.isDisplayed());

    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
