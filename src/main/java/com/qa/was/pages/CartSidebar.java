package com.qa.was.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.was.base.Base;

public class CartSidebar extends Base {
	
	@FindBy(className="Cart66WidgetCartEmpty")
	WebElement briefStatement;
	
	public CartSidebar() {
		PageFactory.initElements(driver, this);
	}
	
	public int getItemsInCart() {
		String statement = briefStatement.getText().split("item")[0];
		return Integer.parseInt(statement.substring(statement.length()-2, statement.length()-1));
	}
}
