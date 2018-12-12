package com.qa.was.pages;

import static org.testng.Assert.assertTrue;

import com.qa.was.base.Base;

public class InfoPage extends Base {
	
	public String getActualTitle() {
		return driver.getTitle();
	}
	
	public void validateTitle(String productName) {
		assertTrue(getActualTitle().toLowerCase().contains(productName.toLowerCase()));
	}
}
