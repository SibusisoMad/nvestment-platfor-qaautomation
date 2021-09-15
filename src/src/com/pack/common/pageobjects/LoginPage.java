package src.com.pack.common.pageobjects;

import java.util.List;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import src.com.pack.base.TestBaseSetup;

public class LoginPage extends TestBaseSetup {
	WebDriver driver;
	@FindBy(xpath ="//*[text()='Log In']")									private WebElement Login;
	@FindBy(xpath="//*[@id='UserName' or @name='EmailAddress']")			List<WebElement> loginEmailTextBox;
	@FindBy(id="EmailAddress-error")										private WebElement loginEmailError;
	@FindBy(id="Password")													private WebElement loginPasswordTextBox;
	@FindBy(id="Password-error")											private WebElement loginPasswordError;
	@FindBy(xpath="//*[@type='submit']")									private WebElement loginSubmitBtn;
	@FindBy(xpath ="//*[@href='/SignUp']")									private WebElement SignupLink;
	@FindBy(xpath ="//a[contains(@href,'Logout')]")							List<WebElement> Logout;
	@FindBy(xpath ="//button[text()='Accept Cookies']")						List<WebElement> Cookies;
	public LoginPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	public void logInlink() throws Exception{
		Login.click();
		Thread.sleep(500);
		boolean popcookie =	  Cookies.size()!=0; 
		System.out.println(popcookie); 
		if(popcookie){
			Cookies.get(0).click();
		}
	}
	public void enterEmail(String email) throws Exception{
		loginEmailTextBox.clear();
		loginEmailTextBox.get(0).sendKeys(email);
		//loginEmailTextBox.sendKeys(email);
		Reporter.log("Email Entered - "+email);	
	}
	public void EmailValidation() throws Exception{
		
		Reporter.log("Email Verification Message - "+loginEmailError.getText());	
	}
	public void OpenSignup() throws Exception{
	SignupLink.click();
	Thread.sleep(500);
		
	}
	public void enterPassword(String password) throws Exception{
		loginPasswordTextBox.clear();
		loginPasswordTextBox.sendKeys(password);
		Reporter.log("Password Entered - "+password);	
	}
	public void PasswordValidation() throws Exception{
		
		Reporter.log("Password Verification Message - "+loginPasswordError.getText());	
	}
	public void submitBtn() throws Exception{
		loginSubmitBtn.click();
		Reporter.log("Submit Button");
		Thread.sleep(500);
	}
	public void verifyUserloggedin() throws Exception{
		if(Logout.size()!=0) {
			
			Reporter.log("User Successfully logged in ");
			}
	}
	public void Login(String admin, String email,String password) throws Exception{
		boolean emailv = loginEmailTextBox.size()!=0;
		if(emailv) {
			System.out.println("Email Field Visible");
		}else {
			Logout();
			Thread.sleep(500);
		}
		
		if(admin.contains("Admin")) {
			enterEmail( email);
			enterPassword( password);
			submitBtn();
		}else {
			logInlink();
			enterEmail( email);
			enterPassword( password);
			submitBtn();
		}
	}
	
	public void Logout() throws Exception{
		boolean logout = Logout.size()!=0;
		if(logout) {
			Logout.get(0).click();;
			}
	}
}
