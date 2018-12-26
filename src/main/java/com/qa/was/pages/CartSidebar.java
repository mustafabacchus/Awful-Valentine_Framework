package com.qa.was.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.was.base.Base;

public class CartSidebar extends Base {
	
	public void clickViewCart() {
		try {
		//Empty cart
			driver.findElement(By.className("Cart66WidgetViewCartCheckoutEmpty")).
				findElement(By.className("Cart66WidgetViewCart")).click();
		} catch (ElementNotVisibleException e) {
		//Full cart
			driver.findElement(By.className("Cart66WidgetViewCartCheckoutItems")).
				findElement(By.className("Cart66WidgetViewCart")).click();
		}
	}
	
	public int getNumItemsInCart() {
		try {
			WebElement brief = driver.findElement(By.id("Cart66AdvancedSidebarAjax")).findElement(By.id("Cart66WidgetCartEmptyAdvanced"));
			return Integer.valueOf(brief.getText().split(" ")[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
}
