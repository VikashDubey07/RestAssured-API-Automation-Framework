package com.qa.api.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

	private static String EXCEL_FILE_PATH = "./src/test/resources/TestData/ExcelTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] readData(String sheetName) throws InvalidFormatException {
		
		Object data[][] = null;

		try 
		{
			FileInputStream file = new FileInputStream(EXCEL_FILE_PATH); //FileInputStream is a Java class used to read data from a file as a stream of bytes.
			
			book = WorkbookFactory.create(file); // WorkbookFactory-> it supports both formats .xlsx and .xls . no need create XSSFWorkbook and HSSFWorkbook objects separately  
			
			sheet = book.getSheet(sheetName);

			int rowCount = sheet.getLastRowNum();    // get Number of Rows (excluding header that is 1st Row)
            int colCount = sheet.getRow(0).getLastCellNum();  //get Number of Columns in Excel file

			
			data = new Object[rowCount][colCount];

			for (int i = 0; i < rowCount; i++)
			{
				for (int j = 0; j < colCount; j++)
				{
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();// toString coz we need to convert excel object to String and getRow(i+1) means extracting data except 1st as its header row
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;

	}

}
