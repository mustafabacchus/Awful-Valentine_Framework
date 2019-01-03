package com.qa.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
	public void sidebarSyncWithCart() {
		util.displayTestCaseTitle("Sidebar Sync With Cart");
		boolean itemsInCart = false;
		
		//Add items to the cart
		for (int i = 0; i < landingPage.getSpecialOffers().size(); i++) {
			List<WebElement> products = landingPage.getSpecialOffers();
			landingPage.addItemToCart(products.get(i));
			
			boolean ok = true;
			int cartItems = 0;
			try {
				//Confirm item is added to the cart
				cartPage.validateTitle();
				ok = true;
				itemsInCart = true;
				List<WebElement> items = cartPage.getItemsInCart();
				
				//Display added item
				System.out.println(cartPage.getItemName(items.get(items.size()-1)) + 
						" +" + Integer.toString(cartPage.getQuantity(items.get(items.size()-1))));
				
				//Update quantity from last added item
				for (WebElement item: items) {
					cartItems += cartPage.getQuantity(item);
				}
				
				cartPage.clickContinueShopping();
			} catch (java.lang.AssertionError e) {
				ok = false;
				driver.navigate().to(prop.getProperty("url"));
			}
			
			if (ok) {
				//Check sync
				assertEquals(cartSidebar.getNumItemsInCart(), cartItems);
				//Display results
				System.out.println("Cart Sidebar: " + Integer.toString(cartSidebar.getNumItemsInCart()) + 
						"; Items In Cart: " + Integer.toString(cartItems));
				System.out.println();
			}
		}
		
		
		//Remove items from the cart
		cartSidebar.clickViewCart();
		if (itemsInCart) {
			for (int i = cartPage.getItemsInCart().size()-1; i >= 0; i--) {
				List<WebElement> items = cartPage.getItemsInCart();
				
				//Display removed item
				System.out.println(cartPage.getItemName(items.get(0)) + 
						" -" + Integer.toString(cartPage.getQuantity(items.get(0))));
				//Remove item
				cartPage.clickTrashIcon(items.get(0));
				
				//Update quantity from removed item
				int cartItems = 0;
				if (i > 0) {
					items = cartPage.getItemsInCart();
					for (WebElement item: items) {
						cartItems += cartPage.getQuantity(item);
					}
				}
				
				//Check sync
				if (i > 0) {
					assertEquals(cartSidebar.getNumItemsInCart(), cartItems);
					
					//Display results
					System.out.println("Cart Sidebar: " + Integer.toString(cartSidebar.getNumItemsInCart()) + 
							"; Items In Cart: " + Integer.toString(cartItems));
					System.out.println();
				} else {
					assertEquals(cartSidebar.getNumItemsInCart(), 0);
					cartPage.validateEmptyCartMessage();
					
					//Display results
					System.out.println("Cart Sidebar: " + Integer.toString(cartSidebar.getNumItemsInCart()) +
							"; Empty cart message validated.");
				}
			}
		} else {
			System.out.println("No items were added to the cart.");
			assertEquals(cartSidebar.getNumItemsInCart(), 0);
			cartPage.validateEmptyCartMessage();
			
			System.out.println("Cart Sidebar: " + Integer.toString(cartSidebar.getNumItemsInCart()) +
					"; Empty cart message validated.");
		}
	}
	
	
	@AfterMethod
	public void tearDown() {
		System.out.println();
		driver.quit();
	}
}
