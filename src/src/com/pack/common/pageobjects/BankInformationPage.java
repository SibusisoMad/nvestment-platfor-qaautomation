package src.com.pack.common.pageobjects;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.github.javafaker.Faker;

import src.com.pack.base.TestBaseSetup;

public class BankInformationPage extends TestBaseSetup {
	WebDriver driver;
	@FindBy(id="z0__CountryId")							private WebElement CountrySel;
	@FindBy(id="z0__BankName")							private WebElement BankNameTB;
	@FindBy(id="z0__AddressLine")						private WebElement AddressTB;
	@FindBy(id="z0__TownCity")							private WebElement CityTB;
	@FindBy(id="z0__State")								private WebElement StateTB;
	@FindBy(id="z0__BankInstitutionNumber")				private WebElement BankInstuTB;
	@FindBy(id="z0__IBAN")								private WebElement IbanTB;
	@FindBy(id="z0__BICSWIFT")							private WebElement BicSwiftTB;
	@FindBy(id="z0__AccountNumber")						private WebElement AccountNumberTB;
	@FindBy(id="z0__SortCode")							private WebElement SortCodeTB;
	@FindBy(id="z0__CurrencyId")   						private WebElement CurrencySel;
	@FindBy(id="z0__AccountName")						private WebElement AccountNameTB;
	@FindBy(id="z0__BranchTransitNumber")				private WebElement BranchTransitTB;
	@FindBy(id="z0__RoutingNumber")						private WebElement RoutingNumTB;
	@FindBy(xpath="//button[text()='Continue']")		private WebElement ContinueBtn;
	Faker faker;
	
	public BankInformationPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker= new Faker();
	}

	public void CountryDetail() throws Exception{
		Select select = new Select(CountrySel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(getRandomValue(l.size()));
		Thread.sleep(500);
		BankName();
	}
	
	public void BankName() throws Exception{
		BankNameTB.clear();
		BankNameTB.sendKeys("HSBC Bank UK");
		Address();
	}
	public void Address() throws Exception{
		AddressTB.clear();
		AddressTB.sendKeys(faker.address().fullAddress());
		City() ;
	}
	public void City() throws Exception{
		CityTB.clear();
		CityTB.sendKeys(faker.address().city());
		State();
	}
	
	public void State() throws Exception{
		StateTB.clear();
		StateTB.sendKeys(faker.address().state());
		BankInstitution();
	}
	public void BankInstitution() throws Exception{
		BankInstuTB.clear();
		BankInstuTB.sendKeys(faker.address().zipCode());
		IBAN();
	}
	public void IBAN() throws Exception{
		IbanTB.clear();
		IbanTB.sendKeys(faker.finance().iban());
		Swift();
	}
	
	public void Swift() throws Exception{
		BicSwiftTB.clear();
		BicSwiftTB.sendKeys("RFTFDSEF");
		AccountNumber();
	}
	public void AccountNumber() throws Exception{
		AccountNumberTB.clear();
		AccountNumberTB.sendKeys((faker.business().creditCardNumber()).replaceAll("-", ""));
		SortCode();
	}
	public void SortCode() throws Exception{
		SortCodeTB.clear();
		SortCodeTB.sendKeys("22-33-44");

		Currency();
	}
	
	public void Currency() throws Exception{
		Select select = new Select(CurrencySel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(getRandomValue(l.size()));
		Thread.sleep(500);
		AccountName();
		
	}
	public void AccountName() throws Exception{
		AccountNameTB.clear();
		AccountNameTB.sendKeys(faker.company().name());
		BranchTransit();
	}
	public void BranchTransit() throws Exception{
		BranchTransitTB.clear();
		BranchTransitTB.sendKeys("87878");
		RoutingNumber();
	}
	public void RoutingNumber() throws Exception{
		RoutingNumTB.clear();
		RoutingNumTB.sendKeys("220130912");
	}
	public void Continue() throws Exception{
		ContinueBtn.click();
		Thread.sleep(500);
	}
	
	
	public static int getRandomValue(int RecordData) {
	    int record = new Random().nextInt(RecordData);
	    if(record==0) {
	    	record++;
	    }
//	    System.out.println("RV-"+record);
	    return record;
	}
}
