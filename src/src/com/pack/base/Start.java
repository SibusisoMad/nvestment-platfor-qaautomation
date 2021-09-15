// <copyright file="Start.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.pack.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.BasicConfigurator;
import org.testng.TestNG;
/**
 * Entry point of script exectution Main Method is mentioned here.Just keep in mind the first XML file should be named as 
 * "atomstart.xml"
 */

public class Start {

	public static void main(String[] args) {
		List<String> xmlFile = new ArrayList<String>();
		String path = new File("atomstart.xml").getAbsolutePath();
		xmlFile.add(path);
		TestNG testng = new TestNG();
		testng.setTestSuites(xmlFile);
		testng.run();
	}

}
