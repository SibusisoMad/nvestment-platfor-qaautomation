package src.com.pack.common.pageobjects;

import java.io.File;
import java.util.List;
import org.sikuli.script.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Screen;
import org.testng.Reporter;

import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class CDealPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	Faker faker;
	FileSearch fs;
	@FindBy(xpath = "//a[text()='Create Deal']")
	private WebElement CreateDealLink;
	@FindBy(id = "DealTypeId")
	private WebElement DealTypeSel;
	@FindBy(id = "CurrencyId")
	private WebElement CurrencySel;
	@FindBy(id = "TermStartDate")
	private WebElement StartDateTB;
	@FindBy(id = "TermEndDate")
	private WebElement EndDateTB;
	@FindBy(id = "FundingCloseDate")
	private WebElement FundingCloseDateTB;
	@FindBy(id = "ArrangementFee")
	private WebElement ArrangementFeeTB;
	@FindBy(id = "PaymentDetails")
	private WebElement PaymentDetailTB;
	@FindBy(id = "OriginatorSpreadBps")
	private WebElement OriginatorSprTB;
	@FindBy(xpath = "//input[@name='continue']")
	private WebElement Continue;
	@FindBy(id = "InvestmentRequested")
	private WebElement InvestementValueTB;
	@FindBy(id = "GrossYield")
	private WebElement GrossYieldTB;
	@FindBy(id = "AnticipatedYield")
	private WebElement AnticipatedYieldTB;
	@FindBy(id = "PaymentFrequencyId")
	private WebElement PaymentFreqSel;
	@FindBy(id = "ExpectedIncomePerYear")
	private WebElement ExpectedIncomePerYear;
	@FindBy(id = "Investee")
	private WebElement InvesteeTB;
	@FindBy(id = "NatureOfSecurity")
	private WebElement NatureOfSecurityTB;
	@FindBy(id = "DealInvestorIdsOrEmails")
	private WebElement DealInvestorEmailsSel;
	@FindBy(id = "DealDescription")
	private WebElement DealDescriptionTB;
	@FindBy(xpath = "//img[@src='/images/upload.svg']")
	private WebElement UploadButton;
	@FindBy(xpath = "//button[text()='Publish']")
	private WebElement PublishButton;
	@FindBy(xpath = "//button[text()='Yes ']")
	private WebElement YesButton;

	private String[] InvestementValue = new String[] { "5000", "10000", "15000", "20000", "25000", "30000", "35000",
			"40000", "45000", "50000" };

	public void DealType() throws Exception {
		Select select = new Select(DealTypeSel);
		List<WebElement> l = select.getOptions();
		int rand = CF.getRandomValue(l.size());
		Reporter.log("Deal Type" + l.get(rand).getText());
		select.selectByIndex(rand);
		Thread.sleep(500);

	}

	public void Currency() throws Exception {
		Select select = new Select(CurrencySel);
		List<WebElement> l = select.getOptions();
		int rand = CF.getRandomValue(l.size());
		Reporter.log("Deal Currency-" + l.get(rand).getText());
		select.selectByIndex(rand);
	}

	public CDealPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		CF = new CommonFunction();
		fs = new FileSearch();
	}

	public void CreateDeallink() throws Exception {
		CreateDealLink.click();
		Thread.sleep(500);
	}

	public void StartDate() throws Exception {
		StartDateTB.clear();
		StartDateTB.sendKeys(CF.DateReturn(0));
	}

	public void EndDate() throws Exception {
		EndDateTB.clear();
		EndDateTB.sendKeys(CF.DateReturn(CF.getRandomValue(90)));
	}

	public void ArrangementFee() throws Exception {
		ArrangementFeeTB.clear();
		ArrangementFeeTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void FundingCloseDate() throws Exception {
		FundingCloseDateTB.clear();
		FundingCloseDateTB.sendKeys(CF.DateReturn(200));

	}

	public void PaymentDetail() throws Exception {
		PaymentDetailTB.clear();
		PaymentDetailTB.sendKeys(faker.book().genre());

	}

	public void IsPrivate() throws Exception {
		driver.findElement(By.xpath("(//input[@id='IsPrivate'])[" + CF.getRandomValue(3) + "]")).click();

	}

	public void IsSecured() throws Exception {
		driver.findElement(By.xpath("(//input[@id='IsSecured'])[" + CF.getRandomValue(3) + "]")).click();
	}

	public void OriginatorSpr() throws Exception {
		OriginatorSprTB.clear();
		OriginatorSprTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void Continue() throws Exception {
		Continue.click();
	}

	public void InvestementValue() throws Exception {
		Thread.sleep(500);
		InvestementValueTB.clear();
		String InvestmentAmount = CF.getRandomValue(InvestementValue);
		InvestementValueTB.sendKeys(InvestmentAmount);
	}

	public void GrossYield() throws Exception {
		GrossYieldTB.clear();
		GrossYieldTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
		Thread.sleep(500);
	}

	public void AnticipatedYield() throws Exception {
		AnticipatedYieldTB.clear();
		AnticipatedYieldTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
		Thread.sleep(500);
	}

	public void PaymentFreq() throws Exception {
		Select select = new Select(PaymentFreqSel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
	}

	public void ExpectedIncome() throws Exception {
		Thread.sleep(500);
		ExpectedIncomePerYear.clear();
		String IncomePerYear = CF.getRandomValue(InvestementValue);
		ExpectedIncomePerYear.sendKeys(IncomePerYear);
	}

	public void Investee() throws Exception {
		InvesteeTB.clear();
		InvesteeTB.sendKeys(faker.company().name());
		Thread.sleep(500);
	}

	public void NatureOfSecurity() throws Exception {
		InvesteeTB.clear();
		InvesteeTB.sendKeys(faker.finance().toString());
		System.out.println(faker.finance().toString());
	}

	public void DealInvestorEmails() throws Exception {
		Select DealInvitation = new Select(DealInvestorEmailsSel);
		List<WebElement> l = DealInvitation.getOptions();
		if (l.size() > 0) {
			for (int i = 0; i < l.size(); i++) {
				DealInvitation.selectByIndex(i);
			}
		}
	}

	public void DealDescription() throws Exception {
		DealDescriptionTB.clear();
		DealDescriptionTB.sendKeys(faker.book().title());
	}

	public void DocumentsUpload() throws Exception {
		Thread.sleep(2000);
		UploadButton.click(); //Clicking on upload button
		String FilePath = new File("data/Filename.PNG").getAbsolutePath(); //Getting path of the Filename.png which have screenshot of the Fileuploadpath
		String OpenButton = new File("data/Open.PNG").getAbsolutePath();	//Getting path of the Open.png which have screenshot of the Open Button
		String UploadDoc = new File("data/Logo-Test.png").getAbsolutePath(); //Getting path of the Logo-Test.png.png which has to be uploaded
		Screen s = new Screen(); //Creating object of Screen class to access windows screen
		Pattern fileInputTextBox = new Pattern(FilePath); // Creating a pattern based on the image 
		Pattern openButton = new Pattern(OpenButton);
		s.type(fileInputTextBox, UploadDoc); //Typing the location of upload document in the filepath present in windows
		s.click(openButton); //Clicking the open button present on windows
		Thread.sleep(2000);
	}

	public void DealPublish() throws Exception {
		PublishButton.click();
		Thread.sleep(1000);
	}

	public void DealConfirmPublish() throws Exception {
		YesButton.click();
		Thread.sleep(1000);
	}
}