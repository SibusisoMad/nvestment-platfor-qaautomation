package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.ADealInvestorPage;
import src.com.pack.common.pageobjects.ADeletePage;
import src.com.pack.common.pageobjects.AInvestonDealPage;
import src.com.pack.common.pageobjects.LoginPage;

public class ADeletePageTest extends TestBaseSetup {
	WebDriver driver;
	public String adminUser;
	public String adminPassword;
	LoginPage LoginPage ;
	private String adminURL;
	ADealInvestorPage AdminDealInvestor;
	ADeletePage deletePage;
	
	
	@BeforeClass
	public void setUp(ITestContext context) {
		driver=getDriver();
		adminURL=getAdminUrl();
		adminUser=getAdminUser();
		adminPassword=getAdminPassword();	
		
			try {
				LoginPage = new LoginPage(driver);
				AdminDealInvestor = new ADealInvestorPage(driver);
				deletePage = new ADeletePage(driver); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test(priority=10,groups= {"DeleteImage"},description="Delete Document of Investor")
	public void adeleteInvestorImage() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		AdminDealInvestor.OpenInvestor();
		deletePage.deleteInvestorImage();
	}
	
	@Test(priority=20,groups= {"DeleteDeal"},description="Delete a Deal")
	public void bdeleteDeal() throws Exception{
		deletePage.deleteDeal();
	}

}
