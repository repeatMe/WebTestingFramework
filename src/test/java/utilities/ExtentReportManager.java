package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
 public ExtentSparkReporter sparkReporter;
 public ExtentReports extent;
 
 /*
  * 
  */
 
 
 public ExtentTest test;
 
 String repName;
 
 public void onStart(ITestContext testContext) {
	 /*SimpleDateFormat df=new SimpleDateFormat("yyy.MM.dd.HH.mm.ss");
	  * Date dt=new Date();
	  * String date=date.format(dt);
	  */
	
	String timeStamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	 repName="Test-Report-"+timeStamp+".html";
	 sparkReporter =new ExtentSparkReporter(".\\reports\\"+repName);
	 sparkReporter.config().setDocumentTitle("opencart Automation Report");
	 sparkReporter.config().setReportName("opencart Functional Testing");
	 sparkReporter.config().setTheme(Theme.DARK);
	 
	 extent =new ExtentReports();
	 extent.attachReporter(sparkReporter);
	 extent.setSystemInfo("Application", "opencart");
	 extent.setSystemInfo("Module", "Admin");
	 extent.setSystemInfo("Sub Module", "Customer");
	 extent.setSystemInfo("User Name", System.getProperty("user.name"));
	 extent.setSystemInfo("Env", "QA");
	 
	 String os=testContext.getCurrentXmlTest().getParameter("os");
	 extent.setSystemInfo("Operating System", os);
	 
	 String browser=testContext.getCurrentXmlTest().getParameter("browser");
	 extent.setSystemInfo("Operating System", browser);
	 
	 List<String> includeGroups=testContext.getCurrentXmlTest().getIncludedGroups();
	 if(!includeGroups.isEmpty()) {
		 extent.setSystemInfo("Groups", includeGroups.toString());
		 
	 }
	 }
 
 public void onTestSuccess(ITestResult result) {
	 
	 test=extent.createTest(result.getTestClass().getName());
	 test.assignCategory(result.getMethod().getGroups());//to display the groups name in report
	 test.log(Status.PASS, result.getName()+"got successfully executed.");
	 
 }
 public void onTestFailure(ITestResult result) {
	 test=extent.createTest(result.getTestClass().getName());
	 test.assignCategory(result.getMethod().getGroups());//to display the groups name in report
	 test.log(Status.FAIL, result.getName()+"  got failed");
	 test.log(Status.INFO, result.getThrowable().getMessage());
	 
	 try {
		 String imgPath=new BaseClass().captureScreen(result.getName());
		 test.addScreenCaptureFromPath(imgPath);
		 
	 }
	 catch (Exception e ) {
		 e.printStackTrace();
	 }
 }
 public void onTestSkipped(ITestResult result) {
	 test=extent.createTest(result.getTestClass().getName());
	 test.assignCategory(result.getMethod().getGroups());
	 test.log(Status.SKIP, result.getName()+"got skipped");
	 test.log(Status.INFO, result.getThrowable().getMessage()); 
 }
 public void onFinish(ITestContext testContext) {
	 extent.flush();
	 /*
	  * below line of code will help  us to open the report after execution of the script.
	  */
	 String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
	 File extentReport=new File(pathOfExtentReport);
	 try {
		 Desktop.getDesktop().browse(extentReport.toURI());
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	 /*Below code will be used to send an report on mail post completion of the exceution
	  *add java email dependency with name apache commons email
	  */
//	  try{
//	   URL url =new URL("file///"+System.getProperty("user.dir")+"\\reports\\"+repName);
//	   
//	   ImageHtmlEmail email=new ImageHtmlEmail();
//	   email.setDataSourceResolver(new DataSourceUrlResolver(url));
//	   email.setHostName("smtp.googlemail.com");
//	   email.setSmtpPort(465);
//	   email.setAuthenticator(new DefaultAuthenticator("okabhi59@gmail.com","password"));
//	   email.setSSLOnConnect(true);
//	   email.setFrom("pavanoltraining@gmail.com");//sender
//	   email.setSubject("Test Result");
//	   email.setMsg("please find the attached Report");
//	   email.addTo("okabhi95@gmail.com");//receiver
//	   email.attach(url, "extent Report","Please check on this report");
//	   email.send();
//	  }
//	  catch(Exception e) {
//		  e.printStackTrace();
//	  }
}
}