package src.com.pack.common.pageobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import src.com.pack.base.TestBaseSetup;

public class EmailCheckPage extends TestBaseSetup {

	WebDriver driver;

	@FindBy(name = "loginfmt")
	private WebElement EmailOutlook;
	@FindBy(id = "idSIButton9")
	List<WebElement> EmailSubmitBtn;
	@FindBy(name = "passwd")
	private WebElement EmailPassword;
	@FindBy(id = "mectrl_headerPicture")
	private WebElement topheader;
	@FindBy(id = "OwaOpenTargetMailboxLink")
	private WebElement openMailBox;
	@FindBy(xpath = "//div[@title='Test Mailbox']/button/span/i")
	private WebElement emailBox;
	@FindBy(xpath = "(//div[@title='Inbox'])[3]")
	private WebElement clickInbox;
	@FindBy(xpath = "(//div[@title='Junk Email'])[2]")
	private WebElement clickjunk;
	@FindBy(xpath = "//div[@role='listbox']/div/div/div")
	List<WebElement> clickEmail;
	@FindBy(xpath = "//div[@role='listbox']/div/div/div/div/div/div/div/div/span")
	List<WebElement> timeEmail;
	@FindBy(xpath = "//div[@role='listbox']/div/div/div/div/div/div/div[2]/div[2]/div/span")
	List<WebElement> subjectEmail;
	@FindBy(xpath = "//a[text()='Verify Email']")
	private WebElement RegisterAccount;

	public EmailCheckPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void openOutlook() throws Exception {
		driver.navigate().to("https://outlook.office365.com/");
		Reporter.log("Open Outlook and Verify Email");
	}

	public void enterEmail(String outlookemail) throws Exception {
		EmailOutlook.clear();
		EmailOutlook.sendKeys(outlookemail);
		EmailSubmitBtn.get(0).click();

		Reporter.log("Outlook Email Entered - " + outlookemail);
	}

	public void enterPassword(String outlookpassword) throws Exception {
		Thread.sleep(2000);
		EmailPassword.clear();
		EmailPassword.sendKeys(outlookpassword);
		EmailSubmitBtn.get(0).click();
		Thread.sleep(2000);
		boolean checkemail = EmailSubmitBtn.size() != 0;
		if (checkemail) {
			EmailSubmitBtn.get(0).click();
			Thread.sleep(2000);
		}
		Reporter.log("Email Password Entered - ");
	}

	public void ClickInbox() throws Exception {
//		WebDriverWait wait = new WebDriverWait(driver, 20);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Test Mailbox']/button/span/i")));
		Thread.sleep(5000);
		emailBox.click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated((By) clickInbox));
		Thread.sleep(5000);
		clickInbox.click();
		Thread.sleep(2000);
//		clickjunk.click();
//		Thread.sleep(2000);
	}

	public void checkEmail() throws Exception {

		String emailtime = timeEmail.get(0).getText();

		String emailSubject = subjectEmail.get(0).getText();
		System.out.println(emailSubject);
		System.out.println(emailtime);
		clickEmail.get(0).click();
		Thread.sleep(2000);

	}

	public void clickRegister() throws Exception {
		String ActivationUrl = RegisterAccount.getAttribute("href");
		System.out.println(ActivationUrl);

		driver.navigate().to(ActivationUrl);
		// RegisterAccount.click();
		Thread.sleep(2000);

	}

	public void yopmail(String emailc) throws Exception {
		driver.navigate().to("http://www.yopmail.com/en/");
		driver.findElement(By.id("login")).sendKeys(emailc);
		driver.findElement(By.xpath("//input[contains(@value,'Check Inbox')]")).click();
		int size = driver.findElements(By.tagName("iframe")).size();
		System.out.println("total frame- " + size);
		WebElement fr = driver.findElement(By.xpath("//iframe[@id='ifinbox']"));
		driver.switchTo().frame(fr);
		driver.findElement(By.id("m1")).click();
		Thread.sleep(500);
		driver.switchTo().defaultContent();
		WebElement frs = driver.findElement(By.xpath("//iframe[@id='ifmail']"));
		driver.switchTo().frame(frs);

	}

	public String getTimehhmm() {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
