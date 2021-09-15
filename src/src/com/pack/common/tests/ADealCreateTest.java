package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.ADealPage;
import src.com.pack.common.pageobjects.EmailCheckPage;
import src.com.pack.common.pageobjects.LoginPage;

public class ADealCreateTest extends TestBaseSetup {
	WebDriver driver;
	public String adminUser;
	public String adminPassword;
	public String userEmail;
	private String adminURL;
	private String file;
	LoginPage LoginPage ;
	ADealPage ADealpage;
	EmailCheckPage emailP;
	
	@BeforeClass
	public void setUp(ITestContext context) {
		driver=getDriver();
		userEmail=getUserEmail();
		adminURL=getAdminUrl();
		adminUser=getAdminUser();
		adminPassword=getAdminPassword();
		file = context.getCurrentXmlTest().getParameter("file");		
			try {
				LoginPage = new LoginPage(driver);
				ADealpage = new ADealPage(driver);
				emailP = new EmailCheckPage(driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Test(priority=10,groups= {"Mandatory_Field_Check"},description="Check Message for Mandatory Fields")
	public void MandatoryFieldDeal() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		ADealpage.MandatoryFieldCreateDeal();
	}
	
	@Test(priority=20,groups= {"Create_Deal_inAdmin"},description="Create a new Deal in Admin")
	public void CreateDealAdmin() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		ADealpage.CreateDeal();
	}
}
