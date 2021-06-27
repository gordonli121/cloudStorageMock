package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "editNoteModal")
    private WebElement editNoteModal;

    @FindBy(id = "edit-note-title")
    private WebElement editNoteTitle;

    @FindBy(id = "edit-note-description")
    private WebElement editNoteDescription;

    @FindBy(id = "editNoteSubmit")
    private WebElement editNoteSubmit;

    @FindBy(id = "deleteNoteModal")
    private WebElement deleteNoteModal;

    @FindBy(id = "deleteNoteSubmit")
    private WebElement deleteNoteSubmit;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(id = "editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(id = "deleteCredentialButton")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credentialModal")
    private WebElement credentialModal;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "editCredentialModal")
    private WebElement editCredentialModal;

    @FindBy(id = "edit-credential-url")
    private WebElement editCredentialUrl;

    @FindBy(id = "edit-credential-username")
    private WebElement editCredentialUsername;

    @FindBy(id = "edit-credential-password")
    private WebElement editCredentialPassword;

    @FindBy(id = "editCredentialSubmit")
    private WebElement editCredentialSubmit;

    @FindBy(id = "deleteCredentialModal")
    private WebElement deleteCredentialModal;

    @FindBy(id = "deleteCredentialSubmit")
    private WebElement deleteCredentialSubmit;

    @FindBy(className = "noteTitles")
    private List<WebElement> noteTitleList;

    @FindBy(className = "noteDescriptions")
    private List<WebElement> noteDescriptionList;

    @FindBy(className = "credentialUrls")
    private List<WebElement> credentialUrlList;

    @FindBy(className = "credentialUsernames")
    private List<WebElement> credentialUsernameList;

    @FindBy(className = "credentialPasswords")
    private List<WebElement> credentialPasswordList;

    public HomePage(WebDriver driver) { PageFactory.initElements(driver, this); }

    public void logout(WebDriver driver) {new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(logoutButton)).click();}

    public void createNote(WebDriver driver, String title, String description) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", noteTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addNoteButton)).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescription);
        jse.executeScript("arguments[0].click()", noteSubmit);
    }

    public void editNote(WebDriver driver, String title, String description) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", noteTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", editNoteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", editNoteDescription);
        jse.executeScript("arguments[0].click()", editNoteSubmit);
    }

    public void deleteNote(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", noteTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
        jse.executeScript("arguments[0].click()", deleteNoteSubmit);
    }

    public void createCredential(WebDriver driver, String url, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addCredentialButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialUrl)).sendKeys(url);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialUsername)).sendKeys(username);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialPassword)).sendKeys(password);
        jse.executeScript("arguments[0].click()", credentialSubmit);
    }

    public void editCredential(WebDriver driver, String url, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialUrl)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialUrl)).sendKeys(url);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialUsername)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialUsername)).sendKeys(username);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialPassword)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredentialPassword)).sendKeys(password);
        jse.executeScript("arguments[0].click()", editCredentialSubmit);
    }

    public void deleteCredential(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
        jse.executeScript("arguments[0].click()", deleteCredentialSubmit);
    }

    public List<WebElement> getNoteTitle(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", noteTab);
        return noteTitleList;
    }

    public List<WebElement> getNoteDescription(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", noteTab);
        return noteDescriptionList;
    }

    public List<WebElement> getCredentialUrl(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        return credentialUrlList;
    }

    public List<WebElement> getCredentialUsername(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        return credentialUsernameList;
    }

    public List<WebElement> getCredentialPassword(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", credentialTab);
        return credentialPasswordList;
    }
}
