package src.com.pack.common.pageobjects;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.TestBaseSetup;

public class ContactInformationPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	@FindBy(id = "LinkedInProfileUrl")
	private WebElement LinkedinTB;

	@FindBy(id = "Address_AddressLine1")
	private WebElement Address1TB;
	@FindBy(id = "Address_TownCity")
	private WebElement TownTB;
	@FindBy(id = "Address_Postcode")
	private WebElement PostTB;
	@FindBy(id = "ContactNumber")
	private WebElement ContactTB;
	@FindBy(id = "FacebookProfileUrl")
	private WebElement FacebookTB;
	@FindBy(id = "Address_CountryId")
	private WebElement CountrySel;
	@FindBy(id = "Address_AddressLine2")
	private WebElement Address2TB;
	@FindBy(id = "Address_State")
	private WebElement StateTB;
	@FindBy(xpath = "//button[text()='Continue']")
	private WebElement ContinueBtn;
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement ContinueBtn_Nxt;
	@FindBy(xpath = "//label[1]/input[@id='IsCompanyRegistration']")
	private WebElement IndiviCompaRB;
	Faker faker;

	public ContactInformationPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		CF = new CommonFunction();
	}

	public void IndividualCompany() throws Exception {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		IndiviCompaRB.click();
	}

	public void LinkedinInvestor() throws Exception {
		LinkedinTB.clear();
		LinkedinTB.sendKeys("http://www.linkedin.com");
		Address1Investor();
	}

	public void Address1Investor() throws Exception {
		Address1TB.clear();
		Address1TB.sendKeys(faker.address().fullAddress());
		TownInvestor();
	}

	public void TownInvestor() throws Exception {
		TownTB.clear();
		TownTB.sendKeys(faker.address().cityName());
		PostInvestor();
	}

	public void PostInvestor() throws Exception {
		PostTB.clear();
		PostTB.sendKeys(faker.address().zipCode());
		ContactInvestor();
	}

	public void ContactInvestor() throws Exception {
		ContactTB.clear();
		ContactTB.sendKeys("+" + Math.abs(ThreadLocalRandom.current().nextInt()));
		FacebookInvestor();
	}

	public void FacebookInvestor() throws Exception {
		FacebookTB.clear();
		FacebookTB.sendKeys("http://facebook.com");
		CountryInvestor();
	}

	public void CountryInvestor() throws Exception {
		Select select = new Select(CountrySel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
		Thread.sleep(500);
		Address2Investor();
	}

	public void Address2Investor() throws Exception {
		Address2TB.clear();
		Address2TB.sendKeys(faker.address().secondaryAddress());
		StateInvestor();
	}

	public void StateInvestor() throws Exception {
		StateTB.clear();
		StateTB.sendKeys(faker.address().state());

	}

	public void Continue() throws Exception {
		ContinueBtn.click();
		Thread.sleep(500);
	}
	
	public void Continue_Next() throws Exception {
		ContinueBtn_Nxt.click();
		Thread.sleep(500);
	}
//	public static int getRandomValue(int RecordData) {
//	    int record = new Random().nextInt(RecordData);
//	    if(record==0) {
//	    	record++;
//	    }
////	    System.out.println("RV-"+record);
//	    return record;
//	}

}
