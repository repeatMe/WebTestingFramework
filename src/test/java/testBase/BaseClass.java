package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public WebDriver driver;
	public Logger  logger;
	public Properties p;
	
	   @BeforeClass
	   @Parameters({"OS","Browser"})
	   public void setup(String os,String browser) throws IOException {
		   FileReader file=new FileReader(".//src//test//resources//config.properties");
		   p=new Properties();
		   p.load(file);
		   
		   
		   
		logger=LogManager.getLogger(this.getClass());
		
		switch(browser.toLowerCase())
		{
		case "chrome":driver=new ChromeDriver();break;
		case "edge":driver=new EdgeDriver();break;
		default:System.out.println("No matching browser..");
		return ;
		
		}
		
		
		
	//	driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
	    driver.manage().window().maximize();
		}
		
		@AfterClass
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
		
}
