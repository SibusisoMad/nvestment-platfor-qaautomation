package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.ADealInvestorPage;
import src.com.pack.common.pageobjects.AInvestonDealPage;
import src.com.pack.common.pageobjects.LoginPage;

public class AInvestonDealTest extends TestBaseSetup {
	WebDriver driver;
	public String adminUser;
	public String adminPassword;
	public String userEmail;
	private String adminURL;
	public String [] dealDetail;
	private String depositFile;
	private String documentFile;
	LoginPage LoginPage ;
	ADealInvestorPage AdminDealInvestor;
	AInvestonDealPage  AdminInvestonDealPage;
	
	@BeforeClass
	public void setUp(ITestContext context) {
		driver=getDriver();
		userEmail=getUserEmail();
		adminURL=getAdminUrl();
		adminUser=getAdminUser();
		adminPassword=getAdminPassword();	
		depositFile = context.getCurrentXmlTest().getParameter("depositdata");
		documentFile = context.getCurrentXmlTest().getParameter("file");
		
			try {
				LoginPage = new LoginPage(driver);
				AdminDealInvestor = new ADealInvestorPage(driver);
				AdminInvestonDealPage = new AInvestonDealPage(driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test(priority=10,groups= {"UploadDoc"},description="Upload Document on Deal")
	public void aUploadDocumentOnDeal() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		AdminDealInvestor.OpenInvestor();
		dealDetail=AdminInvestonDealPage.AddDocumentonDeal(documentFile,depositFile);
	}
	@Test(priority=20,groups= {"InvestonDeal"},description="Invest on Deal")
	public void bInvestonDeal() throws Exception{
		AdminInvestonDealPage.InvestOnRandomDeal(depositFile, dealDetail);
	}
	@Test(priority=30,groups= {"VerifyInvestorAmount"},description="Check Balance in Investor")
	public void cverifyAmountInvestor() throws Exception{
		String investorLink = AdminInvestonDealPage.investorLink();
		driver.navigate().to(investorLink);
		AdminInvestonDealPage.VerifyAmountInInvestor(depositFile,dealDetail);
	}
	@Test(priority=40,groups= {"DeleteInvestment"},description="Delete Investment of Investor")
	public void dDeleteInvestment() throws Exception{
		AdminInvestonDealPage.DeleteInvestement(depositFile);
	}
	
	

}
