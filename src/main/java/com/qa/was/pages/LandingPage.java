package com.qa.was.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.was.base.Base;

public class LandingPage extends Base {
	
	
	//FEATURED SLIDE ELEMENTS 
	
	@FindBy(id="left-arrow")
	WebElement leftArrow;
	
	@FindBy(id="right-arrow")
	WebElement rightArrow;
	
	@FindBy(id="controllers")
	WebElement controller;

	
	public LandingPage() {
		PageFactory.initElements(driver, this);
	}
	
	private String getActualTitle() {
		return driver.getTitle();
	}
	
	private String getExpectedTitle() {
		return "Valentine's Day Cards | Please your loved ones with a card this year!";
	}
	
	public void validateTitle() {
		assertEquals(getActualTitle(), getExpectedTitle());
	}
	
	
	//OFFERS
	
	//Special Offers
	public List<WebElement> getSpecialOffers() {
		return driver.findElements(By.className("special-item"));
	}
	
	//Recent Products
	public List<WebElement> getRecentProducts() {
		return driver.findElements(By.className("main-product"));
	}
	
	//Get title of a product
	public String getProductTitle(WebElement product) {
		return product.findElement(By.className("title")).findElement(By.tagName("a")).getText();
	}
	
	//Get product ID
	public String getProductID(WebElement product) {
		return product.findElement(By.cssSelector(".Cart66ButtonPrimary.purAddToCart.ajax-button")).getAttribute("id").split("t")[1];
	}
	
	//Open popup description
	public void openInfoPopup(WebElement product) {
		product.findElement(By.cssSelector(".et-links.clearfix")).findElement(By.cssSelector(".add-to-cart.et-shop-item")).click();
	}
	
	//Add to cart from pop up
	public void addProductToCartFromPopup(WebElement product, String id) {
		String buttonFormXpath = "//*[@id=\'cartButtonForm" + id + "\']";
		String addToCartBtnXpath = "//*[@id=\'addToCart" + id + "\']";
		product.findElement(By.xpath("//*[@id=\"fancybox-overlay\"]")).
			findElement(By.xpath(buttonFormXpath)).findElement(By.xpath(addToCartBtnXpath)).click();
	}
	
	//Add item to cart
	public void addItemToCart(WebElement product) {
		String id = getProductID(product);
		openInfoPopup(product);
		addProductToCartFromPopup(product, id);
	}
	
	
	
	//FEATURED SLIDES
	
	//Get featured title
	public String getFeaturedTitle(int slideNum) {
		String xpath = "//*[@id=\'slides\']/div[" + Integer.toString(slideNum) + "]/div[2]/h2/a";
		return driver.findElement(By.xpath(xpath)).getText();
	}
	
	//Control Buttons
	public List<WebElement> getControlButtons() {
		return controller.findElements(By.tagName("a"));
	}
	
	//Click left arrow
	public void clickLeftArrow() {
		leftArrow.click();
	}
	
	//Click right arrow
	public void clickRightArrow() {
		rightArrow.click();
	}
	
	public boolean slideIsActive(String classOfSlide) {
		return classOfSlide.equals("activeSlide");
	}
	
	public void clickReadMore(int slideNum) {
		String xpath = "//*[@id=\'slides\']/div[" + Integer.toString(slideNum) + "]/div[2]/a/span";
		driver.findElement(By.xpath(xpath)).click();
	}
}
