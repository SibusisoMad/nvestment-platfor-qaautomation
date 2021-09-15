// <copyright file="TestNGCustomReportListener.java" company="Atom Consulting Services Ltd">
// Copyright (c) Atom Consulting Services Ltd. All rights reserved.
// </copyright>
package src.com.easy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;
import src.com.dropbox.DBMain;
import src.com.pack.base.SendMail;
import src.com.pack.listeners.*;

import src.com.pack.base.*;

/*
 * This class is used to customize the testng report which is used 
 */
public class TestNGCustomReportListener implements IReporter{

	private PrintWriter writer;
	private int m_row;
	private Integer m_testIndex;
	private int m_methodIndex;
	private String reportTitle= "iHuddle Detailed Report";
	private String reportFileName="";
	private String reportFileName1 = "huddle-detailed-report.html";
	private String reportSummaryTitle= "iHuddle Summary Report";
	private String reportsummary = "SummaryReport.html";
	private static String errorimage;
	public static int dropboxerrorcount=0;
	public static int dropboxerrorcount1=0;
	public static int dropboxerrorcount2=0;
	/*
	 * Generated the customized summary and detailed report in html format in root folder
	 */
	  
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,String outdir) {
		int email_qty_tests=0;
		int email_pass=0;
		int email_failed=0;
		try {
			
			writer = createSWriter(outdir);
			startHtml(writer);
	
//			writeReportTitleemail(suites);
			// Uncomment the below 2 line if you want tabular summary report.
					writeReportTitle(reportSummaryTitle);
					generateSSuiteSummaryReport(suites);
			endHtml(writer);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.err.println("Unable to create output file");
			e.printStackTrace();
			return;
		}
// Compiled detail report will be generated from here.		
		try {
			writer = createWriter(outdir);
			startHtml(writer);
			writeReportTitle(reportTitle);
			generateSuiteSummaryReport(suites);
			generateMethodSummaryReport(suites);
			generateMethodDetailReport(suites);
			endHtml(writer);
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Unable to create Detailed  output file");
			e1.printStackTrace();
			return;
		}
		
		String Envio = null;
		String ExeMode = null;
		String EmailPassword = null;
		String EmailUser = null;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
			//	Envio= suite.getParameter("env");
				ExeMode= suite.getParameter("browserType");
				EmailPassword = suite.getParameter("outlookPassword")+"Rajmicro1#";
				EmailUser = suite.getParameter("outlookemail");
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				 email_qty_tests += 1;
				ITestContext overview = r.getTestContext();
				 int q = 	getMethodSet(overview.getPassedTests(), suite).size();
				 email_pass+=q;
				 q=  getMethodSet(overview.getFailedTests(), suite).size();
				  email_failed+=q;
			}	
			}
		try {
			SendMail.execute("\\"+reportsummary,"\\"+reportFileName,  EmailPassword, EmailUser,ExeMode,email_failed,email_pass);
		//	SendGridMail.execute("\\"+reportsummary,"\\"+reportFileName,  EmailPassword, EmailUser,ExeMode,email_failed,email_pass);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Writer to Creates detailed report file
	 */
	protected PrintWriter createWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		reportFileName=	getDateAsString()+ "-" +reportFileName1;
		System.out.println("reportFileName-"+reportFileName);
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportFileName))));
	}
	
	/**
	 * Writer to Creates summary report file
	 */
	protected PrintWriter createSWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, reportsummary))));
	}

	/**
	 * Creates a table showing the highlights of each test method with links to
	 * the method details
	 */
	protected void generateMethodSummaryReport(List<ISuite> suites) {
		m_methodIndex = 0;
		startResultSummaryTable("methodOverview");
		int testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName(), 5);
			}
	
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				m_testIndex = testIndex;
				resultSummary(suite, testContext.getFailedConfigurations(), testName, "failed", " (configuration methods)");
				resultSummary(suite, testContext.getFailedTests(), testName, "failed", "");
				resultSummary(suite, testContext.getSkippedConfigurations(), testName, "skipped", " (configuration methods)");
				resultSummary(suite, testContext.getSkippedTests(), testName, "skipped", "");
				resultSummary(suite, testContext.getPassedTests(), testName, "passed", "");
				testIndex++;
			}
		}
		writer.println("</table>");
	}
   
	/**
	 * Creates a section showing detailed report
	 */

	protected void generateMethodDetailReport(List<ISuite> suites) {
		m_methodIndex = 0;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				if (r.values().size() > 0) {
					writer.println("<h2>" + testContext.getName() + "</h2>");
				}
				resultDetail(testContext.getFailedConfigurations());
				resultDetail(testContext.getFailedTests());
				resultDetail(testContext.getSkippedConfigurations());
				resultDetail(testContext.getSkippedTests());
				resultDetail(testContext.getPassedTests());
			}
		}
	}

	/**
	 * Generated the emailsummary report which is used in mail body
	 */
	private void emailresultSummary(ISuite suite, IResultMap tests, String testname,String style, String details) {
			if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			int mq = 0;
			int cq = 0;
			
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				m_row += 1;
				m_methodIndex += 1;
				ITestClass testClass = method.getTestClass();
				String className = method.getDescription();
				if (mq == 0) {
					String id = (m_testIndex == null ? null : "t"
					+ Integer.toString(m_testIndex));
					titleRow(testname + " &#8212; " + "Fail" + details, 5, id);
					m_testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
						cq += 1;
						writer.print("<tr class=\"" + style	+"" +(cq % 2 == 0 ? "even" : "odd") + "\">"
								+ "<td");
						if (mq > 1) {
							writer.print(" rowspan=\"" + mq + "\"");
						}
						writer.println(">" + lastClassName + "</td>" + buff);
					}
					mq = 0;
					buff.setLength(0);			
					lastClassName = className;
				}

				Set<ITestResult> resultSet = tests.getResults(method);
				String longerrorstring="";
				String  startT = null;
				String endT=null;
				long fend = Long.MIN_VALUE;
				long fstart = Long.MAX_VALUE;
				long startMS=0;
				for (ITestResult testResult : tests.getResults(method)) {
					
					Throwable exception=testResult.getThrowable();
					SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss"); 
					startT= summaryFormat.format(testResult.getStartMillis());
					endT= summaryFormat.format(testResult.getEndMillis());
					//summaryFormat.format(startT);
					if (testResult.getEndMillis() > fend) {
						fend = testResult.getEndMillis()/1000;
					}
					if (testResult.getStartMillis() < fstart) {
						startMS = testResult.getStartMillis();
						fstart =startMS/1000;
					}
//					System.out.println("Start time- "+summaryFormat.format(testResult.getStartMillis()));
//					System.out.println("End time- "+summaryFormat.format(testResult.getEndMillis()));
					boolean hasThrowable = exception != null;
					if(hasThrowable){		
						longerrorstring = Utils.longStackTrace(exception, true);
					}
					
					
				}
				String errorty;
				String errobject=null;
				String erroelemen=null;
				if (longerrorstring.contains("NoSuchElementException")){
					final String REGeX ="(Using.*, )";
					final String RegeX ="(value.*)";
					Pattern p = Pattern.compile(REGeX);
					Pattern p1 = Pattern.compile(RegeX);
                    java.util.regex.Matcher m = p.matcher(longerrorstring);
                    java.util.regex.Matcher m1 = p1.matcher(longerrorstring);
                    boolean foun= m.find();
                    boolean found= m1.find();
 //                   System.out.println("going to regex loop-"+foun);
 //                   System.out.println(m.group(1));
                    if(foun || found){
//                      System.out.println("now in regex loop");
                      String theGroup1 = m.group(1);
                      String theGroup2=m1.group(1);
                      // print the group out for verification
                      //System.out.format("'%s'\n", theGroup);
                      //System.out.println(theGroup);
                      System.out.println(theGroup1);
                      errobject=theGroup1;
                      erroelemen=theGroup2;

//                      System.out.println("Error Object-"+errobject);
                    }
						errorty ="Element Not found";
					}else{
						errorty ="Error Reason Unknown";
					}
				mq += 1;
				if (mq > 1) {
					buff.append("<tr class=\"" + style
							+ (cq % 2 == 0 ? "odd" : "even") + "\">");
				}
				String description = method.getDescription();
				String errorlink ="error image link";
				String testInstanceName = resultSet.toArray(new ITestResult[] {})[0].getTestName();
				Object[] erorarray =  DBMain.shareurl.toArray();//new String[DBMain.shareurl.size()];
				Object[] imageerorarray = TestListener.imageshareurl.toArray();  
				errorlink="<a href ="+imageerorarray[dropboxerrorcount2]+" target = '_blank' >"+imageerorarray[dropboxerrorcount2]+ "</a>";
				errorty="<a href ="+erorarray[dropboxerrorcount1]+" target = '_blank' >"+errorty+ "</a>";
				buff.append(
			/*			"<td>"
						+ qualifiedName(method)
						+ " "
						+ (description != null && description.length() > 0 ? "("
								+ description + ")"
								: "")
								+ ""
								+ (null == testInstanceName ? "" : "<br>("
										+ testInstanceName + ")") + "</td>" */
										"<td class=\"numiemail\" style=\"text-align:left;padding-right:2em\">" 
										+errorty+""
										//+ firstLine+""
										+ "</td>"
										+ "<td style=\"text-align:right\">"
										+errobject+""
										//+ formatter.format(calendar.getTime())
										+ "</td>"
										+ "<td class=\"numiemail\">"
										+erroelemen+""
										//+ timeConversion(end - start) 
										+ "</td>"  
										+ "</td>"
										+ "<td class=\"numiemail\">"
										+errorlink+""
										//+ timeConversion(end - start) 
										+ "</td>"
										+ "<td class=\"numiemail\">"							
										+startT+""
										//+ timeConversion(end - start) 
										+ "</td>"
										+ "<td class=\"numiemail\">"
										+endT +""
										//+ timeConversion(end - start) 
										+ "</td>"
										+ "<td class=\"numiemail\">"
										+ timeConversion(fend - fstart)+"" 
										+ "</td>"
										
										+"</tr>");
				dropboxerrorcount1++;
				dropboxerrorcount2++;
			}
			if (mq > 0) {
				cq += 1;
				writer.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
				if (mq > 1) {
					writer.print(" rowspan=\"" + mq + "\"");
				}
				writer.println(">" + lastClassName + "</td>" + buff);
			}
		}
	}
	
	private void resultSummary(ISuite suite, IResultMap tests, String testname,	String style, String details) 
	{
		if (tests.getAllResults().size() > 0) {
			StringBuffer buff = new StringBuffer();
			String lastClassName = "";
			int mq = 0;
			int cq = 0;
			for (ITestNGMethod method : getMethodSet(tests, suite)) {
				m_row += 1;
				m_methodIndex += 1;
				//ITestNGMethod method;
				ITestClass testClass = method.getTestClass();
				//String className = testClass.getName();
				String className = method.getDescription();
				if (mq == 0) {
					String id = (m_testIndex == null ? null : "t"
							+ Integer.toString(m_testIndex));
					titleRow(testname + " &#8212; " + style + details, 5, id);
					m_testIndex = null;
				}
				if (!className.equalsIgnoreCase(lastClassName)) {
					if (mq > 0) {
						cq += 1;
						writer.print("<tr class=\"" + style
								+ (cq % 2 == 0 ? "even" : "odd") + "\">"
								+ "<td");
						if (mq > 1) {
							writer.print(" rowspan=\"" + mq + "\"");
						}
						writer.println(">" + lastClassName + "</td>" + buff);
					}
					mq = 0;
					buff.setLength(0);
					lastClassName = className;
				}
				Set<ITestResult> resultSet = tests.getResults(method);
				long end = Long.MIN_VALUE;
				long start = Long.MAX_VALUE;
				long startMS=0;
				String firstLine="";
				String longerrorstring="";
				
				for (ITestResult testResult : tests.getResults(method)) {
					if (testResult.getEndMillis() > end) {
						end = testResult.getEndMillis()/1000;
					}
					if (testResult.getStartMillis() < start) {
						startMS = testResult.getStartMillis();
						start =startMS/1000;
					}
					
					Throwable exception=testResult.getThrowable();
					boolean hasThrowable = exception != null;
					if(hasThrowable){
						String str = Utils.longStackTrace(exception, true);
								//stackTrace(exception, true)[0];
						
						Scanner scanner = new Scanner(str);
						firstLine = scanner.nextLine();
						//RaJeev Changes
						//System.out.println("FL -"+firstLine);
						scanner.close();
					}
					
				}
				DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
				Calendar calendar = Calendar.getInstance();
			    calendar.setTimeInMillis(startMS);
			     
				mq += 1;
				if (mq > 1) {
					buff.append("<tr class=\"" + style
							+ (cq % 2 == 0 ? "odd" : "even") + "\">");
				}
				String description = method.getDescription();
				String testInstanceName = resultSet.toArray(new ITestResult[] {})[0].getTestName();
				buff.append("<td><a href=\"#m"
						+ m_methodIndex
						+ ">"
						+ qualifiedName(method)
						+ " "
						+ (description != null && description.length() > 0 ? "("
								+ description + ")"
								: "")
								+ "</a>"
								+ (null == testInstanceName ? "" : "<br>("
										+ testInstanceName + ")") + "</td>"
										+"<td class=\"numi\" style=\"text-align:left;padding-right:2em\">" 
										+ firstLine+"<br/>"
										+ "</td>"
										+ "<td style=\"text-align:right\">"
										+ formatter.format(calendar.getTime())
										+ "</td>"
										+ "<td class=\"numi\">"
										+ timeConversion(end - start) 
										+ "</td>"  
										+"</tr>");
				
			}
			if (mq > 0) {
				cq += 1;
				writer.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
				if (mq > 1) {
					writer.print(" rowspan=\"" + mq + "\"");
				}
				writer.println(">" + lastClassName + "</td>" + buff);
			}
		}
	}
    
	/** Convert time which is used in report */
	private String timeConversion(long seconds) {
	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;
	    int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
	    seconds -= minutes * SECONDS_IN_A_MINUTE;
	    int hours = minutes / MINUTES_IN_AN_HOUR;
	    minutes -= hours * MINUTES_IN_AN_HOUR;
	    return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int)seconds);
	}
	
	private String prefixZeroToDigit(int num){
		int number=num;
		if(number<=9){
			String sNumber="0"+number;
			return sNumber;
		}
		else
			return ""+number;
		
	}
	
	/** Starts and defines columns result summary table */
	private void startResultSummaryTable(String style) {
		tableStart(style, "Summary");
		writer.println("<tr><th>Class</th>"
				+ "<th>Exception Info</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
				//+ "<th>Method</th><th>Exception Info</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
		m_row = 0;
	}
	/** Email Body Starts and defines columns result summary table */
	private void emailstartResultSummaryTable(String style) {
		tableStart(style, "Summary");
		writer.println("<tr><th>Scenario</th>"
				+ "<th>Reason</th><th>Type </th><th>Element</th><th>Error Location</th><th>Start Time</th><th>End Time</th><th>Total Time</th></tr>");
		m_row = 0;
	}

	private String qualifiedName(ITestNGMethod method) {
		StringBuilder addon = new StringBuilder();
		String[] groups = method.getGroups();
		int length = groups.length;
		if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
			addon.append("(");
			for (int i = 0; i < length; i++) {
				if (i > 0) {
					addon.append(", ");
				}
				addon.append(groups[i]);
			}
			addon.append(")");
		}

		return "<b>" + method.getMethodName() + "</b> " + addon;
	}

	private void resultDetail(IResultMap tests) {
		Set<ITestResult> testResults=tests.getAllResults();
		List<ITestResult> testResultsList = new ArrayList<ITestResult>(testResults);
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
//Sorting		Collections.sort(testResultsList, new TestResultsSorter());
		for (ITestResult result : testResultsList) {
			ITestNGMethod method = result.getMethod();
			m_methodIndex++;
				writer.println("<h3 id=\"m" + m_methodIndex + "\">" /*+ cname*/ + ":"
					+method.getDescription() + "</h3>");
			Set<ITestResult> resultSet = tests.getResults(method);
			generateResult(result, method, resultSet.size());
			writer.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");

		}
	}
	
	protected void writeReportTitleemail(List<ISuite> suites) {
		int pass=0;
		int failed=0;
		int qty_tests=0;
		writer.print("<p class=\"detail\" >Hello !!  </p>");
		writer.println("<p class=\"detail\">Today execution summary report- </p>");
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				 int q = 	getMethodSet(overview.getPassedTests(), suite).size();
				 pass+=q;
				 q=  getMethodSet(overview.getFailedTests(), suite).size();
				 failed+=q;
			}}	
		writer.print("<p class=\"detail\">"+"Total Passed Scenario"+" -    "+ "<span class=\"namep\">"+ pass+"</span>"+ "</p>");
		writer.print("<p class=\"detail\">"+"Total Failed Scenario"+" -    "+ "<span class=\"namef\">"+failed+"</span>"+ "</p>");
		m_methodIndex = 0;
		if(failed>0){
		emailstartResultSummaryTable("methodOverview");
		}
		int testIndex = 1;
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				String testName = testContext.getName();
				m_testIndex = testIndex;
				emailresultSummary(suite, testContext.getFailedTests(), testName, "failedemail", "");
				testIndex++;
			}
		}
		writer.println("</table>");
	}
	private void generateResult(ITestResult ans, ITestNGMethod method,int resultSetSize) {
		Object[] parameters = ans.getParameters();
		boolean hasParameters = parameters != null && parameters.length > 0;
		if (hasParameters) {
			tableStart("result", null);
			writer.print("<tr class=\"param\">");
			for (int x = 1; x <= parameters.length; x++) {
				writer.print("<th>Param." + x + "</th>");
			}
			writer.println("</tr>");
			writer.print("<tr class=\"param stripe\">");
			for (Object p : parameters) {
				writer.println("<td>" + Utils.escapeHtml(Utils.toString(p))
						+ "</td>");
			}
			writer.println("</tr>");
		}
		List<String> msgs = Reporter.getOutput(ans);
		boolean hasReporterOutput = msgs.size() > 0;
		Throwable exception = ans.getThrowable();
		boolean hasThrowable = exception != null;
		if (hasReporterOutput || hasThrowable) {
			if (hasParameters) {
				writer.print("<tr><td");
				if (parameters.length > 1) {
					writer.print("colspan=\"" + parameters.length + "\"");
				}
				writer.println(">");
			} else {
				writer.println("<div >");
			}
			if (hasReporterOutput) {
				if (hasThrowable) {
					//writer.println("<h3>Test Messages</h3>");
				}
				writer.println("<table>");
				for (String line : msgs) {
					writer.println("<tr>");
					writer.println("<td>");
					writer.println(line );
					writer.println("</td>");
					writer.println("<td> <font color=\"green\">");
					writer.println("Pass");
					writer.println("</font></td></tr>");
				}
				writer.println("</table>");
			}
			if (hasThrowable) {
				boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					writer.println("<h2>"
							+ (wantsMinimalOutput ? "Expected Exception"
									: "Failure") + "</h2>");
				}
				Object[] erorarray =  DBMain.shareurl.toArray();//new String[DBMain.shareurl.size()];
				writer.println("<h4><font color=\"red\"><a href ="+ erorarray[dropboxerrorcount]+ " target = '_blank' >" +"Click Here to View Error Image"+ "</a></font></h4>");
				dropboxerrorcount++;
				generateExceptionReport(exception, method);
			}
			if (hasParameters) {
				writer.println("</td></tr>");
			} else {
				writer.println("</div>");
			}
		}
		if (hasParameters) {
			writer.println("</table>");
		}
	}
	public static void getimagepath(String imagePath) {
		errorimage =imagePath;
	}
	protected void generateExceptionReport(Throwable exception, ITestNGMethod method) {
		writer.print("<div class=\"stacktrace\">");
		String value = null;
		boolean hasThrowable = exception != null;
		if(hasThrowable){
			String str = Utils.longStackTrace(exception, true);
				Scanner scanner = new Scanner(str);
			 value = scanner.nextLine();
			scanner.close();
		}
		writer.print("<font color=\"red\">");
		writer.print(value);
		writer.print("</font>");
		writer.println("</div>");
	}

	/**
	 * Since the methods will be sorted chronologically, we want to return the
	 * ITestNGMethod from the invoked methods.
	 */
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
		
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
//Sorting		Collections.sort(r,new TestSorter());
		List<ITestNGMethod> result = Lists.newArrayList();
		for (IInvokedMethod m : r) {
			for (ITestNGMethod temp : result) {
				if (!temp.equals(m.getTestMethod()))
					result.add(m.getTestMethod());
			}
		}
		Collection<ITestNGMethod> allMethodsCollection=tests.getAllMethods();
		List<ITestNGMethod> allMethods=new ArrayList<ITestNGMethod>(allMethodsCollection);
