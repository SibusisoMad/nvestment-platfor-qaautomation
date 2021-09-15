// <copyright file="TestListener.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
//updated till 4th Jan 2021
package src.com.pack.listeners;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

//import java.awt.event.KeyEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import src.com.easy.TestNGCustomReportListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;

import src.com.dropbox.*;


import src.com.pack.base.TestBaseSetup;


public class TestListener extends TestListenerAdapter {
	WebDriver driver;
	public static String errrovalue=null;
	private static String fileSeperator = System.getProperty("file.separator");
	public static ArrayList<String> imageshareurl = new ArrayList<String>();

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("***** Error " + result.getName() + " test has failed *****");

		driver = TestBaseSetup.getDriver();

		String testClassName = getTestClassName(result.getInstanceName()).trim();
		String testMethodName = result.getName().toString().trim();
		
		//Rajeev Newly added to catch the error exeception and print on screenshot image 
		Throwable exception=result.getThrowable();
		boolean hasThrowable = exception != null;
		if(hasThrowable){
			String str = Utils.longStackTrace(exception, true);
					//stackTrace(exception, true)[0];
			
			Scanner scanner = new Scanner(str);
			errrovalue = scanner.nextLine();
			//Rajeev Changes
			//System.out.println("FL -"+errrovalue);
			scanner.close();
		}
		//////////////////
		//Calendar cal = Calendar.getInstance();
		//int currentminute = cal.get(Calendar.MINUTE);
		//int currenthour = cal.get(Calendar.HOUR_OF_DAY);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date date = new Date();
		String currentdatetime = dateFormat.format(date);
		String screenShotName = currentdatetime+"-"+ testMethodName + ".png";
		 String basePath = new File("").getAbsolutePath();
		   System.out.println("base path- "+basePath);
		if (driver != null) {
			String imagePath = basePath + fileSeperator + "Screenshots"
					+ fileSeperator + "Results" + fileSeperator + testClassName
					+ fileSeperator
					+ takeScreenShot(driver, screenShotName, testClassName,  testMethodName);
			
			TestNGCustomReportListener.getimagepath(imagePath);
			
			System.out.println("Screenshot can be found : " + imagePath);
		}
	}

	public static String takeScreenShot(WebDriver driver,
			String screenShotName, String testName, String MethodName) {
		try {
			File file = new File("Screenshots" + fileSeperator + "Results");
			if (!file.exists()) {
				System.out.println("File created " + file);
				file.mkdir();
			}
			String currentURL = driver.getCurrentUrl();
			imageshareurl.add(currentURL) ;
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			final BufferedImage image = ImageIO.read(screenshotFile);
			Graphics g = image.getGraphics();
			g.setFont(g.getFont().deriveFont(16f));
		    g.setColor(Color.RED);
		   //Rajeev -update the error printing on image
		    g.drawString("Error Page -"+currentURL, 30, 200);
		    g.drawString("Error While -"+testName+"-"+MethodName, 30, 350); 
		    errrovalue=  errrovalue.replace("&amp;", "&").replace("&quot;", "\"").replace("&apos", "\'").replace("org.openqa.selenium.", ".");
		    g.setFont(g.getFont().deriveFont(14f));
		    g.setColor(Color.RED.darker());
		    g.drawString("Cause -"+errrovalue, 10, 450);
		    g.dispose();
		    //Calendar cal = Calendar.getInstance();
			//int currentminute = cal.get(Calendar.SECOND);
			//String img = "rajerrortest"+currentminute +".png";
		    //System.out.println(img);
		  //  Reporter.log("Failure Image-" +img);
			ImageIO.write(image, "png", new File(screenShotName));
			File targetFile = new File("Screenshots" + fileSeperator + "Results" + fileSeperator + testName, screenShotName);
			//FileUtils.copyFile(screenshotFile, targetFile);
			FileUtils.copyFile(new File(screenShotName), targetFile);
			DBMain dbmain = new DBMain();
			try {
				//System.out.println("SSN-"+screenShotName);
				dbmain.DBMain(screenShotName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return screenShotName;
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
			return null;
		}
	}

	public String getTestClassName(String testName) {
		String[] reqTestClassname = testName.split("\\.");
		int i = reqTestClassname.length - 1;
		System.out.println("Required Test Name : " + reqTestClassname[i]);
		return reqTestClassname[i];
	}
	
	
	

}

