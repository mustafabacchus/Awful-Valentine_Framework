package com.qa.was.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Util {
	
	public static long PAGE_LOAD_TIMEOUT = 15;
	public static long IMPLICIT_WAIT = 15;
	public static long EXPLICIT_WAIT = 10;
	
	public List<String> readExcelData(String relativeFilePath, int sheetNum, int colNum) throws IOException {
		List<String> data = new ArrayList<String>();
		
		//Get relative path of file
		File file = new File(System.getProperty("user.dir") + relativeFilePath);
		FileInputStream fin = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fin);
		//Extract data from specified sheet
		XSSFSheet sheet = workbook.getSheetAt(sheetNum);
		Iterator<Row> rowIt = sheet.iterator();
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			//Store data form specified column
			data.add(row.getCell(colNum).toString());
		}
		workbook.close();
		fin.close();
		
		return data;
	}
	
	public static boolean validateLink(String href) throws IOException {
		boolean working = true;
		//Create url
		URL url = new URL(href);
		//Create a http connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		//Get responses
		int code = connection.getResponseCode();
		if (code != 200) {
			working = false;
		}
		String response = connection.getResponseMessage();
		//Display info
		System.out.println(href + ":");
		System.out.println(code + ": " + response);
		connection.disconnect();
		return working;
	}
	
	public void displayTestCaseTitle(String testCaseName) {
		System.out.println();
		System.out.println("TEST CASE: " + testCaseName + "\n" + "---------------------------------------------------");
	}
}