//Sorting		Collections.sort(allMethods, new TestMethodSorter());
		
		for (ITestNGMethod m : allMethods) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	public void generateSuiteSummaryReport(List<ISuite> suites) {
		tableStart("testOverview", null);
		writer.print("<tr>");
		tableColumnStart("Scenario");
		tableColumnStart("Methods<br/>Passed");
		tableColumnStart("# skipped");
		tableColumnStart("# failed");
		tableColumnStart("Browser");
		tableColumnStart("Start<br/>Time");
		tableColumnStart("End<br/>Time");
		tableColumnStart("Total<br/>Time(hh:mm:ss)");
		tableColumnStart("Environment");
		tableColumnStart("Excluded<br/>Scenario");
		writer.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_pass_s = 0;
		int qty_skip = 0;
		long time_start = Long.MAX_VALUE;
		int qty_fail = 0;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName() +"-"+suite.getParameter("env")+" environment", 10);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				startSummaryRow(overview.getName());
				int q = getMethodSet(overview.getPassedTests(), suite).size();
				int failed = getMethodSet(overview.getFailedTests(), suite).size();
				qty_pass_m += q;
				summaryPPCell(q,failed);
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				summaryCell(q, 0);
				qty_fail += failed;
				summaryCell(failed, 0);
				String browser = suite.getParameter("browserType") ;
				summaryCell(browser, true);
				writer.println("</td>");
				SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
				summaryCell(summaryFormat.format(overview.getStartDate()),true);				
				writer.println("</td>");
				summaryCell(summaryFormat.format(overview.getEndDate()),true);
				writer.println("</td>");
				time_start = Math.min(overview.getStartDate().getTime(), time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				summaryCell(timeConversion((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);
				summaryCell(suite.getParameter("env"), true);
				summaryCell(overview.getExcludedGroups());
				writer.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 1) {
			writer.println("<tr class=\"total\"><td>Total</td>");
			summaryCell(qty_pass_m, Integer.MAX_VALUE);
			summaryCell(qty_skip, 0);
			summaryCell(qty_fail, 0);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
			writer.println("<td colspan=\"3\">&nbsp;</td></tr>");
		}
		writer.println("</table>");
	}
	/**
	 * Since the methods summaryreport
	 * (List<ISuite> from the invoked methods.
	 */
	@SuppressWarnings("unused")
	public void generateSSuiteSummaryReport(List<ISuite> suites) {
		tableStart("testOverview", null);
		writer.print("<tr>");
		tableColumnStart("Scenario");
		tableColumnStart("Methods<br/>Passed");
		tableColumnStart("# skipped");
		tableColumnStart("# failed");
		tableColumnStart("Start<br/>Time");
		tableColumnStart("End<br/>Time");
		tableColumnStart("Total<br/>Time(hh:mm:ss)");
		writer.println("</tr>");
		NumberFormat formatter = new DecimalFormat("#,##0.0");
		int qty_tests = 0;
		int qty_pass_m = 0;
		int qty_pass_s = 0;
		int qty_skip = 0;
		long time_start = Long.MAX_VALUE;
		int qty_fail = 0;
		long time_end = Long.MIN_VALUE;
		m_testIndex = 1;
		for (ISuite suite : suites) {
			if (suites.size() >= 1) {
				titleRow(suite.getName()+"-"+suite.getParameter("env")+" environment", 10);
			}
			Map<String, ISuiteResult> tests = suite.getResults();
			for (ISuiteResult r : tests.values()) {
				qty_tests += 1;
				ITestContext overview = r.getTestContext();
				startSSummaryRow(overview.getName());
				int q = getMethodSet(overview.getPassedTests(), suite).size();
				int failed = getMethodSet(overview.getFailedTests(), suite).size();
				qty_pass_m += q;
				summaryPCell(q,failed);
				q = getMethodSet(overview.getSkippedTests(), suite).size();
				qty_skip += q;
				summaryCell(q, 0);
				qty_fail += failed;
				summaryCell(failed, 0);		
				writer.println("</td>");
				SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
				summaryCell(summaryFormat.format(overview.getStartDate()),true);				
				writer.println("</td>");
				summaryCell(summaryFormat.format(overview.getEndDate()),true);
				writer.println("</td>");
				time_start = Math.min(overview.getStartDate().getTime(), time_start);
				time_end = Math.max(overview.getEndDate().getTime(), time_end);
				summaryCell(timeConversion((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);
				writer.println("</tr>");
				m_testIndex++;
			}
		}
		if (qty_tests > 1) {
			writer.println("<tr class=\"total\"><td>Total</td>");
			summaryCell(qty_pass_m, Integer.MAX_VALUE);
			summaryCell(qty_skip, 0);
			summaryCell(qty_fail, 0);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(" ", true);
			summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
			writer.println("<td colspan=\"3\">&nbsp;</td></tr>");
		}
		writer.println("</table>");
	}
	private void summaryCell(String[] val) {
		StringBuffer b = new StringBuffer();
		for (String v : val) {
			b.append(v + ", ");
		}
		summaryCell(b.toString(), true);
	}
	private void summaryPPCell(int v, int failed) {
		
		writer.print("<td class=\"numi" + (failed== 0? " passedodds" : "") + "\">" + v
				+ "</td>");
	}
	private void summaryPCell(int v, int failed) {
		//System.out.println("Failed-"+failed);
	writer.print("<td class=\"numi" + (failed== 0 ? "_passed": "") + "\">" + v
			+ "</td>");
	}
	
	private void summaryCell(String v, boolean isgood) {
		writer.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
				+ "</td>");
		
	}
	private void startSSummaryRow(String label) {
		m_row += 1;
		writer.print("<tr"
				+ (m_row % 2 == 0 ? "\" ":"")
				//+ (m_row % 2 == 0 ? "class=stripe\" ":"")
				+ ">"
				+ "<td style=\"text-align:left;padding-right:2em\">"
				+ "<b>" + label + "</b>" + "</td>");
		
	}
	private void startSummaryRow(String label) {
		m_row += 1;
		writer.print("<tr"
				+ (m_row % 2 == 0 ? "\" ":"")
				//+ (m_row % 2 == 0 ? "class=stripe\" ":"")
				+ ">"
				+ "<td style=\"text-align:left;padding-right:2em\">"
				+ "<a href=\"#t"+ m_testIndex + "\"><b>" + label + "</b></a>" + "</td>");
	}

	private void summaryCell(int v, int maxexpected) {
		summaryCell(String.valueOf(v), v <= maxexpected);
	}
	

	private void tableStart(String cssclass, String id) {
		writer.println("<table cellspacing=0 cellpadding=0"
				+ (cssclass != null ? " class=\"" + cssclass + "\""
						: "style="+"padding-bottom:2em"+"")
						+ (id != null ? " id=\"" + id + "\"" : "") + ">");
		m_row = 0;
	}

	private void tableColumnStart(String label) {
		writer.print("<th>" + label + "</th>");
	}

	private void titleRow(String label, int cq) {
		titleRow(label, cq, null);
	}
	private void titleRow(String label, int cq, String id) {
		writer.print("<tr");
		if (id != null) {
			writer.print(" id=\"" + id + "\"");
		}
		writer.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
		m_row = 0;
	}
	protected void writeReportTitle(String title) {
		writer.print("<center><h2>" + title + " - " + getDateAsnString() + "</h2></center>");
	}
	/*
	 * Method to get Date as String
	 */
	private String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getDateAsnString() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	/** Starts HTML stream */
	protected void startHtml(PrintWriter out) {
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>Detailed Report</title>");
		out.println("<style type=\"text/css\">");
		out.println("p.detail { color:#000000;font-family:'hurme_geometric_sans_4regular' !important;font-size:12 }");
		out.println("span.namep { color:#008000;font-weight:bold;font-family:'hurme_geometric_sans_4regular' !important;font-size:12 }");
		out.println("span.namef { color:#FF0000;font-weight:bold;font-family:'hurme_geometric_sans_4regular' !important;font-size:12 }");
		out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
		out.println("td,th {border:1px solid #009;padding:.25em .5em}");
		out.println(".result th {vertical-align:bottom}");
		out.println(".param th {padding-left:1em;padding-right:1em}");
		out.println(".param td {padding-left:.5em;padding-right:2em}");
		out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		out.println(".numi,.numi_attn, .numi_passed {text-align:right}");
		out.println(".total td {font-weight:bold}");
		out.println(".passedodds  {background-color: #3F3}");
		out.println(".passedodd td {background-color: #0A0}");
		out.println(".passedeven td {background-color: #3F3}");
		out.println(".skippedodd td {background-color: #CCC}");
		out.println(".skippedodd td {background-color: #DDD}");
		out.println(".passed td,.numi_passed {background-color: #3F3}");
		out.println(".failedodd td,.numi_attn {background-color: #F33}");
		out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
		out.println(".failedemailodd td,.numiemail {background-color: #FF7797;font-family:'hurme_geometric_sans_4regular' !important;font-size:12}");
		out.println(".failedemaileven td,.stripe .numiemail  {background-color: #FF5B82;font-family:'hurme_geometric_sans_4regular' !important;font-size:12}");
		out.println(".stacktrace {white-space:pre;font-family:'hurme_geometric_sans_4regular' !important;}");
		out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
	}

	/** Finishes HTML stream */
	protected void endHtml(PrintWriter out) {
		out.println("<center> &copy; Powered by Atom Ventures </center>");
		out.println("</body></html>");
	}

	// ~ Inner Classes --------------------------------------------------------
	/** Arranges methods by classname and method name */
/*
	private class TestSorter implements Comparator<IInvokedMethod> {
		// ~ Methods
		// -------------------------------------------------------------

		/** Arranges methods by classname and method name */
/*
		@Override
		public int compare(IInvokedMethod obj1, IInvokedMethod obj2) {
			int r = obj1.getTestMethod().getTestClass().getName().compareTo(obj2.getTestMethod().getTestClass().getName());
			return r;
		}
	}
	/*	
	private class TestMethodSorter implements Comparator<ITestNGMethod> {
		@Override
		public int compare(ITestNGMethod obj1, ITestNGMethod obj2) {
			int r = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (r == 0) {
				r = obj1.getMethodName().compareTo(obj2.getMethodName());
			}
			return r;
		}
	}

	private class TestResultsSorter implements Comparator<ITestResult> {
		@Override
		public int compare(ITestResult obj1, ITestResult obj2) {
			int result = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
			if (result == 0) {
				result = obj1.getMethod().getMethodName().compareTo(obj2.getMethod().getMethodName());
			}
			return result;
		}
	}*/

	

}