package com.qa.testcases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;

public class readPropertyFile extends Base {

	public readPropertyFile() {
		super();
	}
	
	@BeforeTest
	public void setup() throws Exception {
		initialize();
	}
	
	@Test
	public void read() {
		System.out.println(driver.getTitle());
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("browser"));
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
