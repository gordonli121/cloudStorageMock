package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {
    @FindBy(id = "file-success-msg")
    private WebElement fileSuccessMsg;

    @FindBy(id = "file-error-msg")
    private WebElement fileErrorMsg;

    @FindBy(id = "note-success-msg")
    private WebElement noteSuccessMsg;

    @FindBy(id = "note-success-msg")
    private WebElement noteErrorMsg;

    @FindBy(id = "credential-success-msg")
    private WebElement credentialSuccessMsg;

    @FindBy(id = "credential-success-msg")
    private WebElement credentialErrorMsg;

    @FindBy(className = "homeLink")
    private WebElement homeLink;

    public ResultPage(WebDriver driver) { PageFactory.initElements(driver, this); }

    public void redirectToHome(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(homeLink)).click();
//        homeLink.click();
    }
}
