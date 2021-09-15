package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.ADealInvestorPage;
import src.com.pack.common.pageobjects.EmailCheckPage;
import src.com.pack.common.pageobjects.LoginPage;


public class ADealInvestorPageTest extends TestBaseSetup {
	WebDriver driver;
	public String adminUser;
	public String adminPassword;
	public String userEmail;
	private String adminURL;
	private String file;
	LoginPage LoginPage ;
	ADealInvestorPage AdminDealInvestor;
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
				AdminDealInvestor = new ADealInvestorPage(driver);
				emailP = new EmailCheckPage(driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test(priority=10,groups= {"Check_User_inAdmin"},description="Verify Signup User in Admin")
	public void verifyuserinAdmin() throws Exception{
		driver.navigate().to(adminURL);
		LoginPage.Login("Admin", adminUser, adminPassword);
		AdminDealInvestor.OpenInvestor();		
	}
	
	@Test(priority=20,groups= {"Update_Investor_Contact_inAdmin"},description="Update Investor contact Detail from Admin")
	public void updateContactDetail() throws Exception{
		boolean checkpage = AdminDealInvestor.checkPage();
		if(checkpage) {
		AdminDealInvestor.UpdateInvestorDetail();}
		else {
			driver.navigate().to(adminURL);
			LoginPage.Login("Admin", adminUser, adminPassword);
			AdminDealInvestor.OpenInvestor();
			AdminDealInvestor.UpdateInvestorDetail();}
	}
	@Test(priority=30,groups= {"Update_Investor_Contact_inAdmin"},description="Update Investor contact Detail from Admin")
	public void updateRegistrationStatus() throws Exception{
		boolean checkpage = AdminDealInvestor.checkPage();
		if(checkpage) {
		AdminDealInvestor.UpdateRegistrationStatustoAuthorise();}
		else {
			driver.navigate().to(adminURL);
			LoginPage.Login("Admin", adminUser, adminPassword);
			AdminDealInvestor.OpenInvestor();
			AdminDealInvestor.UpdateRegistrationStatustoAuthorise();}
	}
	
	
	@Test(priority=40,groups= {"AddDeposit"},description="Add Depost")
	public void AddDepositAmount() throws Exception{
		boolean checkpage = AdminDealInvestor.checkPage();
		if(checkpage) {
		AdminDealInvestor.AddDeposit();}
		else {
			driver.navigate().to(adminURL);
			LoginPage.Login("Admin", adminUser, adminPassword);
			AdminDealInvestor.OpenInvestor();
			AdminDealInvestor.AddDeposit();}
	}
	@Test(priority=50,groups= {"AddDocuments"},description="Add user Documents")
	public void UploadUserDocuments() throws Exception{
		boolean checkpage = AdminDealInvestor.checkPage();
		if(checkpage) {
		AdminDealInvestor.AddDocument(file);}
		else {
			driver.navigate().to(adminURL);
			LoginPage.Login("Admin", adminUser, adminPassword);
			AdminDealInvestor.OpenInvestor();
			AdminDealInvestor.AddDocument(file);}
	}
	
	
	
	

}
