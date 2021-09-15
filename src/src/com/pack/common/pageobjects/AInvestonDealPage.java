package src.com.pack.common.pageobjects;

import java.text.DecimalFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class AInvestonDealPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	DecimalFormat df;
	FileSearch fs;
	@FindBy(xpath="//div[@id='deposits']/div/dl/dd") 				List<WebElement> NumberofDeposit;
	@FindBy(xpath ="//a[contains(@href,'AddInvestment')]")			private WebElement AddInvestmentBtn;
	@FindBy(id="DealInvestorId")									private WebElement DealInvestorSel;
	@FindBy(id="InvestmentAmount")									private WebElement InvestmentAmt;
	@FindBy(xpath="//button[text()='Submit']")					private WebElement SubmitBtn;
	@FindBy(xpath ="//a[contains(@href,'AddDocument')]")			private WebElement AddDocumentBtn;
	@FindBy(id="File")									private WebElement FileTB;
	@FindBy(id="VisibilityTypeId")									private WebElement FileVisibilitySel;
	
	private  float amountFunded;
	private  float amountRemaining;
	private  float investFund;
	private Float[] InitialBalance;
	
	
	public AInvestonDealPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		CF = new CommonFunction();	
		fs = new FileSearch();
		 df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
	}
	public String[]  AddDocumentonDeal(String filepath, String depositFile) throws Exception{
		storeinvestorDeposit(depositFile);
		String dealDetail[] = getDeal();
		openDeal(dealDetail[2]);
		adddocumentBtn();
		fileaddPath(filepath);
		fileVisibility();
		Submit();
		Reporter.log("Document added successfully on Deal");
		return dealDetail;
	}
	public void InvestOnRandomDeal(String depositFile, String[] dealDetail) throws Exception {
		InitialBalance= getinvestorDeposit(depositFile, dealDetail[0]);
		Reporter.log("Investor Deposits In Currency -"+dealDetail[0]+ "<br> Clear Balance -  "+InitialBalance[0]+"<br> Committed Funds - "+InitialBalance[1]);
		Reporter.log("Deals Originated<br>Deal Currency-"+dealDetail[0]+"<br> Sought -"+ dealDetail[1]+ "<br> Deal Link  "+dealDetail[2]);
		amountFunded =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[6]/span")).getAttribute("data-value"));
		amountRemaining =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[7]/span")).getAttribute("data-value"));
		Reporter.log("Amount Funded - "+amountFunded+"<br> Amount Remaining - "+ amountRemaining);
		addinvestmentBtn();
		InvestorSelect();
		InvestorInvestment(InitialBalance[0]);
		Submit();
		amountFunded =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[6]/span")).getAttribute("data-value"));
		amountRemaining =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[7]/span")).getAttribute("data-value"));
		Reporter.log("Amount Funded - "+amountFunded+"<br> Amount Remaining -  "+ amountRemaining);
	}
	
	public void VerifyAmountInInvestor(String DepositFile,String[] dealDetail) throws Exception{
		System.out.println("Verify Investor Amount");
	//	Float[] InitialBalance= getinvestorDeposit(DepositFile, Currency);
//		System.out.println("InitialBalance[0]"+ InitialBalance[0]);
//		Reporter.log("Initial current Balance investor-"+ InitialBalance[0]);
//		Reporter.log("Investor Invested-"+ investFund);
//		System.out.println("investFund"+investFund);
//		float CurrentBalance= InitialBalance[0]-investFund;
//		df.format(CurrentBalance);
//		Reporter.log("Current Balance Investor -"+ CurrentBalance);
//		System.out.println("CurrentBalance"+CurrentBalance);
//		Reporter.log("Initial Total Investment"+InitialBalance[1]);
//		Reporter.log("Investor Invested-"+ investFund);
//		float CurrentInvestment = InitialBalance[1]+investFund;
//		Reporter.log("Current Total Investment"+CurrentInvestment);
//		df.format(CurrentInvestment);
		storeinvestorDeposit(DepositFile);
		Float[] CurrentBal= getinvestorDeposit(DepositFile,dealDetail[0]);
		Reporter.log("Investor Deposits In Currency -"+dealDetail[0]+ "<br> Clear Balance -  "+CurrentBal[0]+"<br> Committed Funds - "+CurrentBal[1]);

	}
	public void DeleteInvestement(String DepositFile) throws Exception{
		storeinvestorDeposit(DepositFile);
		int noofInvest;
		String DealFunded;
		String AmountRemaining;
		noofInvest= driver.findElements(By.xpath("//div[@id='investments']//table/tbody/tr")).size();
		int record =CF.getRandomValue(noofInvest);
		String InvestementValue = driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+record+"]/td[3]/span")).getAttribute("data-value");
		String CurrencyInv = driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+record+"]/td[3]/span")).getAttribute("data-currency");
		String InvestmentURL = driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+record+"]/td[1]/a")).getAttribute("href");
		String InvestorURL = driver.getCurrentUrl();
		String InvestorName = driver.findElement(By.xpath("//div[@id='contact']/div/dl/dd")).getText();
		Float[] BeforeDeleteBalance= getinvestorDeposit(DepositFile,CurrencyInv);
		driver.navigate().to(InvestmentURL);
		DealFunded = driver.findElement(By.xpath("//div[@id='deal']/div/dl/dd[6]/span")).getAttribute("data-value");
		AmountRemaining = driver.findElement(By.xpath("//div[@id='deal']/div/dl/dd[7]/span")).getAttribute("data-value");
		System.out.println("CurBal"+BeforeDeleteBalance[0]);
		System.out.println("CurBal"+BeforeDeleteBalance[1]);
		noofInvest=driver.findElements(By.xpath("//div[@id='investments']//table/tbody/tr")).size();
		for(int i=1;i<=noofInvest;i++) {
			String Investname = driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+i+"]/td/a")).getText();
			String investvalue = driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+i+"]/td[3]/span")).getAttribute("data-value");
			if(Investname.contains(InvestorName)&&InvestementValue.equals(investvalue)) {
				driver.findElement(By.xpath("//div[@id='investments']//table/tbody/tr["+i+"]/td/form/span/a")).click();
				driver.findElement(By.xpath("//button[text()='Delete']")).click();
				Thread.sleep(10000);
				break;
			}
		}
		float dealfund = Float.parseFloat(DealFunded)-Float.parseFloat(InvestementValue);
		String DealFundeds = driver.findElement(By.xpath("//div[@id='deal']/div/dl/dd[6]/span")).getAttribute("data-value");
	//	Assert.assertEquals(df.format(Float.parseFloat(DealFundeds)), df.format(dealfund), "Deal Funded Value Matches");
		Reporter.log("Deal Fund initial- "+DealFunded+"<br> Investement Deleted "+InvestementValue+"<br> Current "+DealFundeds);

		float amountRemaing = Float.parseFloat(AmountRemaining)+Float.parseFloat(InvestementValue);
		String AmountRemainings = driver.findElement(By.xpath("//div[@id='deal']/div/dl/dd[7]/span")).getAttribute("data-value");
		AmountRemainings = AmountRemainings.replace(",", "");
		String AmountRemainin =df.format(Float.parseFloat(AmountRemainings));
	//	Assert.assertEquals(AmountRemainin,df.format(amountRemaing),"Amount Remaining Values Matches");
		Reporter.log("Amount Remaining initial- "+AmountRemaining+"<br> Investement Deleted "+InvestementValue+"<br> Current "+AmountRemainin);

		driver.navigate().to(InvestorURL);
		storeinvestorDeposit(DepositFile);
		float clearBalance = BeforeDeleteBalance[0]+Float.parseFloat(InvestementValue);
		Float[] AfterDeleteBalance= getinvestorDeposit(DepositFile,CurrencyInv);
		float currentClearBalance =AfterDeleteBalance[0];
	//	Assert.assertEquals(df.format(currentClearBalance),df.format(clearBalance),"Clear Balance Matches");
		Reporter.log("Clear Balance initial- "+BeforeDeleteBalance[0]+"<br> Investement Deleted "+InvestementValue+"<br> Current "+currentClearBalance);
		float commitedFunds =  BeforeDeleteBalance[1]-Float.parseFloat(InvestementValue);
		float CurrentcommitedFund =AfterDeleteBalance[1];
	//	Assert.assertEquals(df.format(CurrentcommitedFund),df.format(commitedFunds),"Current Commited Fund Balance Matches");
		Reporter.log("Commited Fund initial- "+BeforeDeleteBalance[1]+"<br> Investement Deleted "+InvestementValue+"<br> Current "+CurrentcommitedFund);
		
	}
	public void storeinvestorDeposit(String DepositFile) throws Exception{
		System.out.println(NumberofDeposit.size());
		fs.clearTheFile(DepositFile);
		fs.createFile(DepositFile);
		for(int i=1;i<=NumberofDeposit.size();i++) {
			String value=driver.findElement(By.xpath("//div[@id='deposits']/div/dl/dd["+i+"]/span")).getAttribute("data-value");
			String Currency=driver.findElement(By.xpath("//div[@id='deposits']/div/dl/dd["+i+"]/span")).getAttribute("data-currency");
		//	String value= NumberofDeposit.get(i).getAttribute("data-value");
		//	String currency=NumberofDeposit.get(i).getAttribute("data-currency");
		//	System.out.println(value+"-"+Currency);
			fs.writeInFile(DepositFile,Currency,value );
		}
	}
	public Float[] getinvestorDeposit(String DepositFile, String Currency) throws Exception{
		String[] tvalue= fs.searchinFile(DepositFile, Currency);
		Float[] investorDeposit = new Float[2];
		System.out.println(tvalue.length);
		for(int i=0;i<tvalue.length;i++) {
			//System.out.println(tvalue[i]);
			tvalue[i]=tvalue[i].replace(Currency+",", "");
			investorDeposit[i]= Float.parseFloat(tvalue[i]);
			System.out.println(investorDeposit[i]);
		}
		return investorDeposit;
	}
	public String[] getDeal() throws Exception{
		int deal = driver.findElements(By.xpath("//div[@id='dealsOriginated']//table/tbody/tr")).size();
		String[] dealDetail= new String[3];
		deal = CF.getRandomValue(deal);
		String dealcurrency=driver.findElement(By.xpath("//div[@id='dealsOriginated']//table/tbody/tr["+deal+"]/td[3]/span")).getAttribute("data-currency");
		String dealAmount = driver.findElement(By.xpath("//div[@id='dealsOriginated']//table/tbody/tr["+deal+"]/td[3]/span")).getAttribute("data-value");
		String dealLink = driver.findElement(By.xpath("//div[@id='dealsOriginated']//table/tbody/tr["+deal+"]/td/a")).getAttribute("href");
		dealDetail[0]= dealcurrency;
		dealDetail[1]= dealAmount;
		dealDetail[2]= dealLink;
		return dealDetail;
	}
	public void openDeal(String dealURL) throws Exception{
		System.out.println(dealURL);
		driver.navigate().to(dealURL);
		
	}
	public void InvestorSelect() throws Exception{
		String user =  fs.readFromFile();
		Select select = new Select(DealInvestorSel);
		List<WebElement> l = select.getOptions();
		for(int i=0;i<l.size();i++) {
			//System.out.println(l.get(i).getText());
			if(l.get(i).getText().contains(user)) 
			{
				//System.out.println("Got value");
				select.selectByIndex(i);
				break;
			}
		}
		Thread.sleep(500);
	}	
	public float InvestorInvestment(Float investorAmount) throws Exception{
		InvestmentAmt.clear();
		investFund= (float) (investorAmount*0.10);
		InvestmentAmt.sendKeys(Float. toString(investFund));
		Reporter.log("Amount Invested on Deal - "+investFund);
		return investFund;
	}
	public void Submit() throws Exception{
		SubmitBtn.click();
		Thread.sleep(500);
			}
	public void CheckAmount() throws Exception{
		float totalFunded= investFund+amountFunded;
		float totalFundRemaining = amountRemaining-investFund;
		amountFunded =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[6]/span")).getAttribute("data-value"));
		String amountFunde=df.format(amountFunded);
		amountRemaining =Float.parseFloat(driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd[7]/span")).getAttribute("data-value"));
		df.format(amountRemaining);
		Reporter.log("Initial Fund "+investFund+ " amount Funded "+amountFunde+ " Fund Remaining "+amountFunde );
		Assert.assertEquals(df.format(totalFunded), amountFunde, "Fund Remaining updated correctly");
		Reporter.log("Initial Amount "+amountRemaining+ " amount Funded "+amountFunde+ " Amount Remaining "+totalFundRemaining );
		Assert.assertEquals(totalFundRemaining,amountRemaining,"Amount Remaining Updated Correctly");		

	}
	public void addinvestmentBtn() throws Exception{
		AddInvestmentBtn.click();
		Thread.sleep(500);
	}
	public void adddocumentBtn() throws Exception{
		AddDocumentBtn.click();
		Thread.sleep(500);
	}
	public void fileaddPath(String filepath) throws Exception{
		FileTB.clear();
		FileTB.sendKeys(filepath);
		
	}
	public void fileVisibility() throws Exception{
		Select select = new Select(FileVisibilitySel);
		List<WebElement> l = select.getOptions();
		select.selectByIndex(CF.getRandomValue(l.size()));
		Thread.sleep(100);
		Reporter.log("File visibility Selected");
		
	}
	public String investorLink() throws Exception{
		String investorLink = driver.findElement(By.xpath("//div[@id='dealInvestor']//div/dl/dd/a")).getAttribute("href");
		return investorLink;
		
	}
	
}
