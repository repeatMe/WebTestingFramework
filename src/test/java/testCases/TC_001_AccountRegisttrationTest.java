package testCases;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC_001_AccountRegisttrationTest extends BaseClass {

	

	@Test
	public void verify_account_registration() {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		AccountRegistrationPage regPage=new AccountRegistrationPage(driver);
		regPage.setFirstName(randomString().toUpperCase());
		regPage.setLastName(randomString().toUpperCase());
		regPage.setEmail(randomString()+"@gmail.com");
		regPage.setTelephone(randomNumber());
		String pwd=randomAlphaNumberic();
		regPage.setPassword(pwd);
		regPage.setConfirmPassword(pwd);
		regPage.setPrivacyPolicy();
		regPage.clickContinue();
		Assert.assertEquals(regPage.getConfirmationMsg(), "Your Account Has Been Created!");
	}
	public String randomString() {
		String generatedString=RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
		
	}
	public String randomAlphaNumberic() {
		String str=RandomStringUtils.randomAlphabetic(3);
		String num=RandomStringUtils.randomNumeric(3);
		return (str+"#"+num);
		}
	}
	