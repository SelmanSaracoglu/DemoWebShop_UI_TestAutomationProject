package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.demo.framework.pages.pages.CartPage;
import com.demo.framework.pages.pages.HomePage;
import com.demo.framework.pages.pages.LoginPage;
import com.demo.framework.pages.pages.ProductPage;
import com.demo.framework.core.ConfigReader;
import com.demo.framework.core.Driver;
import com.demo.framework.core.ReusableMethods;

public class T06_HomePage_FeaturedProductsTest {


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

    @Test (priority = 1)
    public void testFeaturedProductsTitleVisible(){
        Assert.assertTrue(homePage.featuredProductTitle.isDisplayed());
    }

    @Test(priority = 2)
    public void testFeaturedProductsVisible() {

        Assert.assertTrue(ReusableMethods.isListNotEmpty(homePage.featuredProducts));
        for (int i = 0; i < homePage.featuredProducts.size(); i++) {
            WebElement product = homePage.featuredProducts.get(i);
            System.out.println(product.getText());
        }

        System.out.println("✅ Tüm featured ürünler görünür durumda.");
    }

    @Test(priority = 3)
    public void testEachFeaturedProductElementsVisible() {

        for (int i = 0; i < homePage.featuredProducts.size(); i++) {
            WebElement image = homePage.featuredProductImages.get(i);
            WebElement title = homePage.featuredProductTitles.get(i);
            WebElement price = homePage.featuredProductPrices.get(i);
            WebElement cartButton = homePage.featuredAddToCartButtons.get(i);


            Assert.assertTrue(image.isDisplayed(), "❌ Ürün görseli görünmüyor.");
            Assert.assertTrue(title.isDisplayed(), "❌ Ürün başlığı görünmüyor.");
            Assert.assertTrue(price.isDisplayed(), "❌ Ürün fiyatı görünmüyor.");
            Assert.assertTrue(cartButton.isEnabled(), "❌ Sepete ekle butonu görünmüyor.");
        }

        System.out.println("✅ Her ürün bileşeni (resim, başlık, fiyat, buton) görünür.");

    }

    @Test(priority = 4)
    public void testFeaturedProductTitleNavigatesToDetailPage() {

        WebElement firstProduct = homePage.featuredProducts.get(0);
        String expectedTitle = firstProduct.getText().trim();

        System.out.println(expectedTitle);

        firstProduct.click();
        ReusableMethods.waitForSeconds(1);

        String actualTitle = Driver.getDriver().getTitle();

        Assert.assertTrue(actualTitle.toLowerCase().contains(expectedTitle.toLowerCase()),
                "Ürün detay sayfası açılmadı. Beklenen: " + expectedTitle + " | Gerçek: " + actualTitle);

    }


    @AfterClass
    public void tearDown() {
        Driver.closeDriver();
    }

}
