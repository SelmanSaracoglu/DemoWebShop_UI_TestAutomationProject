package com.demo.framework.pages.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class HomePage {

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@alt='Tricentis Demo Web Shop']")
    public WebElement homePageLogo;

    @FindBy(linkText = "Register")
    public WebElement registerLink;
    @FindBy(linkText = "Log in")
    public WebElement loginLink;
    @FindBy(linkText = "Log out")
    public WebElement logoutLink;

    @FindBy(linkText = "Shopping cart")
    public WebElement shoppingCartLink;

    @FindBy(linkText = "Wishlist")
    public WebElement wishListLink;


    @FindBy(xpath = "//input[@id='small-searchterms']")
    public WebElement searchBox;
    @FindBy(xpath = "//input[@value='Search']")
    public WebElement searchButton;


    @FindBy(xpath = "//ul[@class='top-menu']//a[not(ancestor::ul[@class='sublist firstLevel'])]")
    public List<WebElement> categories;

    @FindBy(xpath = "//div[@class='sub-category-item']")
    public List<WebElement> subCategories;

    @FindBy(xpath = "//ul[@class='top-menu']//a[contains(text(), 'Desktops')]")
    public WebElement desktopsLink;
    @FindBy(xpath = "//ul[@class='top-menu']//a[contains(text(), 'Notebooks')]")
    public WebElement notebooksLink;
    @FindBy(xpath = "//ul[@class='top-menu']//a[contains(text(), 'Accessories')]")
    public WebElement accessoriesLink;

    public List<WebElement> getComputersSubmenuLinks() {
        return List.of(desktopsLink, notebooksLink, accessoriesLink);
    }

    @FindBy(xpath = "//ul[@class='top-menu']//a[contains(text(), 'Camera, photo')]")
    public WebElement cameraPhotoLink;
    @FindBy(xpath = "//ul[@class='top-menu']//a[contains(text(), 'Cell phones')]")
    public WebElement cellPhonesLink;

    public List<WebElement> getElectronicsSubmenuLinks() {
        return List.of(cameraPhotoLink, cellPhonesLink);
    }

    @FindBy(xpath = "//ul[contains(@class,'ui-autocomplete')]//li")
    public List<WebElement> suggestionList;

    // -------------------------Newsletter Block-------------------------------
    @FindBy(xpath = "div[@class='block block-newsletter']" )
    public WebElement newsletterBlock;
    @FindBy(xpath = "//strong[contains(text(), 'Newsletter')]")
    public  WebElement newsLetterTitle;
    @FindBy(id = "newsletter-email")
    public WebElement newsLetterSignUpBox;
    @FindBy(id = "newsletter-subscribe-button")
    public WebElement subscribeButton;
    @FindBy(id = "newsletter-result-block")
    public WebElement subscriptionMessage;


    //--------------------------------Footer-------------------------------------
    @FindBy(xpath = "//div[@class='footer']")
    public WebElement footerBlock;
    @FindBy(xpath = "//div[@class='footer']//div[@class='footer-menu-wrapper']//div")
    public List<WebElement> footerMenuSections;

    // Sadece Information sütunundaki linkler
    @FindBy(xpath = "//div[@class='column information']//ul//li//a")
    public List<WebElement> informationLinks;

    // Customer Service sütunu
    @FindBy(xpath = "//div[@class='column customer-service']//ul//li//a")
    public List<WebElement> customerServiceLinks;

    // My Account sütunu
    @FindBy(xpath = "//div[@class='column my-account']//ul//li//a")
    public List<WebElement> myAccountLinks;

    // Follow Us (Sosyal medya ikonları)
    @FindBy(xpath = "//div[@class='column follow-us']//ul//li//a")
    public List<WebElement> followUsLinks;

    @FindBy(xpath = "//div[@class = 'footer-poweredby']")
    public WebElement powerdByText;

    @FindBy(xpath = "//div[@class = 'footer-disclaimer']")
    public WebElement footerDisclaimerText;


    //---------------------------- Slider ---------------------------
    @FindBy(id = "nivo-slider")
    public WebElement mainSlider;

    @FindBy(xpath = "//div[@class= 'nivo-controlNav']//a")
    public List<WebElement> sliderClickableItems;

    @FindBy(xpath = "//a[@class= 'nivo-prevNav']")
    public WebElement sliderLeftArrow;

    @FindBy(xpath = "//a[@class= 'nivo-nextNav']")
    public WebElement sliderRightArrow;


    //-------------------- Featured Products ---------------------
    @FindBy(xpath = "//strong[contains(text(), 'Featured products')]")
    public WebElement featuredProductTitle;

    @FindBy(xpath = "//h2[@class='product-title']//a")
    public List<WebElement> featuredProducts;

    @FindBy(xpath = "//h2[@class='product-title']")
    public List<WebElement> featuredProductTitles;

    @FindBy(xpath = "//div[@class='product-item']//span[@class='price actual-price']")
    public List<WebElement> featuredProductPrices;

    @FindBy(xpath = "//div[@class='product-item']//div[@class='picture']//a//img")
    public List<WebElement> featuredProductImages;

    @FindBy(xpath = "//div[@class='product-item']//input[@value='Add to cart']")
    public List<WebElement> featuredAddToCartButtons;

    /*
    @FindBy(xpath = "//a[contains(text(),'Books')]")
    public WebElement booksCategory;
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    public WebElement computersCategory;
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    public WebElement electronicsCategory;
    @FindBy(xpath = "//a[contains(text(),'Apparel & Shoes')]")
    public WebElement apperalAndShoesCategory;
    @FindBy(xpath = "//a[contains(text(),'Digital downloads')]")
    public WebElement digitalDownloadsCategory;
    @FindBy(xpath = "//a[contains(text(),'Jewelry')]")
    public WebElement jewelryCategory;
    @FindBy(xpath = "//a[contains(text(),'Gift Cards')]")
    public WebElement giftCardsCategory;
     */


    //nextInt(n) metodu,
    //Java’daki Random sınıfına ait bir metottur ve
    // 0 (dahil) ile n (hariç) arasında rastgele bir tamsayı üretir.
    @FindBy (xpath = "//a[contains(text(), 'Sitemap')]")
    public WebElement siteMapLink;

    public void clickRandomCategory() {
        Random random = new Random();
        int index = random.nextInt(categories.size());
        categories.get(index).click();
    }



}
