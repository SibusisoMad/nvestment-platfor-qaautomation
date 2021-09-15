package src.com.pack.common.pageobjects;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;

import src.com.pack.base.TestBaseSetup;

public class DealViewPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	Faker faker;
	FileSearch fs;

	@FindBy(xpath = "//a[text()='View All']")
	private WebElement ViewAllLink;

	@FindBy(xpath = "//a[text()='Invitation']")
	private WebElement InvitationLink;
	@FindBy(xpath = "//a[text()='Market']")
	private WebElement MarketLink;

	@FindBy(xpath = "//a[text()='Investments']")
	private WebElement InvestmentsLink;

	@FindBy(xpath = "//a[@href='#invitations']")
	private WebElement Invitations;

	@FindBy(xpath = "//a[@href='#open-huddles']")
	private WebElement OpenHuddles;

	@FindBy(xpath = "//a[@href='#secondary-market']")
	private WebElement SecondaryMarket;

	@FindBy(xpath = "//a[@href='#my-investments']")
	private WebElement MyInvestments;
	@FindBy(xpath = "//a[@href='#my-originations']")
	private WebElement MyOriginations;
	@FindBy(xpath = "//a[@href='#my-bids']")
	private WebElement MyBids;
	@FindBy(xpath = "//a[@href='#my-offers']")
	private WebElement MyOffers;
	@FindBy(xpath = "//a[@href='#closed-huddles']")
	private WebElement ClosedHuddles;
	@FindBy(id = "InvestmentAmount")
	private WebElement EnterAmount;
	@FindBy(xpath = "//button[text()='Invest Now']")
	private WebElement InvestNowButton;
	@FindBy(xpath = "//span[@class='input-group-addon']")
	private WebElement CurrencyFormat;
	@FindBy(xpath = "//span[@data-currency='USD']")
	private WebElement CurrencyUSD;
	@FindBy(xpath = "//span[@data-currency='EUR']")
	private WebElement CurrencyEURO;
	@FindBy(xpath = "//span[@data-currency='GBP']")
	private WebElement CurrencyPOUND;
	@FindBy(id = "funds")
	private WebElement AvailableFunds;
	@FindBy(xpath = "//label[text()='Funds Requested']/following-sibling::div")
	private WebElement FundRequestedAmount;
	@FindBy(xpath = "//label[text()='Total Invested']/following-sibling::div")
	private WebElement TotalInvestedAmount;
	@FindBy(xpath = "//button[text()='Yes ']")
	private WebElement ConfirmButton;
	@FindBy(xpath = "//button[text()='Save']")
	private WebElement SaveButton;
	@FindBy(xpath = "//label[text()='Status']/following-sibling::div")
	private WebElement DealStatus;
	

	public DealViewPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		CF = new CommonFunction();
		fs = new FileSearch();
	}

	public void ViewAllLink() throws Exception {
		ViewAllLink.click();
		Thread.sleep(500);
	}

	public void Invitationlink() throws Exception {
		InvitationLink.click();
		Thread.sleep(500);
	}

	public void OpenDeal() throws Exception {
		Thread.sleep(1000);
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[contains(@href,'/Deal/Details')]")); // Finding all the web elements matching the
																			// xpath
		Random r = new Random();
		int randomValue = r.nextInt(allLinks.size()); // Creating a random value
		System.out.println("Random Value: " + randomValue );
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement ele = allLinks.get(randomValue); // Storing a element from the list(allLinks) into ele based on the
													// random value generated
		executor.executeScript("arguments[0].click();", ele); // Clicking the element selected
		loaderWait();
		
	}
	public void EditDeal() throws Exception {
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[contains(@href,'/Deal/Edit')]")); // Finding all the web elements matching the
																			// xpath
		Random r = new Random();
		int randomValue = r.nextInt(allLinks.size()); // Creating a random value
		System.out.println("Random Value: " + randomValue );
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement ele = allLinks.get(randomValue); // Storing a element from the list(allLinks) into ele based on the
													// random value generated
		executor.executeScript("arguments[0].click();", ele); // Clicking the element selected
	}
	public void Savelink() throws Exception {
		SaveButton.click();
		Thread.sleep(500);
	}

	public void InvestementValue() throws Exception {
		if(DealStatus.getText()=="Open") {
		WebDriverWait wait= new WebDriverWait(driver,50);
		Random random = new Random();
		wait.until(ExpectedConditions.elementToBeClickable(EnterAmount));
		EnterAmount.clear();
		String TotalCurrency_str;
		Integer randomNumber;
		String currency = CurrencyFormat.getText(); // Getting the currency format of the deal
		wait.until(ExpectedConditions.elementToBeClickable(By.id("funds")));
		AvailableFunds.click(); // clicking available funds drop down
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='Available']")));
	
		// Checking the available balance for the specific currency used in this deal
		if (currency.equals("€"))
			TotalCurrency_str = CurrencyEURO.getText();
		else if (currency.equals("$"))
			TotalCurrency_str = CurrencyUSD.getText();
		else
			TotalCurrency_str = CurrencyPOUND.getText();

		// Replacing special characters and changing data type to Integer
		Integer TotalCurrency = Integer.parseInt(TotalCurrency_str.replaceAll("[^0-9]", ""));
		Integer FundRequested = Integer.parseInt(FundRequestedAmount.getText().replaceAll("[^0-9]", ""));
		Integer TotalInvested = Integer.parseInt(TotalInvestedAmount.getText().replaceAll("[^0-9]", ""));
		

		// Generating amount to be invested based on available funds and Total invested
		// amount for a deal
		if (FundRequested - TotalInvested < TotalCurrency)
			randomNumber = random.nextInt((FundRequested - TotalInvested) - 1) + 1;
		else
		
			randomNumber = random.nextInt(TotalCurrency - 1) + 1;

		// Logging
		System.out.println("Currency Type :" + currency);
		System.out.println("Currency Type and Value :" + TotalCurrency_str);
		System.out.println("Total Currency :" + TotalCurrency);
		System.out.println("Fund Requested :" + FundRequested);
		System.out.println("Total Invested :" + TotalInvested);
		System.out.println("Random Number :" + randomNumber);

		EnterAmount.sendKeys(randomNumber.toString()); // Typing investment amount for a deal
		InvestNowButton.click(); // Clicking on invest now
		ConfirmButton.click(); // Clicking on Yes pop up
		loaderWait();
		}
	}

	public void MarketLink() throws Exception {
		MarketLink.click();
		Thread.sleep(2000);
	}

	public void InvestmentsLink() throws Exception {
		InvestmentsLink.click();
		Thread.sleep(5000);
	}

	public void Invitations() throws Exception {
		Invitations.click();
		Thread.sleep(5000);
	}

	public void OpenHuddles() throws Exception {
		OpenHuddles.click();
		Thread.sleep(5000);
	}

	public void SecondaryMarket() throws Exception {
		SecondaryMarket.click();
		Thread.sleep(5000);
	}

	public void MyInvestments() throws Exception {
		MyInvestments.click();
		Thread.sleep(5000);
	}

	public void MyOriginations() throws Exception {
		MyOriginations.click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[@href='/MyOriginations' and text()='Show All']")).click();
	}

	public void MyBids() throws Exception {
		MyBids.click();
		Thread.sleep(5000);
	}

	public void MyOffers() throws Exception {
		MyOffers.click();
		Thread.sleep(5000);
	}

	public void ClosedHuddles() throws Exception {
		ClosedHuddles.click();
		Thread.sleep(5000);
	}
}
