package src.com.pack.common.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import src.com.pack.base.TestBaseSetup;
import src.com.pack.common.pageobjects.IconPage;
import src.com.pack.common.pageobjects.LoginPage;

public class IconPageTest extends TestBaseSetup {
	WebDriver driver;
	public String userEmail;
	public String userPassword;
	LoginPage LoginPage;
	IconPage IconPage;

	@BeforeClass
	public void setUp(ITestContext context) {
		driver = getDriver();
		userEmail = getUserEmail();
		userPassword=getUserPassword();

		try {
			LoginPage = new LoginPage(driver);
			IconPage = new IconPage(driver);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 10, groups = { "Notification View Page" }, description = "Check notification and Set Prefrences")
	public void NotificationSet() throws Exception {
		LoginPage.logInlink();
		LoginPage.enterEmail(userEmail);
		LoginPage.enterPassword(userPassword);
		System.out.println(userPassword);
		System.out.println(userEmail);
		LoginPage.submitBtn();
		IconPage.Iconlink();
		IconPage.Notificationlink();
		IconPage.SelectNotificationItems();
		IconPage.SaveChanges();
	}
		
		@Test(priority = 20, groups = { "My details View Page" }, description = "Check My details and Edit Details")
		public void MyProfileDetails() throws Exception {
		IconPage.Iconlink();
		IconPage.MyDetailslink();
		IconPage.DocumentEditlink();
		IconPage.DocumentsUpload();
		IconPage.DocumentDeletelink();
		IconPage.Backlink();
	}
}