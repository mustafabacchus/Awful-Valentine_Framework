package com.qa.testcases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.CartPage;
import com.qa.was.pages.CartSidebar;
import com.qa.was.pages.InfoPage;
import com.qa.was.pages.LandingPage;
import com.qa.was.pages.TopNavigation;
import com.qa.was.util.Util;

public class LandingPageTest extends Base {
	
	LandingPage landingPage;
	TopNavigation topNavigation;
	CartSidebar cartSidebar;
	CartPage cartPage;
	InfoPage infoPage;
	Util util;

	
	public LandingPageTest() {
		super();
	}
	
	@BeforeTest
	public void setup() throws Exception {
		initialize();
		landingPage = new LandingPage();
		topNavigation = new TopNavigation();
		cartSidebar = new CartSidebar();
		cartPage = new CartPage();
		infoPage = new InfoPage();
		util = new Util();
	}
	
	
	@Test(priority=0)
	public void validateTitle() {
		landingPage.validateTitle();
	}
	
	
	@Test(priority=1)
	public void hoverOverRecentProducts() {
		driver.navigate().refresh();
		List<WebElement>recentProducts = landingPage.getRecentProducts();
		util.displayTestCaseTitle("Hover Over Recent Products");
		
		boolean working = true;
		//Hover over each product
		for (WebElement product: recentProducts) {
			String item = landingPage.getProductTitle(product);
			try {
				//Description is NOT displayed pre hover
				WebElement description = product.findElement(By.className("boutique_description_border"));
				assertFalse(description.isDisplayed());
				//Hover over product
				act.moveToElement(product.findElement(By.cssSelector(".et-links.clearfix"))).build().perform();
				//Description is displayed post hover
				assertTrue(description.isDisplayed());
				System.out.println(item + ": Pass");
			} catch (Exception e) {
				working = false;
				System.out.println(item + ": Fail");
			}
		}
		assertTrue(working);
	}
	
	
	@Test(priority=2)
	public void addRecentProductToCart() {
		driver.navigate().refresh();
		util.displayTestCaseTitle("Add Recent Products To Cart");
		
		boolean working = true;
		int numOfItems = landingPage.getRecentProducts().size();
		for (int i = 0; i < numOfItems; i++) {
			List<WebElement> recentProducts = landingPage.getRecentProducts();
			WebElement product = recentProducts.get(i);
			String item = landingPage.getProductTitle(product);
			
			landingPage.addItemToCart(product);
			try {	
				//Confirm redirect to cart
				cartPage.validateTitle();
				//Employ the continue shopping option from cart
				cartPage.clickContinueShopping();
				//Confirm redirect to landing page
				landingPage.validateTitle();
				
				System.out.println(item + ": Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println(item + ": Fail");
				working = false;
				driver.navigate().to(prop.getProperty("url"));
			}
		}
		assertTrue(working);
	}

	
	@Test(priority=3)
	public void navigateSlidesWithButtons() {
		driver.navigate().refresh();
		List<WebElement> controlButtons = landingPage.getControlButtons();
		util.displayTestCaseTitle("Slide Navigation: Control Buttons");
				
		boolean working = true;
		int numButtons = controlButtons.size();
		//Check each control button
		for(int i = 0; i < numButtons; i ++) {
			controlButtons.get(i).click();
			//Get slide related to control button
			String activeSlide = controlButtons.get(i).getAttribute("class");
			//Ensure related slide is active
			try {
				assertTrue(landingPage.slideIsActive(activeSlide));
				System.out.println("Control " + Integer.toString(i+1) + ": Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Control " + Integer.toString(i+1) + ": Fail ");
				working = false;
			}
		}
		assertTrue(working);
	}


	@Test(priority=4)
	public void navigateSlidesWithRightArrow() {
		driver.navigate().refresh();
		List<WebElement> controlButtons = landingPage.getControlButtons();
		util.displayTestCaseTitle("Slide Navigation: Right Arrow");
		//Start with first slide active
		controlButtons.get(0).click();
		
		boolean working = true;
		int numSlides = controlButtons.size();
		for(int i = 0; i < numSlides; i ++) {
			String activeSlide = controlButtons.get(i).getAttribute("class");
			//Ensure slide is active
			try {
				assertTrue(landingPage.slideIsActive(activeSlide));
				System.out.println("Slide " + Integer.toString(i+1) + ": Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Slide " + Integer.toString(i+1) + ": Fail ");
				working = false;
			}
			landingPage.clickRightArrow();
		}
		
		//Test loop right; last slide loops to first slide
		try {
			controlButtons.get(controlButtons.size()-1).click();
			landingPage.clickRightArrow();
			String activeSlide = controlButtons.get(0).getAttribute("class");
			assertTrue(landingPage.slideIsActive(activeSlide));
			System.out.println("Loop Right: Pass");
		} catch (java.lang.AssertionError e) {
			System.out.println("Loop Right: Fail");
			working = false;
		}
		
		assertTrue(working);
	}

	
	@Test(priority=5)
	public void navigateSlidesWithLeftArrow() {
		driver.navigate().refresh();
		List<WebElement> controlButtons = landingPage.getControlButtons();
		util.displayTestCaseTitle("Slide Navigation: Left Arrow");
		//Start with last slide active
		controlButtons.get(controlButtons.size()-1).click();
		
		boolean working = true;
		int numSlides = controlButtons.size();
		for (int i = numSlides-1; i >= 0; i--) {
			String activeSlide = controlButtons.get(i).getAttribute("class");
			//Ensure slide is active
			try {
				assertTrue(landingPage.slideIsActive(activeSlide));
				System.out.println("Slide " + Integer.toString(i+1) + ": Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Slide " + Integer.toString(i+1) + ": Fail ");
				working = false;
			}
			landingPage.clickLeftArrow();
		}
		
		//Test loop left; first slide loops to last slide
		try {
			controlButtons.get(0).click();
			landingPage.clickLeftArrow();
			String activeSlide = controlButtons.get(controlButtons.size()-1).getAttribute("class");
			assertTrue(landingPage.slideIsActive(activeSlide));
			System.out.println("Loop Left: Pass");
		} catch(java.lang.AssertionError e) {
			System.out.println("Loop Left: Fail");
			working = false;
		}	
		
		assertTrue(working);
	}
	
	
	@Test(priority = 6)
	public void clickMoreInfoOnSlides() {
		driver.navigate().refresh();
		util.displayTestCaseTitle("Click More Info on Slides");
		
		boolean working = true;
		int numSlides = landingPage.getControlButtons().size();
		//Loop through each slide
		for (int i = 0; i < numSlides; i++) {
			//Prevent stale reference by refreshing DOM
			List<WebElement> controlButtons = landingPage.getControlButtons();
			String productName = "";
			try {
				//Move to slide
				controlButtons.get(i).click();
				productName = landingPage.getFeaturedTitle(i+1);
				
				//Navigate to product info
				landingPage.clickReadMore(i+1);
				infoPage.validateTitle(productName);
				
				System.out.println("Slide " + Integer.toString(i+1) + " [" + productName + "]: Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("Slide " + Integer.toString(i+1) + " [" + productName + "]: Fail");
				working = false;
			}
			//Go to home page
			driver.navigate().back();
		}
		assertTrue(working);
	}
	

	@AfterTest
	public void tearDown() {
		System.out.println();
		driver.quit();
	}
}
