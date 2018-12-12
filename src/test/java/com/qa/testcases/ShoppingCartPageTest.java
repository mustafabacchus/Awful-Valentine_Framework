package com.qa.testcases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.CartPage;
import com.qa.was.pages.LandingPage;

public class ShoppingCartPageTest extends Base {
	
	CartPage cartPage;
	LandingPage landingPage;
	
	public ShoppingCartPageTest() {
		super();
	}
	
	@BeforeTest
	public void setup() throws Exception {
		initialize();
		cartPage = new CartPage();
		landingPage = new LandingPage();
	}

	@Test(priority=0)
	public void test() {
		landingPage.addItemToCart(landingPage.getRecentProducts().get(0));
		
	}
	
	
}
