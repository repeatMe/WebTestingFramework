package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass{

	@Test
	public void verifyLogin() {
		logger.info("***Starting TC_002_LoginTest***");
		logger.debug("Capturing application debug");
		try {		
		HomePage homePage=new HomePage(driver);
		homePage.clickMyAccount();
		homePage.clickLogin();
		logger.info("Navigating to the login page");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("email"));
		loginPage.enterPassword(p.getProperty("password"));
		loginPage.clickLogin();
		logger.info("user clicked on login button.");
		MyAccountPage map=new MyAccountPage(driver);
		boolean targetPage=map.isMyAccountPageExists();
	Assert.assertTrue(targetPage);
	if(targetPage=true) {
		logger.info("login test passsed.");
	}
	else {
	logger.error("login test failed");
	Assert.fail();
	}}
		
	catch(Exception e) {		
		Assert.fail();
	}
	logger.info("***Finished TC_002_LoginTest ****");
	}
}
