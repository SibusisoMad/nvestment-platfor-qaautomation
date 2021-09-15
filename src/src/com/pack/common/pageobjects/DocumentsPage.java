package src.com.pack.common.pageobjects;


import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import src.com.pack.base.TestBaseSetup;

public class DocumentsPage extends TestBaseSetup{
	WebDriver driver;
	@FindBy(xpath ="//div[contains(@data-uploadurl,'ProofOfAddress')]")							private WebElement AddressProof;
	@FindBy(xpath ="//div[contains(@data-uploadurl,'ProofOfIdentity')]")						private WebElement IdentityProof;
	@FindBy(xpath="//button[text()='Continue']")			private WebElement ContinueBtn;
	public DocumentsPage(WebDriver driver) throws Exception  {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void AddressProofFile(String FilePath) throws Exception{
		
	//	DropFile(new File(FilePath), AddressProof, 0, 0);
		AddressProof.sendKeys(FilePath);
		// creating object of Robot class
//	    Robot rb = new Robot();
	 
	    // copying File path to Clipboard
//	    StringSelection str = new StringSelection(FilePath);
//	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
	
	     // press Contol+V for pasting
//	     rb.keyPress(KeyEvent.VK_CONTROL);
//	     rb.keyPress(KeyEvent.VK_V);
	  
	    // release Contol+V for pasting
//	    rb.keyRelease(KeyEvent.VK_CONTROL);
//	    rb.keyRelease(KeyEvent.VK_V);
	 
	    // for pressing and releasing Enter
//	    rb.keyPress(KeyEvent.VK_ENTER);
//	    rb.keyRelease(KeyEvent.VK_ENTER);
	//	AddressProof.sendKeys(FilePath);
	}
	public void IdentityProofFile(String FilePath) throws Exception{
	//	IdentityProof.click();
		IdentityProof.sendKeys(FilePath);
	}
	public void Continue() throws Exception{
		ContinueBtn.click();
		Thread.sleep(500);
	}
	
	public static void DropFile(File filePath, WebElement target, int offsetX, int offsetY) {
	    if(!filePath.exists())
	        throw new WebDriverException("File not found: " + filePath.toString());
	   
	    WebDriver driver = ((RemoteWebElement)target).getWrappedDriver();
	   
	    JavascriptExecutor jse = (JavascriptExecutor)driver;
	    WebDriverWait wait = new WebDriverWait(driver, 30);

	    String JS_DROP_FILE =
	        "var target = arguments[0]," +
	        "    offsetX = arguments[1]," +
	        "    offsetY = arguments[2]," +
	        "    document = target.ownerDocument || document," +
	        "    window = document.defaultView || window;" +
	        "" +
	        "var input = document.createElement('INPUT');" +
	        "input.type = 'file';" +
	        "input.style.display = 'none';" +
	        "input.onchange = function () {" +
	        "  var rect = target.getBoundingClientRect()," +
	        "      x = rect.left + (offsetX || (rect.width >> 1))," +
	        "      y = rect.top + (offsetY || (rect.height >> 1))," +
	        "      dataTransfer = { files: this.files };" +
	        "" +
	        "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
	        "    var evt = document.createEvent('MouseEvent');" +
	        "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
	        "    evt.dataTransfer = dataTransfer;" +
	        "    target.dispatchEvent(evt);" +
	        "  });" +
	        "" +
	        "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
	        "};" +
	        "document.body.appendChild(input);" +
	        "return input;";

	    WebElement input =  (WebElement)jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
	    input.sendKeys(filePath.getAbsoluteFile().toString());
	    wait.until(ExpectedConditions.stalenessOf(input));
	}


}
