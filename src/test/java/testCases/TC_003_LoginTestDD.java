package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginTestDD extends BaseClass
{
	//DataProviders dp=new DataProviders();
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class)
	public void verifyLogin(String email, String password,String exp) {
		logger.info("***Starting TC_002_LoginTestDD***");
		try {
			
		logger.debug("Capturing application debug");
		HomePage homePage=new HomePage(driver);
		homePage.clickMyAccount();
		homePage.clickLogin();
		logger.info("Navigating to the login page");
		LoginPage loginPage= new LoginPage(driver);
		loginPage.enterEmail(email);
		loginPage.enterPassword(password);
	    loginPage.clickLogin();
		logger.info("user clicked on login button.");
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExists();
		if(exp.equalsIgnoreCase("valid")) 
			{
				if(targetPage=true) 
				{
					macc.clickLogout();
					Assert.assertTrue(true);
				}	
				else {
					Assert.assertTrue(false);
				}
			}
	if(exp.equalsIgnoreCase("Invalid")) {
		if(targetPage==true) {
			Assert.assertTrue(false);
		}
		else {
			Assert.assertTrue(targetPage);
		}
	}
}
	catch(Exception e) {
		Assert.fail();
	}

	logger.info("***Finished TC_003_LoginTest ****");
	
	}
}