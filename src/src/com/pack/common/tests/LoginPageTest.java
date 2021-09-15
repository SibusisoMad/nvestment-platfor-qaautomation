package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.IconPage;
import src.com.pack.common.pageobjects.LoginPage;


public class LoginPageTest extends TestBaseSetup {
WebDriver driver;
public String loginemail;
public String Loginpassword;

	@BeforeClass
	public void setUp(ITestContext context) {
		driver=getDriver();
		loginemail = context.getCurrentXmlTest().getParameter("User");
		Loginpassword = context.getCurrentXmlTest().getParameter("Password");
	}
	
	@Test(groups ={"Login"}, description="Login Page Verification")	
	public void CheckLoginPageTest() throws Exception {
	 LoginPage LoginP = new LoginPage(driver);
	 LoginP.submitBtn();
	 LoginP.EmailValidation();
	 LoginP.PasswordValidation();
	}
	
}