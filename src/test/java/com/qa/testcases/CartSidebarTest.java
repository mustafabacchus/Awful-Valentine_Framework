package com.qa.testcases;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.CartPage;
import com.qa.was.pages.CartSidebar;
import com.qa.was.pages.LandingPage;
import com.qa.was.util.Util;

public class CartSidebarTest extends Base {
	
	CartPage cartPage;
	LandingPage landingPage;
	CartSidebar cartSidebar;
	Util util;
	
	public CartSidebarTest() {
		super();
	}
	
	
	@BeforeMethod
	public void setup() throws Exception {
		initialize();
		cartPage = new CartPage();
		landingPage = new LandingPage();
		cartSidebar = new CartSidebar();
		util = new Util();
	}
	
	
	@Test(priority=0)
	public void cartSyncWithSidebar() {
		//Add items to the cart
		for (int i = 0; i < landingPage.getSpecialOffers().size(); i++) {
			List<WebElement> products = landingPage.getSpecialOffers();
			landingPage.addItemToCart(products.get(i));
			
			boolean ok = true;
			try {
				cartPage.validateTitle();
				ok = true;
			} catch (java.lang.AssertionError e) {
				driver.navigate().to(prop.getProperty("url"));
				ok = false;
			}
			//Check sync
			if (ok) {
				assertEquals(cartPage.getItemsInCart().size(), cartSidebar.getNumItemsInCart());
				cartPage.clickContinueShopping();
			}
		}
		
		//Move to cart page
		cartSidebar.clickViewCart();
		//Delete each item from cart
		int total = cartPage.getItemsInCart().size();
		for (int i = 0; i < total; i++) {
			List<WebElement>items = cartPage.getItemsInCart();
			cartPage.clickTrashIcon(items.get(0));
			//Check sync
			assertEquals(items.size()-1, cartSidebar.getNumItemsInCart());
		}
		
	}
	
	
	@AfterMethod
	public void tearDown() {
		System.out.println();
		driver.quit();
	}
}