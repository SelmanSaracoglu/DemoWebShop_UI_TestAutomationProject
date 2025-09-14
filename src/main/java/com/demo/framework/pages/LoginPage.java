package com.demo.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//input[@id='Email']")
    public WebElement emailInput;
    @FindBy(xpath = "//input[@id='Password']")
    public WebElement passwordInput;
    @FindBy(xpath = "//input[@value='Log in']")
    public WebElement loginButton;

    @FindBy(xpath = "//div[@class='validation-summary-errors']")
    public WebElement loginErrorMessage;
    @FindBy(xpath = "//span[@class='field-validation-error']")
    public WebElement invalidMailErrorMessage;
    @FindBy(xpath = "//li[contains(text(), 'No customer account found')]")
    public WebElement nonExistingAccountErrorMessage;
    @FindBy(xpath = "//li[contains(text(), 'The credentials provided are incorrect')]")
    public WebElement incorrectCredentialsErrorMessage;




}
