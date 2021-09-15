// <copyright file="TestBaseSetup.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.pack.base;
import java.io.BufferedWriter;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import src.com.pack.base.FileSearch;
/**
 * Base file for script execution. here browser setup and framework setup method is mentioned here.
 * 
 */
public class TestBaseSetup  {
	private static  WebDriver driver;
	private static  String Browser;
	private static  String CompanyName;
	private static  String AdminURL;
	private static  String AdminUser;
	private static  String AdminPassword;
	private static  String UsereMail;
	private static  String UserPassword;
	private static String downloadFilepath = "TestDownloads";
	public String appURL;
	//private String[] emaiDo = new String[]{"@junk.jun","@scrap.scr","@garbage.gar","@refuse.ref","@discard.dis"};
	BufferedWriter writer = null;
	public static  WebDriver getDriver() {
		return driver;
	}
	public static String getCompany(){
		return CompanyName;
	}
	public static String getAdminUrl(){
		return AdminURL;
	}
	public static String getAdminUser(){
		return AdminUser;
	}
	public static String getAdminPassword(){
		return AdminPassword;
	}
	public static String getUserEmail(){
		return UsereMail;
	}
	public static String getUserPassword(){
		return UserPassword;
	}
	public static String getBrowser(){
		return Browser;
	}
	private void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "Chrome":
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "Phone":
		case "phone":
			driver = initphoneChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}
	
	private static WebDriver initphoneChromeDriver(String appURL) {
		// iPad Pro, Galaxy S5
		
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "iPad Pro");
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		String basePath = new File("").getAbsolutePath();
		 System.out.println("Basep"+basePath);
	     String path = new File("Drivers/chromedriver.exe").getAbsolutePath();
	     System.out.println("Absp"+path);
		System.setProperty("webdriver.chrome.driver", path);
		@SuppressWarnings("deprecation")
		WebDriver driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		
		driver.navigate().to(appURL);
		
//		Reporter.log("URL- "+appURL);
//		boolean popcookie = driver.findElements(By.xpath("//div[@id='cookieConsent']/div[3]/button")).size()!=0;
//		  System.out.println(popcookie);
//			if(popcookie){
//				driver.findElement(By.xpath("//div[@id='cookieConsent']/div[3]/button")).click();
//			}
		return driver;	}
	/**
	 * Chrome driver setup here
	 * 
	 */
	private static WebDriver initChromeDriver(String appURL) { 
		 String basePath = new File("").getAbsolutePath();
		  System.out.println("Basep"+basePath);
		  String path = new File("Drivers/chromedriver.exe").getAbsolutePath();
		  System.out.println("Absp"+path);
		  System.setProperty("webdriver.chrome.driver", path);
		  HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		  chromePrefs.put("profile.default_content_settings.popups", 0);
		  chromePrefs.put("download.default_directory", basePath+"\\"+downloadFilepath);
		  ChromeOptions options = new ChromeOptions();
		  options.addArguments("--incognito");
		  options.setExperimentalOption("prefs", chromePrefs);
		  DesiredCapabilities cap = DesiredCapabilities.chrome();
		  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		  cap.setCapability(ChromeOptions.CAPABILITY, options);
		  @SuppressWarnings("deprecation")
		  WebDriver driver = new ChromeDriver(cap);
		  System.out.println(appURL); 
	      driver.navigate().to(appURL);
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		  System.out.println("waiting for cookies");
			
			/*
			 * boolean popcookie =
			 * driver.findElements(By.xpath("//div[@id='cookieConsent']/div[3]/button")).
			 * size()!=0; System.out.println(popcookie); if(popcookie){
			 * driver.findElement(By.xpath("//div[@id='cookieConsent']/div[3]/button")).
			 * click(); }
			 */
			 	  return driver;}
	private static WebDriver initFirefoxDriver(String appURL) { 
		System.out.println("Launching Firefox browser..");
		String basePath = new File("").getAbsolutePath();
	    System.out.println("firefox bast path-"+basePath);
        String path = new File("Drivers/geckodriver.exe").getAbsolutePath();
	    System.out.println(path);
	    System.setProperty("webdriver.gecko.driver", path);
	    FirefoxProfile firefoxProfile=new FirefoxProfile();
	    firefoxProfile.setPreference("http.response.timeout", 15);
	    firefoxProfile.setPreference("network.http.phishy-userpass-length", 255);
	    firefoxProfile.setPreference("network.automatic-ntlm-auth.trusted-uris", "x.x.x.x");
	    
	    FirefoxOptions options=new FirefoxOptions();
	    options.setProfile(firefoxProfile); 
	    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	   // capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
	    capabilities.setCapability("marionette", true);
	     @SuppressWarnings("deprecation")
		WebDriver driver=new FirefoxDriver(capabilities);
	    driver.navigate().to(appURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Reporter.log("URL- "+appURL); 
		
		return driver;
	}
	
	@Parameters({ "browserType", "sitename","cURL", "uCompany","aURL","aUser","aPassword","cPassword"})
	@BeforeClass
	public void initializeTestBaseSetup(String browserType, String sitename, String URL,  String uCompany, String aURL,String aUser, String aPassword, String cPassword  ) {
		try {			
			setDriver(browserType, URL);
			System.out.println("appURL-"+URL);
				
		} catch (Exception e) {
			System.out.println("Check Error....." + e.getStackTrace());}
		
		CompanyName= uCompany+"-"+browserType+getDateDDMMM();
		AdminURL= aURL;
		AdminPassword=aPassword;
		AdminUser=aUser;
	
		
		UsereMail=	  CompanyName+"@discard.com";
		 
	//	UsereMail="huddle-27-feb@junk.jun";
		UserPassword=cPassword;
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		if (!result.isSuccess())
			Thread.sleep(1000);
	}
	public String getDateDDMMM() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date);		
		
	}
	public static String getRandomValue(String[] emailDomain) {
	    int rnd = new Random().nextInt(emailDomain.length);
	    String emailD= (emailDomain[rnd]);
	    Reporter.log("Data is - "+emailD);
	    return emailD;
	}
	
	public static void loaderWait () {
		WebDriverWait wait=new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//i[@class='fas fa-circle-notch fa-spin fa-2x']")));
	}
	
}
