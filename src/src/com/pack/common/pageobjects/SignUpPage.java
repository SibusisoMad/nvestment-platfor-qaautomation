package src.com.pack.common.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.github.javafaker.Faker;

import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class SignUpPage extends TestBaseSetup {
	WebDriver driver;
	private String firstname;
	private String lastname;
	Faker faker;
	@FindBy(id="FirstName")													private WebElement FirstNameTB;
	@FindBy(id="LastName")													private WebElement LastNameTB;
	@FindBy(id="EmailAddress")												private WebElement EmailAddressTB;
	@FindBy(id="Password")													private WebElement PasswordTB;
	@FindBy(id="RetypePassword")											private WebElement RetypePasswordTB;
	@FindBy(xpath="//input[@id='customCheckTerms']")						private WebElement TermsCB;
	@FindBy(xpath="//*[@value='Sign Up']")									private WebElement SignUpB;
	
	public SignUpPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		
	}
	public void FirstName() throws Exception{
		
		firstname = faker.name().firstName();
		lastname = faker.name().lastName();
		FileSearch fs = new FileSearch();
		fs.clearTheFile();
		fs.writeInFile(firstname);
		FirstNameTB.clear();
		FirstNameTB.sendKeys(firstname);
		Reporter.log("First Name Entered - ");	
	}
	public void LastName() throws Exception{
		LastNameTB.clear();
		LastNameTB.sendKeys(lastname);
		Reporter.log("Last Name Entered - ");	
	}
	public void EmailAddress(String email) throws Exception{
		EmailAddressTB.clear();
		EmailAddressTB.sendKeys(email);
		Reporter.log("Email Address Entered - "+email);	
	}
	public void Password(String userPassword) throws Exception{
		PasswordTB.clear();
		PasswordTB.sendKeys(userPassword);
		Reporter.log("Password Entered - ");	
	}
	public void RetypePassword(String userPassword) throws Exception{
		RetypePasswordTB.clear();
		RetypePasswordTB.sendKeys(userPassword);
		Reporter.log("Retype Password Entered - ");	
	}
	public void Terms() throws Exception{
		Thread.sleep(5000);
		TermsCB.click();
		Reporter.log("Terms Clicked - ");	
	}
	public void SignUp() throws Exception{
		SignUpB.click();
		Thread.sleep(500);
		Reporter.log("SignUp Clicked - ");	
	}


}
