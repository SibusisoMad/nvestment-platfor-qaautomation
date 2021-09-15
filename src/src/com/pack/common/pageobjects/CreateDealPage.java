package src.com.pack.common.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.github.javafaker.Faker;

import src.com.pack.base.CommonFunction;
import src.com.pack.base.TestBaseSetup;

public class CreateDealPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	Faker faker;
	@FindBy(xpath ="//a[contains(@href,'Deal/Create')]")		private WebElement CreatedealBtn;
	@FindBy(id="InvestmentRequested")							private WebElement InvestementValueTB;
	@FindBy(id="TermStartDate")									private WebElement StartDateTB;
	@FindBy(id="PaymentFrequencyId")							private WebElement PaymentFreqSel;
	@FindBy(id="TermEndDate")									private WebElement EndDateTB;
	@FindBy(id="PaymentDetails")								private WebElement PaymentDetailTB;
	@FindBy(id="CurrencyId")									private WebElement CurrencySel;
	@FindBy(id="ArrangementFee")								private WebElement ArrangementFeeTB;
	@FindBy(id="IsSecured")										private WebElement IsSecuredSel;
	@FindBy(id="DealTypeId")									private WebElement DealTypeTB;
	@FindBy(id="OriginatorSpreadBps")							private WebElement OriginatorSprTB;
	@FindBy(id="Investee")										private WebElement InvesteeTB;
	@FindBy(id="GrossYield")									private WebElement GrossYieldTB;
	@FindBy(id="FundingCloseDate")								private WebElement FundingCloseDateTB;
	@FindBy(id="AnticipatedYield")								private WebElement AnticipatedYieldTB;
	@FindBy(id="IsPrivate")										private WebElement IsPrivateSel;
	@FindBy(id="DealInvestorIdsOrEmails")						private WebElement DealInvestorEmailsSel;
	@FindBy(id="DealDescription")								private WebElement DealDescriptionTB;
	
	//private String[] InvestementValue = new String[]{"5000","10000","15000","20000","25000","30000","35000","40000","45000","50000"};
	
	public CreateDealPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker= new Faker();
		CF = new CommonFunction();
	}
	
	public void CreateDealBtn() throws Exception{
		CreatedealBtn.click();
		Thread.sleep(500);
	}
	
	public void InvestementValue() throws Exception{
		InvestementValueTB.clear();
		//InvestementValueTB.sendKeys(CF.getRandomValue(0));
	}
}
