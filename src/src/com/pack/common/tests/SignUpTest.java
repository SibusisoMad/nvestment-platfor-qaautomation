package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.SendMail;
import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.ADealInvestorPage;
import src.com.pack.common.pageobjects.EmailCheckPage;
import src.com.pack.common.pageobjects.LoginPage;
import src.com.pack.common.pageobjects.SignUpPage;

public class SignUpTest extends TestBaseSetup {
	WebDriver driver;
	public String adminUser;
	public String adminPassword;
	public String userEmail;
	private String adminURL;
	public String userPassword;
	LoginPage LoginPage ;
	SignUpPage SignUpPage;
	EmailCheckPage emailP;
	private String file;
	ADealInvestorPage AdminDealInvestor;

	
	@BeforeClass
	public void setUp(ITestContext context) {
		driver=getDriver();
		userEmail=getUserEmail();
		userPassword=getUserPassword();
		adminURL=getAdminUrl();
		adminUser=getAdminUser();
		adminPassword=getAdminPassword();
		file = context.getCurrentXmlTest().getParameter("file");	
				
			try {
				LoginPage = new LoginPage(driver);
				SignUpPage = new SignUpPage(driver);
				emailP = new EmailCheckPage(driver);
				 AdminDealInvestor = new ADealInvestorPage(driver);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test(priority=10,groups= {"SignUp"}, description= "Signup to iHuddle")
	public void Signup() throws Exception{
		
		LoginPage.logInlink();
		LoginPage.OpenSignup();
		SignUpPage.FirstName();
		SignUpPage.LastName();
		SignUpPage.EmailAddress(userEmail);
		SignUpPage.Password(userPassword);
		SignUpPage.RetypePassword(userPassword);
		SignUpPage.Terms();
		SignUpPage.SignUp();
		}
	@Test(priority=20,groups= {"Check_User_inAdmin"},description="Verify Signup User in Admin")
	public void verifyuserinAdmin() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		AdminDealInvestor.OpenInvestor();	
		AdminDealInvestor.AddDocument(file);
	}
	@Test(priority=30,groups= {"EmailVerify"}, description= "Verify Signup Email")
	public void VerifyEmail() throws Exception{
		 emailP.openOutlook();
		 emailP.enterEmail("rajeev@atomcto.com");
		 emailP.enterPassword("Rajmicro1#");
		 emailP.ClickInbox();
		 emailP.checkEmail();
		 emailP.clickRegister();
		}
}
