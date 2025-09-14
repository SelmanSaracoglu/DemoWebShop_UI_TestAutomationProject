package com.demo.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    public RegisterPage (WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy( id = "gender-male")
    public WebElement genderMaleRadio;
    @FindBy( id = "gender-female")
    public WebElement genderFemaleRadio;

    @FindBy( xpath = "//input[@id='FirstName']")
    public WebElement firstNameInput;
    @FindBy( xpath = "//span[contains(text(), 'First name is required.')]")
    public WebElement firstNameRequiredMessage;

    @FindBy( xpath = "//input[@id='LastName']")
    public WebElement lastNameInput;
    @FindBy( xpath = "//span[contains(text(), 'Last name is required.')]")
    public WebElement lastNameRequired;

    @FindBy( xpath = "//input[@id='Email']")
    public WebElement emailInput;
    @FindBy(xpath = "//span[contains(text(), 'Wrong email')]")
    public WebElement wrongEmailMessage;
    @FindBy(xpath = "//span[contains(text(), 'Email is required.')]")
    public WebElement emailRequiredMessage;


    @FindBy( xpath = "//input[@id='Password']")
    public WebElement passwordInput;
    @FindBy(xpath = "//span[contains(text(), 'Password is required.')]")
    public WebElement passwordRequiredMessage;
    @FindBy( xpath = "//input[@id='ConfirmPassword']")
    public WebElement confirmPasswordInput;

    @FindBy(xpath = "//input[@id='register-button']")
    public WebElement registerButton;

    @FindBy(xpath = "//div[@class='result']") // Kayıt başarı mesajı → "Your registration completed"
    public WebElement successMessage;
    @FindBy(xpath = "//input[@value='Continue']")
    public WebElement continuelink;



}
