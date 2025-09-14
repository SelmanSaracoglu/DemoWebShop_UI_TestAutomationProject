package com.demo.framework.pages.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class ProductPage {
    public ProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@class='button-1 add-to-cart-button']")
    public WebElement addtoCart;
    @FindBy(xpath = "//div[@class='item-box']//input[@value='Add to cart']")
    public List<WebElement> addToCartButtons;
    @FindBy(xpath = "//p[@class='content']")
    public WebElement addtoCartSuccessMessage;

    @FindBy(xpath = "//h2[@class='product-title']/a")
    public List<WebElement> productList;
    @FindBy(xpath = "//strong[@class='result']")
    public WebElement noResultFound;
    @FindBy(xpath = "//strong[@class= 'warning']")
    public WebElement minLengthWarningMessage;

    @FindBy(xpath = "//div[@class='page-title']/h1")
    public WebElement categoryTitle;

    @FindBy(xpath = "//h2[@class='product-title']")
    public List<WebElement> productTitles;

    public void clickRandomProduct() {
        Random random = new Random();
        int index = random.nextInt(productList.size());
        productList.get(index).click();
    }


}
