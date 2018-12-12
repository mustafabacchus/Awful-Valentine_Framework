package com.qa.was.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.was.base.Base;

public class SearchResultsPage extends Base {
	
	@FindBy(className="entry")
	WebElement searchHeading;
	
	public SearchResultsPage() {
		PageFactory.initElements(driver, this);
	}
	
	public String getActualTitle() {
		return driver.getTitle();
	}
	
	public int getNumberOfResults() {
		return driver.findElements(By.className("main-product")).size();
	}
	
	public WebElement searchHeading() {
		return searchHeading.findElement(By.tagName("h1"));
	}
	
	public String getActualResultsResponse() {
		return searchHeading.findElement(By.tagName("h1")).getText();
	}
	
	public String getExpectedEmptyResultResponse() {
		return "No Results Found";
	}
}
