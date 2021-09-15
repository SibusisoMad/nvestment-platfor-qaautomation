package src.com.pack.common.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.CDealPage;
import src.com.pack.common.pageobjects.DealViewPage;
import src.com.pack.common.pageobjects.LoginPage;

public class DealViewTest extends TestBaseSetup {
	WebDriver driver;
	public String userEmail;
	public String userPassword;
	LoginPage LoginPage;
	DealViewPage DealViewPage;
	CDealPage CDealPage;


	@BeforeClass
	public void setUp(ITestContext context) {
		driver = getDriver();
		userEmail = getUserEmail();
		userPassword=getUserPassword();

		try {
			LoginPage = new LoginPage(driver);
			DealViewPage = new DealViewPage(driver);
			CDealPage = new CDealPage(driver);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 10, groups = {
			"Deal View Invitations" }, description = "Check invitations for a deal and invest in one of them")
	public void InvitationsDeal() throws Exception {
	
		LoginPage.logInlink();
		LoginPage.enterEmail(userEmail);
		LoginPage.enterPassword(userPassword);
		System.out.println(userPassword);
		System.out.println(userEmail);
		LoginPage.submitBtn();
		DealViewPage.Invitationlink();
		
		
		//The below section including if block is to make sure that a deal is opened only if atleast 1 deal is present for a investor
		loaderWait();
		Integer numberOfDeal = Integer.parseInt(
				driver.findElement(By.xpath("//span[@class='js-deal-count counter']")).getAttribute("textContent"));
		System.out.println("Number of deal: " + numberOfDeal);

		if (numberOfDeal > 0) {
			DealViewPage.OpenDeal();
			DealViewPage.InvestementValue();
		}
		DealViewPage.MarketLink();
		// DealViewPage.InvestmentsLink();
		// DealViewPage.Invitations();
		// DealViewPage.SecondaryMarket();
		// DealViewPage.MyInvestments();
	}

	@Test(priority = 20, groups = { "Deal View MyOrigination" }, description = "Check MyOrigination and edit a deal")
	public void MyOriginationDeal() throws Exception {
		DealViewPage.ViewAllLink();
		DealViewPage.MyOriginations();
		DealViewPage.EditDeal();
		CDealPage.FundingCloseDate();
		DealViewPage.Savelink();
	}

	@Test(priority = 30, groups = { "Deal View OpenHuddles" }, description = "Check Openhuddles and Invest in a deal")
	public void OpenHuddlesDeal() throws Exception {
		DealViewPage.MarketLink();
		DealViewPage.OpenHuddles();
		DealViewPage.OpenDeal();
		DealViewPage.InvestementValue();
	}
}