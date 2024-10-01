package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	   @BeforeClass(groups= {"sanity","regression","master"})
	   @Parameters({"OS","Browser"})
	   public void setup(String os,String browser) throws IOException {
		   FileReader file=new FileReader(".//src//test//resources//config.properties");
		   p=new Properties();
		   p.load(file);
		      	   
		logger=LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			  //grid
			   String huburl="http://localhost:4444/";
			   DesiredCapabilities cap=new DesiredCapabilities();
			   if(os.equalsIgnoreCase("windows")) {
				   cap.setPlatform(Platform.WIN11);
				    }
			   
			   else if (os.equalsIgnoreCase("mac")){
				   cap.setPlatform(Platform.MAC);
			   }
			   else {
				   System.out.println("No matching os");
				   return;
			   }
			   
			   //browser
			   switch (browser.toLowerCase())
			   {
			   case "chrome": cap.setBrowserName("chrome");break;
			   
			   case "edge": cap.setBrowserName("MicrosoftEdge");break;
			   
			   default:System.out.println("No matching os");return;
			   }
		   WebDriver driver=new RemoteWebDriver(new URL(huburl),cap);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) 
		{
			switch(browser.toLowerCase())
			{
			case "chrome":driver=new ChromeDriver();break;
			case "edge":driver=new EdgeDriver();break;
			case "firefox":driver=new FirefoxDriver();break;
			default:System.out.println("No matching browser..");
			return ;
			
			}
		}
		
		
			
	//	driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
	    driver.manage().window().maximize();
		}
		
		@AfterClass(groups= {"sanity","regression","master"})
		public void tearDown() {
		driver.close();	
		}
		
		public String randomString()
		{
			String generatedString=RandomStringUtils.randomAlphabetic(5);
			return generatedString;
		}
		
		public String randomNumber()
		{
			String generatedString=RandomStringUtils.randomNumeric(10);
			return generatedString;
		}
		
		public String randomAlphaNumeric()
		{
			String str=RandomStringUtils.randomAlphabetic(3);
			String num=RandomStringUtils.randomNumeric(3);		
			return (str+"@"+num);
		}
/*
 * 1.create timestamp
 * 2.create takescreenshot object
 * 3.invoke get screenshot method and take screen shot and take output as a file
 * 4.create file path and change the file path String type to File type
 * 5.change sourcefile name to target file
 * 6.returrn the target filepath
 */
		public String captureScreen(String tname) {
			String timeStamp=new SimpleDateFormat("yyyMMddhhmmss").format(new Date());
			
			TakesScreenshot takesScreenShot=(TakesScreenshot)driver;
			File sourceFile=takesScreenShot.getScreenshotAs(OutputType.FILE);
			
			String targetFilepath=System.getProperty("user.dir")+"\\screenshots"+tname+"_"+timeStamp+".png";
			File targetFile = new File(targetFilepath);	
			sourceFile.renameTo(targetFile);
		    
		   return targetFilepath;
			
			
			
		}
		
}
