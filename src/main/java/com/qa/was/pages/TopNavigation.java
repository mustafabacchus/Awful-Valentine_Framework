package com.qa.was.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.was.base.Base;

public class TopNavigation extends Base {
	
	@FindBy(id="logo-area")
	WebElement logo;
	
	@FindBy(id="search-form")
	WebElement searchForm;
	
	public TopNavigation() {
		PageFactory.initElements(driver, this);
	}
	
	
	public WebElement logo() {
		return logo.findElement(By.id("logo"));
	}
	
	public void clickLogo() {
		//Goes to landing page
		logo.findElement(By.id("logo")).click();
	}
	
	public WebElement slogan() {
		return logo.findElement(By.id("slogan"));
	}
	
	public String getActualSlogan() {
		return logo.findElement(By.id("slogan")).getText();
	}
	
	public String getExpectedSlogan() {
		return "Please your loved ones with a card this year!";
	}
	
	public void validateLogoAndSlogan() {
		assertTrue(logo().isDisplayed());
		assertTrue(slogan().isDisplayed());
		assertEquals(getActualSlogan(), getExpectedSlogan());
	}
	
	
	//NAVIGATION
	public void navigateToCart() {
		driver.findElement(By.cssSelector(".page_item page-item-6.current_page_item")).click();
	}
	
	
	//SERACH
	
	public void querySearchInput(String query) {
		searchForm.findElement(By.id("searchinput")).sendKeys(query);
	}
	
	public void clearSearchInput() {
		searchForm.findElement(By.id("searchinput")).clear();
	}
	
	public void submitSearch() {
		searchForm.findElement(By.id("searchsubmit")).click();;
	}
}
