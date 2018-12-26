package com.qa.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.CartPage;
import com.qa.was.pages.CartSidebar;
import com.qa.was.pages.LandingPage;
import com.qa.was.util.Util;

public class CartPageTest extends Base {
	
	CartPage cartPage;
	LandingPage landingPage;
	CartSidebar cartSidebar;
	Util util;
	
	public CartPageTest() {
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
	public void checkCartQTY() {
		util.displayTestCaseTitle("Item Populates In Cart With Quantity Of 1");
		int totalOffers = landingPage.getSpecialOffers().size();
		
		//Populate cart
		cartPage.populateCartWithSpecialOffers();
		cartSidebar.clickViewCart();
		
		//Proceed if there are items in the cart
		if (cartSidebar.getNumItemsInCart() != 0) {
			
			//Report items populated
			List<WebElement> items = cartPage.getItemsInCart();
			System.out.println(Integer.toString(items.size()) + "/" + Integer.toString(totalOffers) 
				+ " products populated." );
			
			//Check the quantity of each item
			boolean working = true;
			for (WebElement item: items) {
				try {
					assertEquals(cartPage.getQuantity(item), 1);
					System.out.println(cartPage.getItemName(item) + ": Pass");
				} catch (java.lang.AssertionError e) {
					System.out.println(cartPage.getItemName(item) + ": Fail");
					working = false;
				}
			}
			assertTrue(working);
			
		} else {
			System.out.println("No products populated.");
			assertTrue(false);
		}
	}
	
	
	@Test(priority=1)
	public void updateCart() {
		util.displayTestCaseTitle("Update Item Quantity In Cart");
		int totalOffers = landingPage.getSpecialOffers().size();
		
		//Populate cart
		cartPage.populateCartWithSpecialOffers();
		cartSidebar.clickViewCart();
		
		if (cartSidebar.getNumItemsInCart() != 0) {
			
			//Report items populated
			List<WebElement> items = cartPage.getItemsInCart();
			System.out.println(Integer.toString(items.size()) + "/" + Integer.toString(totalOffers) 
				+ " products populated." );
			
			int count = 0;
			int[] qtys = new int[items.size()];
			//Change the quantity of items
			for(WebElement item: items) {
				qtys[count] = count + 5;
				cartPage.inputQuantity(item, qtys[count]);
				count++;
			}
			cartPage.clickUpdateTotal();
			
			//Check if quantity has been updated
			items = cartPage.getItemsInCart();
			count = 0;
			boolean working = true;
			for (WebElement item: items) {
				try {
					int qty = qtys[count];
					assertEquals(cartPage.getQuantity(item), qty);
					System.out.println(cartPage.getItemName(item) + ": Pass");
				} catch (java.lang.AssertionError e) {
					System.out.println(cartPage.getItemName(item) + ": Fail");
					working = false;
				}
				count++;
			}
			assertTrue(working);
			
		} else {
			System.out.println("No products populated.");
			assertTrue(false);
		}
	}
	
	
	@Test(priority=2)
	public void deleteItem()  {
		util.displayTestCaseTitle("Delete Items From Cart");
		int totalOffers = landingPage.getSpecialOffers().size();
		
		//Populate cart
		cartPage.populateCartWithSpecialOffers();
		cartSidebar.clickViewCart();
		
		if (cartSidebar.getNumItemsInCart() != 0) {
			
			//Report items populated
			List<WebElement> items = cartPage.getItemsInCart();
			System.out.println(Integer.toString(items.size()) + "/" + Integer.toString(totalOffers) 
				+ " products populated." );
			
			//Delete each item
			int total = items.size();
			for (int i = 0; i < total; i++) {
				items = cartPage.getItemsInCart();
				cartPage.clickTrashIcon(items.get(0));
			}
			
			boolean working = true;
			//Validate empty cart
			try {
				assertEquals(cartSidebar.getNumItemsInCart(), 0);
				System.out.println("Items Deleted: Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Items Deleted: Fail");
				working = false;
			}
			//Validate empty cart message
			try {
				cartPage.validateEmptyCartMessage();
				System.out.println("Message Validation: Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Message Validation: Fail");
				working = false;
			}
			assertTrue(working);
			
		} else {
			System.out.println("No products populated.");
			assertTrue(false);
		}
	}
	
	
	@AfterMethod
	public void tearDown() {
		System.out.println();
		driver.quit();
	}
	
	
}
