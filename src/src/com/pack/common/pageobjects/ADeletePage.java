package src.com.pack.common.pageobjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;
import src.com.pack.base.TestBaseSetup;

public class ADeletePage  extends TestBaseSetup{
	WebDriver driver;
	CommonFunction CF;
	ADealPage dealpage;
	@FindBy(xpath="//div[@id='documents']//table/tbody/tr") 					List<WebElement> NumberofImage;
	@FindBy(xpath="//div[@id='deleteDocumentModal']//button[text()='Delete']") 	private WebElement DeleteBtn;
	@FindBy(xpath="//table[@id='deal-table']/tbody/tr") 						List<WebElement> NumberofDeal;
	@FindBy(xpath="//a[contains(@data-target,'deleteDeal')]") 					private WebElement DeleteDeal;
	@FindBy(xpath="//div[@id='deleteDealModal']//button[text()='Yes']") 		private WebElement DeleteDealYes;
	
	public ADeletePage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		dealpage = new ADealPage(driver);
	}	
	public void deleteInvestorImage() throws Exception{
		int totalimage= NumberofImage.size();
		Reporter.log("Total Image -"+totalimage);
		driver.findElement(By.xpath("//div[@id='documents']//table/tbody/tr/td[4]/form/span/a")).click();
		Thread.sleep(500);
		DeleteBtn.click();
		Thread.sleep(500);
		int imageNow= NumberofImage.size();
		Reporter.log("After Delete Image -"+imageNow);
	}
	public void deleteDeal() throws Exception{
		dealpage.Deallink();
		dealpage.searchDeal();
		Thread.sleep(2000);
		int totaldeal = NumberofDeal.size();
		Reporter.log("Total Deal -"+totaldeal);
		String DealNo = NumberofDeal.get(0).getText();
		Reporter.log("Deal to be Deleted-"+DealNo);
		driver.findElement(By.xpath("//table[@id='deal-table']/tbody/tr/td/a")).click();
		//NumberofDeal.get(0).click();
		Thread.sleep(500);
		DeleteDeal.click();
		Thread.sleep(500);
		DeleteDealYes.click();
		Reporter.log("Deal Deleted Successfully");
		Thread.sleep(1000);
		dealpage.searchDeal();
		Thread.sleep(1000);
		int nowdeal = NumberofDeal.size();
		Reporter.log("Total Deal Now -"+nowdeal);
	}
	

}
