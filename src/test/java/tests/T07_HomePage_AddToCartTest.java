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

public class T07_HomePage_AddToCartTest {

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

    @Test(priority = 1)
    public void testAddMultipleProductsToCart() {
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
        homePage.categories.get(0).click();
        productPage.productList.get(2).click();
        productPage.addtoCart.click();
        homePage.categories.get(0).click();
        productPage.productList.get(4).click();
        productPage.addtoCart.click();

        homePage.shoppingCartLink.click();

        Assert.assertEquals(cartPage.cartItems.size(), 3,"Sepette 3 ürün yok!");

        // Her ürünün fiyatını ve miktarını kontrol et
        for (int i = 0; i < 3; i++) {
            double unitPrice =
                    Double.parseDouble(cartPage.itemUnitPrices
                            .get(i).getText().replace("$", ""));
            int quatity = Integer.parseInt(cartPage.itemQuantities
                    .get(i).getAttribute("value"));
            double subtotal =
                    Double.parseDouble(cartPage.itemSubtotals
                            .get(i).getText().replace("$", ""));

            double expectedSubtotal = unitPrice*quatity;
            Assert.assertEquals(subtotal, expectedSubtotal, 0.01,
                    "Ürün " + (i + 1) + " için subtotal yanlış!");
        }
        System.out.println("✅ Uc ürün sepete eklendi ve fiyatlar doğru hesaplandı.");
    }

    @Test(priority = 2)
    public void testUpdateCartQuantity() {
        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.shoppingCartLink.click();

        double unitPrice = Double.parseDouble(
                cartPage.itemUnitPrices
                        .get(0).getText().replace("$", ""));

        WebElement quantityInput = cartPage.itemQuantities.get(0);
        quantityInput.clear();
        quantityInput.sendKeys("2");

        cartPage.updateShoppingCartButton.click();
        ReusableMethods.waitForSeconds(1);

        double subtotal = Double.parseDouble(
                cartPage.itemSubtotals
                        .get(0).getText().replace("$", ""));

        double expectedSubtotal= 2*unitPrice;
        Assert.assertEquals(subtotal, expectedSubtotal, 0.01,
                "Subtotal yanlış! Beklenen: $" + expectedSubtotal + ", Gerçek: $" + subtotal);

        System.out.println("✅ Quantity 2 olarak güncellendi, fiyat doğru hesaplandı.");

    }

    @Test(priority = 3)
    public void testRemoveItemFromCart() {

        homePage.loginLink.click();

        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.shoppingCartLink.click();
        int size = cartPage.cartItems.size();
        Assert.assertTrue(size > 0, "Sepette hiç ürün yok, silinecek ürün bulunamadı!");

        //WebElement quantity = cartPage.itemQuantities.get(0);
        //int quantityValue = Integer.parseInt(quantity.getAttribute("value"));

        cartPage.removeItems.get(0).click();

        cartPage.updateShoppingCartButton.click();
        ReusableMethods.waitForSeconds(1);

        int updatedSize = cartPage.cartItems.size();

        if (updatedSize == 0){
            Assert.assertTrue(cartPage.cartIsEmptyMessage.isDisplayed(),
                    "Sepet boş ama 'sepet boş' mesajı gösterilmedi.");
            System.out.println("Sepet tamamen boşaltıldı.");
        }else {
            Assert.assertEquals(updatedSize,size-1,
                    "Ürün silinmedi! Beklenen boyut: " + (size-1) + ", Gerçek: " + updatedSize);
            System.out.println("Ürün silindi. Sepette kalan ürün sayısı: " + updatedSize);
        }


    }

    @Test(priority = 4)
    public void testSameItemAddedTwice_IncreasesQuantity() {

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

        Driver.getDriver().navigate().back();
        Driver.getDriver().navigate().back();

        homePage.categories.get(0).click();
        productPage.productList.get(0).click();
        productPage.addtoCart.click();

        // Sepete git
        homePage.shoppingCartLink.click();
        ReusableMethods.waitForSeconds(2);

        Assert.assertEquals(cartPage.cartItems.size(),1,
                "Aynı ürün ayrı satırlar halinde eklendi!");

        String quantity = cartPage.itemQuantities.get(0).getAttribute("value");

        Assert.assertEquals(quantity,"2","Aynı üründen 2 adet eklenmedi!");

        System.out.println("✅ Aynı ürün tekrar eklenince miktar 2 oldu, satır birikmesi yok.");

    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
