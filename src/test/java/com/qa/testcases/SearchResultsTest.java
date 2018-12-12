package com.qa.testcases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.was.base.Base;
import com.qa.was.pages.SearchResultsPage;
import com.qa.was.pages.TopNavigation;
import com.qa.was.util.Util;

public class SearchResultsTest extends Base {
	
	SearchResultsPage searchResultsPage;
	TopNavigation topNavigation;
	Util util;
	
	public SearchResultsTest() {
		super();
	}
	
	@BeforeTest
	public void setup() throws Exception {
		initialize();
		searchResultsPage = new SearchResultsPage();
		topNavigation = new TopNavigation();
		util = new Util();
	}
	
	@Test(priority=0) 
	public void search() throws IOException {
		util.displayTestCaseTitle("Query a Search");
		
		boolean working = true;
		//Read data from excel file
		List<String> data = util.readExcelData("//src//main//java//com//qa//was//testdata//SearchQueries.xlsx", 0, 0);
		for (String query: data) {
			//Input query
			topNavigation.querySearchInput(query);
			topNavigation.submitSearch();
			
			try {
				//Confirm inputted query is executed
				assertTrue(searchResultsPage.getActualTitle().toLowerCase().contains(query));
				//Get number of results returned
				System.out.println("'" + query + "'" + ": (" + searchResultsPage.getNumberOfResults() + " results)" + " Pass");
			} catch (java.lang.AssertionError e) {
				System.out.println("'" + query + "'" + ": Fail");
				working = false;
			}
		}
		assertTrue(working);
	}
	
	@Test(priority=2)
	public void emptySearch() {
		//Clear search input
		topNavigation.clearSearchInput();
		topNavigation.submitSearch();
		
		//Ensure correct error is shown
		assertTrue(searchResultsPage.searchHeading().isDisplayed());
		//Ensure query is executed as null
		assertTrue(searchResultsPage.getActualTitle().toLowerCase().contains("search"));
		assertTrue(searchResultsPage.getActualResultsResponse().
				equalsIgnoreCase(searchResultsPage.getExpectedEmptyResultResponse()));
	}
	
	@AfterTest
	public void tearDown() {
		System.out.println();
		driver.quit();
	}
	
	
}
