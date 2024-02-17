package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import jdk.internal.org.jline.utils.Log;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC_001_AccountRegisttrationTest extends BaseClass {

	@Test
	public void verify_account_registration() {
		
    logger.info("***** starting TC_001_AccountRegisttrationTest *******");
    logger.debug("application logs start..");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("clicked on my  account link");
		hp.clickRegister();
		logger.info("clicked on Register link");
		AccountRegistrationPage regPage=new AccountRegistrationPage(driver);
		regPage.setFirstName(randomString().toUpperCase());
		regPage.setLastName(randomString().toUpperCase());
		regPage.setEmail(randomString()+"@gmail.com");
		regPage.setTelephone(randomNumber());
		String pwd=randomAlphaNumeric();
		regPage.setPassword(pwd);
		regPage.setConfirmPassword(pwd);
		regPage.setPrivacyPolicy();
		regPage.clickContinue();
		logger.info("clicked on continue");
		logger.info("validating expected message....");
		Assert.assertEquals(regPage.getConfirmationMsg(), "Your Account Has Been Created!");	
		
		}
	catch(Exception e) {
		logger.error("test failed");
		logger.debug("debug logs....");
		Assert.fail();
	}
		logger.debug("application logs ending...");
		logger.info("****Finished TC_001_AccountRegisttrationTest*****");
	}
	}	
	
	