package src.com.pack.common.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.BankInformationPage;
import src.com.pack.common.pageobjects.ContactInformationPage;
import src.com.pack.common.pageobjects.DocumentsPage;
import src.com.pack.common.pageobjects.LoginPage;
import src.com.pack.common.pageobjects.SelfAccreditationPage;

public class RegistrationTest extends TestBaseSetup {
	WebDriver driver;
	public String userEmail;
	public String userPassword;
	LoginPage LoginPage;
	public String File;
	SelfAccreditationPage SelfAccreditatnPage;
	ContactInformationPage ContactInfoPage;
	BankInformationPage BankDetail;
	DocumentsPage Documentupload;

	@BeforeClass
	public void setUp(ITestContext context) {
		driver = getDriver();
		userEmail = getUserEmail();
		userPassword = getUserPassword();
		File = context.getCurrentXmlTest().getParameter("file");
		try {
			LoginPage = new LoginPage(driver);
			SelfAccreditatnPage = new SelfAccreditationPage(driver);
			ContactInfoPage = new ContactInformationPage(driver);
			BankDetail = new BankInformationPage(driver);
			Documentupload = new DocumentsPage(driver);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 10, groups = {"SelfAccreditation" }, description = "Select Self Accreditation Option")
	public void selfAccredition() throws Exception {
		LoginPage.logInlink();
		LoginPage.enterEmail(userEmail);
		LoginPage.enterPassword(userPassword);
		System.out.println(userPassword);
		System.out.println(userEmail);
		LoginPage.submitBtn();
		String accredistatus = driver.findElement(By.xpath("//ul/li[1]/a")).getAttribute("class");
		if (!(accredistatus.contains("done"))) {
			SelfAccreditatnPage.AccreditionOption();

			SelfAccreditatnPage.Continue();
			SelfAccreditatnPage.AccreditionAgree();
			SelfAccreditatnPage.Continue_Next();
		}
	}

	@Test(priority = 20, groups = { "ContactInfo" }, description = "Contact Detail of user")
	public void contactInfo() throws Exception {
		ContactInfoPage.Continue_Next();
		String contactinfostatus = driver.findElement(By.xpath("//ul/li[2]/a")).getAttribute("class");
		if (!(contactinfostatus.contains("done"))) {
			ContactInfoPage.IndividualCompany();
			ContactInfoPage.LinkedinInvestor();
			ContactInfoPage.Continue_Next();
		}

	}

	@Test(priority = 30, groups = { "BankDetail" }, description = "Bank Detail of user")
	public void bankDetail() throws Exception {
		ContactInfoPage.Continue_Next();
		String paymentstatus = driver.findElement(By.xpath("//ul/li[3]/a")).getAttribute("class");
		if (!(paymentstatus.contains("done"))) {
			BankDetail.CountryDetail();
			ContactInfoPage.Continue_Next();
		}
	}

	@Test(priority = 40, groups = { "Document" }, description = "Document Upload of user")
	public void documents() throws Exception {
		ContactInfoPage.Continue();
//		String documentstatus = driver.findElement(By.xpath("//ul/li[4]/a")).getAttribute("class");
//		if(!(documentstatus.contains("done"))) {
//			Documentupload.IdentityProofFile(File);
//			Documentupload.AddressProofFile(File);
//			
//			ContactInfoPage.Continue();
		Thread.sleep(500);
	}
//	}

}
