package src.com.pack.common.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import src.com.pack.base.CommonFunction;
import src.com.pack.base.TestBaseSetup;

public class SelfAccreditationPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	@FindBy(xpath = "//span[@class='checkmark']")
	List<WebElement> SelfAccreditation;
	@FindBy(xpath = "//button[text()='Continue']")
	private WebElement ContinueBtn;
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement ContinueBtn_Nxt;
	@FindBy(xpath = "//button[text()='I agree ']")
	private WebElement AgreeBtn;

	public SelfAccreditationPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		CF = new CommonFunction();
	}

	public void AccreditionOption() throws Exception {
		SelfAccreditation.get(CF.getRandomValueIncludingZero(SelfAccreditation.size())).click();
		Thread.sleep(500);
	}

	public void AccreditionAgree() throws Exception {
		AgreeBtn.click();
		Thread.sleep(500);
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
