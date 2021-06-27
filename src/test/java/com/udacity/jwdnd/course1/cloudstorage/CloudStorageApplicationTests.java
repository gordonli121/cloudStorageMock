package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	private String baseURL;
	private String firstName = "Gordon";
	private String lastName = "Li";
	private String userName = "gordonli";
	private String password = "12345678";
	private String randNum;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		Random rand = new Random();
		Integer num = rand.nextInt(1000);
		randNum = num.toString();

		signUp();
	}

	@AfterEach
	public void afterEach() {
		driver.quit();
	}

	// Unauthorized user can only access login and signup page
	@Test
	public void getPages() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		driver.get(baseURL + "/result");
		Assertions.assertNotEquals("Result", driver.getTitle());
	}

	// sign up user, login and can access home page; logout and cannot access home page
	@Test
	public void ensureSignupLoginLogoutWorking() {
		login();

		// verify user is at home page
		String url = driver.getCurrentUrl();
		assertEquals(baseURL + "/home", url);

		// logout user
		waitTillElementClickable("logoutButton");
		logout();

		// verify user cannot access home page anymore
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	// create a note and verify it is displayed
	@Test
	public void testCreateNote() {
		login();

		String noteTitle = "ToDoList";
		String noteDescription = "List of things to accomplish every day";

		// create a new note
		waitTillElementClickable("nav-notes-tab");
		homePage.createNote(driver, noteTitle, noteDescription);

		// verify note is created on home page
		driver.get(baseURL + "/home");
		Assertions.assertEquals(homePage.getNoteTitle(driver).size(), 1);
		Assertions.assertEquals(homePage.getNoteDescription(driver).size(), 1);
	}

	// verify edit note works
	@Test
	public void testEditNote() {
		String noteTitle = "ToDoList";
		String noteDescription = "List of things to accomplish every day";
		String editNoteTitle = "Extended";
		String editNoteDescription = " and every week";

		login();

		// create a new note
		waitTillElementClickable("nav-notes-tab");
		homePage.createNote(driver, noteTitle, noteDescription);

		// edit note
		driver.get(baseURL + "/home");
		waitTillElementClickable("nav-notes-tab");
		homePage.editNote(driver, editNoteTitle, editNoteDescription);

		// verify note is edited
		driver.get(baseURL + "/home");
		WebElement header = driver.findElement(By.cssSelector("#userTable > tbody > tr > th"));
		Assertions.assertEquals(homePage.getNoteTitle(driver).size(), 1);
		Assertions.assertEquals(homePage.getNoteDescription(driver).size(), 1);
		Assertions.assertNotEquals(header.getText(), noteTitle);
	}

	// verify delete note works
	@Test
	public void testDeleteNote() {
		String noteTitle = "ToDoList";
		String noteDescription = "List of things to accomplish every day";

		login();

		// create a new note
		waitTillElementClickable("nav-notes-tab");
		homePage.createNote(driver, noteTitle, noteDescription);

		// delete note
		driver.get(baseURL + "/home");
		waitTillElementClickable("nav-notes-tab");
		homePage.deleteNote(driver);

		driver.get(baseURL + "/home");
		Assertions.assertEquals(homePage.getNoteTitle(driver).size(), 0);
		Assertions.assertEquals(homePage.getNoteDescription(driver).size(), 0);
	}

	@Test
	public void testCreateCredential() {
		String url = "www.udacity.com";
		String usrname = "gordonli";
		String pwd = "123456";

		login();

		// create a new credential
		waitTillElementClickable("nav-credentials-tab");
		homePage.createCredential(driver, url, usrname, pwd);

		// verify credential is created on home page
		driver.get(baseURL + "/home");
		waitTillElementClickable("nav-credentials-tab");
		Assertions.assertEquals(homePage.getCredentialUrl(driver).size(), 1);
		Assertions.assertEquals(homePage.getCredentialUsername(driver).size(), 1);
		Assertions.assertEquals(homePage.getCredentialPassword(driver).size(), 1);
	}

	@Test
	public void testEditCredential() {
		String url = "www.udacity.com";
		String usrname = "gordonli";
		String pwd = "123456";
		String editUrl = "www.udacity.ca";
		String editUsrname = "gordonli123";
		String editPwd = "12345678";

		login();

		// create a new credential
		waitTillElementClickable("nav-credentials-tab");
		homePage.createCredential(driver, url, usrname, pwd);

		// edit credential
		driver.get(baseURL + "/home");
		waitTillElementClickable("nav-credentials-tab");
		homePage.editCredential(driver, editUrl, editUsrname, editPwd);

		// verify credential is edited on home page
		driver.get(baseURL + "/home");
		WebElement header = driver.findElement(By.className("credentialPasswords"));
		Assertions.assertEquals(homePage.getCredentialUrl(driver).size(), 1);
		Assertions.assertEquals(homePage.getCredentialUsername(driver).size(), 1);
		Assertions.assertEquals(homePage.getCredentialPassword(driver).size(), 1);
		Assertions.assertNotEquals(header.getText(), pwd);
	}

	@Test
	public void testDeleteCredential() {
		String url = "www.udacity.com";
		String usrname = "gordonli";
		String pwd = "123456";

		login();

		// create a new credential
		waitTillElementClickable("nav-credentials-tab");
		homePage.createCredential(driver, url, usrname, pwd);

		// delete credential
		driver.get(baseURL + "/home");
		waitTillElementClickable("nav-credentials-tab");
		homePage.deleteCredential(driver);

		// verify credential is deleted on home page
		driver.get(baseURL + "/home");
		Assertions.assertEquals(homePage.getCredentialUrl(driver).size(), 0);
		Assertions.assertEquals(homePage.getCredentialUsername(driver).size(), 0);
		Assertions.assertEquals(homePage.getCredentialPassword(driver).size(), 0);
	}

	private void signUp() {
		driver.get(baseURL + "/signup");
		signupPage.signUpUser(firstName, lastName, userName+randNum, password);
	}

	private void login() {
		driver.get(baseURL + "/login");
		loginPage.loginUser(userName+randNum, password);
	}

	private void logout() {
		waitTillElementClickable("logoutButton");
		homePage.logout(driver);
	}

	private WebElement waitTillElementClickable(String id) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
	}
}
