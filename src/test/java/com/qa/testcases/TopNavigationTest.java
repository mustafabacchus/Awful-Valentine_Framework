package com.qa.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.LandingPage;
import com.qa.was.pages.TopNavigation;

public class TopNavigationTest extends Base {
	
	TopNavigation topNavigation;
	LandingPage landingPage;
	
	public TopNavigationTest() {
		super();
	}
	
	@BeforeTest
	public void setup() throws Exception {
		initialize();
		topNavigation = new TopNavigation();
		landingPage = new LandingPage();
	}
	
	@Test(priority=0)
	public void validateLogoAndSlogan() {
		landingPage.validateTitle();
		topNavigation.validateLogoAndSlogan();
	}
	
	@Test(priority=1)
	public void clickOnLogo() {
		//Confirm logo redirects to landing page
		landingPage.validateTitle();
		topNavigation.clickLogo();
		landingPage.validateTitle();
	}
	
	@Test(priority=2)
	public void navigation() {
		topNavigation.navigateToCart();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
