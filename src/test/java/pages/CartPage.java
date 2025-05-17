package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//div[@class= 'order-summary-content']")
    public WebElement cartIsEmptyMessage;

    @FindBy(xpath = "//tr[@class='cart-item-row']")
    public List<WebElement> cartItems;
    @FindBy(xpath = "//td[@class='remove-from-cart']")
    public WebElement removeItems;

    @FindBy(xpath = "//input[@value='Update shopping cart']")
    public WebElement updateShoppingCartButton;
    @FindBy(xpath = "//input[@value='Continue shopping']")
    public WebElement continueShoppingButton;

    @FindBy(xpath = "//button[@id='checkout']")
    public WebElement checkoutButton;
    @FindBy(id = "termsofservice")
    public WebElement termsOfServiceRadioButton;

   @FindBy(xpath = "//div[@class='page-title']//h1")
    public WebElement checkOutTitle;
}
