package com.qa.was.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.qa.was.base.Base;

public class CartPage extends Base {
	
	LandingPage landingPage = new LandingPage();
	CartSidebar cartSidebar = new CartSidebar();
	
	
	public String getActualTitle() {
		return driver.getTitle();
	}
	
	public String getExpectedTitle() {
		return "Cart | Valentine's Day Cards";
	}
	
	public void clickContinueShopping() {
		driver.findElement(By.id("continueShopping")).click();
	}
	
	public void validateTitle() {
		assertEquals(getActualTitle(), getExpectedTitle());
	}
	
	
	public void populateCartWithSpecialOffers() {
		for (int i = 0; i < landingPage.getSpecialOffers().size(); i++) {
			List<WebElement> products = landingPage.getSpecialOffers();
			WebElement product = products.get(i);
			landingPage.addItemToCart(product);
			try {
				validateTitle();
				clickContinueShopping();
			} catch (java.lang.AssertionError e) {
				driver.navigate().to(prop.getProperty("url"));
			}
		}
	}
	
	public List<WebElement> getItemsInCart() {
		List<WebElement> items = null;
		while (true) {
			try {
				items = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
				items.subList(0, items.size()-4);
				break;
			} catch(IllegalArgumentException e) {
				continue;
			}
		}
		return items.subList(0, items.size()-4);
	}
	
	public void validateEmptyCartMessage() {
		WebElement msg = driver.findElement(By.id("emptyCartMsg")).findElement(By.tagName("h3"));
		assertTrue(msg.isDisplayed());
		assertEquals(msg.getText(), "Your Cart Is Empty");
	}
	
	public String getItemName(WebElement item) {
		String product = item.getText().split("\\$")[0];
		return product.substring(0, product.length()-1);
	}
	
	public double getItemPrice(WebElement item) {
		String price = item.getText().split("\\$")[1];
		price = price.substring(0, price.length()-1);
		return Double.valueOf(price);
	}
	
	public double getItemTotal(WebElement itemInCart) {
		String total = itemInCart.getText().split("\\$")[2];
		return Double.valueOf(total);
	}
	
	public void inputQuantity(WebElement item, int value) {
		WebElement qtyInput = item.findElement(By.className("itemQuantity"));
		qtyInput.clear();
		qtyInput.sendKeys(String.valueOf(value));
	}
	
	public int getQuantity(WebElement item) {
		String qty = item.findElement(By.className("itemQuantity")).getAttribute("value");
		return Integer.valueOf(qty);
	}
	
	public void clickUpdateTotal() {
		driver.findElement(By.cssSelector(".Cart66UpdateTotalButton.Cart66ButtonSecondary")).click();
	}
	
	public void clickTrashIcon(WebElement item) {
		item.findElement(By.tagName("a")).click();
	}
}
