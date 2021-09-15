// <copyright file="PageObject.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.pack.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * POM create Page object
 */
public class PageObject {

protected WebDriver driver;
	
	public PageObject(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(driver, this);
	}
}
