package com.qa.was.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qa.was.base.Base;

public class CartPage extends Base {
	
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
	
	public List<WebElement> getItemInCart() {
		return driver.findElements(By.tagName("tr"));
	}
}
