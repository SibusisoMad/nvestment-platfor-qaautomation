package src.com.pack.common.pageobjects;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class ADealInvestorPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	@FindBy(xpath ="//*[@href='/DealInvestor']")							private WebElement DealInvestorLink;
	@FindBy(id="searchBox")													private WebElement SearchBoxTB;
	@FindBy(xpath ="//button[contains(@data-original-title,'filters')]")	private WebElement FiltersBtn;
	@FindBy(name="Statuses")												private WebElement StatusSel;
	@FindBy(xpath="//table[@id='dealinvestor-table']/tbody/tr")				List <WebElement> SearchRecord;
	@FindBy(xpath ="//a[contains(@href,'dealInvestorId')]")					private WebElement EditInvestorBtn;
	@FindBy(id="LinkedInProfileUrl")										private WebElement LinkedinTB;
	@FindBy(id="InvestorTypeId")											private WebElement InvestorTypSel;
	@FindBy(id="Address_AddressLine1")										private WebElement Address1TB;
	@FindBy(id="Address_TownCity")											private WebElement TownTB;
	@FindBy(id="Address_Postcode")											private WebElement PostTB;
	@FindBy(id="ContactNumber")												private WebElement ContactTB;
	@FindBy(id="FacebookProfileUrl")										private WebElement FacebookTB;
	@FindBy(id="Address_CountryId")											private WebElement CountrySel;
	@FindBy(id="Address_AddressLine2")										private WebElement Address2TB;
	@FindBy(id="Address_State")												private WebElement StateTB;
	@FindBy(xpath="//button[text()='Submit']")								private WebElement SubmitBtn;
	@FindBy(xpath ="//a[contains(@href,'Deposit')]")						private WebElement AddDepositBtn;
	@FindBy(xpath ="//a[contains(@href,'InvestorDocument')]")				private WebElement AddDocumentBtn;
	@FindBy(id="DepositDate")												private WebElement DepositDateTB;
	@FindBy(id="DepositAmount")												private WebElement DepositAmountTB;
	@FindBy(id="CurrencyId")												private WebElement CurrencySel;
	@FindBy(xpath ="//a[contains(@href,'EditBank')]")						private WebElement EditBankBtn;
	@FindBy(xpath ="//a[contains(@href,'Logout')]")							List<WebElement> Logout;
	@FindBy(xpath ="//h6")													List<WebElement> headertext;
	@FindBy(id="File")														private WebElement FileTB;
	@FindBy(id="DocumentTypeId")											private WebElement DocumentTypeSel;
	@FindBy(xpath ="//div[@class='dropdown']/button")						private WebElement Registrationdropdown;
	@FindBy(xpath ="//a[@data-status='Authorised']")						List<WebElement> RegistrationStatus;
	private String[] DepositValue = new String[]{"5000","1000","1500","2000","2500","3000","3500","4000","4500","10000"};

	
	
	Faker faker;
	public ADealInvestorPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker= new Faker();
		CF = new CommonFunction();
	}
	
	public void OpenInvestor() throws Exception{
		DealInvestorlink();
		SearchBox();
//		StatusRegister();
		SearchResult();
		OpenInvestorDetail();
	}
	
	public void UpdateInvestorDetail() throws Exception{
		EditInvestor();
		LinkedinInvestor();
		Submit();
	}
	
	public void AddDeposit() throws Exception{
		for(int i=1;i<=3;i++) {
		EditBankbtn();
		Select select = new Select(CurrencySel);
	//	List<WebElement> l = select.getOptions();
		select.selectByIndex(i);
		Submit();
		DepositAdd();
		DepositDate();
		DepositAmount();
		Currency();
		Submit();}
	}
	
	public void UpdateRegistrationStatustoAuthorise() throws Exception{
		boolean authoris = RegistrationStatus.size()!=0;
		if(authoris) {
			Registrationdropdown.click();
			Thread.sleep(500);
			RegistrationStatus.get(0).click();
			Thread.sleep(500);
		}
	}
	public void AddDocument(String file) throws Exception{
		for(int i=0;i<=1;i++) {
		DocumentAdd();
		FilePath(file);
		Select select = new Select(DocumentTypeSel);
	//	List<WebElement> l = select.getOptions();
		select.selectByIndex(i);
		Submit();}
	}
	public boolean checkPage() throws Exception{
		String Admin = driver.getCurrentUrl();
		boolean logout = Logout.size()!=0;
		String header = null;
		boolean heade = headertext.size()!=0;
		if(heade) {
		header = headertext.get(0).getText();}
		FileSearch fs = new FileSearch();
		String user =  fs.readFromFile();
		boolean correctpage;
		if(Admin.contains("admin")&&logout&&header.contains(user)) {
			correctpage=true;
		}else {
			correctpage=false;
		}
		return correctpage;
	}
	
	public void DealInvestorlink() throws Exception{
		DealInvestorLink.click();
		Thread.sleep(500);
		
	}
	public void SearchBox() throws Exception{
		FileSearch fs = new FileSearch();
		String user =  fs.readFromFile();
		SearchBoxTB.clear();
		SearchBoxTB.sendKeys(user);
		Thread.sleep(1000);
	}
	public void StatusRegister() throws Exception{
		Select Status = new Select(StatusSel);
//		List<WebElement> l = Status.getOptions();
		Status.selectByIndex(0);
	}
	
	public void SearchResult() throws Exception{
		System.out.println("Search result="+SearchRecord.size());
	}
	
	public void OpenInvestorDetail() throws Exception{
		Thread.sleep(1000);
		driver.findElement(By.xpath("//table[@id='dealinvestor-table']/tbody/tr/td/a")).click();
		Thread.sleep(500);
	}
	public void EditInvestor() throws Exception{
		EditInvestorBtn.click();
		Thread.sleep(500);
	}
	
	public void EditBankbtn() throws Exception{
		EditBankBtn.click();
		Thread.sleep(500);
	}
	public void LinkedinInvestor() throws Exception{
		LinkedinTB.clear();
		LinkedinTB.sendKeys("http://www.linkedin.com");
		TypeInvestor();
	}
	public void TypeInvestor() throws Exception{
		Select select = new Select(InvestorTypSel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
		Thread.sleep(500);
		Address1Investor();
	}
	
	public void Address1Investor() throws Exception{
		Address1TB.clear();
		Address1TB.sendKeys(faker.address().fullAddress());
		TownInvestor();
	}
	public void TownInvestor() throws Exception{
		TownTB.clear();
		TownTB.sendKeys(faker.address().cityName());
		PostInvestor();
	}
	public void PostInvestor() throws Exception{
		PostTB.clear();
		PostTB.sendKeys(faker.address().zipCode());
		ContactInvestor();
	}
	
	public void ContactInvestor() throws Exception{
		ContactTB.clear();
		ContactTB.sendKeys("+"+Math.abs(ThreadLocalRandom.current().nextInt()));
		FacebookInvestor();
	}
	public void FacebookInvestor() throws Exception{
		FacebookTB.clear();
		FacebookTB.sendKeys("http://facebook.com");
		CountryInvestor();
	}
	public void CountryInvestor() throws Exception{
		Select select = new Select(CountrySel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
		Thread.sleep(500);
		Address2Investor();
	}
	
	public void Address2Investor() throws Exception{
		Address2TB.clear();
		Address2TB.sendKeys(faker.address().secondaryAddress());
		StateInvestor();
	}
	
	public void StateInvestor() throws Exception{
		StateTB.clear();
		StateTB.sendKeys(faker.address().state());
	}
	public void Submit() throws Exception{
		SubmitBtn.click();
		Thread.sleep(500);
	}
	
	public void FiltersButton() throws Exception{
		FiltersBtn.click();
	}

	public void DepositAdd() throws Exception{
		AddDepositBtn.click();
		Thread.sleep(1000);
	}
	
	public void DepositDate() throws Exception{
		DepositDateTB.clear();
		DepositDateTB.sendKeys(CF.DateReturn(0));
	}
	public void DepositAmount() throws Exception{
		DepositAmountTB.clear();
		DepositAmountTB.sendKeys(CF.getRandomValue(DepositValue));
		//DepositAmountTB.sendKeys("1000");
	}
	public void Currency() throws Exception{
		Select select = new Select(CurrencySel);
		select.selectByIndex(0);
		Thread.sleep(500);
	}
	public void DocumentAdd() throws Exception{
		AddDocumentBtn.click();
		Thread.sleep(1000);
	}
	public void FilePath(String FilePath) throws Exception{
		FileTB.clear();
		FileTB.sendKeys(FilePath);
	}
//	public String DateReturn(int days) throws Exception{
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//		Date date = new Date();
//		Calendar c = Calendar.getInstance(); 
//		c.setTime(date);
//		c.add(Calendar.DATE, days);
//		date = c.getTime();
//		System.out.println(formatter.format(date));
//		return formatter.format(date);
//		
//	}
//	public static int getRandomValue(int RecordData) {
//	    int record = new Random().nextInt(RecordData);
//	    if(record==0) {
//	    	record++;
//	    }
////	    System.out.println("RV-"+record);
//	    return record;
//	}
	

}
