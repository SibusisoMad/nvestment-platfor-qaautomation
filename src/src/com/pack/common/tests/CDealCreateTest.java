package src.com.pack.common.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.LoginPage;
import src.com.pack.common.pageobjects.CDealPage;

public class CDealCreateTest extends TestBaseSetup {
	WebDriver driver;
	public String userEmail;
	public String userPassword;
	LoginPage LoginPage;
	CDealPage CDealPage;

	@BeforeClass
	public void setUp(ITestContext context) {
		driver = getDriver();
		userEmail = getUserEmail();
		userPassword=getUserPassword();
		try {
			LoginPage = new LoginPage(driver);
			CDealPage = new CDealPage(driver);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(enabled = true, priority = 10, groups = { "Create Deal " }, description = "Client creates a deal")
	public void dealDetails() throws Exception {

		LoginPage.logInlink();
		LoginPage.enterEmail(userEmail);
		LoginPage.enterPassword(userPassword);
		System.out.println(userPassword);
		System.out.println(userEmail);
		LoginPage.submitBtn();
		CDealPage.CreateDeallink();
		CDealPage.DealType();
		CDealPage.Currency();
		CDealPage.StartDate();
		CDealPage.EndDate();
		CDealPage.ArrangementFee();
		CDealPage.FundingCloseDate();
		CDealPage.PaymentDetail();
		CDealPage.IsPrivate();
		CDealPage.IsSecured();
		CDealPage.OriginatorSpr();
		CDealPage.Continue();
		CDealPage.InvestementValue();
		CDealPage.GrossYield();
		CDealPage.AnticipatedYield();
		CDealPage.ExpectedIncome();
		CDealPage.PaymentFreq();
		CDealPage.Investee();
	//	CDealPage.NatureOfSecurity();
		CDealPage.DealDescription();
		CDealPage.DealInvestorEmails();
		CDealPage.Continue();
		CDealPage.DocumentsUpload();
		CDealPage.DealPublish();
		CDealPage.DealConfirmPublish();
		
	}

}
