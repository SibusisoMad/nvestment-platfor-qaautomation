package src.com.pack.common.pageobjects;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Reporter;

import com.github.javafaker.Faker;
import src.com.pack.base.CommonFunction;
import src.com.pack.base.FileSearch;

import src.com.pack.base.TestBaseSetup;

public class IconPage extends TestBaseSetup {
	WebDriver driver;
	CommonFunction CF;
	Faker faker;
	FileSearch fs;

	@FindBy(id = "profile")
	private WebElement IconTB;
	@FindBy(xpath = "//a[@href='/Notifications']")
	private WebElement NotificationTB;
	@FindBy(id = "Items_0__IsActive")
	private WebElement Notify0;
	@FindBy(xpath = "//button[text()='Save Changes']")
	private WebElement SaveChangesTB;
	@FindBy(xpath = "//a[@href='/MyDetails']")
	private WebElement MyDetailsTB;
	@FindBy(xpath = "//a[@href='/Documents']")
	private WebElement EditDocumentTB;
	@FindBy(xpath = "//img[@src='/images/upload.svg']")
	private WebElement UploadButton;
	@FindBy(xpath = "//img[@src='/images/close.svg']")
	private WebElement DeleteButton;
	@FindBy(xpath = "//div[text()='Back']")
	private WebElement BackTB;

	public IconPage(WebDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		faker = new Faker();
		CF = new CommonFunction();
		fs = new FileSearch();
	}

	public void Iconlink() throws Exception {
		IconTB.click();
		Thread.sleep(2000);
	}

	public void Notificationlink() throws Exception {
		NotificationTB.click();
		Thread.sleep(2000);
	}

	public void SelectNotificationItems() throws Exception {
		Integer random = CF.getRandomValue(6);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		WebElement ele = driver.findElement(By.xpath("//input[@id='Items_" + random + "__IsActive']"));
		WebElement box = driver.findElement(By.xpath("//input[@id='Items_0__Value']"));

		//To check if first option is checked or unchecked 
		if (random == 0 && box.isEnabled())
			jse.executeScript("arguments[0].click()", ele);
		else if (random == 0) {
			jse.executeScript("arguments[0].click()", ele);
			box.sendKeys(Integer.toString(CF.getRandomValue(99)));
		} else
			jse.executeScript("arguments[0].click()", ele);

	}

	public void SaveChanges() throws Exception {
		SaveChangesTB.click();
		Thread.sleep(2000);
	}

	public void MyDetailslink() throws Exception {
		MyDetailsTB.click();
		Thread.sleep(2000);
	}

	public void DocumentEditlink() throws Exception {
		Thread.sleep(200);
		EditDocumentTB.click();
		Thread.sleep(1000);
	}

	public void DocumentsUpload() throws Exception {
		Thread.sleep(2000);
		UploadButton.click(); // Clicking on upload button
		String FilePath = new File("data/Filename.PNG").getAbsolutePath(); // Getting path of the Filename.png which
																			// have screenshot of the Fileuploadpath
		String OpenButton = new File("data/Open.PNG").getAbsolutePath(); // Getting path of the Open.png which have
																			// screenshot of the Open Button
		String UploadDoc = new File("data/Logo-Test.png").getAbsolutePath(); // Getting path of the Logo-Test.png.png
																				// which has to be uploaded
		Screen s = new Screen(); // Creating object of Screen class to access windows screen
		Pattern fileInputTextBox = new Pattern(FilePath); // Creating a pattern based on the image
		Pattern openButton = new Pattern(OpenButton);
		s.type(fileInputTextBox, UploadDoc); // Typing the location of upload document in the filepath present in
												// windows
		s.click(openButton); // Clicking the open button present on windows
		Thread.sleep(2000);
	}

	public void DocumentDeletelink() throws Exception {
		DeleteButton.click();
		Thread.sleep(2000);
	}

	public void Backlink() throws Exception {
		BackTB.click();
		Thread.sleep(2000);
	}
}
