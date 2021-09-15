package src.com.pack.common.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class ADealPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	Faker faker;
	FileSearch fs;
	@FindBy(xpath = "//a[@href='/Deal']")
	private WebElement DealLink;
	@FindBy(xpath = "//a[contains(@href,'Create')]")
	private WebElement CreateDealLink;
	@FindBy(id = "OriginatorInvestorId")
	private WebElement OriginInvestorId;
	@FindBy(id = "DealTypeId")
	private WebElement DealTypeSel;
	@FindBy(id = "Investee")
	private WebElement InvesteeTB;
	@FindBy(id = "InvestmentRequested")
	private WebElement InvestementValueTB;
	@FindBy(id = "BookValue")
	private WebElement BookValueTB;
	@FindBy(id = "CurrencyId")
	private WebElement CurrencySel;
	@FindBy(id = "FundingCloseDate")
	private WebElement FundingCloseDateTB;
	@FindBy(id = "ArrangementFee")
	private WebElement ArrangementFeeTB;
	@FindBy(id = "OriginatorSpreadBps")
	private WebElement OriginatorSprTB;
	@FindBy(id = "GrossYield")
	private WebElement GrossYieldTB;
	@FindBy(id = "AnticipatedYield")
	private WebElement AnticipatedYieldTB;
	@FindBy(id = "TermStartDate")
	private WebElement StartDateTB;
	@FindBy(id = "TermEndDate")
	private WebElement EndDateTB;
	@FindBy(id = "PaymentFrequencyId")
	private WebElement PaymentFreqSel;
	@FindBy(id = "PaymentDetails")
	private WebElement PaymentDetailTB;
	@FindBy(id = "IsSecured")
	private WebElement IsSecuredSel;
	@FindBy(id = "IsPrivate")
	private WebElement IsPrivateSel;
	@FindBy(id = "DealInvestorIdsOrEmails")
	private WebElement DealInvestorEmailsSel;
	@FindBy(id = "DealDescription")
	private WebElement DealDescriptionTB;
	@FindBy(id = "searchBox")
	private WebElement SearchBoxTB;

	@FindBy(xpath = "//button[text()='Submit']")
	private WebElement SubmitBtn;
	@FindBy(xpath = "//a[@data-target='#publishModal']")
	private WebElement PublishBtn;
	@FindBy(xpath = "//button[text()='Yes']")
	private WebElement PublisYesBtn;
	private String[] InvestementValue = new String[] { "5000", "10000", "15000", "20000", "25000", "30000", "35000",
			"40000", "45000", "50000" };

	public ADealPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		CF = new CommonFunction();
		fs = new FileSearch();
	}

	public void CreateDeal() throws Exception {
		for (int i = 1; i <= 3; i++) {
			Deallink();
			CreateDeallink();
			OriginatorInvestor();
			DealType();
			Investee();
			InvestementValue();
			BookValue();
			Currency(i);
			FundingCloseDate();
			ArrangementFee();
			OriginatorSpr();
			GrossYield();
			AnticipatedYield();
			StartDate();
			EndDate();
			PaymentFreq();
			PaymentDetail();
			IsSecured();
			IsPrivate();
			DealInvestorEmails();
			DealDescription();
			// Submit();
			Publish();
		}
	}

	public void MandatoryFieldCreateDeal() throws Exception {
		Deallink();
		CreateDeallink();
		Submit();
		fieldsMessage();

	}

	public void fieldsMessage() throws Exception {
		int totalmandatory = driver.findElements(By.xpath("//div[@class='col-md-6']/div/span[1]")).size();
		String field = null;
		System.out.println(totalmandatory);
		for (int i = 1; i < totalmandatory; i++) {
			String attriclass = driver.findElement(By.xpath("(//div[@class='col-md-6']/div/span[1])[" + i + "]"))
					.getAttribute("class");

			// System.out.println(field);
			if (attriclass.equalsIgnoreCase("text-danger")) {
				try {
					field = driver.findElement(By.xpath("(//div[@class='col-md-6']/div/label[1])[" + i + "]"))
							.getText();
					String Message = driver.findElement(By.xpath(
							"(//div[@class='col-md-6']/div/span[@class='field-validation-error']/span)[" + i + "]"))
							.getText();
					System.out.println(field + "-" + Message);
				} catch (Exception ex) {
					System.out.println(" Missing Mandatory message. " + field);

					// Log.WriteLine();
				}

			}
		}
	}

	public void Deallink() throws Exception {
		DealLink.click();
		Thread.sleep(500);
	}

	public void CreateDeallink() throws Exception {
		CreateDealLink.click();
		Thread.sleep(500);
	}

	public void searchDeal() throws Exception {
		String user = fs.readFromFile();
		SearchBoxTB.sendKeys(user);
		Thread.sleep(500);
	}

	public void OriginatorInvestor() throws Exception {
		String user = fs.readFromFile();
		Select select = new Select(OriginInvestorId);
		List<WebElement> l = select.getOptions();
		for (int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i).getText());
			if (l.get(i).getText().contains(user)) {
				System.out.println("Got value");
				select.selectByIndex(i);
				break;
			}
		}
		Thread.sleep(500);
	}

	public void DealType() throws Exception {
		Select select = new Select(DealTypeSel);
		List<WebElement> l = select.getOptions();
		int rand = CF.getRandomValue(l.size());
		Reporter.log(l.get(rand).getText());
		select.selectByIndex(rand);
	}

	public void Investee() throws Exception {
		InvesteeTB.clear();
		InvesteeTB.sendKeys(faker.company().name());
	}

	public void InvestementValue() throws Exception {
		InvestementValueTB.clear();
		String InvestmentAmount = CF.getRandomValue(InvestementValue);
		InvestementValueTB.sendKeys(InvestmentAmount);
	}

	public void BookValue() throws Exception {
		BookValueTB.clear();
		BookValueTB.sendKeys(Integer.toString(CF.getRandomValue(10)));
	}

	public void Currency(int i) throws Exception {
		Select select = new Select(CurrencySel);
		List<WebElement> l = select.getOptions();
		Reporter.log("Deal Currency-" + l.get(i).getText());
		select.selectByIndex(i);
	}

	public void FundingCloseDate() throws Exception {
		FundingCloseDateTB.clear();
		FundingCloseDateTB.sendKeys(CF.DateReturn(200));
	}

	public void ArrangementFee() throws Exception {
		ArrangementFeeTB.clear();
		ArrangementFeeTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void OriginatorSpr() throws Exception {
		OriginatorSprTB.clear();
		OriginatorSprTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void GrossYield() throws Exception {
		GrossYieldTB.clear();
		GrossYieldTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void AnticipatedYield() throws Exception {
		AnticipatedYieldTB.clear();
		AnticipatedYieldTB.sendKeys(Integer.toString(CF.getRandomValue(5)));
	}

	public void StartDate() throws Exception {
		StartDateTB.clear();
		StartDateTB.sendKeys(CF.DateReturn(0));
	}

	public void EndDate() throws Exception {
		EndDateTB.clear();
		EndDateTB.sendKeys(CF.DateReturn(CF.getRandomValue(90)));
	}

	public void PaymentFreq() throws Exception {
		Select select = new Select(PaymentFreqSel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
	}

	public void PaymentDetail() throws Exception {
		PaymentDetailTB.clear();
		PaymentDetailTB.sendKeys(faker.book().genre());
	}

	public void IsSecured() throws Exception {
		Select select = new Select(IsSecuredSel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
	}

	public void IsPrivate() throws Exception {
		Select select = new Select(IsPrivateSel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
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

	public void Submit() throws Exception {
		SubmitBtn.click();
		Thread.sleep(500);
	}

	public void Publish() throws Exception {
		PublishBtn.click();
		Thread.sleep(500);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		PublisYesBtn.click();
		Thread.sleep(500);
		Reporter.log("Deal Published");
	}
}
